package com.mmotors.mmotors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminControllerIntegrationTest {

    @Autowired
    private AdminController adminController;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private DossierRepository dossierRepository;

    private Vehicule vehiculeTest() {
        Vehicule v = new Vehicule();
        v.setMarque("Peugeot");
        v.setModele("308");
        v.setAnnee(2020);
        v.setPrix(15000.0);
        v.setKilometrage(50000);
        v.setTypeOffre("ACHAT");
        return v;
    }

    @BeforeEach
    void setUp() {


        vehiculeRepository.deleteAll();
        dossierRepository.deleteAll();
    }

    @Test
    void ajouterVehicule_enregistreEnBase() {
        ResponseEntity<?> response = adminController.ajouterVehicule(vehiculeTest());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, vehiculeRepository.count());


    }

    @Test
    void supprimerVehiculeInexistant_retourne404() {
        ResponseEntity<?> response = adminController.supprimerVehicule(999L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void supprimerVehicule_retireDeBase() {
        Vehicule v = vehiculeRepository.save(vehiculeTest());

        ResponseEntity<?> response = adminController.supprimerVehicule(v.getId());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, vehiculeRepository.count());
    }

    @Test
    void modifierVehicule_modifieEnBase() {
        Vehicule v = vehiculeRepository.save(vehiculeTest());

        Vehicule modification = vehiculeTest();
        modification.setMarque("Renault");

        adminController.modifierVehicule(v.getId(), modification);

        String marqueEnBase = vehiculeRepository.findById(v.getId()).get().getMarque();
        assertEquals("Renault", marqueEnBase); // la modification est vraiment persistée
    }

    @Test
    void mettreAJourDossierInexistant_retourne404() {
        ResponseEntity<?> response = adminController.mettreAJourDossier(999L, new Dossier());

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void mettreAJourStatutDossier_persisteEnBase() {
        Dossier d = new Dossier();
        d.setStatut("EN_ATTENTE");
        d = dossierRepository.save(d);

        Dossier maj = new Dossier();
        maj.setStatut("VALIDE");
        adminController.mettreAJourDossier(d.getId(), maj);

        String statutEnBase = dossierRepository.findById(d.getId()).get().getStatut();
        assertEquals("VALIDE", statutEnBase);
    }
}
