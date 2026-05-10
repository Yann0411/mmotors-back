package com.mmotors.mmotors;

  import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByTypeOffre(String typeOffre);
    List<Vehicule> findByTypeOffreAndMarqueContainingIgnoreCaseAndModeleContainingIgnoreCaseAndPrixBetweenAndKilometrageIsLessThanEqual(
            String typeOffre,
            String marque,
            String modele,
            double prixMin,
            double prixMax,
            int kilometrageMax


    );


}
