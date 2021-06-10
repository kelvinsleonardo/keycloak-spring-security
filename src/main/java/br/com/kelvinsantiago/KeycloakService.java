package br.com.kelvinsantiago;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.singletonList;
import static java.util.List.of;
import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeycloakService {

    private final Keycloak keycloakMasterInstance;
    private final Faker faker;

    @Value("${keycloak.auth-server-url}")
    private static String authServerUrl;

    @Deprecated
    public UserRepresentation getEmailByUserId(UUID userId) {
        final var REALM = "abamais";
        final var CLIENT_ID = "abamais";
        final var CLIENT_SECRET = "020efc4c-0df4-440b-8df5-9df2d751b9e3";

        var keycloak = KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl(authServerUrl)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();

        UsersResource usersResource = keycloak.realm(REALM).users();
        UserResource userResource = usersResource.get(userId.toString());
        return userResource.toRepresentation();
    }

    public UserRepresentation findUserRepresentationById(String realmName, UUID userId) {
        var userResource = keycloakMasterInstance.realm(realmName).users();
        return userResource.get(userId.toString()).toRepresentation();
    }

    public void createRealm(String realmName) {
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(realmName.trim().toLowerCase());
        realm.setEnabled(TRUE);
        keycloakMasterInstance.realms().create(realm);
    }

    public Map<String, String> createClient(String realmName) {
        var clientId = String.format("%s-cli", realmName);
        var clientSecret = UUID.randomUUID().toString();
        ClientRepresentation client = new ClientRepresentation();
        client.setClientId(clientId);
        client.setEnabled(TRUE);
        client.setDirectAccessGrantsEnabled(TRUE);
        client.setSecret(clientSecret);
        client.setDefaultClientScopes(of(String.format("%s-scope", realmName.trim().toLowerCase())));
        keycloakMasterInstance.realm(realmName).clients().create(client);
        return Map.of(
                "clientId", clientId,
                "clientSecret", clientSecret);
    }

    public Map<String, String> createClientScope(String realmName) {
        var scopeName = String.format("%s-scope", realmName.trim().toLowerCase());
        var clientScopeRepresentation = new ClientScopeRepresentation();
        clientScopeRepresentation.setName(scopeName);
        clientScopeRepresentation.setProtocol("openid-connect");

        // Create mapper
        var protocolMapperRepresentation = new ProtocolMapperRepresentation();
        protocolMapperRepresentation.setName("authorization");
        protocolMapperRepresentation.setProtocol("openid-connect");
        protocolMapperRepresentation.setProtocolMapper("oidc-usermodel-realm-role-mapper");
        protocolMapperRepresentation.setConfig(
                Map.of(
                        "access.token.claim", "true",
                        "claim.name", "authorization",
                        "id.token.claim", "true",
                        "jsonType.label", "String",
                        "multivalued", "true",
                        "userinfo.token.claim", "true")
        );
        clientScopeRepresentation.setProtocolMappers(singletonList(protocolMapperRepresentation));

        keycloakMasterInstance.realm(realmName).clientScopes().create(clientScopeRepresentation);
        return Map.of("scope", scopeName);
    }

    public UserRepresentation createUser(String realmName) {

        // Get realm
        RealmResource realmResource = keycloakMasterInstance.realm(realmName);
        UsersResource userResource = realmResource.users();

        var userFaker = faker.name();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userFaker.username());
        user.setFirstName(userFaker.firstName());
        user.setLastName(userFaker.lastName());
        user.setEmailVerified(TRUE);
        user.setEnabled(TRUE);
        user.setEmail(faker.internet().emailAddress());

        try {
            var response = userResource.create(user);
            var userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            // Define password
            var credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue("123456");
            credentialRepresentation.setTemporary(FALSE);
            userResource.get(userId).resetPassword(credentialRepresentation);

            response.close();
            return userResource.get(userId).toRepresentation();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    public Map<String, String> createGroup(String realmName) {
        var groupName = String.format("%s-grp", faker.team().state().replaceAll("\\s+","").toLowerCase());
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(groupName);
        keycloakMasterInstance.realm(realmName).groups().add(groupRepresentation);
        return Map.of("groupName", groupName);
    }

}
