package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testInscriptionEmailDejaUtilise() {
        Client client = new Client();
         client.setEmail("test@mail.com");

        when(clientRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(client));

        ResponseEntity<?> response = authController.inscription(client);

         assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void inscriptionOk() {
        Client client = new Client();

        client.setEmail("nouveau@mail.com");
        client.setMotDePasse("motdepasse123");

         when(clientRepository.findByEmail("nouveau@mail.com")).thenReturn(Optional.empty());
        when(clientRepository.save(any())).thenReturn(client);

            ResponseEntity<?> response = authController.inscription(client);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testEmailIntrouvable() {

        when(clientRepository.findByEmail("inconnu@mail.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.connexion(
                java.util.Map.of("email", "inconnu@mail.com", "motDePasse", "1234"));

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testMdpNok() {


        Client client = new Client();

        client.setEmail("test@mail.com");
        client.setMotDePasse("$2a$10$wronghashvalue123456789012345678901234567890123456789");

        when(clientRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(client));

        ResponseEntity<?> response = authController.connexion(

                java.util.Map.of("email", "test@mail.com", "motDePasse", "mauvaismdp"));

        assertEquals(400, response.getStatusCode().value());
    }

}
