package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.Occurrence;
import io.github.jhipster.application.repository.OccurrenceRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OccurrenceResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class OccurrenceResourceIT {

    private static final Integer DEFAULT_OCCURRENCE_CODE_NBR = 1;
    private static final Integer UPDATED_OCCURRENCE_CODE_NBR = 2;
    private static final Integer SMALLER_OCCURRENCE_CODE_NBR = 1 - 1;

    private static final Integer DEFAULT_CPF = 1;
    private static final Integer UPDATED_CPF = 2;
    private static final Integer SMALLER_CPF = 1 - 1;

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;
    private static final Integer SMALLER_CUSTOMER_ID = 1 - 1;

    private static final Integer DEFAULT_EMPLOYEE_ID = 1;
    private static final Integer UPDATED_EMPLOYEE_ID = 2;
    private static final Integer SMALLER_EMPLOYEE_ID = 1 - 1;

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEDIA = 1;
    private static final Integer UPDATED_MEDIA = 2;
    private static final Integer SMALLER_MEDIA = 1 - 1;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final Integer SMALLER_TYPE = 1 - 1;

    private static final String DEFAULT_FINAL_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ENTRY_WAY = "AAAAAAAAAA";
    private static final String UPDATED_ENTRY_WAY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AGILITY = false;
    private static final Boolean UPDATED_AGILITY = true;

    private static final Instant DEFAULT_PREDICTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PREDICTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_PREDICTION = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_RESOLVE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESOLVE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_RESOLVE = Instant.ofEpochMilli(-1L);

    @Autowired
    private OccurrenceRepository occurrenceRepository;

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

    private MockMvc restOccurrenceMockMvc;

    private Occurrence occurrence;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OccurrenceResource occurrenceResource = new OccurrenceResource(occurrenceRepository);
        this.restOccurrenceMockMvc = MockMvcBuilders.standaloneSetup(occurrenceResource)
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
    public static Occurrence createEntity(EntityManager em) {
        Occurrence occurrence = new Occurrence()
            .occurrenceCodeNbr(DEFAULT_OCCURRENCE_CODE_NBR)
            .cpf(DEFAULT_CPF)
            .customerId(DEFAULT_CUSTOMER_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .media(DEFAULT_MEDIA)
            .type(DEFAULT_TYPE)
            .finalComment(DEFAULT_FINAL_COMMENT)
            .entryWay(DEFAULT_ENTRY_WAY)
            .agility(DEFAULT_AGILITY)
            .prediction(DEFAULT_PREDICTION)
            .resolve(DEFAULT_RESOLVE);
        return occurrence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Occurrence createUpdatedEntity(EntityManager em) {
        Occurrence occurrence = new Occurrence()
            .occurrenceCodeNbr(UPDATED_OCCURRENCE_CODE_NBR)
            .cpf(UPDATED_CPF)
            .customerId(UPDATED_CUSTOMER_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .media(UPDATED_MEDIA)
            .type(UPDATED_TYPE)
            .finalComment(UPDATED_FINAL_COMMENT)
            .entryWay(UPDATED_ENTRY_WAY)
            .agility(UPDATED_AGILITY)
            .prediction(UPDATED_PREDICTION)
            .resolve(UPDATED_RESOLVE);
        return occurrence;
    }

    @BeforeEach
    public void initTest() {
        occurrence = createEntity(em);
    }

    @Test
    @Transactional
    public void createOccurrence() throws Exception {
        int databaseSizeBeforeCreate = occurrenceRepository.findAll().size();

        // Create the Occurrence
        restOccurrenceMockMvc.perform(post("/api/occurrences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrence)))
            .andExpect(status().isCreated());

        // Validate the Occurrence in the database
        List<Occurrence> occurrenceList = occurrenceRepository.findAll();
        assertThat(occurrenceList).hasSize(databaseSizeBeforeCreate + 1);
        Occurrence testOccurrence = occurrenceList.get(occurrenceList.size() - 1);
        assertThat(testOccurrence.getOccurrenceCodeNbr()).isEqualTo(DEFAULT_OCCURRENCE_CODE_NBR);
        assertThat(testOccurrence.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testOccurrence.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testOccurrence.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testOccurrence.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testOccurrence.getMedia()).isEqualTo(DEFAULT_MEDIA);
        assertThat(testOccurrence.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOccurrence.getFinalComment()).isEqualTo(DEFAULT_FINAL_COMMENT);
        assertThat(testOccurrence.getEntryWay()).isEqualTo(DEFAULT_ENTRY_WAY);
        assertThat(testOccurrence.isAgility()).isEqualTo(DEFAULT_AGILITY);
        assertThat(testOccurrence.getPrediction()).isEqualTo(DEFAULT_PREDICTION);
        assertThat(testOccurrence.getResolve()).isEqualTo(DEFAULT_RESOLVE);
    }

    @Test
    @Transactional
    public void createOccurrenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = occurrenceRepository.findAll().size();

        // Create the Occurrence with an existing ID
        occurrence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccurrenceMockMvc.perform(post("/api/occurrences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrence)))
            .andExpect(status().isBadRequest());

        // Validate the Occurrence in the database
        List<Occurrence> occurrenceList = occurrenceRepository.findAll();
        assertThat(occurrenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOccurrences() throws Exception {
        // Initialize the database
        occurrenceRepository.saveAndFlush(occurrence);

        // Get all the occurrenceList
        restOccurrenceMockMvc.perform(get("/api/occurrences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occurrence.getId().intValue())))
            .andExpect(jsonPath("$.[*].occurrenceCodeNbr").value(hasItem(DEFAULT_OCCURRENCE_CODE_NBR)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].media").value(hasItem(DEFAULT_MEDIA)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].finalComment").value(hasItem(DEFAULT_FINAL_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].entryWay").value(hasItem(DEFAULT_ENTRY_WAY.toString())))
            .andExpect(jsonPath("$.[*].agility").value(hasItem(DEFAULT_AGILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].prediction").value(hasItem(DEFAULT_PREDICTION.toString())))
            .andExpect(jsonPath("$.[*].resolve").value(hasItem(DEFAULT_RESOLVE.toString())));
    }
    
    @Test
    @Transactional
    public void getOccurrence() throws Exception {
        // Initialize the database
        occurrenceRepository.saveAndFlush(occurrence);

        // Get the occurrence
        restOccurrenceMockMvc.perform(get("/api/occurrences/{id}", occurrence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(occurrence.getId().intValue()))
            .andExpect(jsonPath("$.occurrenceCodeNbr").value(DEFAULT_OCCURRENCE_CODE_NBR))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.media").value(DEFAULT_MEDIA))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.finalComment").value(DEFAULT_FINAL_COMMENT.toString()))
            .andExpect(jsonPath("$.entryWay").value(DEFAULT_ENTRY_WAY.toString()))
            .andExpect(jsonPath("$.agility").value(DEFAULT_AGILITY.booleanValue()))
            .andExpect(jsonPath("$.prediction").value(DEFAULT_PREDICTION.toString()))
            .andExpect(jsonPath("$.resolve").value(DEFAULT_RESOLVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOccurrence() throws Exception {
        // Get the occurrence
        restOccurrenceMockMvc.perform(get("/api/occurrences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOccurrence() throws Exception {
        // Initialize the database
        occurrenceRepository.saveAndFlush(occurrence);

        int databaseSizeBeforeUpdate = occurrenceRepository.findAll().size();

        // Update the occurrence
        Occurrence updatedOccurrence = occurrenceRepository.findById(occurrence.getId()).get();
        // Disconnect from session so that the updates on updatedOccurrence are not directly saved in db
        em.detach(updatedOccurrence);
        updatedOccurrence
            .occurrenceCodeNbr(UPDATED_OCCURRENCE_CODE_NBR)
            .cpf(UPDATED_CPF)
            .customerId(UPDATED_CUSTOMER_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .media(UPDATED_MEDIA)
            .type(UPDATED_TYPE)
            .finalComment(UPDATED_FINAL_COMMENT)
            .entryWay(UPDATED_ENTRY_WAY)
            .agility(UPDATED_AGILITY)
            .prediction(UPDATED_PREDICTION)
            .resolve(UPDATED_RESOLVE);

        restOccurrenceMockMvc.perform(put("/api/occurrences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOccurrence)))
            .andExpect(status().isOk());

        // Validate the Occurrence in the database
        List<Occurrence> occurrenceList = occurrenceRepository.findAll();
        assertThat(occurrenceList).hasSize(databaseSizeBeforeUpdate);
        Occurrence testOccurrence = occurrenceList.get(occurrenceList.size() - 1);
        assertThat(testOccurrence.getOccurrenceCodeNbr()).isEqualTo(UPDATED_OCCURRENCE_CODE_NBR);
        assertThat(testOccurrence.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testOccurrence.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testOccurrence.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testOccurrence.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testOccurrence.getMedia()).isEqualTo(UPDATED_MEDIA);
        assertThat(testOccurrence.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOccurrence.getFinalComment()).isEqualTo(UPDATED_FINAL_COMMENT);
        assertThat(testOccurrence.getEntryWay()).isEqualTo(UPDATED_ENTRY_WAY);
        assertThat(testOccurrence.isAgility()).isEqualTo(UPDATED_AGILITY);
        assertThat(testOccurrence.getPrediction()).isEqualTo(UPDATED_PREDICTION);
        assertThat(testOccurrence.getResolve()).isEqualTo(UPDATED_RESOLVE);
    }

    @Test
    @Transactional
    public void updateNonExistingOccurrence() throws Exception {
        int databaseSizeBeforeUpdate = occurrenceRepository.findAll().size();

        // Create the Occurrence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccurrenceMockMvc.perform(put("/api/occurrences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrence)))
            .andExpect(status().isBadRequest());

        // Validate the Occurrence in the database
        List<Occurrence> occurrenceList = occurrenceRepository.findAll();
        assertThat(occurrenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOccurrence() throws Exception {
        // Initialize the database
        occurrenceRepository.saveAndFlush(occurrence);

        int databaseSizeBeforeDelete = occurrenceRepository.findAll().size();

        // Delete the occurrence
        restOccurrenceMockMvc.perform(delete("/api/occurrences/{id}", occurrence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Occurrence> occurrenceList = occurrenceRepository.findAll();
        assertThat(occurrenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Occurrence.class);
        Occurrence occurrence1 = new Occurrence();
        occurrence1.setId(1L);
        Occurrence occurrence2 = new Occurrence();
        occurrence2.setId(occurrence1.getId());
        assertThat(occurrence1).isEqualTo(occurrence2);
        occurrence2.setId(2L);
        assertThat(occurrence1).isNotEqualTo(occurrence2);
        occurrence1.setId(null);
        assertThat(occurrence1).isNotEqualTo(occurrence2);
    }
}
