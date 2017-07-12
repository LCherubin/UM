package org.lcherubin.ium.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.lcherubin.ium.domain.AnneeUniversitaire;
import org.lcherubin.ium.service.AnneeUniversitaireService;
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
 * REST controller for managing AnneeUniversitaire.
 */
@RestController
@RequestMapping("/api")
public class AnneeUniversitaireResource {

    private final Logger log = LoggerFactory.getLogger(AnneeUniversitaireResource.class);

    private static final String ENTITY_NAME = "anneeUniversitaire";

    private final AnneeUniversitaireService anneeUniversitaireService;

    public AnneeUniversitaireResource(AnneeUniversitaireService anneeUniversitaireService) {
        this.anneeUniversitaireService = anneeUniversitaireService;
    }

    /**
     * POST  /annee-universitaires : Create a new anneeUniversitaire.
     *
     * @param anneeUniversitaire the anneeUniversitaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anneeUniversitaire, or with status 400 (Bad Request) if the anneeUniversitaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annee-universitaires")
    @Timed
    public ResponseEntity<AnneeUniversitaire> createAnneeUniversitaire(@Valid @RequestBody AnneeUniversitaire anneeUniversitaire) throws URISyntaxException {
        log.debug("REST request to save AnneeUniversitaire : {}", anneeUniversitaire);
        if (anneeUniversitaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new anneeUniversitaire cannot already have an ID")).body(null);
        }
        AnneeUniversitaire result = anneeUniversitaireService.save(anneeUniversitaire);
        return ResponseEntity.created(new URI("/api/annee-universitaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annee-universitaires : Updates an existing anneeUniversitaire.
     *
     * @param anneeUniversitaire the anneeUniversitaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anneeUniversitaire,
     * or with status 400 (Bad Request) if the anneeUniversitaire is not valid,
     * or with status 500 (Internal Server Error) if the anneeUniversitaire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annee-universitaires")
    @Timed
    public ResponseEntity<AnneeUniversitaire> updateAnneeUniversitaire(@Valid @RequestBody AnneeUniversitaire anneeUniversitaire) throws URISyntaxException {
        log.debug("REST request to update AnneeUniversitaire : {}", anneeUniversitaire);
        if (anneeUniversitaire.getId() == null) {
            return createAnneeUniversitaire(anneeUniversitaire);
        }
        AnneeUniversitaire result = anneeUniversitaireService.save(anneeUniversitaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anneeUniversitaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annee-universitaires : get all the anneeUniversitaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of anneeUniversitaires in body
     */
    @GetMapping("/annee-universitaires")
    @Timed
    public ResponseEntity<List<AnneeUniversitaire>> getAllAnneeUniversitaires(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AnneeUniversitaires");
        Page<AnneeUniversitaire> page = anneeUniversitaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/annee-universitaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /annee-universitaires/:id : get the "id" anneeUniversitaire.
     *
     * @param id the id of the anneeUniversitaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anneeUniversitaire, or with status 404 (Not Found)
     */
    @GetMapping("/annee-universitaires/{id}")
    @Timed
    public ResponseEntity<AnneeUniversitaire> getAnneeUniversitaire(@PathVariable Long id) {
        log.debug("REST request to get AnneeUniversitaire : {}", id);
        AnneeUniversitaire anneeUniversitaire = anneeUniversitaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anneeUniversitaire));
    }

    /**
     * DELETE  /annee-universitaires/:id : delete the "id" anneeUniversitaire.
     *
     * @param id the id of the anneeUniversitaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annee-universitaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnneeUniversitaire(@PathVariable Long id) {
        log.debug("REST request to delete AnneeUniversitaire : {}", id);
        anneeUniversitaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/annee-universitaires?query=:query : search for the anneeUniversitaire corresponding
     * to the query.
     *
     * @param query the query of the anneeUniversitaire search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/annee-universitaires")
    @Timed
    public ResponseEntity<List<AnneeUniversitaire>> searchAnneeUniversitaires(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AnneeUniversitaires for query {}", query);
        Page<AnneeUniversitaire> page = anneeUniversitaireService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/annee-universitaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
