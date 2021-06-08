package br.com.kelvinsantiago;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.idm.*;
import org.keycloak.representations.idm.authorization.*;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonList;
import static java.util.List.of;
import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakAuthorizationService {

    private static final String DEFAULT_REALM = "abamais";

    private final Keycloak keycloakMasterInstance;
    private final Faker faker;

    @Value("${keycloak.auth-server-url}")
    private static String authServerUrl;

    @SneakyThrows
    public Object createClient(String tenantId, String companyName) {
        var clientRepresentation = new ClientRepresentation();
        clientRepresentation.setId(tenantId);
        clientRepresentation.setClientId(tenantId);
        clientRepresentation.setName(companyName.toUpperCase());
        clientRepresentation.setAuthorizationServicesEnabled(TRUE);
        clientRepresentation.setServiceAccountsEnabled(TRUE);
        clientRepresentation.setAuthorizationSettings(new ResourceServerRepresentation());
        clientRepresentation.getAuthorizationSettings().setDecisionStrategy(DecisionStrategy.AFFIRMATIVE);

        /*=================================
         *   Create authorization scope
         *=================================*/
        var scopes = Arrays.stream(Actions.values()).map(e -> new ScopeRepresentation(e.getName())).collect(Collectors.toSet());

        /*=================================
         *   Create resource scope
         *=================================*/
        var resources = Arrays.stream(Features.values())
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


        return keycloakMasterInstance
                .realm(DEFAULT_REALM)
                .clients()
                .get(tenantId)
                .toRepresentation();

    }

    private String buildConfigOption(String... values) {
        StringBuilder builder = new StringBuilder();

        for (String value : values) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append("\"" + value + "\"");
        }

        return builder.insert(0, "[").append("]").toString();
    }

    public enum Features {
        USUARIO("USUARIO"),
        GRUPO("GRUPO"),
        PERMISSAO("PERMISSAO"),
        LOG("LOG"),
        EMPRESA("EMPRESA"),
        FILIAL("FILIAL"),
        FORNECEDOR("FORNECEDOR"),
        CARGO("CARGO"),
        FUNCIONARIO("FUNCIONARIO"),
        CLIENTE("CLIENTE"),
        EQUIPE("CLIENTES"),
        PROTOCOLO("PROTOCOLO"),
        DOMINIO("DOMINIO"),
        AVALIACAO("AVALIACAO"),
        FAMILIA("FAMILIA"),
        TIPO_AVALIACAO("TIPO_AVALIACAO"),
        TIPO_RESPOSTA("TIPO_RESPOSTA"),
        TIPO_REFORCO("TIPO_REFORCOO"),
        PARAMETROS_CLIENTE("PARAMETROS_CLIENTE"),
        PROGRAMA("PROGRAMA"),
        PROGRAMA_PAIS("PROGRAMA_PAIS"),
        PROGRAMA_ESCOLA("PROGRAMA_ESCOLA"),
        CURRICULUM("CURRICULUM"),
        COMPORTAMENTO("COMPORTAMENTO"),
        REFORCADOR("REFORCADOR"),
        INCIDENTE("INCIDENTE"),
        REGISTRO("REGISTRO"),
        TIPO_REGISTRO("TIPO_REGISTRO"),
        LOCAL_REGISTRO("LOCAL_REGISTRO"),
        SESSAO("SESSAO"),
        SESSAO_PAIS("SESSAO_PAIS"),
        SESSAO_ESCOLA("SESSAO_ESCOLA"),
        SUPERVISAO("SUPERVISAO"),
        GRUPO_RELATORIO("GRUPO_RELATORIO"),
        ASSERTIVIDADE_RELATORIO("ASSERTIVIDADE_RELATORIO"),
        SITUACAO_ALVOS_RELATORIO("SITUACAO_ALVOS_RELATORIO"),
        BAIRRO("BAIRRO"),
        CIDADE("CIDADE"),
        ENDERECO("ENDERECO"),
        ESTADO("ESTADO"),
        AGENDA("AGENDA"),
        SUPORTE("SUPORTE"),
        ESCALA("ESCALA"),
        PRONTUARIO("PRONTUARIO"),
        ANAMNESE("ANAMNESE"),
        HABILIDADES_BASICAS("HABILIDADES_BASICAS"),
        HABILIDADES_BASICAS_AREAS("HABILIDADES_BASICAS_AREAS"),
        HABILIDADES_BASICAS_PROGRAMAS("HABILIDADES_BASICAS_PROGRAMAS"),
        HABILIDADES_BASICAS_PROGRAMAS_ITENS("HABILIDADES_BASICAS_PROGRAMAS_ITENS");

        private String code;

        public String getCode() {
            return code;
        }

        public static Features of(String value) {
            return Arrays.stream(Features.values())
                    .filter(v -> value.equals(v.getCode()))
                    .findFirst()
                    .orElse(null);
        }

        Features(String code) {
            this.code = code;
        }

        public String getName() {
            return name();
        }
    }

    public enum Actions {
        EDITAR("EDITAR"),
        EXCLUIR("EXCLUIR"),
        INATIVAR("INATIVAR"),
        INSERIR("INSERIR"),
        VISUALIZAR("VISUALIZAR");

        private String code;

        public String getCode() {
            return code;
        }

        public static Actions of(String value) {
            return Arrays.stream(Actions.values())
                    .filter(v -> value.equals(v.getCode()))
                    .findFirst()
                    .orElse(null);
        }

        Actions(String code) {
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
            return Arrays.stream(Roles.values())
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
