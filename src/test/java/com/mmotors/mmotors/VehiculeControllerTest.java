package com.mmotors.mmotors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeControllerTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private VehiculeController vehiculeController;

    @Test
    void getAllVehicules() {
        
        when(vehiculeRepository.findAll()).thenReturn(List.of());
         List<Vehicule> result = vehiculeController.getAllVehicules(null, null, null,
                null, null, null);
        assertNotNull(result);
        verify(vehiculeRepository).findAll();
    }

    @Test
    void getAllVehiculesParType() {

        when(vehiculeRepository.findByTypeOffre("ACHAT")).thenReturn(List.of());
         List<Vehicule> result = vehiculeController.getAllVehicules("ACHAT", null, null,
                null, null, null);
        assertNotNull(result);
        verify(vehiculeRepository).findByTypeOffre("ACHAT");
    }

    @Test
    void getAllVehiculesAllFiltres() {

        when(vehiculeRepository
                        .findByTypeOffreAndMarqueContainingIgnoreCaseAndModeleContainingIgnoreCaseAndPrixBetweenAndKilometrageIsLessThanEqual(
                        "ACHAT", "Peugeot", "", 0.0, Double.MAX_VALUE,
                        Integer.MAX_VALUE))
                .thenReturn(List.of());
        List<Vehicule> result = vehiculeController.getAllVehicules("ACHAT", "Peugeot",
                null, null, null, null);
        assertNotNull(result);
    }
}
