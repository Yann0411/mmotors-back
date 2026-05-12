package com.mmotors.mmotors;

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
    public ResponseEntity<?> ajouterVehicule(@RequestBody Vehicule vehicule) {
        vehiculeRepository.save(vehicule);
        return ResponseEntity.ok("Véhicule ajouté");
    }


    // Modifier un véhicule
    @PutMapping("/vehicules/{id}")
    public ResponseEntity<?> modifierVehicule(@PathVariable Long id, @RequestBody Vehicule
            vehicule) {
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
        if (dossierRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Dossier d = dossierRepository.findById(id).get();
        d.setStatut(dossier.getStatut());
        dossierRepository.save(d);
        return ResponseEntity.ok("Dossier mis à jour");
    }

}

