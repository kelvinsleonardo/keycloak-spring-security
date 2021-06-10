package br.com.kelvinsantiago.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KeycloakConfig {

    @Value("${keycloak.master.username}")
    private String masterUserName;
    @Value("${keycloak.master.password}")
    private String masterPassword;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm("master")
                .username(masterUserName)
                .password(masterPassword)
                .clientId("admin-cli")
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(100).build()
                ).build();
    }
}