package com.mmotors.mmotors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehiculeControllerIntegrationTest {

    @Autowired
    private VehiculeController vehiculeController;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @BeforeEach
    void setUp() {
        vehiculeRepository.deleteAll();
    }

    private Vehicule creerVehicule(String marque, String typeOffre, double prix) {


        Vehicule v = new Vehicule();
        v.setMarque(marque);
        v.setModele("Modele");
        v.setAnnee(2020);
        v.setPrix(prix);
        v.setKilometrage(50000);
        v.setTypeOffre(typeOffre);
        return vehiculeRepository.save(v);
    }

    @Test
    void getAllVehicules_retourneTousLesVehicules() {
        creerVehicule("Toyota", "ACHAT", 10000);

        creerVehicule("Renault", "LOCATION", 500);
        List<Vehicule> result = vehiculeController.getAllVehicules(null, null, null, null, null, null);

        assertEquals(2, result.size());
    }

    @Test
    void getAllVehicules_filtreParTypeOffre() {


        creerVehicule("Toyota", "ACHAT", 10000);
        creerVehicule("Renault", "LOCATION", 500);

        List<Vehicule> result = vehiculeController.getAllVehicules("ACHAT", null, null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getMarque());
    }

    @Test
    void getAllVehicules_filtreParPrixMax() {
        creerVehicule("Pas cher", "ACHAT", 8000);

        creerVehicule("Cher", "ACHAT", 30000);

        List<Vehicule> result = vehiculeController.getAllVehicules("ACHAT", null, null, null, 10000.0, null);

        assertEquals(1, result.size());
        assertEquals("Pas cher", result.get(0).getMarque());
    }
}

