package org.lcherubin.ium.web.rest;

import org.lcherubin.ium.MathFinEcoUniversityApp;

import org.lcherubin.ium.domain.Enseignant;
import org.lcherubin.ium.repository.EnseignantRepository;
import org.lcherubin.ium.service.EnseignantService;
import org.lcherubin.ium.repository.search.EnseignantSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.lcherubin.ium.domain.enumeration.Sexe;
/**
 * Test class for the EnseignantResource REST controller.
 *
 * @see EnseignantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MathFinEcoUniversityApp.class)
public class EnseignantResourceIntTest {

    private static final String DEFAULT_PRENOMS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOMS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.MASCULIN;
    private static final Sexe UPDATED_SEXE = Sexe.FEMININ;

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISANCE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ENREGISTREMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENREGISTREMENT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private EnseignantSearchRepository enseignantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnseignantResource enseignantResource = new EnseignantResource(enseignantService);
        this.restEnseignantMockMvc = MockMvcBuilders.standaloneSetup(enseignantResource)
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
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .prenoms(DEFAULT_PRENOMS)
            .nom(DEFAULT_NOM)
            .sexe(DEFAULT_SEXE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaisance(DEFAULT_LIEU_NAISANCE)
            .titre(DEFAULT_TITRE)
            .dateEnregistrement(DEFAULT_DATE_ENREGISTREMENT);
        return enseignant;
    }

    @Before
    public void initTest() {
        enseignantSearchRepository.deleteAll();
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testEnseignant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEnseignant.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testEnseignant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEnseignant.getLieuNaisance()).isEqualTo(DEFAULT_LIEU_NAISANCE);
        assertThat(testEnseignant.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testEnseignant.getDateEnregistrement()).isEqualTo(DEFAULT_DATE_ENREGISTREMENT);

        // Validate the Enseignant in Elasticsearch
        Enseignant enseignantEs = enseignantSearchRepository.findOne(testEnseignant.getId());
        assertThat(enseignantEs).isEqualToComparingFieldByField(testEnseignant);
    }

    @Test
    @Transactional
    public void createEnseignantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant with an existing ID
        enseignant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setPrenoms(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setNom(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setSexe(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setDateNaissance(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaisanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setLieuNaisance(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaisance").value(hasItem(DEFAULT_LIEU_NAISANCE.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].dateEnregistrement").value(hasItem(DEFAULT_DATE_ENREGISTREMENT.toString())));
    }

    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId().intValue()))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaisance").value(DEFAULT_LIEU_NAISANCE.toString()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.dateEnregistrement").value(DEFAULT_DATE_ENREGISTREMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantService.save(enseignant);

        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findOne(enseignant.getId());
        updatedEnseignant
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .sexe(UPDATED_SEXE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaisance(UPDATED_LIEU_NAISANCE)
            .titre(UPDATED_TITRE)
            .dateEnregistrement(UPDATED_DATE_ENREGISTREMENT);

        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnseignant)))
            .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEnseignant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEnseignant.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testEnseignant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEnseignant.getLieuNaisance()).isEqualTo(UPDATED_LIEU_NAISANCE);
        assertThat(testEnseignant.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEnseignant.getDateEnregistrement()).isEqualTo(UPDATED_DATE_ENREGISTREMENT);

        // Validate the Enseignant in Elasticsearch
        Enseignant enseignantEs = enseignantSearchRepository.findOne(testEnseignant.getId());
        assertThat(enseignantEs).isEqualToComparingFieldByField(testEnseignant);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseignant() throws Exception {
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Create the Enseignant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignant)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantService.save(enseignant);

        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Get the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean enseignantExistsInEs = enseignantSearchRepository.exists(enseignant.getId());
        assertThat(enseignantExistsInEs).isFalse();

        // Validate the database is empty
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnseignant() throws Exception {
        // Initialize the database
        enseignantService.save(enseignant);

        // Search the enseignant
        restEnseignantMockMvc.perform(get("/api/_search/enseignants?query=id:" + enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaisance").value(hasItem(DEFAULT_LIEU_NAISANCE.toString())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].dateEnregistrement").value(hasItem(DEFAULT_DATE_ENREGISTREMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enseignant.class);
        Enseignant enseignant1 = new Enseignant();
        enseignant1.setId(1L);
        Enseignant enseignant2 = new Enseignant();
        enseignant2.setId(enseignant1.getId());
        assertThat(enseignant1).isEqualTo(enseignant2);
        enseignant2.setId(2L);
        assertThat(enseignant1).isNotEqualTo(enseignant2);
        enseignant1.setId(null);
        assertThat(enseignant1).isNotEqualTo(enseignant2);
    }
}
