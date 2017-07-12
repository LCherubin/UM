package org.lcherubin.ium.repository;

import org.lcherubin.ium.domain.Enseignant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Enseignant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {
    
}
