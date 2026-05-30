package com.mmotors.mmotors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DossierControllerTest {

    @Mock
     private DossierRepository dossierRepository;

    @Mock
    private EmailService emailService;


    @InjectMocks
    private DossierController dossierController;

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    void connecterUtilisateur(String email) {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(email, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void testDepotDossier() {
         connecterUtilisateur("test@mail.com");

        Dossier dossier = new Dossier();
        dossier.setTypeOffre("ACHAT");
             dossier.setMessage("Je suis intéressé");

        dossierController.deposerDossier(dossier);

        verify(dossierRepository).save(dossier);
         assertEquals("EN_ATTENTE", dossier.getStatut());

         assertEquals("test@mail.com", dossier.getClientEmail());
    }

    @Test
    void testMesDossiers() {

        connecterUtilisateur("test@mail.com");
        when(dossierRepository.findByClientEmail("test@mail.com")).thenReturn(List.of());

        List<Dossier> result = dossierController.mesDossiers();

        assertNotNull(result);
        verify(dossierRepository).findByClientEmail("test@mail.com");
    }
}
