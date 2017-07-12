package org.lcherubin.ium.service;

import org.lcherubin.ium.domain.Contribution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Contribution.
 */
public interface ContributionService {

    /**
     * Save a contribution.
     *
     * @param contribution the entity to save
     * @return the persisted entity
     */
    Contribution save(Contribution contribution);

    /**
     *  Get all the contributions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Contribution> findAll(Pageable pageable);

    /**
     *  Get the "id" contribution.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Contribution findOne(Long id);

    /**
     *  Delete the "id" contribution.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contribution corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Contribution> search(String query, Pageable pageable);
}
