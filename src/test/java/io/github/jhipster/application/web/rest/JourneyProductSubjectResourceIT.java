package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.JourneyProductSubject;
import io.github.jhipster.application.repository.JourneyProductSubjectRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JourneyProductSubjectResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class JourneyProductSubjectResourceIT {

    private static final Integer DEFAULT_SLA = 1;
    private static final Integer UPDATED_SLA = 2;
    private static final Integer SMALLER_SLA = 1 - 1;

    private static final Integer DEFAULT_SLA_TYPE = 1;
    private static final Integer UPDATED_SLA_TYPE = 2;
    private static final Integer SMALLER_SLA_TYPE = 1 - 1;

    @Autowired
    private JourneyProductSubjectRepository journeyProductSubjectRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restJourneyProductSubjectMockMvc;

    private JourneyProductSubject journeyProductSubject;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourneyProductSubjectResource journeyProductSubjectResource = new JourneyProductSubjectResource(journeyProductSubjectRepository);
        this.restJourneyProductSubjectMockMvc = MockMvcBuilders.standaloneSetup(journeyProductSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyProductSubject createEntity(EntityManager em) {
        JourneyProductSubject journeyProductSubject = new JourneyProductSubject()
            .sla(DEFAULT_SLA)
            .slaType(DEFAULT_SLA_TYPE);
        return journeyProductSubject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyProductSubject createUpdatedEntity(EntityManager em) {
        JourneyProductSubject journeyProductSubject = new JourneyProductSubject()
            .sla(UPDATED_SLA)
            .slaType(UPDATED_SLA_TYPE);
        return journeyProductSubject;
    }

    @BeforeEach
    public void initTest() {
        journeyProductSubject = createEntity(em);
    }

    @Test
    @Transactional
    public void createJourneyProductSubject() throws Exception {
        int databaseSizeBeforeCreate = journeyProductSubjectRepository.findAll().size();

        // Create the JourneyProductSubject
        restJourneyProductSubjectMockMvc.perform(post("/api/journey-product-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyProductSubject)))
            .andExpect(status().isCreated());

        // Validate the JourneyProductSubject in the database
        List<JourneyProductSubject> journeyProductSubjectList = journeyProductSubjectRepository.findAll();
        assertThat(journeyProductSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        JourneyProductSubject testJourneyProductSubject = journeyProductSubjectList.get(journeyProductSubjectList.size() - 1);
        assertThat(testJourneyProductSubject.getSla()).isEqualTo(DEFAULT_SLA);
        assertThat(testJourneyProductSubject.getSlaType()).isEqualTo(DEFAULT_SLA_TYPE);
    }

    @Test
    @Transactional
    public void createJourneyProductSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journeyProductSubjectRepository.findAll().size();

        // Create the JourneyProductSubject with an existing ID
        journeyProductSubject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyProductSubjectMockMvc.perform(post("/api/journey-product-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyProductSubject)))
            .andExpect(status().isBadRequest());

        // Validate the JourneyProductSubject in the database
        List<JourneyProductSubject> journeyProductSubjectList = journeyProductSubjectRepository.findAll();
        assertThat(journeyProductSubjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJourneyProductSubjects() throws Exception {
        // Initialize the database
        journeyProductSubjectRepository.saveAndFlush(journeyProductSubject);

        // Get all the journeyProductSubjectList
        restJourneyProductSubjectMockMvc.perform(get("/api/journey-product-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journeyProductSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].sla").value(hasItem(DEFAULT_SLA)))
            .andExpect(jsonPath("$.[*].slaType").value(hasItem(DEFAULT_SLA_TYPE)));
    }
    
    @Test
    @Transactional
    public void getJourneyProductSubject() throws Exception {
        // Initialize the database
        journeyProductSubjectRepository.saveAndFlush(journeyProductSubject);

        // Get the journeyProductSubject
        restJourneyProductSubjectMockMvc.perform(get("/api/journey-product-subjects/{id}", journeyProductSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journeyProductSubject.getId().intValue()))
            .andExpect(jsonPath("$.sla").value(DEFAULT_SLA))
            .andExpect(jsonPath("$.slaType").value(DEFAULT_SLA_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingJourneyProductSubject() throws Exception {
        // Get the journeyProductSubject
        restJourneyProductSubjectMockMvc.perform(get("/api/journey-product-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJourneyProductSubject() throws Exception {
        // Initialize the database
        journeyProductSubjectRepository.saveAndFlush(journeyProductSubject);

        int databaseSizeBeforeUpdate = journeyProductSubjectRepository.findAll().size();

        // Update the journeyProductSubject
        JourneyProductSubject updatedJourneyProductSubject = journeyProductSubjectRepository.findById(journeyProductSubject.getId()).get();
        // Disconnect from session so that the updates on updatedJourneyProductSubject are not directly saved in db
        em.detach(updatedJourneyProductSubject);
        updatedJourneyProductSubject
            .sla(UPDATED_SLA)
            .slaType(UPDATED_SLA_TYPE);

        restJourneyProductSubjectMockMvc.perform(put("/api/journey-product-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJourneyProductSubject)))
            .andExpect(status().isOk());

        // Validate the JourneyProductSubject in the database
        List<JourneyProductSubject> journeyProductSubjectList = journeyProductSubjectRepository.findAll();
        assertThat(journeyProductSubjectList).hasSize(databaseSizeBeforeUpdate);
        JourneyProductSubject testJourneyProductSubject = journeyProductSubjectList.get(journeyProductSubjectList.size() - 1);
        assertThat(testJourneyProductSubject.getSla()).isEqualTo(UPDATED_SLA);
        assertThat(testJourneyProductSubject.getSlaType()).isEqualTo(UPDATED_SLA_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingJourneyProductSubject() throws Exception {
        int databaseSizeBeforeUpdate = journeyProductSubjectRepository.findAll().size();

        // Create the JourneyProductSubject

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyProductSubjectMockMvc.perform(put("/api/journey-product-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journeyProductSubject)))
            .andExpect(status().isBadRequest());

        // Validate the JourneyProductSubject in the database
        List<JourneyProductSubject> journeyProductSubjectList = journeyProductSubjectRepository.findAll();
        assertThat(journeyProductSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJourneyProductSubject() throws Exception {
        // Initialize the database
        journeyProductSubjectRepository.saveAndFlush(journeyProductSubject);

        int databaseSizeBeforeDelete = journeyProductSubjectRepository.findAll().size();

        // Delete the journeyProductSubject
        restJourneyProductSubjectMockMvc.perform(delete("/api/journey-product-subjects/{id}", journeyProductSubject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourneyProductSubject> journeyProductSubjectList = journeyProductSubjectRepository.findAll();
        assertThat(journeyProductSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyProductSubject.class);
        JourneyProductSubject journeyProductSubject1 = new JourneyProductSubject();
        journeyProductSubject1.setId(1L);
        JourneyProductSubject journeyProductSubject2 = new JourneyProductSubject();
        journeyProductSubject2.setId(journeyProductSubject1.getId());
        assertThat(journeyProductSubject1).isEqualTo(journeyProductSubject2);
        journeyProductSubject2.setId(2L);
        assertThat(journeyProductSubject1).isNotEqualTo(journeyProductSubject2);
        journeyProductSubject1.setId(null);
        assertThat(journeyProductSubject1).isNotEqualTo(journeyProductSubject2);
    }
}
