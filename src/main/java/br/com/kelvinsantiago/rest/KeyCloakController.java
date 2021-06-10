package br.com.kelvinsantiago.rest;

import br.com.kelvinsantiago.KeycloakAuthorizationService;
import br.com.kelvinsantiago.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeyCloakController {

    private final KeycloakService keycloakService;
    private final KeycloakAuthorizationService keycloakAuthorizationService;

    @GetMapping("/public/keycloak/realms/{realmName}/users/{uid}/email-info")
    private ResponseEntity<Object> getEmailById(@PathVariable String realmName, @PathVariable UUID uid) {
        return ResponseEntity.ok(keycloakService.findUserRepresentationById(realmName, uid));
    }

    @PutMapping("/public/keycloak/realms")
    private ResponseEntity<Object> createRealm(@RequestParam String realmName) {
        keycloakService.createRealm(realmName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/public/keycloak/realms/{realmName}/clients")
    private ResponseEntity<Object> createClient(@PathVariable String realmName) {
        return ResponseEntity.ok(keycloakService.createClient(realmName));
    }

    @PutMapping("/public/keycloak/realms/{realmName}/clients/scope")
    private ResponseEntity<Object> createClientScope(@PathVariable String realmName) {
        return ResponseEntity.ok(keycloakService.createClientScope(realmName));
    }

    @PutMapping("/public/keycloak/realms/{realmName}/users")
    private ResponseEntity<Object> createUser(@PathVariable String realmName) {
        return ResponseEntity.ok(keycloakService.createUser(realmName));
    }

    @PutMapping("/public/keycloak/realms/{realmName}/groups")
    private ResponseEntity<Object> createGroup(@PathVariable String realmName) {
        return ResponseEntity.ok(keycloakService.createGroup(realmName));
    }

    @PutMapping("/public/keycloak/realms/clients/{clientName}")
    private ResponseEntity<Object> createClientID(@PathVariable String clientName) {
        var id = UUID.randomUUID();
        return ResponseEntity.ok(keycloakAuthorizationService.createClientStructure(id.toString(), clientName));
    }

    @PutMapping("/public/keycloak/realms/users")
    private ResponseEntity<Object> createUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(keycloakAuthorizationService.createUserIfNotExist(username, email, password));
    }

    @PutMapping("/public/keycloak/realms/clients/{tenantId}/users/{username}/attributes")
    private ResponseEntity<Object> addTenantToUser(@PathVariable String tenantId, @PathVariable String username) {
        keycloakAuthorizationService.addClientToUser(tenantId, username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/public/keycloak/realms/clients/{tenantId}/users/{username}/roles/{role}")
    private ResponseEntity<Object> addRoleToUser(@PathVariable String tenantId, @PathVariable String username, @PathVariable KeycloakAuthorizationService.Roles role) {
        keycloakAuthorizationService.addRoleToUser(tenantId, role, username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
