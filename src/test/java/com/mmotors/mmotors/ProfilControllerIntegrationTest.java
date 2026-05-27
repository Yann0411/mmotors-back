package com.mmotors.mmotors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfilControllerIntegrationTest {

    @Autowired
    private ProfilController profilController;

    @Autowired
    private AuthController authController;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();

        Client c = new Client();
        c.setNom("Dupont");
        c.setPrenom("Jean");
        c.setEmail("jean@test.com");
            c.setMotDePasse("motdepasse123");
        authController.inscription(c);
    }

    private Authentication auth() {
        return new UsernamePasswordAuthenticationToken("jean@test.com", null, List.of());
    }

    @Test
    void getProfil_retourneInfosClient() {
        ResponseEntity<?> response = profilController.getProfil(auth());

        assertEquals(200, response.getStatusCode().value());
        Map<?, ?> data = (Map<?, ?>) response.getBody();
        assertEquals("Dupont", data.get("nom"));
        assertEquals("jean@test.com", data.get("email"));
    }

    @Test
    void updateProfil_modifieNomEtPrenom() {
        ResponseEntity<?> response = profilController.updateProfil(auth(),
                Map.of("nom", "Martin", "prenom", "Pierre"));

        assertEquals(200, response.getStatusCode().value());

        Client enBase = clientRepository.findByEmail("jean@test.com").get();
        assertEquals("Martin", enBase.getNom());
        assertEquals("Pierre", enBase.getPrenom());
    }

    @Test
    void updateProfil_nomVide_retourne400() {
          ResponseEntity<?> response = profilController.updateProfil(auth(),
                Map.of("nom", "", "prenom", "Pierre"));

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void changerMotDePasse_ancienMotDePasseIncorrect_retourne400() {
        ResponseEntity<?> response = profilController.changerMotDePasse(auth(),
                Map.of("ancienMotDePasse", "mauvaismdp", "nouveauMotDePasse", "NouveauMdp1!"));

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void changerMotDePasse_nouveauMotDePasseValide_retourne200() {
        ResponseEntity<?> response = profilController.changerMotDePasse(auth(),
                Map.of("ancienMotDePasse", "motdepasse123", "nouveauMotDePasse", "NouveauMdp1!"));

        assertEquals(200, response.getStatusCode().value());
    }
}
