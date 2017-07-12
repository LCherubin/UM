package org.lcherubin.ium.service.impl;

import org.lcherubin.ium.service.CoursService;
import org.lcherubin.ium.domain.Cours;
import org.lcherubin.ium.repository.CoursRepository;
import org.lcherubin.ium.repository.search.CoursSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Cours.
 */
@Service
@Transactional
public class CoursServiceImpl implements CoursService{

    private final Logger log = LoggerFactory.getLogger(CoursServiceImpl.class);

    private final CoursRepository coursRepository;

    private final CoursSearchRepository coursSearchRepository;

    public CoursServiceImpl(CoursRepository coursRepository, CoursSearchRepository coursSearchRepository) {
        this.coursRepository = coursRepository;
        this.coursSearchRepository = coursSearchRepository;
    }

    /**
     * Save a cours.
     *
     * @param cours the entity to save
     * @return the persisted entity
     */
    @Override
    public Cours save(Cours cours) {
        log.debug("Request to save Cours : {}", cours);
        Cours result = coursRepository.save(cours);
        coursSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the cours.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cours> findAll(Pageable pageable) {
        log.debug("Request to get all Cours");
        return coursRepository.findAll(pageable);
    }

    /**
     *  Get one cours by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Cours findOne(Long id) {
        log.debug("Request to get Cours : {}", id);
        return coursRepository.findOne(id);
    }

    /**
     *  Delete the  cours by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cours : {}", id);
        coursRepository.delete(id);
        coursSearchRepository.delete(id);
    }

    /**
     * Search for the cours corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cours> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cours for query {}", query);
        Page<Cours> result = coursSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
