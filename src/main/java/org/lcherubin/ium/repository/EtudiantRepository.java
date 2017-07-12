package org.lcherubin.ium.repository;

import org.lcherubin.ium.domain.Etudiant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Etudiant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {
    
}
