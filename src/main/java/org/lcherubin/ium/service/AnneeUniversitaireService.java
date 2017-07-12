package org.lcherubin.ium.service;

import org.lcherubin.ium.domain.AnneeUniversitaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AnneeUniversitaire.
 */
public interface AnneeUniversitaireService {

    /**
     * Save a anneeUniversitaire.
     *
     * @param anneeUniversitaire the entity to save
     * @return the persisted entity
     */
    AnneeUniversitaire save(AnneeUniversitaire anneeUniversitaire);

    /**
     *  Get all the anneeUniversitaires.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AnneeUniversitaire> findAll(Pageable pageable);

    /**
     *  Get the "id" anneeUniversitaire.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AnneeUniversitaire findOne(Long id);

    /**
     *  Delete the "id" anneeUniversitaire.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the anneeUniversitaire corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AnneeUniversitaire> search(String query, Pageable pageable);
}
