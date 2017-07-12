package org.lcherubin.ium.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.lcherubin.ium.domain.Etudiant;
import org.lcherubin.ium.service.EtudiantService;
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
 * REST controller for managing Etudiant.
 */
@RestController
@RequestMapping("/api")
public class EtudiantResource {

    private final Logger log = LoggerFactory.getLogger(EtudiantResource.class);

    private static final String ENTITY_NAME = "etudiant";

    private final EtudiantService etudiantService;

    public EtudiantResource(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    /**
     * POST  /etudiants : Create a new etudiant.
     *
     * @param etudiant the etudiant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etudiant, or with status 400 (Bad Request) if the etudiant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etudiants")
    @Timed
    public ResponseEntity<Etudiant> createEtudiant(@Valid @RequestBody Etudiant etudiant) throws URISyntaxException {
        log.debug("REST request to save Etudiant : {}", etudiant);
        if (etudiant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new etudiant cannot already have an ID")).body(null);
        }
        Etudiant result = etudiantService.save(etudiant);
        return ResponseEntity.created(new URI("/api/etudiants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etudiants : Updates an existing etudiant.
     *
     * @param etudiant the etudiant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etudiant,
     * or with status 400 (Bad Request) if the etudiant is not valid,
     * or with status 500 (Internal Server Error) if the etudiant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etudiants")
    @Timed
    public ResponseEntity<Etudiant> updateEtudiant(@Valid @RequestBody Etudiant etudiant) throws URISyntaxException {
        log.debug("REST request to update Etudiant : {}", etudiant);
        if (etudiant.getId() == null) {
            return createEtudiant(etudiant);
        }
        Etudiant result = etudiantService.save(etudiant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etudiant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etudiants : get all the etudiants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etudiants in body
     */
    @GetMapping("/etudiants")
    @Timed
    public ResponseEntity<List<Etudiant>> getAllEtudiants(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Etudiants");
        Page<Etudiant> page = etudiantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etudiants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /etudiants/:id : get the "id" etudiant.
     *
     * @param id the id of the etudiant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etudiant, or with status 404 (Not Found)
     */
    @GetMapping("/etudiants/{id}")
    @Timed
    public ResponseEntity<Etudiant> getEtudiant(@PathVariable Long id) {
        log.debug("REST request to get Etudiant : {}", id);
        Etudiant etudiant = etudiantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etudiant));
    }

    /**
     * DELETE  /etudiants/:id : delete the "id" etudiant.
     *
     * @param id the id of the etudiant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etudiants/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        log.debug("REST request to delete Etudiant : {}", id);
        etudiantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/etudiants?query=:query : search for the etudiant corresponding
     * to the query.
     *
     * @param query the query of the etudiant search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/etudiants")
    @Timed
    public ResponseEntity<List<Etudiant>> searchEtudiants(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Etudiants for query {}", query);
        Page<Etudiant> page = etudiantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/etudiants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
