package br.com.kelvinsantiago;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;

@Service
public class KeycloakService {

    private static final String SERVER_URL = "http://localhost:8080/auth";
    private static final String REALM = "abamais";
    private static final String CLIENT_ID = "abamais";
    private static final String CLIENT_SECRET = "020efc4c-0df4-440b-8df5-9df2d751b9e3";

    public UserRepresentation getEmailByUserId(UUID userId) {
        var keycloak = KeycloakBuilder.builder()
                .grantType(CLIENT_CREDENTIALS)
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();

        UsersResource usersResource = keycloak.realm(REALM).users();
        UserResource userResource = usersResource.get(userId.toString());
        return userResource.toRepresentation();
    }
}
