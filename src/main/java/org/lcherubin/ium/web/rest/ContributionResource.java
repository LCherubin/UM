package org.lcherubin.ium.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.lcherubin.ium.domain.Contribution;
import org.lcherubin.ium.service.ContributionService;
import org.lcherubin.ium.web.rest.util.HeaderUtil;
import org.lcherubin.ium.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Contribution.
 */
@RestController
@RequestMapping("/api")
public class ContributionResource {

    private final Logger log = LoggerFactory.getLogger(ContributionResource.class);

    private static final String ENTITY_NAME = "contribution";

    private final ContributionService contributionService;

    public ContributionResource(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    /**
     * POST  /contributions : Create a new contribution.
     *
     * @param contribution the contribution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contribution, or with status 400 (Bad Request) if the contribution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contributions")
    @Timed
    public ResponseEntity<Contribution> createContribution(@Valid @RequestBody Contribution contribution) throws URISyntaxException {
        log.debug("REST request to save Contribution : {}", contribution);
        if (contribution.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contribution cannot already have an ID")).body(null);
        }
        Contribution result = contributionService.save(contribution);
        return ResponseEntity.created(new URI("/api/contributions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contributions : Updates an existing contribution.
     *
     * @param contribution the contribution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contribution,
     * or with status 400 (Bad Request) if the contribution is not valid,
     * or with status 500 (Internal Server Error) if the contribution couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contributions")
    @Timed
    public ResponseEntity<Contribution> updateContribution(@Valid @RequestBody Contribution contribution) throws URISyntaxException {
        log.debug("REST request to update Contribution : {}", contribution);
        if (contribution.getId() == null) {
            return createContribution(contribution);
        }
        Contribution result = contributionService.save(contribution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contribution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contributions : get all the contributions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contributions in body
     */
    @GetMapping("/contributions")
    @Timed
    public ResponseEntity<List<Contribution>> getAllContributions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Contributions");
        Page<Contribution> page = contributionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contributions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contributions/:id : get the "id" contribution.
     *
     * @param id the id of the contribution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contribution, or with status 404 (Not Found)
     */
    @GetMapping("/contributions/{id}")
    @Timed
    public ResponseEntity<Contribution> getContribution(@PathVariable Long id) {
        log.debug("REST request to get Contribution : {}", id);
        Contribution contribution = contributionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contribution));
    }

    /**
     * DELETE  /contributions/:id : delete the "id" contribution.
     *
     * @param id the id of the contribution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contributions/{id}")
    @Timed
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        log.debug("REST request to delete Contribution : {}", id);
        contributionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contributions?query=:query : search for the contribution corresponding
     * to the query.
     *
     * @param query the query of the contribution search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contributions")
    @Timed
    public ResponseEntity<List<Contribution>> searchContributions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Contributions for query {}", query);
        Page<Contribution> page = contributionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contributions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
