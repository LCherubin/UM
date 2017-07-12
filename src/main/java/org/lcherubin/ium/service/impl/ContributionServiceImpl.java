package org.lcherubin.ium.service.impl;

import org.lcherubin.ium.service.ContributionService;
import org.lcherubin.ium.domain.Contribution;
import org.lcherubin.ium.repository.ContributionRepository;
import org.lcherubin.ium.repository.search.ContributionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Contribution.
 */
@Service
@Transactional
public class ContributionServiceImpl implements ContributionService{

    private final Logger log = LoggerFactory.getLogger(ContributionServiceImpl.class);

    private final ContributionRepository contributionRepository;

    private final ContributionSearchRepository contributionSearchRepository;

    public ContributionServiceImpl(ContributionRepository contributionRepository, ContributionSearchRepository contributionSearchRepository) {
        this.contributionRepository = contributionRepository;
        this.contributionSearchRepository = contributionSearchRepository;
    }

    /**
     * Save a contribution.
     *
     * @param contribution the entity to save
     * @return the persisted entity
     */
    @Override
    public Contribution save(Contribution contribution) {
        log.debug("Request to save Contribution : {}", contribution);
        Contribution result = contributionRepository.save(contribution);
        contributionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the contributions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contribution> findAll(Pageable pageable) {
        log.debug("Request to get all Contributions");
        return contributionRepository.findAll(pageable);
    }

    /**
     *  Get one contribution by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Contribution findOne(Long id) {
        log.debug("Request to get Contribution : {}", id);
        return contributionRepository.findOne(id);
    }

    /**
     *  Delete the  contribution by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contribution : {}", id);
        contributionRepository.delete(id);
        contributionSearchRepository.delete(id);
    }

    /**
     * Search for the contribution corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contribution> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Contributions for query {}", query);
        Page<Contribution> result = contributionSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
