package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DossierTest {

    @Test
    void dossierStockeClientEmailEtStatut() {

        Dossier dossier = new Dossier();
        dossier.setClientEmail("jean@test.com");
        dossier.setStatut("EN_ATTENTE");

        assertEquals("jean@test.com", dossier.getClientEmail());
        assertEquals("EN_ATTENTE", dossier.getStatut());
    }

    @Test
    void dossierStockeTypeOffre() {

        Dossier dossier = new Dossier();
          dossier.setTypeOffre("ACHAT");

        assertEquals("ACHAT", dossier.getTypeOffre());
    }

    @Test
    void dossierStockeMessageEtTelephone() {

        Dossier dossier = new Dossier();
        dossier.setMessage("Je suis intéressé par ce véhicule.");
            dossier.setTelephone("06 12 34 56 78");

        assertEquals("Je suis intéressé par ce véhicule.", dossier.getMessage());
        assertEquals("06 12 34 56 78", dossier.getTelephone());
    }

    @Test
    void dossierStockeDateDepotEtVehiculeInfo() {

        Dossier dossier = new Dossier();
        dossier.setDateDepot("2026-05-27");
        dossier.setVehiculeInfo("Renault Clio 2020 - 45 000 km - 12 000 €");

        assertEquals("2026-05-27", dossier.getDateDepot());
        assertEquals("Renault Clio 2020 - 45 000 km - 12 000 €", dossier.getVehiculeInfo());

    }
}
