package com.mmotors.mmotors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DossierControllerIntegrationTest {



    @Autowired
    private DossierController dossierController;

    @Autowired
    private DossierRepository dossierRepository;

    @BeforeEach
    void setUp() {
        dossierRepository.deleteAll();
        connecterUtilisateur("jean@test.com");
    }

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    private void connecterUtilisateur(String email) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(email, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Dossier creerDossierEnBase(String email) {
        Dossier d = new Dossier();
        d.setClientEmail(email);
        d.setTypeOffre("ACHAT");
           d.setMessage("Message de test");
        d.setStatut("EN_ATTENTE");
        d.setDateDepot(LocalDate.now().toString());
        return dossierRepository.save(d);

    }

    @Test
    void deposerDossier_typeOffreInvalide_retourne400() {
        Dossier dossier = new Dossier();
        dossier.setTypeOffre("INVALIDE");
        dossier.setMessage("test");

        ResponseEntity<?> response = dossierController.deposerDossier(dossier);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void deposerDossier_dossierDejaEnAttente_retourne400() {
        creerDossierEnBase("jean@test.com");

        Dossier nouveau = new Dossier();
        nouveau.setTypeOffre("ACHAT");
          nouveau.setMessage("deuxieme dossier");

        ResponseEntity<?> response = dossierController.deposerDossier(nouveau);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void annulerDossier_inexistant_retourne404() {
        ResponseEntity<?> response = dossierController.annulerDossier(9999L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void annulerDossier_appartenantAAutreClient_retourne403() {
        Dossier dossier = creerDossierEnBase("autre@test.com");

        ResponseEntity<?> response = dossierController.annulerDossier(dossier.getId());

        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void annulerDossier_enAttente_supprimeLeDossier() {
        Dossier dossier = creerDossierEnBase("jean@test.com");

        ResponseEntity<?> response = dossierController.annulerDossier(dossier.getId());

        assertEquals(200, response.getStatusCode().value());
        assertFalse(dossierRepository.existsById(dossier.getId()));
    }

    @Test
    void modifierDossier_inexistant_retourne404() {
        ResponseEntity<?> response = dossierController.modifierDossier(9999L,
                Map.of("message", "nouveau message"));

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void modifierDossier_appartenantAAutreClient_retourne403() {
        Dossier dossier = creerDossierEnBase("autre@test.com");

        ResponseEntity<?> response = dossierController.modifierDossier(dossier.getId(),
                Map.of("message", "modification"));

        assertEquals(403, response.getStatusCode().value());
    }

    @Test
    void modifierDossier_modifieMessageEtTypeOffre() {
        Dossier dossier = creerDossierEnBase("jean@test.com");

        ResponseEntity<?> response = dossierController.modifierDossier(dossier.getId(),
                Map.of("message", "message modifie", "typeOffre", "LOCATION"));

        assertEquals(200, response.getStatusCode().value());
    }
}
