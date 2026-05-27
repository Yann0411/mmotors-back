package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void clientStockeNomEtPrenom() {

        Client client = new Client();
        client.setNom("Dupont");
          client.setPrenom("Jean");

        assertEquals("Dupont", client.getNom());
        assertEquals("Jean", client.getPrenom());
    }

    @Test
    void clientStockeEmail() {

        Client client = new Client();
         client.setEmail("jean@test.com");

        assertEquals("jean@test.com", client.getEmail());

    }

    @Test
    void clientStockeRole() {
        Client client = new Client();

        client.setRole("CLIENT");
        assertEquals("CLIENT", client.getRole());

        client.setRole("ADMIN");
        assertEquals("ADMIN", client.getRole());

    }

    @Test
    void clientStockeMotDePasse() {

        Client client = new Client();

          client.setMotDePasse("$2a$10$hashedpassword");

        assertEquals("$2a$10$hashedpassword", client.getMotDePasse());
    }
}
