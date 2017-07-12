package org.lcherubin.ium.repository;

import org.lcherubin.ium.domain.Cours;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoursRepository extends JpaRepository<Cours,Long> {
    
}
