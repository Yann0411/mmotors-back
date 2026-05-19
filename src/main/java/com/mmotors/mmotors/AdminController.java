package com.mmotors.mmotors;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final VehiculeRepository vehiculeRepository;

    private final DossierRepository dossierRepository;

    public AdminController(VehiculeRepository vehiculeRepository, DossierRepository
            dossierRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.dossierRepository = dossierRepository;
    }



    // Ajouter un vehicule
    @PostMapping("/vehicules")
    public ResponseEntity<?> ajouterVehicule(@Valid @RequestBody Vehicule vehicule) {

        System.out.println("===================JE SUIS DANS AMIN_CONTROLEUR====================================");
        System.out.println("=>=>=>=>=>=>=>=>=> ADMIN POST /admin/vehicules <=<=<=<=<=<=");
        System.out.println("marque : " + vehicule.getMarque() + " | modele : " + vehicule.getModele());
        System.out.println("prix : " + vehicule.getPrix() + " | km : " + vehicule.getKilometrage());
        System.out.println("typeOffre : " + vehicule.getTypeOffre());
        System.out.println("=======================================================");


        vehiculeRepository.save(vehicule);
        return ResponseEntity.ok("Véhicule ajouté");
    }


    // Modifier un véhicule
    @PutMapping("/vehicules/{id}")
    public ResponseEntity<?> modifierVehicule(@PathVariable Long id, @Valid @RequestBody Vehicule
            vehicule) {


        System.out.println("=====================JE SUIS DANS AMIN_CONTROLEUR==================================");
        System.out.println("=>=>=>=>=>=>=>=>=> ADMIN PUT /admin/vehicules/" + id + " <=<=<=<=<=<=<=<=<=<=<=<=");
        System.out.println("marque : " + vehicule.getMarque() + " | modele : " + vehicule.getModele());
        System.out.println("prix : " + vehicule.getPrix() + " | typeOffre : " + vehicule.getTypeOffre());
        System.out.println("=======================================================");


        if (!vehiculeRepository.existsById(id)) {
             return ResponseEntity.notFound().build();
        }
        vehicule.setId(id);
          vehiculeRepository.save(vehicule);
        return ResponseEntity.ok("Véhicule modifié");
    }



    // Supprimer un véhicule
    @DeleteMapping("/vehicules/{id}")
    public ResponseEntity<?> supprimerVehicule(@PathVariable Long id) {


        System.out.println("====================JE SUIS DANS AMIN_CONTROLEUR===================================");
        System.out.println("=>=>=>=>=>=>=>=>=> ADMIN DELETE /admin/vehicules/" + id + " <=<=<=<=<=<=");
        System.out.println("=======================================================");


        if (!vehiculeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();

        }
        vehiculeRepository.deleteById(id);
        return ResponseEntity.ok("Véhicule supprimé");
    }

    // Voir tous les dossier
    @GetMapping("/dossiers")
     public List<Dossier> tousLesDossiers() {
        return dossierRepository.findAll();
    }

    // Valider ou refuser un dossier

    @PutMapping("/dossiers/{id}")
    public ResponseEntity<?> mettreAJourDossier(@PathVariable Long id, @RequestBody Dossier dossier) {

        System.out.println("==================JE SUIS DANS AMIN_CONTROLEUR=====================================");
        System.out.println("=>=>=>=>=>=> ADMIN PUT /admin/dossiers/" + id + " <=<=<=<=<=<=");
        System.out.println("nouveau statut : " + dossier.getStatut());
        System.out.println("=======================================================");



        if (dossierRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Dossier d = dossierRepository.findById(id).get();
        d.setStatut(dossier.getStatut());
        dossierRepository.save(d);
        return ResponseEntity.ok("Dossier mis à jour");




    }


    @DeleteMapping("/dossiers/{id}")
    public ResponseEntity<?> supprimerDossier(@PathVariable Long id) {
        System.out.println("====================JE SUIS DANS AMIN_CONTROLEUR===================================");
        System.out.println("=>=>=>=>=>=>=>=>=> ADMIN DELETE /admin/dossiers/" + id + " <=<=<=<=<=<=");
        System.out.println("=======================================================");
        if (!dossierRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dossierRepository.deleteById(id);
        return ResponseEntity.ok("Dossier supprimé");
    }


}

