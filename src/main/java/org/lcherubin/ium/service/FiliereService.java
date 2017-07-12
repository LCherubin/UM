package org.lcherubin.ium.service;

import org.lcherubin.ium.domain.Filiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Filiere.
 */
public interface FiliereService {

    /**
     * Save a filiere.
     *
     * @param filiere the entity to save
     * @return the persisted entity
     */
    Filiere save(Filiere filiere);

    /**
     *  Get all the filieres.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Filiere> findAll(Pageable pageable);

    /**
     *  Get the "id" filiere.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Filiere findOne(Long id);

    /**
     *  Delete the "id" filiere.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the filiere corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Filiere> search(String query, Pageable pageable);
}
