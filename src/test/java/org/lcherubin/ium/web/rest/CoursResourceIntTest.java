package org.lcherubin.ium.web.rest;

import org.lcherubin.ium.MathFinEcoUniversityApp;

import org.lcherubin.ium.domain.Cours;
import org.lcherubin.ium.repository.CoursRepository;
import org.lcherubin.ium.service.CoursService;
import org.lcherubin.ium.repository.search.CoursSearchRepository;
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
 * Test class for the CoursResource REST controller.
 *
 * @see CoursResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MathFinEcoUniversityApp.class)
public class CoursResourceIntTest {

    private static final String DEFAULT_CODE_UE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_UE = "BBBBBBBBBB";

    private static final String DEFAULT_INTITULE_UE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_UE = "BBBBBBBBBB";

    private static final String DEFAULT_SEMESTRE = "AAAAAAAAAA";
    private static final String UPDATED_SEMESTRE = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private CoursService coursService;

    @Autowired
    private CoursSearchRepository coursSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoursMockMvc;

    private Cours cours;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CoursResource coursResource = new CoursResource(coursService);
        this.restCoursMockMvc = MockMvcBuilders.standaloneSetup(coursResource)
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
    public static Cours createEntity(EntityManager em) {
        Cours cours = new Cours()
            .codeUE(DEFAULT_CODE_UE)
            .intituleUE(DEFAULT_INTITULE_UE)
            .semestre(DEFAULT_SEMESTRE)
            .duree(DEFAULT_DUREE);
        return cours;
    }

    @Before
    public void initTest() {
        coursSearchRepository.deleteAll();
        cours = createEntity(em);
    }

    @Test
    @Transactional
    public void createCours() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();

        // Create the Cours
        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isCreated());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate + 1);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getCodeUE()).isEqualTo(DEFAULT_CODE_UE);
        assertThat(testCours.getIntituleUE()).isEqualTo(DEFAULT_INTITULE_UE);
        assertThat(testCours.getSemestre()).isEqualTo(DEFAULT_SEMESTRE);
        assertThat(testCours.getDuree()).isEqualTo(DEFAULT_DUREE);

        // Validate the Cours in Elasticsearch
        Cours coursEs = coursSearchRepository.findOne(testCours.getId());
        assertThat(coursEs).isEqualToComparingFieldByField(testCours);
    }

    @Test
    @Transactional
    public void createCoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursRepository.findAll().size();

        // Create the Cours with an existing ID
        cours.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeUEIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setCodeUE(null);

        // Create the Cours, which fails.

        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntituleUEIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setIntituleUE(null);

        // Create the Cours, which fails.

        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemestreIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setSemestre(null);

        // Create the Cours, which fails.

        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDureeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursRepository.findAll().size();
        // set the field null
        cours.setDuree(null);

        // Create the Cours, which fails.

        restCoursMockMvc.perform(post("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isBadRequest());

        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get all the coursList
        restCoursMockMvc.perform(get("/api/cours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cours.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeUE").value(hasItem(DEFAULT_CODE_UE.toString())))
            .andExpect(jsonPath("$.[*].intituleUE").value(hasItem(DEFAULT_INTITULE_UE.toString())))
            .andExpect(jsonPath("$.[*].semestre").value(hasItem(DEFAULT_SEMESTRE.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.toString())));
    }

    @Test
    @Transactional
    public void getCours() throws Exception {
        // Initialize the database
        coursRepository.saveAndFlush(cours);

        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", cours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cours.getId().intValue()))
            .andExpect(jsonPath("$.codeUE").value(DEFAULT_CODE_UE.toString()))
            .andExpect(jsonPath("$.intituleUE").value(DEFAULT_INTITULE_UE.toString()))
            .andExpect(jsonPath("$.semestre").value(DEFAULT_SEMESTRE.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCours() throws Exception {
        // Get the cours
        restCoursMockMvc.perform(get("/api/cours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCours() throws Exception {
        // Initialize the database
        coursService.save(cours);

        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Update the cours
        Cours updatedCours = coursRepository.findOne(cours.getId());
        updatedCours
            .codeUE(UPDATED_CODE_UE)
            .intituleUE(UPDATED_INTITULE_UE)
            .semestre(UPDATED_SEMESTRE)
            .duree(UPDATED_DUREE);

        restCoursMockMvc.perform(put("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCours)))
            .andExpect(status().isOk());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate);
        Cours testCours = coursList.get(coursList.size() - 1);
        assertThat(testCours.getCodeUE()).isEqualTo(UPDATED_CODE_UE);
        assertThat(testCours.getIntituleUE()).isEqualTo(UPDATED_INTITULE_UE);
        assertThat(testCours.getSemestre()).isEqualTo(UPDATED_SEMESTRE);
        assertThat(testCours.getDuree()).isEqualTo(UPDATED_DUREE);

        // Validate the Cours in Elasticsearch
        Cours coursEs = coursSearchRepository.findOne(testCours.getId());
        assertThat(coursEs).isEqualToComparingFieldByField(testCours);
    }

    @Test
    @Transactional
    public void updateNonExistingCours() throws Exception {
        int databaseSizeBeforeUpdate = coursRepository.findAll().size();

        // Create the Cours

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoursMockMvc.perform(put("/api/cours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cours)))
            .andExpect(status().isCreated());

        // Validate the Cours in the database
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCours() throws Exception {
        // Initialize the database
        coursService.save(cours);

        int databaseSizeBeforeDelete = coursRepository.findAll().size();

        // Get the cours
        restCoursMockMvc.perform(delete("/api/cours/{id}", cours.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean coursExistsInEs = coursSearchRepository.exists(cours.getId());
        assertThat(coursExistsInEs).isFalse();

        // Validate the database is empty
        List<Cours> coursList = coursRepository.findAll();
        assertThat(coursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCours() throws Exception {
        // Initialize the database
        coursService.save(cours);

        // Search the cours
        restCoursMockMvc.perform(get("/api/_search/cours?query=id:" + cours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cours.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeUE").value(hasItem(DEFAULT_CODE_UE.toString())))
            .andExpect(jsonPath("$.[*].intituleUE").value(hasItem(DEFAULT_INTITULE_UE.toString())))
            .andExpect(jsonPath("$.[*].semestre").value(hasItem(DEFAULT_SEMESTRE.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cours.class);
        Cours cours1 = new Cours();
        cours1.setId(1L);
        Cours cours2 = new Cours();
        cours2.setId(cours1.getId());
        assertThat(cours1).isEqualTo(cours2);
        cours2.setId(2L);
        assertThat(cours1).isNotEqualTo(cours2);
        cours1.setId(null);
        assertThat(cours1).isNotEqualTo(cours2);
    }
}
