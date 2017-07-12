package org.lcherubin.ium.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.lcherubin.ium.domain.Enseignant;
import org.lcherubin.ium.service.EnseignantService;
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
 * REST controller for managing Enseignant.
 */
@RestController
@RequestMapping("/api")
public class EnseignantResource {

    private final Logger log = LoggerFactory.getLogger(EnseignantResource.class);

    private static final String ENTITY_NAME = "enseignant";

    private final EnseignantService enseignantService;

    public EnseignantResource(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    /**
     * POST  /enseignants : Create a new enseignant.
     *
     * @param enseignant the enseignant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enseignant, or with status 400 (Bad Request) if the enseignant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enseignants")
    @Timed
    public ResponseEntity<Enseignant> createEnseignant(@Valid @RequestBody Enseignant enseignant) throws URISyntaxException {
        log.debug("REST request to save Enseignant : {}", enseignant);
        if (enseignant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new enseignant cannot already have an ID")).body(null);
        }
        Enseignant result = enseignantService.save(enseignant);
        return ResponseEntity.created(new URI("/api/enseignants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enseignants : Updates an existing enseignant.
     *
     * @param enseignant the enseignant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enseignant,
     * or with status 400 (Bad Request) if the enseignant is not valid,
     * or with status 500 (Internal Server Error) if the enseignant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enseignants")
    @Timed
    public ResponseEntity<Enseignant> updateEnseignant(@Valid @RequestBody Enseignant enseignant) throws URISyntaxException {
        log.debug("REST request to update Enseignant : {}", enseignant);
        if (enseignant.getId() == null) {
            return createEnseignant(enseignant);
        }
        Enseignant result = enseignantService.save(enseignant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enseignant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enseignants : get all the enseignants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enseignants in body
     */
    @GetMapping("/enseignants")
    @Timed
    public ResponseEntity<List<Enseignant>> getAllEnseignants(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Enseignants");
        Page<Enseignant> page = enseignantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enseignants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enseignants/:id : get the "id" enseignant.
     *
     * @param id the id of the enseignant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enseignant, or with status 404 (Not Found)
     */
    @GetMapping("/enseignants/{id}")
    @Timed
    public ResponseEntity<Enseignant> getEnseignant(@PathVariable Long id) {
        log.debug("REST request to get Enseignant : {}", id);
        Enseignant enseignant = enseignantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enseignant));
    }

    /**
     * DELETE  /enseignants/:id : delete the "id" enseignant.
     *
     * @param id the id of the enseignant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enseignants/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        log.debug("REST request to delete Enseignant : {}", id);
        enseignantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/enseignants?query=:query : search for the enseignant corresponding
     * to the query.
     *
     * @param query the query of the enseignant search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/enseignants")
    @Timed
    public ResponseEntity<List<Enseignant>> searchEnseignants(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Enseignants for query {}", query);
        Page<Enseignant> page = enseignantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/enseignants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
