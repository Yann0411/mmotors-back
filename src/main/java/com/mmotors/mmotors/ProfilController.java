package com.mmotors.mmotors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/profil")

public class ProfilController {


    private final ClientRepository clientRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public ProfilController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

        this.passwordEncoder = new BCryptPasswordEncoder();

    }


    // récupère nom, prénom, email du client connecté
    @GetMapping
    public ResponseEntity<?> getProfil(Authentication authentication) {

        String email = (String) authentication.getPrincipal();

        Client client = clientRepository.findByEmail(email).orElse(null);


        if (client == null) return ResponseEntity.notFound().build();

        Map<String, String> data = new HashMap<>();
        data.put("nom",    client.getNom());
        data.put("prenom", client.getPrenom());
        data.put("email",  client.getEmail());

        return ResponseEntity.ok(data);

    }

    // modifie nom et prénom
    @PutMapping
    public ResponseEntity<?> updateProfil(Authentication authentication,
                                          @RequestBody Map<String, String> body) {

        String email = (String) authentication.getPrincipal();

        String nom    = body.get("nom");
        String prenom = body.get("prenom");

        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {

            return ResponseEntity.badRequest().body("Nom et prénom sont obligatoires.");
        }


        clientRepository.updateProfil(email, nom.trim(), prenom.trim());
        return ResponseEntity.ok("Profil mis à jour");
    }

    // change le mot de passe
    @PutMapping("/mot-de-passe")
    public ResponseEntity<?> changerMotDePasse(Authentication authentication,
                                               @RequestBody Map<String, String> body) {

        String email = (String) authentication.getPrincipal();
        Client client = clientRepository.findByEmail(email).orElse(null);

        if (client == null) return ResponseEntity.notFound().build();



         String ancienMdp  = body.get("ancienMotDePasse");
        String nouveauMdp = body.get("nouveauMotDePasse");

         if (!passwordEncoder.matches(ancienMdp, client.getMotDePasse())) {
            return ResponseEntity.badRequest().body("Mot de passe actuel incorrect.");
        }

        // même règle que l'inscription
        if (!nouveauMdp.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$")) {
            return ResponseEntity.badRequest().body("Le mot de passe doit contenir au moins 8 caractères, une majuscule, un chiffre et un caractère spécial.");
        }

         clientRepository.updateMotDePasse(email, passwordEncoder.encode(nouveauMdp));
         return ResponseEntity.ok("Mot de passe modifié");
    }
}
