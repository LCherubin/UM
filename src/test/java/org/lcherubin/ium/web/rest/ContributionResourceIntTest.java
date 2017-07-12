package org.lcherubin.ium.web.rest;

import org.lcherubin.ium.MathFinEcoUniversityApp;

import org.lcherubin.ium.domain.Contribution;
import org.lcherubin.ium.repository.ContributionRepository;
import org.lcherubin.ium.service.ContributionService;
import org.lcherubin.ium.repository.search.ContributionSearchRepository;
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

import org.lcherubin.ium.domain.enumeration.Niveau;
/**
 * Test class for the ContributionResource REST controller.
 *
 * @see ContributionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MathFinEcoUniversityApp.class)
public class ContributionResourceIntTest {

    private static final Niveau DEFAULT_NIVEAU = Niveau.PREPA;
    private static final Niveau UPDATED_NIVEAU = Niveau.LICENCE;

    private static final Double DEFAULT_FRAIS_INSCIPRION = 1D;
    private static final Double UPDATED_FRAIS_INSCIPRION = 2D;

    private static final Double DEFAULT_MONTANT_TOTAL = 1D;
    private static final Double UPDATED_MONTANT_TOTAL = 2D;

    private static final Double DEFAULT_MONTANT_TRANCHE_1 = 1D;
    private static final Double UPDATED_MONTANT_TRANCHE_1 = 2D;

    private static final Double DEFAULT_MONTANT_TRANCHE_2 = 1D;
    private static final Double UPDATED_MONTANT_TRANCHE_2 = 2D;

    private static final Double DEFAULT_MONTANT_PAYER_1 = 1D;
    private static final Double UPDATED_MONTANT_PAYER_1 = 2D;

    private static final Double DEFAULT_MONTANT_PAYER_2 = 1D;
    private static final Double UPDATED_MONTANT_PAYER_2 = 2D;

    private static final Double DEFAULT_TOTAL_PAYER = 1D;
    private static final Double UPDATED_TOTAL_PAYER = 2D;

    private static final LocalDate DEFAULT_DATE_PAYMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PAYMENT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private ContributionSearchRepository contributionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContributionMockMvc;

    private Contribution contribution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContributionResource contributionResource = new ContributionResource(contributionService);
        this.restContributionMockMvc = MockMvcBuilders.standaloneSetup(contributionResource)
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
    public static Contribution createEntity(EntityManager em) {
        Contribution contribution = new Contribution()
            .niveau(DEFAULT_NIVEAU)
            .fraisInsciprion(DEFAULT_FRAIS_INSCIPRION)
            .montantTotal(DEFAULT_MONTANT_TOTAL)
            .montantTranche1(DEFAULT_MONTANT_TRANCHE_1)
            .montantTranche2(DEFAULT_MONTANT_TRANCHE_2)
            .montantPayer1(DEFAULT_MONTANT_PAYER_1)
            .montantPayer2(DEFAULT_MONTANT_PAYER_2)
            .totalPayer(DEFAULT_TOTAL_PAYER)
            .datePayment(DEFAULT_DATE_PAYMENT);
        return contribution;
    }

    @Before
    public void initTest() {
        contributionSearchRepository.deleteAll();
        contribution = createEntity(em);
    }

    @Test
    @Transactional
    public void createContribution() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // Create the Contribution
        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isCreated());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate + 1);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testContribution.getFraisInsciprion()).isEqualTo(DEFAULT_FRAIS_INSCIPRION);
        assertThat(testContribution.getMontantTotal()).isEqualTo(DEFAULT_MONTANT_TOTAL);
        assertThat(testContribution.getMontantTranche1()).isEqualTo(DEFAULT_MONTANT_TRANCHE_1);
        assertThat(testContribution.getMontantTranche2()).isEqualTo(DEFAULT_MONTANT_TRANCHE_2);
        assertThat(testContribution.getMontantPayer1()).isEqualTo(DEFAULT_MONTANT_PAYER_1);
        assertThat(testContribution.getMontantPayer2()).isEqualTo(DEFAULT_MONTANT_PAYER_2);
        assertThat(testContribution.getTotalPayer()).isEqualTo(DEFAULT_TOTAL_PAYER);
        assertThat(testContribution.getDatePayment()).isEqualTo(DEFAULT_DATE_PAYMENT);

        // Validate the Contribution in Elasticsearch
        Contribution contributionEs = contributionSearchRepository.findOne(testContribution.getId());
        assertThat(contributionEs).isEqualToComparingFieldByField(testContribution);
    }

    @Test
    @Transactional
    public void createContributionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contributionRepository.findAll().size();

        // Create the Contribution with an existing ID
        contribution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNiveauIsRequired() throws Exception {
        int databaseSizeBeforeTest = contributionRepository.findAll().size();
        // set the field null
        contribution.setNiveau(null);

        // Create the Contribution, which fails.

        restContributionMockMvc.perform(post("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isBadRequest());

        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContributions() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get all the contributionList
        restContributionMockMvc.perform(get("/api/contributions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].fraisInsciprion").value(hasItem(DEFAULT_FRAIS_INSCIPRION.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTotal").value(hasItem(DEFAULT_MONTANT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTranche1").value(hasItem(DEFAULT_MONTANT_TRANCHE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTranche2").value(hasItem(DEFAULT_MONTANT_TRANCHE_2.doubleValue())))
            .andExpect(jsonPath("$.[*].montantPayer1").value(hasItem(DEFAULT_MONTANT_PAYER_1.doubleValue())))
            .andExpect(jsonPath("$.[*].montantPayer2").value(hasItem(DEFAULT_MONTANT_PAYER_2.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPayer").value(hasItem(DEFAULT_TOTAL_PAYER.doubleValue())))
            .andExpect(jsonPath("$.[*].datePayment").value(hasItem(DEFAULT_DATE_PAYMENT.toString())));
    }

    @Test
    @Transactional
    public void getContribution() throws Exception {
        // Initialize the database
        contributionRepository.saveAndFlush(contribution);

        // Get the contribution
        restContributionMockMvc.perform(get("/api/contributions/{id}", contribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contribution.getId().intValue()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()))
            .andExpect(jsonPath("$.fraisInsciprion").value(DEFAULT_FRAIS_INSCIPRION.doubleValue()))
            .andExpect(jsonPath("$.montantTotal").value(DEFAULT_MONTANT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.montantTranche1").value(DEFAULT_MONTANT_TRANCHE_1.doubleValue()))
            .andExpect(jsonPath("$.montantTranche2").value(DEFAULT_MONTANT_TRANCHE_2.doubleValue()))
            .andExpect(jsonPath("$.montantPayer1").value(DEFAULT_MONTANT_PAYER_1.doubleValue()))
            .andExpect(jsonPath("$.montantPayer2").value(DEFAULT_MONTANT_PAYER_2.doubleValue()))
            .andExpect(jsonPath("$.totalPayer").value(DEFAULT_TOTAL_PAYER.doubleValue()))
            .andExpect(jsonPath("$.datePayment").value(DEFAULT_DATE_PAYMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContribution() throws Exception {
        // Get the contribution
        restContributionMockMvc.perform(get("/api/contributions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContribution() throws Exception {
        // Initialize the database
        contributionService.save(contribution);

        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Update the contribution
        Contribution updatedContribution = contributionRepository.findOne(contribution.getId());
        updatedContribution
            .niveau(UPDATED_NIVEAU)
            .fraisInsciprion(UPDATED_FRAIS_INSCIPRION)
            .montantTotal(UPDATED_MONTANT_TOTAL)
            .montantTranche1(UPDATED_MONTANT_TRANCHE_1)
            .montantTranche2(UPDATED_MONTANT_TRANCHE_2)
            .montantPayer1(UPDATED_MONTANT_PAYER_1)
            .montantPayer2(UPDATED_MONTANT_PAYER_2)
            .totalPayer(UPDATED_TOTAL_PAYER)
            .datePayment(UPDATED_DATE_PAYMENT);

        restContributionMockMvc.perform(put("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContribution)))
            .andExpect(status().isOk());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate);
        Contribution testContribution = contributionList.get(contributionList.size() - 1);
        assertThat(testContribution.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testContribution.getFraisInsciprion()).isEqualTo(UPDATED_FRAIS_INSCIPRION);
        assertThat(testContribution.getMontantTotal()).isEqualTo(UPDATED_MONTANT_TOTAL);
        assertThat(testContribution.getMontantTranche1()).isEqualTo(UPDATED_MONTANT_TRANCHE_1);
        assertThat(testContribution.getMontantTranche2()).isEqualTo(UPDATED_MONTANT_TRANCHE_2);
        assertThat(testContribution.getMontantPayer1()).isEqualTo(UPDATED_MONTANT_PAYER_1);
        assertThat(testContribution.getMontantPayer2()).isEqualTo(UPDATED_MONTANT_PAYER_2);
        assertThat(testContribution.getTotalPayer()).isEqualTo(UPDATED_TOTAL_PAYER);
        assertThat(testContribution.getDatePayment()).isEqualTo(UPDATED_DATE_PAYMENT);

        // Validate the Contribution in Elasticsearch
        Contribution contributionEs = contributionSearchRepository.findOne(testContribution.getId());
        assertThat(contributionEs).isEqualToComparingFieldByField(testContribution);
    }

    @Test
    @Transactional
    public void updateNonExistingContribution() throws Exception {
        int databaseSizeBeforeUpdate = contributionRepository.findAll().size();

        // Create the Contribution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContributionMockMvc.perform(put("/api/contributions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contribution)))
            .andExpect(status().isCreated());

        // Validate the Contribution in the database
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContribution() throws Exception {
        // Initialize the database
        contributionService.save(contribution);

        int databaseSizeBeforeDelete = contributionRepository.findAll().size();

        // Get the contribution
        restContributionMockMvc.perform(delete("/api/contributions/{id}", contribution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean contributionExistsInEs = contributionSearchRepository.exists(contribution.getId());
        assertThat(contributionExistsInEs).isFalse();

        // Validate the database is empty
        List<Contribution> contributionList = contributionRepository.findAll();
        assertThat(contributionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchContribution() throws Exception {
        // Initialize the database
        contributionService.save(contribution);

        // Search the contribution
        restContributionMockMvc.perform(get("/api/_search/contributions?query=id:" + contribution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contribution.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
            .andExpect(jsonPath("$.[*].fraisInsciprion").value(hasItem(DEFAULT_FRAIS_INSCIPRION.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTotal").value(hasItem(DEFAULT_MONTANT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTranche1").value(hasItem(DEFAULT_MONTANT_TRANCHE_1.doubleValue())))
            .andExpect(jsonPath("$.[*].montantTranche2").value(hasItem(DEFAULT_MONTANT_TRANCHE_2.doubleValue())))
            .andExpect(jsonPath("$.[*].montantPayer1").value(hasItem(DEFAULT_MONTANT_PAYER_1.doubleValue())))
            .andExpect(jsonPath("$.[*].montantPayer2").value(hasItem(DEFAULT_MONTANT_PAYER_2.doubleValue())))
            .andExpect(jsonPath("$.[*].totalPayer").value(hasItem(DEFAULT_TOTAL_PAYER.doubleValue())))
            .andExpect(jsonPath("$.[*].datePayment").value(hasItem(DEFAULT_DATE_PAYMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contribution.class);
        Contribution contribution1 = new Contribution();
        contribution1.setId(1L);
        Contribution contribution2 = new Contribution();
        contribution2.setId(contribution1.getId());
        assertThat(contribution1).isEqualTo(contribution2);
        contribution2.setId(2L);
        assertThat(contribution1).isNotEqualTo(contribution2);
        contribution1.setId(null);
        assertThat(contribution1).isNotEqualTo(contribution2);
    }
}
