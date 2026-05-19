package com.mmotors.mmotors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerIntegrationTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {


        clientRepository.deleteAll();
    }

    private Client clientTest(String email) {

        Client c = new Client();
        c.setNom("Dupont");
        c.setPrenom("Jean");
        c.setEmail(email);
        c.setMotDePasse("motdepasse123");

        return c;


    }

    @Test
    void inscription_enregistreLeClientEnBase() {

        authController.inscription(clientTest("jean@test.com"));

        assertTrue(clientRepository.findByEmail("jean@test.com").isPresent());
    }

    @Test
    void inscription_emailDejaUtilise_retourne400() {

        authController.inscription(clientTest("jean@test.com"));

        ResponseEntity<?> response = authController.inscription(clientTest("jean@test.com"));

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void inscription_motDePasseEstHache() {
        authController.inscription(clientTest("jean@test.com"));

        Client enBase = clientRepository.findByEmail("jean@test.com").get();
        assertNotEquals("motdepasse123", enBase.getMotDePasse()); // mdp crypté
        assertTrue(enBase.getMotDePasse().startsWith("$2a$")); // Algo ici
    }

    @Test
    void connexion_identifiantsValides_retourneToken() {
        authController.inscription(clientTest("jean@test.com"));

        ResponseEntity<?> response = authController.connexion(
                Map.of("email", "jean@test.com", "motDePasse", "motdepasse123")

        );

        assertEquals(200, response.getStatusCode().value());
        Map<?, ?> data = (Map<?, ?>) response.getBody();
        assertNotNull(data.get("token")); //  JWT est généré
    }

    @Test
    void connexion_mauvaisMotDePasse_retourne400() {

        authController.inscription(clientTest("jean@test.com"));

        ResponseEntity<?> response = authController.connexion(
                Map.of("email", "jean@test.com", "motDePasse", "mauvaismdp")

        );



        assertEquals(400, response.getStatusCode().value());

    }



    @Test

    void connexion_emailInconnu_retourne400() {

        ResponseEntity<?> response = authController.connexion(

                Map.of("email", "inconnu@test.com", "motDePasse", "1234")

        );



        assertEquals(400, response.getStatusCode().value());

    }

}

