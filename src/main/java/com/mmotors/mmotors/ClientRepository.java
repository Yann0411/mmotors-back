package com.mmotors.mmotors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.nom = :nom, c.prenom = :prenom WHERE c.email = :email")
    void updateProfil(@Param("email") String email,
                      @Param("nom") String nom,
                      @Param("prenom") String prenom);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.motDePasse = :motDePasse WHERE c.email = :email")
    void updateMotDePasse(@Param("email") String email,
                          @Param("motDePasse") String motDePasse);
}
