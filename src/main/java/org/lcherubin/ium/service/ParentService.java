package org.lcherubin.ium.service;

import org.lcherubin.ium.domain.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Parent.
 */
public interface ParentService {

    /**
     * Save a parent.
     *
     * @param parent the entity to save
     * @return the persisted entity
     */
    Parent save(Parent parent);

    /**
     *  Get all the parents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Parent> findAll(Pageable pageable);

    /**
     *  Get the "id" parent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Parent findOne(Long id);

    /**
     *  Delete the "id" parent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the parent corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Parent> search(String query, Pageable pageable);
}
