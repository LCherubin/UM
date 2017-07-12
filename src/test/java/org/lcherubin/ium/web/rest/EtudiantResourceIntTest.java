package org.lcherubin.ium.web.rest;

import org.lcherubin.ium.MathFinEcoUniversityApp;

import org.lcherubin.ium.domain.Etudiant;
import org.lcherubin.ium.repository.EtudiantRepository;
import org.lcherubin.ium.service.EtudiantService;
import org.lcherubin.ium.repository.search.EtudiantSearchRepository;
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
import org.lcherubin.ium.domain.enumeration.Niveau;
import org.lcherubin.ium.domain.enumeration.Statut;
import org.lcherubin.ium.domain.enumeration.StatInscription;
/**
 * Test class for the EtudiantResource REST controller.
 *
 * @see EtudiantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MathFinEcoUniversityApp.class)
public class EtudiantResourceIntTest {

    private static final Long DEFAULT_MATRICULE = 1L;
    private static final Long UPDATED_MATRICULE = 2L;

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

    private static final String DEFAULT_PROMOTION = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTION = "BBBBBBBBBB";

    private static final Niveau DEFAULT_NIVEAU = Niveau.PREPA;
    private static final Niveau UPDATED_NIVEAU = Niveau.LICENCE;

    private static final LocalDate DEFAULT_DATE_INSCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INSCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final Statut DEFAULT_STATUT = Statut.SPONSORING;
    private static final Statut UPDATED_STATUT = Statut.DEMI_BOURSIER;

    private static final StatInscription DEFAULT_STATUT_INSCRIPTION = StatInscription.PREINSCRIPTION;
    private static final StatInscription UPDATED_STATUT_INSCRIPTION = StatInscription.EN_COURS;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantSearchRepository etudiantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtudiantMockMvc;

    private Etudiant etudiant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EtudiantResource etudiantResource = new EtudiantResource(etudiantService);
        this.restEtudiantMockMvc = MockMvcBuilders.standaloneSetup(etudiantResource)
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
    public static Etudiant createEntity(EntityManager em) {
        Etudiant etudiant = new Etudiant()
            .matricule(DEFAULT_MATRICULE)
            .prenoms(DEFAULT_PRENOMS)
            .nom(DEFAULT_NOM)
            .sexe(DEFAULT_SEXE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaisance(DEFAULT_LIEU_NAISANCE)
            .promotion(DEFAULT_PROMOTION)
            .niveau(DEFAULT_NIVEAU)
            .dateInscription(DEFAULT_DATE_INSCRIPTION)
            .statut(DEFAULT_STATUT)
            .statutInscription(DEFAULT_STATUT_INSCRIPTION);
        return etudiant;
    }

    @Before
    public void initTest() {
        etudiantSearchRepository.deleteAll();
        etudiant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtudiant() throws Exception {
        int databaseSizeBeforeCreate = etudiantRepository.findAll().size();

        // Create the Etudiant
        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isCreated());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeCreate + 1);
        Etudiant testEtudiant = etudiantList.get(etudiantList.size() - 1);
        assertThat(testEtudiant.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEtudiant.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testEtudiant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEtudiant.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testEtudiant.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEtudiant.getLieuNaisance()).isEqualTo(DEFAULT_LIEU_NAISANCE);
        assertThat(testEtudiant.getPromotion()).isEqualTo(DEFAULT_PROMOTION);
        assertThat(testEtudiant.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testEtudiant.getDateInscription()).isEqualTo(DEFAULT_DATE_INSCRIPTION);
        assertThat(testEtudiant.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testEtudiant.getStatutInscription()).isEqualTo(DEFAULT_STATUT_INSCRIPTION);

        // Validate the Etudiant in Elasticsearch
        Etudiant etudiantEs = etudiantSearchRepository.findOne(testEtudiant.getId());
        assertThat(etudiantEs).isEqualToComparingFieldByField(testEtudiant);
    }

    @Test
    @Transactional
    public void createEtudiantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etudiantRepository.findAll().size();

        // Create the Etudiant with an existing ID
        etudiant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setMatricule(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setPrenoms(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setNom(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setSexe(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setDateNaissance(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaisanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setLieuNaisance(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPromotionIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setPromotion(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isBadRequest());

        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtudiants() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList
        restEtudiantMockMvc.perform(get("/api/etudiants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaisance").value(hasItem(DEFAULT_LIEU_NAISANCE.toString())))
            .andExpect(jsonPath("$.[*].promotion").value(hasItem(DEFAULT_PROMOTION.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].dateInscription").value(hasItem(DEFAULT_DATE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].statutInscription").value(hasItem(DEFAULT_STATUT_INSCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", etudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etudiant.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.intValue()))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaisance").value(DEFAULT_LIEU_NAISANCE.toString()))
            .andExpect(jsonPath("$.promotion").value(DEFAULT_PROMOTION.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()))
            .andExpect(jsonPath("$.dateInscription").value(DEFAULT_DATE_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.statutInscription").value(DEFAULT_STATUT_INSCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtudiant() throws Exception {
        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtudiant() throws Exception {
        // Initialize the database
        etudiantService.save(etudiant);

        int databaseSizeBeforeUpdate = etudiantRepository.findAll().size();

        // Update the etudiant
        Etudiant updatedEtudiant = etudiantRepository.findOne(etudiant.getId());
        updatedEtudiant
            .matricule(UPDATED_MATRICULE)
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .sexe(UPDATED_SEXE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaisance(UPDATED_LIEU_NAISANCE)
            .promotion(UPDATED_PROMOTION)
            .niveau(UPDATED_NIVEAU)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .statut(UPDATED_STATUT)
            .statutInscription(UPDATED_STATUT_INSCRIPTION);

        restEtudiantMockMvc.perform(put("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtudiant)))
            .andExpect(status().isOk());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeUpdate);
        Etudiant testEtudiant = etudiantList.get(etudiantList.size() - 1);
        assertThat(testEtudiant.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtudiant.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEtudiant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEtudiant.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testEtudiant.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEtudiant.getLieuNaisance()).isEqualTo(UPDATED_LIEU_NAISANCE);
        assertThat(testEtudiant.getPromotion()).isEqualTo(UPDATED_PROMOTION);
        assertThat(testEtudiant.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testEtudiant.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testEtudiant.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testEtudiant.getStatutInscription()).isEqualTo(UPDATED_STATUT_INSCRIPTION);

        // Validate the Etudiant in Elasticsearch
        Etudiant etudiantEs = etudiantSearchRepository.findOne(testEtudiant.getId());
        assertThat(etudiantEs).isEqualToComparingFieldByField(testEtudiant);
    }

    @Test
    @Transactional
    public void updateNonExistingEtudiant() throws Exception {
        int databaseSizeBeforeUpdate = etudiantRepository.findAll().size();

        // Create the Etudiant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtudiantMockMvc.perform(put("/api/etudiants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiant)))
            .andExpect(status().isCreated());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtudiant() throws Exception {
        // Initialize the database
        etudiantService.save(etudiant);

        int databaseSizeBeforeDelete = etudiantRepository.findAll().size();

        // Get the etudiant
        restEtudiantMockMvc.perform(delete("/api/etudiants/{id}", etudiant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean etudiantExistsInEs = etudiantSearchRepository.exists(etudiant.getId());
        assertThat(etudiantExistsInEs).isFalse();

        // Validate the database is empty
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEtudiant() throws Exception {
        // Initialize the database
        etudiantService.save(etudiant);

        // Search the etudiant
        restEtudiantMockMvc.perform(get("/api/_search/etudiants?query=id:" + etudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaisance").value(hasItem(DEFAULT_LIEU_NAISANCE.toString())))
            .andExpect(jsonPath("$.[*].promotion").value(hasItem(DEFAULT_PROMOTION.toString())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].dateInscription").value(hasItem(DEFAULT_DATE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].statutInscription").value(hasItem(DEFAULT_STATUT_INSCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etudiant.class);
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setId(1L);
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setId(etudiant1.getId());
        assertThat(etudiant1).isEqualTo(etudiant2);
        etudiant2.setId(2L);
        assertThat(etudiant1).isNotEqualTo(etudiant2);
        etudiant1.setId(null);
        assertThat(etudiant1).isNotEqualTo(etudiant2);
    }
}
