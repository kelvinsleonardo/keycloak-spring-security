package br.com.kelvinsantiago.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/")
public class HelloController {

    @GetMapping("hello")
    private ResponseEntity<Object> hello(Principal principal) {
        return ResponseEntity.ok("Hello world!");
    }
}
