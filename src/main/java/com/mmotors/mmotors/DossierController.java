package com.mmotors.mmotors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
 import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dossiers")
public class DossierController {

     private final DossierRepository dossierRepository;

    public DossierController(DossierRepository dossierRepository) {

        System.out.println("=================JE SUIS DANS DOSSIER_CONTROLLER=============================");
        System.out.println("=>=>=>=>=>=> REQUÊTE REÇUE /dossiers <=<=<=<=<=<=");
        System.out.println("typeOffre reçu : " + dossier.getTypeOffre());
        System.out.println("message reçu : " + dossier.getMessage());
        System.out.println("email client (JWT) : " + email);
        System.out.println("statut : EN_ATTENTE");
        System.out.println("=======================================================");

        this.dossierRepository = dossierRepository;



    }

    @PostMapping
    public String deposerDossier(@RequestBody Dossier dossier) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        dossier.setClientEmail(email);

        dossier.setStatut("EN_ATTENTE");
        dossier.setDateDepot(LocalDate.now().toString());
        dossierRepository.save(dossier);
        return "Dossier déposé avec succès";
    }

    @GetMapping("/moi")
    public List<Dossier> mesDossiers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return dossierRepository.findByClientEmail(email);
    }
}
