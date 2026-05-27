package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehiculeTest {

    @Test
    void vehiculeStocqueMarqueEtModele() {

        Vehicule vehicule = new Vehicule();
        vehicule.setMarque("Renault");
          vehicule.setModele("Clio");

        assertEquals("Renault", vehicule.getMarque());
        assertEquals("Clio", vehicule.getModele());

    }

    @Test
    void vehiculeStockePrixEtKilometrage() {


        Vehicule vehicule = new Vehicule();
        vehicule.setPrix(12000.0);
        vehicule.setKilometrage(45000);

        assertEquals(12000.0, vehicule.getPrix());
        assertEquals(45000, vehicule.getKilometrage());
    }

    @Test
    void vehiculeStockeAnnee() {
        Vehicule vehicule = new Vehicule();
            vehicule.setAnnee(2020);

        assertEquals(2020, vehicule.getAnnee());

    }

    @Test
    void vehiculeStockeTypeOffre() {

        Vehicule vehicule = new Vehicule();
        vehicule.setTypeOffre("LOCATION");

        assertEquals("LOCATION", vehicule.getTypeOffre());
    }
}
