package br.com.kelvinsantiago.rest;

import br.com.kelvinsantiago.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HelloController {

    private final KeycloakService keycloakService;

    @GetMapping("hello")
    private ResponseEntity<Object> hello(Principal principal) {
        return ResponseEntity.ok("Hello world!");
    }

    @GetMapping("users/{uid}/email-info")
    private ResponseEntity<Object> getEmailById(@PathVariable UUID uid) {
        return ResponseEntity.ok(keycloakService.getEmailByUserId(uid));
    }


}
