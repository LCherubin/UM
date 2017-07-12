package org.lcherubin.ium.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.lcherubin.ium.domain.Filiere;
import org.lcherubin.ium.service.FiliereService;
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
 * REST controller for managing Filiere.
 */
@RestController
@RequestMapping("/api")
public class FiliereResource {

    private final Logger log = LoggerFactory.getLogger(FiliereResource.class);

    private static final String ENTITY_NAME = "filiere";

    private final FiliereService filiereService;

    public FiliereResource(FiliereService filiereService) {
        this.filiereService = filiereService;
    }

    /**
     * POST  /filieres : Create a new filiere.
     *
     * @param filiere the filiere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filiere, or with status 400 (Bad Request) if the filiere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filieres")
    @Timed
    public ResponseEntity<Filiere> createFiliere(@Valid @RequestBody Filiere filiere) throws URISyntaxException {
        log.debug("REST request to save Filiere : {}", filiere);
        if (filiere.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filiere cannot already have an ID")).body(null);
        }
        Filiere result = filiereService.save(filiere);
        return ResponseEntity.created(new URI("/api/filieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filieres : Updates an existing filiere.
     *
     * @param filiere the filiere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filiere,
     * or with status 400 (Bad Request) if the filiere is not valid,
     * or with status 500 (Internal Server Error) if the filiere couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filieres")
    @Timed
    public ResponseEntity<Filiere> updateFiliere(@Valid @RequestBody Filiere filiere) throws URISyntaxException {
        log.debug("REST request to update Filiere : {}", filiere);
        if (filiere.getId() == null) {
            return createFiliere(filiere);
        }
        Filiere result = filiereService.save(filiere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filiere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filieres : get all the filieres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filieres in body
     */
    @GetMapping("/filieres")
    @Timed
    public ResponseEntity<List<Filiere>> getAllFilieres(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Filieres");
        Page<Filiere> page = filiereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filieres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filieres/:id : get the "id" filiere.
     *
     * @param id the id of the filiere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filiere, or with status 404 (Not Found)
     */
    @GetMapping("/filieres/{id}")
    @Timed
    public ResponseEntity<Filiere> getFiliere(@PathVariable Long id) {
        log.debug("REST request to get Filiere : {}", id);
        Filiere filiere = filiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filiere));
    }

    /**
     * DELETE  /filieres/:id : delete the "id" filiere.
     *
     * @param id the id of the filiere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filieres/{id}")
    @Timed
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id) {
        log.debug("REST request to delete Filiere : {}", id);
        filiereService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/filieres?query=:query : search for the filiere corresponding
     * to the query.
     *
     * @param query the query of the filiere search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/filieres")
    @Timed
    public ResponseEntity<List<Filiere>> searchFilieres(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Filieres for query {}", query);
        Page<Filiere> page = filiereService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/filieres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
