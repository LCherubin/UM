package org.lcherubin.ium.service.impl;

import org.lcherubin.ium.service.CountryService;
import org.lcherubin.ium.domain.Country;
import org.lcherubin.ium.repository.CountryRepository;
import org.lcherubin.ium.repository.search.CountrySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    private final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final CountryRepository countryRepository;

    private final CountrySearchRepository countrySearchRepository;

    public CountryServiceImpl(CountryRepository countryRepository, CountrySearchRepository countrySearchRepository) {
        this.countryRepository = countryRepository;
        this.countrySearchRepository = countrySearchRepository;
    }

    /**
     * Save a country.
     *
     * @param country the entity to save
     * @return the persisted entity
     */
    @Override
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        Country result = countryRepository.save(country);
        countrySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the countries.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Country> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countryRepository.findAll(pageable);
    }

    /**
     *  Get one country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Country findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        return countryRepository.findOne(id);
    }

    /**
     *  Delete the  country by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
        countrySearchRepository.delete(id);
    }

    /**
     * Search for the country corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Country> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Countries for query {}", query);
        Page<Country> result = countrySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
