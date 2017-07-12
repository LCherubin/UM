package org.lcherubin.ium.service.impl;

import org.lcherubin.ium.service.ParentService;
import org.lcherubin.ium.domain.Parent;
import org.lcherubin.ium.repository.ParentRepository;
import org.lcherubin.ium.repository.search.ParentSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Parent.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService{

    private final Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);

    private final ParentRepository parentRepository;

    private final ParentSearchRepository parentSearchRepository;

    public ParentServiceImpl(ParentRepository parentRepository, ParentSearchRepository parentSearchRepository) {
        this.parentRepository = parentRepository;
        this.parentSearchRepository = parentSearchRepository;
    }

    /**
     * Save a parent.
     *
     * @param parent the entity to save
     * @return the persisted entity
     */
    @Override
    public Parent save(Parent parent) {
        log.debug("Request to save Parent : {}", parent);
        Parent result = parentRepository.save(parent);
        parentSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the parents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Parent> findAll(Pageable pageable) {
        log.debug("Request to get all Parents");
        return parentRepository.findAll(pageable);
    }

    /**
     *  Get one parent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Parent findOne(Long id) {
        log.debug("Request to get Parent : {}", id);
        return parentRepository.findOne(id);
    }

    /**
     *  Delete the  parent by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parent : {}", id);
        parentRepository.delete(id);
        parentSearchRepository.delete(id);
    }

    /**
     * Search for the parent corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Parent> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Parents for query {}", query);
        Page<Parent> result = parentSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
