package org.lcherubin.ium.web.rest;

import org.lcherubin.ium.MathFinEcoUniversityApp;

import org.lcherubin.ium.domain.AnneeUniversitaire;
import org.lcherubin.ium.repository.AnneeUniversitaireRepository;
import org.lcherubin.ium.service.AnneeUniversitaireService;
import org.lcherubin.ium.repository.search.AnneeUniversitaireSearchRepository;
import org.lcherubin.ium.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnneeUniversitaireResource REST controller.
 *
 * @see AnneeUniversitaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MathFinEcoUniversityApp.class)
public class AnneeUniversitaireResourceIntTest {

    private static final String DEFAULT_ANNEES = "AAAAAAAAAA";
    private static final String UPDATED_ANNEES = "BBBBBBBBBB";

    @Autowired
    private AnneeUniversitaireRepository anneeUniversitaireRepository;

    @Autowired
    private AnneeUniversitaireService anneeUniversitaireService;

    @Autowired
    private AnneeUniversitaireSearchRepository anneeUniversitaireSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnneeUniversitaireMockMvc;

    private AnneeUniversitaire anneeUniversitaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AnneeUniversitaireResource anneeUniversitaireResource = new AnneeUniversitaireResource(anneeUniversitaireService);
        this.restAnneeUniversitaireMockMvc = MockMvcBuilders.standaloneSetup(anneeUniversitaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnneeUniversitaire createEntity(EntityManager em) {
        AnneeUniversitaire anneeUniversitaire = new AnneeUniversitaire()
            .annees(DEFAULT_ANNEES);
        return anneeUniversitaire;
    }

    @Before
    public void initTest() {
        anneeUniversitaireSearchRepository.deleteAll();
        anneeUniversitaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnneeUniversitaire() throws Exception {
        int databaseSizeBeforeCreate = anneeUniversitaireRepository.findAll().size();

        // Create the AnneeUniversitaire
        restAnneeUniversitaireMockMvc.perform(post("/api/annee-universitaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anneeUniversitaire)))
            .andExpect(status().isCreated());

        // Validate the AnneeUniversitaire in the database
        List<AnneeUniversitaire> anneeUniversitaireList = anneeUniversitaireRepository.findAll();
        assertThat(anneeUniversitaireList).hasSize(databaseSizeBeforeCreate + 1);
        AnneeUniversitaire testAnneeUniversitaire = anneeUniversitaireList.get(anneeUniversitaireList.size() - 1);
        assertThat(testAnneeUniversitaire.getAnnees()).isEqualTo(DEFAULT_ANNEES);

        // Validate the AnneeUniversitaire in Elasticsearch
        AnneeUniversitaire anneeUniversitaireEs = anneeUniversitaireSearchRepository.findOne(testAnneeUniversitaire.getId());
        assertThat(anneeUniversitaireEs).isEqualToComparingFieldByField(testAnneeUniversitaire);
    }

    @Test
    @Transactional
    public void createAnneeUniversitaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anneeUniversitaireRepository.findAll().size();

        // Create the AnneeUniversitaire with an existing ID
        anneeUniversitaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnneeUniversitaireMockMvc.perform(post("/api/annee-universitaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anneeUniversitaire)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AnneeUniversitaire> anneeUniversitaireList = anneeUniversitaireRepository.findAll();
        assertThat(anneeUniversitaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnneesIsRequired() throws Exception {
        int databaseSizeBeforeTest = anneeUniversitaireRepository.findAll().size();
        // set the field null
        anneeUniversitaire.setAnnees(null);

        // Create the AnneeUniversitaire, which fails.

        restAnneeUniversitaireMockMvc.perform(post("/api/annee-universitaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anneeUniversitaire)))
            .andExpect(status().isBadRequest());

        List<AnneeUniversitaire> anneeUniversitaireList = anneeUniversitaireRepository.findAll();
        assertThat(anneeUniversitaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnneeUniversitaires() throws Exception {
        // Initialize the database
        anneeUniversitaireRepository.saveAndFlush(anneeUniversitaire);

        // Get all the anneeUniversitaireList
        restAnneeUniversitaireMockMvc.perform(get("/api/annee-universitaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anneeUniversitaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].annees").value(hasItem(DEFAULT_ANNEES.toString())));
    }

    @Test
    @Transactional
    public void getAnneeUniversitaire() throws Exception {
        // Initialize the database
        anneeUniversitaireRepository.saveAndFlush(anneeUniversitaire);

        // Get the anneeUniversitaire
        restAnneeUniversitaireMockMvc.perform(get("/api/annee-universitaires/{id}", anneeUniversitaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anneeUniversitaire.getId().intValue()))
            .andExpect(jsonPath("$.annees").value(DEFAULT_ANNEES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnneeUniversitaire() throws Exception {
        // Get the anneeUniversitaire
        restAnneeUniversitaireMockMvc.perform(get("/api/annee-universitaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnneeUniversitaire() throws Exception {
        // Initialize the database
        anneeUniversitaireService.save(anneeUniversitaire);

        int databaseSizeBeforeUpdate = anneeUniversitaireRepository.findAll().size();

        // Update the anneeUniversitaire
        AnneeUniversitaire updatedAnneeUniversitaire = anneeUniversitaireRepository.findOne(anneeUniversitaire.getId());
        updatedAnneeUniversitaire
            .annees(UPDATED_ANNEES);

        restAnneeUniversitaireMockMvc.perform(put("/api/annee-universitaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnneeUniversitaire)))
            .andExpect(status().isOk());

        // Validate the AnneeUniversitaire in the database
        List<AnneeUniversitaire> anneeUniversitaireList = anneeUniversitaireRepository.findAll();
        assertThat(anneeUniversitaireList).hasSize(databaseSizeBeforeUpdate);
        AnneeUniversitaire testAnneeUniversitaire = anneeUniversitaireList.get(anneeUniversitaireList.size() - 1);
        assertThat(testAnneeUniversitaire.getAnnees()).isEqualTo(UPDATED_ANNEES);

        // Validate the AnneeUniversitaire in Elasticsearch
        AnneeUniversitaire anneeUniversitaireEs = anneeUniversitaireSearchRepository.findOne(testAnneeUniversitaire.getId());
        assertThat(anneeUniversitaireEs).isEqualToComparingFieldByField(testAnneeUniversitaire);
    }

    @Test
    @Transactional
    public void updateNonExistingAnneeUniversitaire() throws Exception {
        int databaseSizeBeforeUpdate = anneeUniversitaireRepository.findAll().size();

        // Create the AnneeUniversitaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnneeUniversitaireMockMvc.perform(put("/api/annee-universitaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anneeUniversitaire)))
            .andExpect(status().isCreated());

        // Validate the AnneeUniversitaire in the database
        List<AnneeUniversitaire> anneeUniversitaireList = anneeUniversitaireRepository.findAll();
        assertThat(anneeUniversitaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnneeUniversitaire() throws Exception {
        // Initialize the database
        anneeUniversitaireService.save(anneeUniversitaire);

        int databaseSizeBeforeDelete = anneeUniversitaireRepository.findAll().size();

        // Get the anneeUniversitaire
        restAnneeUniversitaireMockMvc.perform(delete("/api/annee-universitaires/{id}", anneeUniversitaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean anneeUniversitaireExistsInEs = anneeUniversitaireSearchRepository.exists(anneeUniversitaire.getId());
        assertThat(anneeUniversitaireExistsInEs).isFalse();

        // Validate the database is empty
        List<AnneeUniversitaire> anneeUniversitaireList = anneeUniversitaireRepository.findAll();
        assertThat(anneeUniversitaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAnneeUniversitaire() throws Exception {
        // Initialize the database
        anneeUniversitaireService.save(anneeUniversitaire);

        // Search the anneeUniversitaire
        restAnneeUniversitaireMockMvc.perform(get("/api/_search/annee-universitaires?query=id:" + anneeUniversitaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anneeUniversitaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].annees").value(hasItem(DEFAULT_ANNEES.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnneeUniversitaire.class);
        AnneeUniversitaire anneeUniversitaire1 = new AnneeUniversitaire();
        anneeUniversitaire1.setId(1L);
        AnneeUniversitaire anneeUniversitaire2 = new AnneeUniversitaire();
        anneeUniversitaire2.setId(anneeUniversitaire1.getId());
        assertThat(anneeUniversitaire1).isEqualTo(anneeUniversitaire2);
        anneeUniversitaire2.setId(2L);
        assertThat(anneeUniversitaire1).isNotEqualTo(anneeUniversitaire2);
        anneeUniversitaire1.setId(null);
        assertThat(anneeUniversitaire1).isNotEqualTo(anneeUniversitaire2);
    }
}
