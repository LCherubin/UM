package org.lcherubin.ium.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.lcherubin.ium.domain.Cours;
import org.lcherubin.ium.service.CoursService;
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
 * REST controller for managing Cours.
 */
@RestController
@RequestMapping("/api")
public class CoursResource {

    private final Logger log = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "cours";

    private final CoursService coursService;

    public CoursResource(CoursService coursService) {
        this.coursService = coursService;
    }

    /**
     * POST  /cours : Create a new cours.
     *
     * @param cours the cours to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cours, or with status 400 (Bad Request) if the cours has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cours")
    @Timed
    public ResponseEntity<Cours> createCours(@Valid @RequestBody Cours cours) throws URISyntaxException {
        log.debug("REST request to save Cours : {}", cours);
        if (cours.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cours cannot already have an ID")).body(null);
        }
        Cours result = coursService.save(cours);
        return ResponseEntity.created(new URI("/api/cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cours : Updates an existing cours.
     *
     * @param cours the cours to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cours,
     * or with status 400 (Bad Request) if the cours is not valid,
     * or with status 500 (Internal Server Error) if the cours couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cours")
    @Timed
    public ResponseEntity<Cours> updateCours(@Valid @RequestBody Cours cours) throws URISyntaxException {
        log.debug("REST request to update Cours : {}", cours);
        if (cours.getId() == null) {
            return createCours(cours);
        }
        Cours result = coursService.save(cours);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cours.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cours : get all the cours.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cours in body
     */
    @GetMapping("/cours")
    @Timed
    public ResponseEntity<List<Cours>> getAllCours(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cours");
        Page<Cours> page = coursService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cours");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cours/:id : get the "id" cours.
     *
     * @param id the id of the cours to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cours, or with status 404 (Not Found)
     */
    @GetMapping("/cours/{id}")
    @Timed
    public ResponseEntity<Cours> getCours(@PathVariable Long id) {
        log.debug("REST request to get Cours : {}", id);
        Cours cours = coursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cours));
    }

    /**
     * DELETE  /cours/:id : delete the "id" cours.
     *
     * @param id the id of the cours to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cours/{id}")
    @Timed
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        log.debug("REST request to delete Cours : {}", id);
        coursService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cours?query=:query : search for the cours corresponding
     * to the query.
     *
     * @param query the query of the cours search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cours")
    @Timed
    public ResponseEntity<List<Cours>> searchCours(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Cours for query {}", query);
        Page<Cours> page = coursService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cours");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
