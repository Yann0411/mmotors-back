package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private DossierRepository dossierRepository;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testajoutDeVehicule() {
        Vehicule vehicule = new Vehicule();
        vehicule.setMarque("Peugeot");
        when(vehiculeRepository.save(any())).thenReturn(vehicule);

        ResponseEntity<?> response = adminController.ajouterVehicule(vehicule);

        assertEquals(200, response.getStatusCode().value());
        verify(vehiculeRepository).save(vehicule);
    }

    @Test
    void testModifierVehiculeNonExixtant() {
        when(vehiculeRepository.existsById(99L)).thenReturn(false);

        ResponseEntity<?> response = adminController.modifierVehicule(99L, new
                Vehicule());

        assertEquals(404, response.getStatusCode().value());
    }



    @Test
    void testModifierVehicule() {
        Vehicule vehicule = new Vehicule();
        when(vehiculeRepository.existsById(1L)).thenReturn(true);
        when(vehiculeRepository.save(any())).thenReturn(vehicule);

        ResponseEntity<?> response = adminController.modifierVehicule(1L, vehicule);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testSupprimerVehiculeNonExistant() {
        when(vehiculeRepository.existsById(99L)).thenReturn(false);

        ResponseEntity<?> response = adminController.supprimerVehicule(99L);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testSupprimerVehicule() {
        when(vehiculeRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<?> response = adminController.supprimerVehicule(1L);

        assertEquals(200, response.getStatusCode().value());
        verify(vehiculeRepository).deleteById(1L);
    }

    @Test
    void testTousLesDossiers() {
        when(dossierRepository.findAll()).thenReturn(List.of());

        List<Dossier> result = adminController.tousLesDossiers();

        assertNotNull(result);
        verify(dossierRepository).findAll();
    }

    @Test
    void TestMajLesDossier() {

                 when(dossierRepository.findById(99L)).thenReturn(Optional.empty());


        ResponseEntity<?> response = adminController.mettreAJourDossier(99L, new
                Dossier());

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testMettreAJourDossier() {


        Dossier dossier = new Dossier();
            dossier.setStatut("VALIDE");
        when(dossierRepository.findById(1L)).thenReturn(Optional.of(new Dossier()));
        when(dossierRepository.save(any())).thenReturn(dossier);

        ResponseEntity<?> response = adminController.mettreAJourDossier(1L, dossier);


        assertEquals(200, response.getStatusCode().value());

    }
}
