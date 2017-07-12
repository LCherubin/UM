package org.lcherubin.ium.repository;

import org.lcherubin.ium.domain.AnneeUniversitaire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AnneeUniversitaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnneeUniversitaireRepository extends JpaRepository<AnneeUniversitaire,Long> {
    
}
