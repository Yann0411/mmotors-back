package com.mmotors.mmotors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
 import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.Map;


@RestController
@RequestMapping("/dossiers")
public class DossierController {


    private final DossierRepository dossierRepository;
    private final EmailService emailService;



    public DossierController(DossierRepository dossierRepository, EmailService emailService) {

        this.dossierRepository = dossierRepository;
        this.emailService = emailService;

    }

    @PostMapping
    public ResponseEntity<?> deposerDossier(@RequestBody Dossier dossier) {


        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        dossier.setClientEmail(email);

        System.out.println("==================JE SUIS DANS DOSSIER CONTROLLER=====================================");
        System.out.println("=>=>=>=>=>=> REQUÊTE REÇUE /dossiers <=<=<=<=<=<=");
        System.out.println("typeOffre reçu : " + dossier.getTypeOffre());
        System.out.println("message reçu : " + dossier.getMessage());
        System.out.println("email client (JWT) : " + email);
        System.out.println("statut : EN_ATTENTE");
        System.out.println("=======================================================");

        dossier.setStatut("EN_ATTENTE");
        if (dossierRepository.existsByClientEmailAndStatut(email, "EN_ATTENTE")) {
            return ResponseEntity.badRequest().body("Vous avez déjà un dossier en attente. Annulez-le avant d'en déposer un nouveau.");
        }

        dossier.setDateDepot(LocalDate.now().toString());
        List<String> typesValides = List.of("ACHAT", "LOCATION", "LOCATION_ACHAT");
        if (!typesValides.contains(dossier.getTypeOffre())) {
            return ResponseEntity.status(400).body("Type d'offre invalide.");
        }


        dossierRepository.save(dossier);
        emailService.envoyerConfirmationDossier(email, dossier.getTypeOffre());
        return ResponseEntity.ok("Dossier déposé avec succès");


    }

    @GetMapping("/moi")
    public List<Dossier> mesDossiers() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println("=======================JE SUIS DNAS DOSSIER_CONTROLLEUR==========================");
        System.out.println("=>=>=>=>=>=>=>=>=>=>REQUÊTE REÇUE GET /dossiers/moi <=<=<=<=<=<=");
        System.out.println("email client (JWT) : " + email);
        System.out.println("=======================================================");

        return dossierRepository.findByClientEmail(email);




    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> annulerDossier(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Dossier> dossierOpt = dossierRepository.findById(id);
        if (dossierOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Dossier dossier = dossierOpt.get();
        if (!dossier.getClientEmail().equals(email)) {
            return ResponseEntity.status(403).body("Accès refusé.");
        }
        if (!"EN_ATTENTE".equals(dossier.getStatut())) {
            return ResponseEntity.badRequest().body("Seuls les dossiers en attente peuvent être annulés.");
        }
        dossierRepository.delete(dossier);
        return ResponseEntity.ok("Dossier annulé.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifierDossier(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Dossier> dossierOpt = dossierRepository.findById(id);
        if (dossierOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Dossier dossier = dossierOpt.get();
        if (!dossier.getClientEmail().equals(email)) {
            return ResponseEntity.status(403).body("Accès refusé.");
        }
        if (!"EN_ATTENTE".equals(dossier.getStatut())) {
            return ResponseEntity.badRequest().body("Seuls les dossiers en attente peuvent être modifiés.");
        }
        String nouveauMessage   = body.get("message");
        String nouveauTypeOffre = body.get("typeOffre");

        String telephone = body.get("telephone");
        if (telephone != null) dossier.setTelephone(telephone);

        List<String> typesValides = List.of("ACHAT", "LOCATION", "LOCATION_ACHAT");
        if (nouveauTypeOffre != null && !typesValides.contains(nouveauTypeOffre)) {
            return ResponseEntity.badRequest().body("Type d'offre invalide.");
        }
        if (nouveauMessage   != null) dossier.setMessage(nouveauMessage);
        if (nouveauTypeOffre != null) dossier.setTypeOffre(nouveauTypeOffre);
        dossierRepository.save(dossier);
        return ResponseEntity.ok("Dossier modifié.");
    }

}
