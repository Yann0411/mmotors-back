package com.mmotors.mmotors;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicules")

   public class VehiculeController {

    private VehiculeRepository vehiculeRepository;
     public VehiculeController(VehiculeRepository vehiculeRepository) {

         System.out.println("=>=>=>=>=>=>=>=>=>JE SUIS DANS VEHICULE_CONTROLLER<=<=<=<=<=<=<=<=<=");
         System.out.println("=>=>=>=>=>=>=>=>=>J'injecte vehiculeRepository<=<=<=<=<=<=<=<=<=");

         this.vehiculeRepository = vehiculeRepository;
    }

    @GetMapping
     public List<Vehicule> getAllVehicules(

            @RequestParam(required = false) String typeOffre,

            @RequestParam(required = false) String marque,
            @RequestParam(required = false) String modele,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) Integer kilometrageMax) {

        System.out.println("=========JE SUIS DANS VEHICULE_CONTROLLER=========");
        System.out.println("=>=>=>=>=>=> Resquest REÇUE /vehicules <=<=<=<=<=<=");
        System.out.println("typeOffre reçu : " + typeOffre);
        System.out.println("marque reçue : " + marque);
        System.out.println("prixMin reçu : " + prixMin);
        System.out.println("prixMax reçu : " + prixMax);
        System.out.println("kilometrageMax reçu : " + kilometrageMax);
        System.out.println("=======================================================");


        if  (marque != null || modele != null || prixMin != null || prixMax != null || kilometrageMax != null) {

             return vehiculeRepository.findByTypeOffreAndMarqueContainingIgnoreCaseAndModeleContainingIgnoreCaseAndPrixBetweenAndKilometrageIsLessThanEqual(

                            typeOffre != null ? typeOffre : "",
                            marque != null ? marque : "",
                            modele != null ? modele : "",
                            prixMin != null ? prixMin : 0,
                            prixMax != null ? prixMax : Double.MAX_VALUE,
                            kilometrageMax != null ? kilometrageMax : Integer.MAX_VALUE
                    );
        }
        if (typeOffre != null) {

            return vehiculeRepository.findByTypeOffre(typeOffre);
        }
        return vehiculeRepository.findAll();
    }

}
