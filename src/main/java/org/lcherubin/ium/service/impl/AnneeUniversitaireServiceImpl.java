package org.lcherubin.ium.service.impl;

import org.lcherubin.ium.service.AnneeUniversitaireService;
import org.lcherubin.ium.domain.AnneeUniversitaire;
import org.lcherubin.ium.repository.AnneeUniversitaireRepository;
import org.lcherubin.ium.repository.search.AnneeUniversitaireSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AnneeUniversitaire.
 */
@Service
@Transactional
public class AnneeUniversitaireServiceImpl implements AnneeUniversitaireService{

    private final Logger log = LoggerFactory.getLogger(AnneeUniversitaireServiceImpl.class);

    private final AnneeUniversitaireRepository anneeUniversitaireRepository;

    private final AnneeUniversitaireSearchRepository anneeUniversitaireSearchRepository;

    public AnneeUniversitaireServiceImpl(AnneeUniversitaireRepository anneeUniversitaireRepository, AnneeUniversitaireSearchRepository anneeUniversitaireSearchRepository) {
        this.anneeUniversitaireRepository = anneeUniversitaireRepository;
        this.anneeUniversitaireSearchRepository = anneeUniversitaireSearchRepository;
    }

    /**
     * Save a anneeUniversitaire.
     *
     * @param anneeUniversitaire the entity to save
     * @return the persisted entity
     */
    @Override
    public AnneeUniversitaire save(AnneeUniversitaire anneeUniversitaire) {
        log.debug("Request to save AnneeUniversitaire : {}", anneeUniversitaire);
        AnneeUniversitaire result = anneeUniversitaireRepository.save(anneeUniversitaire);
        anneeUniversitaireSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the anneeUniversitaires.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnneeUniversitaire> findAll(Pageable pageable) {
        log.debug("Request to get all AnneeUniversitaires");
        return anneeUniversitaireRepository.findAll(pageable);
    }

    /**
     *  Get one anneeUniversitaire by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnneeUniversitaire findOne(Long id) {
        log.debug("Request to get AnneeUniversitaire : {}", id);
        return anneeUniversitaireRepository.findOne(id);
    }

    /**
     *  Delete the  anneeUniversitaire by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnneeUniversitaire : {}", id);
        anneeUniversitaireRepository.delete(id);
        anneeUniversitaireSearchRepository.delete(id);
    }

    /**
     * Search for the anneeUniversitaire corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnneeUniversitaire> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AnneeUniversitaires for query {}", query);
        Page<AnneeUniversitaire> result = anneeUniversitaireSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
