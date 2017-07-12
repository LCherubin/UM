package org.lcherubin.ium.repository;

import org.lcherubin.ium.domain.Filiere;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Filiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {
    
}
