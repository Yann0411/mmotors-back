package com.mmotors.mmotors;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
 import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientRepository clientRepository;
     private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(ClientRepository clientRepository, JwtService jwtService) {

        this.clientRepository = clientRepository;
        this.jwtService = jwtService;

        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/inscription")

    public ResponseEntity<?> inscription(@Valid @RequestBody Client client) {
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {

            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        client.setRole("CLIENT");
        clientRepository.save(client);
        return ResponseEntity.ok("Inscription réussie");
    }

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String motDePasse = body.get("motDePasse");

        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email introuvable");
        }

        Client client = clientOpt.get();
        if (!passwordEncoder.matches(motDePasse, client.getMotDePasse())) {
            return ResponseEntity.badRequest().body("Mot de passe incorrect");
        }

        String token = jwtService.genererToken(client.getEmail());
        return ResponseEntity.ok(Map.of("token", token, "nom", client.getNom()));
    }
}
