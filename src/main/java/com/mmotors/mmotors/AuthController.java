package com.mmotors.mmotors;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS AUTH_CONTROLLER<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>=> J'injecte clientRepository jwtService et Bcrypt <=<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
         this.clientRepository = clientRepository;
          this.jwtService = jwtService;
         this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@Valid @RequestBody Client client) {
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS AUTH_CONTROLLER<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS AUTH_CONTROLLER /inscription<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
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
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS AUTH_CONTROLLER<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS AUTH_CONTROLLER /connexion<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>AUTH_CONTROLLER DECLENCHE<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("=>=>=>=>=>=>=>=>=>CONNEXION REÇU <=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("Email reçu : " + body.get("email"));
        System.out.println("mdp reçu : " + body.get("motDePasse"));

        String email = body.get("email");
        String motDePasse = body.get("motDePasse");

        Optional<Client> clientOpt = clientRepository.findByEmail(email);

        System.out.println("vérif email avec client repository: " + clientOpt);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email introuvable");
        }

        Client client = clientOpt.get();
         if (!passwordEncoder.matches(motDePasse, client.getMotDePasse())) {
            return ResponseEntity.badRequest().body("Mot de passe incorrect");
        }

        String token = jwtService.genererToken(client.getEmail());
        System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS AUTH_CONTROLLER /connexion<=<=<=<=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("Token Généré : " + token);
         Map<String, String> resultat = new HashMap<>();
        resultat.put("token", token);
        resultat.put("nom", client.getNom());
        return ResponseEntity.ok(resultat);
    }
}

