package org.lcherubin.ium.web.rest;

import org.lcherubin.ium.MathFinEcoUniversityApp;

import org.lcherubin.ium.domain.Parent;
import org.lcherubin.ium.repository.ParentRepository;
import org.lcherubin.ium.service.ParentService;
import org.lcherubin.ium.repository.search.ParentSearchRepository;
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

import org.lcherubin.ium.domain.enumeration.Sexe;
/**
 * Test class for the ParentResource REST controller.
 *
 * @see ParentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MathFinEcoUniversityApp.class)
public class ParentResourceIntTest {

    private static final String DEFAULT_PRENOMS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOMS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.MASCULIN;
    private static final Sexe UPDATED_SEXE = Sexe.FEMININ;

    private static final String DEFAULT_LIEN = "AAAAAAAAAA";
    private static final String UPDATED_LIEN = "BBBBBBBBBB";

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentService parentService;

    @Autowired
    private ParentSearchRepository parentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParentMockMvc;

    private Parent parent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParentResource parentResource = new ParentResource(parentService);
        this.restParentMockMvc = MockMvcBuilders.standaloneSetup(parentResource)
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
    public static Parent createEntity(EntityManager em) {
        Parent parent = new Parent()
            .prenoms(DEFAULT_PRENOMS)
            .nom(DEFAULT_NOM)
            .sexe(DEFAULT_SEXE)
            .lien(DEFAULT_LIEN);
        return parent;
    }

    @Before
    public void initTest() {
        parentSearchRepository.deleteAll();
        parent = createEntity(em);
    }

    @Test
    @Transactional
    public void createParent() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // Create the Parent
        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate + 1);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testParent.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testParent.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testParent.getLien()).isEqualTo(DEFAULT_LIEN);

        // Validate the Parent in Elasticsearch
        Parent parentEs = parentSearchRepository.findOne(testParent.getId());
        assertThat(parentEs).isEqualToComparingFieldByField(testParent);
    }

    @Test
    @Transactional
    public void createParentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // Create the Parent with an existing ID
        parent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setPrenoms(null);

        // Create the Parent, which fails.

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setNom(null);

        // Create the Parent, which fails.

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setSexe(null);

        // Create the Parent, which fails.

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLienIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setLien(null);

        // Create the Parent, which fails.

        restParentMockMvc.perform(post("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParents() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get all the parentList
        restParentMockMvc.perform(get("/api/parents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())));
    }

    @Test
    @Transactional
    public void getParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get the parent
        restParentMockMvc.perform(get("/api/parents/{id}", parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parent.getId().intValue()))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.lien").value(DEFAULT_LIEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParent() throws Exception {
        // Get the parent
        restParentMockMvc.perform(get("/api/parents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParent() throws Exception {
        // Initialize the database
        parentService.save(parent);

        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Update the parent
        Parent updatedParent = parentRepository.findOne(parent.getId());
        updatedParent
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .sexe(UPDATED_SEXE)
            .lien(UPDATED_LIEN);

        restParentMockMvc.perform(put("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParent)))
            .andExpect(status().isOk());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testParent.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testParent.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testParent.getLien()).isEqualTo(UPDATED_LIEN);

        // Validate the Parent in Elasticsearch
        Parent parentEs = parentSearchRepository.findOne(testParent.getId());
        assertThat(parentEs).isEqualToComparingFieldByField(testParent);
    }

    @Test
    @Transactional
    public void updateNonExistingParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Create the Parent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParentMockMvc.perform(put("/api/parents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parent)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParent() throws Exception {
        // Initialize the database
        parentService.save(parent);

        int databaseSizeBeforeDelete = parentRepository.findAll().size();

        // Get the parent
        restParentMockMvc.perform(delete("/api/parents/{id}", parent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean parentExistsInEs = parentSearchRepository.exists(parent.getId());
        assertThat(parentExistsInEs).isFalse();

        // Validate the database is empty
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchParent() throws Exception {
        // Initialize the database
        parentService.save(parent);

        // Search the parent
        restParentMockMvc.perform(get("/api/_search/parents?query=id:" + parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].lien").value(hasItem(DEFAULT_LIEN.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parent.class);
        Parent parent1 = new Parent();
        parent1.setId(1L);
        Parent parent2 = new Parent();
        parent2.setId(parent1.getId());
        assertThat(parent1).isEqualTo(parent2);
        parent2.setId(2L);
        assertThat(parent1).isNotEqualTo(parent2);
        parent1.setId(null);
        assertThat(parent1).isNotEqualTo(parent2);
    }
}
