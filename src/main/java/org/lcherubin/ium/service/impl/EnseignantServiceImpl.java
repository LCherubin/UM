package org.lcherubin.ium.service.impl;

import org.lcherubin.ium.service.EnseignantService;
import org.lcherubin.ium.domain.Enseignant;
import org.lcherubin.ium.repository.EnseignantRepository;
import org.lcherubin.ium.repository.search.EnseignantSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Enseignant.
 */
@Service
@Transactional
public class EnseignantServiceImpl implements EnseignantService{

    private final Logger log = LoggerFactory.getLogger(EnseignantServiceImpl.class);

    private final EnseignantRepository enseignantRepository;

    private final EnseignantSearchRepository enseignantSearchRepository;

    public EnseignantServiceImpl(EnseignantRepository enseignantRepository, EnseignantSearchRepository enseignantSearchRepository) {
        this.enseignantRepository = enseignantRepository;
        this.enseignantSearchRepository = enseignantSearchRepository;
    }

    /**
     * Save a enseignant.
     *
     * @param enseignant the entity to save
     * @return the persisted entity
     */
    @Override
    public Enseignant save(Enseignant enseignant) {
        log.debug("Request to save Enseignant : {}", enseignant);
        Enseignant result = enseignantRepository.save(enseignant);
        enseignantSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the enseignants.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Enseignant> findAll(Pageable pageable) {
        log.debug("Request to get all Enseignants");
        return enseignantRepository.findAll(pageable);
    }

    /**
     *  Get one enseignant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Enseignant findOne(Long id) {
        log.debug("Request to get Enseignant : {}", id);
        return enseignantRepository.findOne(id);
    }

    /**
     *  Delete the  enseignant by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enseignant : {}", id);
        enseignantRepository.delete(id);
        enseignantSearchRepository.delete(id);
    }

    /**
     * Search for the enseignant corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Enseignant> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Enseignants for query {}", query);
        Page<Enseignant> result = enseignantSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
