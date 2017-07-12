package org.lcherubin.ium.repository;

import org.lcherubin.ium.domain.Parent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Parent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
    
}
