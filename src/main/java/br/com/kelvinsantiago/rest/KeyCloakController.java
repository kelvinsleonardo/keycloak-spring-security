package br.com.kelvinsantiago.rest;

import br.com.kelvinsantiago.KeycloakService;
import lombok.RequiredArgsConstructor;
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
public class KeyCloakController {

    private final KeycloakService keycloakService;

    @GetMapping("/public/users/{uid}/email-info")
    private ResponseEntity<Object> getEmailById(@PathVariable UUID uid) {
        return ResponseEntity.ok(keycloakService.getEmailByUserId(uid));
    }


}
