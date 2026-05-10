package com.mmotors.mmotors;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
    List<Dossier> findByClientEmail(String clientEmail);
}
