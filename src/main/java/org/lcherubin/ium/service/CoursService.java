package org.lcherubin.ium.service;

import org.lcherubin.ium.domain.Cours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cours.
 */
public interface CoursService {

    /**
     * Save a cours.
     *
     * @param cours the entity to save
     * @return the persisted entity
     */
    Cours save(Cours cours);

    /**
     *  Get all the cours.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cours> findAll(Pageable pageable);

    /**
     *  Get the "id" cours.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cours findOne(Long id);

    /**
     *  Delete the "id" cours.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cours corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cours> search(String query, Pageable pageable);
}
