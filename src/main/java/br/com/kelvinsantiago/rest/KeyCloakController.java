package br.com.kelvinsantiago.rest;

import br.com.kelvinsantiago.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KeyCloakController {

    private final KeycloakService keycloakService;

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


}
