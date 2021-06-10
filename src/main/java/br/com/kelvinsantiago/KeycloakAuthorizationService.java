package br.com.kelvinsantiago;

import br.com.kelvinsantiago.enums.Resources;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.kelvinsantiago.KeycloakAuthorizationService.Scopes.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakAuthorizationService {

    /**
     * Pendências
     * 1. Client                  OK
     * 2. Resource                OK
     * 3. Authorization Scope     OK
     * 4. Policies                OK
     * 5. Permissions             OK
     * 6. Usuário                 OK
     * 7. Tenant no usuario       OK
     * 8. Definir papel no tenant OK
     * <p>
     * select entidade,
     * json_agg(obj) as user_files
     * from (
     * SELECT p.entidade, json_build_object(
     * 'perfil', gu.nome,
     * 'podeAlterar', p.alterar,
     * 'podeVer', true,
     * 'podeDesativar', p.desativar,
     * 'podeInserir', p.inserir) perfis
     * FROM permissao p
     * INNER JOIN grupousuario gu
     * ON p.grupousuario_codigo = gu.codigo
     * ) as obj
     * where entidade like lower('%HABI%')
     * group by entidade
     */

    private static final String DEFAULT_REALM = "abamais";

    private final Keycloak keycloakMasterInstance;

    @SneakyThrows
    public ClientRepresentation createClientStructure(String tenantId, String companyName) {
        var clientRepresentation = new ClientRepresentation();
        clientRepresentation.setId(tenantId);
        clientRepresentation.setClientId(tenantId);
        clientRepresentation.setName(companyName.toUpperCase());
        clientRepresentation.setDescription("Cliente " + companyName);
        clientRepresentation.setAuthorizationServicesEnabled(TRUE);
        clientRepresentation.setServiceAccountsEnabled(TRUE);
        clientRepresentation.setAuthorizationSettings(new ResourceServerRepresentation());
        clientRepresentation.getAuthorizationSettings().setDecisionStrategy(DecisionStrategy.AFFIRMATIVE);

        /*=================================
         *   Create authorization scope
         *=================================*/
        var scopes = Arrays.stream(values()).map(e -> new ScopeRepresentation(e.getName())).collect(Collectors.toSet());

        /*=================================
         *   Create resource scope
         *=================================*/
        var resources = Arrays.stream(Resources.values())
                .map(e -> {
                            var resource = new ResourceRepresentation();
                            resource.setName("FUNC_" + e.getName());
                            resource.setDisplayName("FUNCIONALIDADE " + e.getName());
                            resource.setType(String.format("urn:%s:resources:%s", tenantId, "funcionalidade-usuario"));
                            resource.setScopes(scopes);
                            return resource;
                        }
                )
                .collect(Collectors.toList());
        clientRepresentation.getAuthorizationSettings().setResources(resources);

        /*=================================
         *   Save new client
         *=================================*/
        keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .clients()
                .create(clientRepresentation);

        var clientResource = keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .clients()
                .get(tenantId);

        /*=================================
         *   Create roles
         *=================================*/
        var roles = Arrays.stream(Roles.values()).map(r -> new RoleRepresentation(r.getName(), r.getName(), false)).collect(Collectors.toSet());
        roles.forEach(role -> {
            clientResource.roles().create(role);
        });

        /*=================================
         *   Create policy
         *=================================*/
        Arrays.stream(Roles.values())
                .forEach(r -> {
                    var policy = new PolicyRepresentation();
                    policy.setName("POLITICA_PERFIL_" + r.getName());
                    policy.setDecisionStrategy(DecisionStrategy.UNANIMOUS);
                    policy.setLogic(Logic.POSITIVE);
                    policy.setType("role");
                    policy.setConfig(Map.of("roles", String.format("[{\"id\":\"%s\"}]", clientResource.roles().get(r.getName()).toRepresentation().getId())));
                    clientResource.authorization().policies().create(policy);
                });

        /*=================================
         *   Create permissions
         *=================================*/
        var scopePermissionResource = clientResource.authorization().permissions().scope();
        var resourcesDat = clientResource.authorization().resources().resources();
        var scopesDB = clientResource.authorization().scopes().scopes();
        var policiesDB = clientResource.authorization().policies().policies();
        Arrays.stream(Resources.values()).forEach(r -> {
            r.getPermissions().forEach((key, value) -> {
                var scopePermission = new ScopePermissionRepresentation();
                var name = String.format("FUNCIONALIDADE_%s_PERMISSAO_%s", r.getName(), key);
                scopePermission.setName(name);
                scopePermission.setDescription(name);
                scopePermission.setDecisionStrategy(DecisionStrategy.UNANIMOUS);
                scopePermission.setType("scope");
                scopePermission.setResources(Set.of(
                        resourcesDat
                                .stream()
                                .filter(x -> x.getName().equals("FUNC_" + r.getName()))
                                .map(ResourceRepresentation::getId).findFirst().orElse(""))
                );

                // Searching scopes
                var scopeIds = value
                        .stream()
                        .map(x -> scopesDB.stream().filter(s -> s.getName().equals(x.getName())).map(ScopeRepresentation::getId).findFirst().orElse(""))
                        .collect(Collectors.toSet());
                scopePermission.setScopes(scopeIds);

                // Search policy by role
                var policy = Set.of(
                        policiesDB
                                .stream()
                                .filter(x -> x.getName().equals("POLITICA_PERFIL_" + key.getName()))
                                .map(AbstractPolicyRepresentation::getId)
                                .findFirst().orElse(""));
                scopePermission.setPolicies(policy);

                // Save
                scopePermissionResource.create(scopePermission);
            });
        });

        return keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .clients()
                .get(tenantId)
                .toRepresentation();

    }

    public UserRepresentation createUserIfNotExist(@NotNull String username, @NotNull String email, @NotNull String password) {

        RealmResource realmResource = keycloakMasterInstance.realm(DEFAULT_REALM);
        UsersResource userResource = realmResource.users();

        return userResource.search(username, true)
                .stream()
                .findFirst()
                .orElseGet(() -> {

                    UserRepresentation user = new UserRepresentation();
                    user.setUsername(username);
                    user.setEmailVerified(TRUE);
                    user.setEnabled(TRUE);
                    user.setEmail(email);
                    try {
                        var response = userResource.create(user);
                        var userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

                        // Define password
                        var credentialRepresentation = new CredentialRepresentation();
                        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                        credentialRepresentation.setValue(password);
                        credentialRepresentation.setTemporary(FALSE);
                        userResource.get(userId).resetPassword(credentialRepresentation);

                        response.close();
                        return userResource.get(userId).toRepresentation();
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                });


    }

    public void addRoleToUser(String tenantId, Roles role, String username) {
        var resourceUser = keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .users();

        var roleRepresentation = keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .clients()
                .get(tenantId)
                .roles()
                .get(role.getName()).toRepresentation();

        resourceUser
                .search(username, true)
                .stream()
                .findFirst()
                .ifPresentOrElse(u -> {
                    resourceUser
                            .get(u.getId())
                            .roles()
                            .clientLevel(tenantId)
                            .add(List.of(roleRepresentation));

                }, () -> {
                    throw new IllegalArgumentException("User not found");
                });

    }

    public void addClientToUser(String tenantId, String username) {
        keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .users()
                .search(username, true)
                .stream()
                .findFirst()
                .ifPresentOrElse(u -> {
                    if (Optional.ofNullable(u.getAttributes()).isEmpty())
                        u.setAttributes(new HashMap<>());

                    u.getAttributes().put("tenants", u.getAttributes().getOrDefault("tenants", new ArrayList<>()));
                    u.getAttributes().get("tenants").add(tenantId);
                    keycloakMasterInstance
                            .realm(DEFAULT_REALM)
                            .users()
                            .get(u.getId())
                            .update(u);
                }, () -> {
                    throw new IllegalArgumentException("User not found");
                });

    }

    public enum Scopes {
        EDITAR("EDITAR"),
        EXCLUIR("EXCLUIR"),
        INATIVAR("INATIVAR"),
        INSERIR("INSERIR"),
        VISUALIZAR("VISUALIZAR");

        private String code;

        public String getCode() {
            return code;
        }

        public static Scopes of(String value) {
            return Arrays.stream(values())
                    .filter(v -> value.equals(v.getCode()))
                    .findFirst()
                    .orElse(null);
        }

        Scopes(String code) {
            this.code = code;
        }

        public String getName() {
            return name();
        }
    }

    public enum Roles {
        USUARIO("USUARIO"),
        ADMINISTRADOR("ADMINISTRADOR"),
        TUTOR("TUTOR"),
        SUPERVISOR("SUPERVISOR"),
        PARCEIRO("PARCEIRO"),
        PROFESSOR("PROFESSOR"),
        AT("AT"),
        AT2("AT2");

        private String code;

        public String getCode() {
            return code;
        }

        public static Roles of(String value) {
            return Arrays.stream(values())
                    .filter(v -> value.equals(v.getCode()))
                    .findFirst()
                    .orElse(null);
        }

        Roles(String code) {
            this.code = code;
        }

        public String getName() {
            return name();
        }
    }
}
