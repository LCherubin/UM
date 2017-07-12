package org.lcherubin.ium.service;

import org.lcherubin.ium.domain.Enseignant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Enseignant.
 */
public interface EnseignantService {

    /**
     * Save a enseignant.
     *
     * @param enseignant the entity to save
     * @return the persisted entity
     */
    Enseignant save(Enseignant enseignant);

    /**
     *  Get all the enseignants.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Enseignant> findAll(Pageable pageable);

    /**
     *  Get the "id" enseignant.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Enseignant findOne(Long id);

    /**
     *  Delete the "id" enseignant.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the enseignant corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Enseignant> search(String query, Pageable pageable);
}
