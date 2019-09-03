package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.OccurrenceTreatment;
import io.github.jhipster.application.repository.OccurrenceTreatmentRepository;
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
 * Integration tests for the {@link OccurrenceTreatmentResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class OccurrenceTreatmentResourceIT {

    private static final String DEFAULT_OCCURRENCE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OCCURRENCE_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_EMPLOYEE_ID = 1;
    private static final Integer UPDATED_EMPLOYEE_ID = 2;
    private static final Integer SMALLER_EMPLOYEE_ID = 1 - 1;

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CONTACT_TYPE = 1;
    private static final Integer UPDATED_CONTACT_TYPE = 2;
    private static final Integer SMALLER_CONTACT_TYPE = 1 - 1;

    private static final Integer DEFAULT_CONTACT_STATUS = 1;
    private static final Integer UPDATED_CONTACT_STATUS = 2;
    private static final Integer SMALLER_CONTACT_STATUS = 1 - 1;

    @Autowired
    private OccurrenceTreatmentRepository occurrenceTreatmentRepository;

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

    private MockMvc restOccurrenceTreatmentMockMvc;

    private OccurrenceTreatment occurrenceTreatment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OccurrenceTreatmentResource occurrenceTreatmentResource = new OccurrenceTreatmentResource(occurrenceTreatmentRepository);
        this.restOccurrenceTreatmentMockMvc = MockMvcBuilders.standaloneSetup(occurrenceTreatmentResource)
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
    public static OccurrenceTreatment createEntity(EntityManager em) {
        OccurrenceTreatment occurrenceTreatment = new OccurrenceTreatment()
            .occurrenceDescription(DEFAULT_OCCURRENCE_DESCRIPTION)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .contactType(DEFAULT_CONTACT_TYPE)
            .contactStatus(DEFAULT_CONTACT_STATUS);
        return occurrenceTreatment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OccurrenceTreatment createUpdatedEntity(EntityManager em) {
        OccurrenceTreatment occurrenceTreatment = new OccurrenceTreatment()
            .occurrenceDescription(UPDATED_OCCURRENCE_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .contactType(UPDATED_CONTACT_TYPE)
            .contactStatus(UPDATED_CONTACT_STATUS);
        return occurrenceTreatment;
    }

    @BeforeEach
    public void initTest() {
        occurrenceTreatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createOccurrenceTreatment() throws Exception {
        int databaseSizeBeforeCreate = occurrenceTreatmentRepository.findAll().size();

        // Create the OccurrenceTreatment
        restOccurrenceTreatmentMockMvc.perform(post("/api/occurrence-treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrenceTreatment)))
            .andExpect(status().isCreated());

        // Validate the OccurrenceTreatment in the database
        List<OccurrenceTreatment> occurrenceTreatmentList = occurrenceTreatmentRepository.findAll();
        assertThat(occurrenceTreatmentList).hasSize(databaseSizeBeforeCreate + 1);
        OccurrenceTreatment testOccurrenceTreatment = occurrenceTreatmentList.get(occurrenceTreatmentList.size() - 1);
        assertThat(testOccurrenceTreatment.getOccurrenceDescription()).isEqualTo(DEFAULT_OCCURRENCE_DESCRIPTION);
        assertThat(testOccurrenceTreatment.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testOccurrenceTreatment.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testOccurrenceTreatment.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
        assertThat(testOccurrenceTreatment.getContactStatus()).isEqualTo(DEFAULT_CONTACT_STATUS);
    }

    @Test
    @Transactional
    public void createOccurrenceTreatmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = occurrenceTreatmentRepository.findAll().size();

        // Create the OccurrenceTreatment with an existing ID
        occurrenceTreatment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccurrenceTreatmentMockMvc.perform(post("/api/occurrence-treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrenceTreatment)))
            .andExpect(status().isBadRequest());

        // Validate the OccurrenceTreatment in the database
        List<OccurrenceTreatment> occurrenceTreatmentList = occurrenceTreatmentRepository.findAll();
        assertThat(occurrenceTreatmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOccurrenceTreatments() throws Exception {
        // Initialize the database
        occurrenceTreatmentRepository.saveAndFlush(occurrenceTreatment);

        // Get all the occurrenceTreatmentList
        restOccurrenceTreatmentMockMvc.perform(get("/api/occurrence-treatments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occurrenceTreatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].occurrenceDescription").value(hasItem(DEFAULT_OCCURRENCE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE)))
            .andExpect(jsonPath("$.[*].contactStatus").value(hasItem(DEFAULT_CONTACT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getOccurrenceTreatment() throws Exception {
        // Initialize the database
        occurrenceTreatmentRepository.saveAndFlush(occurrenceTreatment);

        // Get the occurrenceTreatment
        restOccurrenceTreatmentMockMvc.perform(get("/api/occurrence-treatments/{id}", occurrenceTreatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(occurrenceTreatment.getId().intValue()))
            .andExpect(jsonPath("$.occurrenceDescription").value(DEFAULT_OCCURRENCE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE))
            .andExpect(jsonPath("$.contactStatus").value(DEFAULT_CONTACT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingOccurrenceTreatment() throws Exception {
        // Get the occurrenceTreatment
        restOccurrenceTreatmentMockMvc.perform(get("/api/occurrence-treatments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOccurrenceTreatment() throws Exception {
        // Initialize the database
        occurrenceTreatmentRepository.saveAndFlush(occurrenceTreatment);

        int databaseSizeBeforeUpdate = occurrenceTreatmentRepository.findAll().size();

        // Update the occurrenceTreatment
        OccurrenceTreatment updatedOccurrenceTreatment = occurrenceTreatmentRepository.findById(occurrenceTreatment.getId()).get();
        // Disconnect from session so that the updates on updatedOccurrenceTreatment are not directly saved in db
        em.detach(updatedOccurrenceTreatment);
        updatedOccurrenceTreatment
            .occurrenceDescription(UPDATED_OCCURRENCE_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .contactType(UPDATED_CONTACT_TYPE)
            .contactStatus(UPDATED_CONTACT_STATUS);

        restOccurrenceTreatmentMockMvc.perform(put("/api/occurrence-treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOccurrenceTreatment)))
            .andExpect(status().isOk());

        // Validate the OccurrenceTreatment in the database
        List<OccurrenceTreatment> occurrenceTreatmentList = occurrenceTreatmentRepository.findAll();
        assertThat(occurrenceTreatmentList).hasSize(databaseSizeBeforeUpdate);
        OccurrenceTreatment testOccurrenceTreatment = occurrenceTreatmentList.get(occurrenceTreatmentList.size() - 1);
        assertThat(testOccurrenceTreatment.getOccurrenceDescription()).isEqualTo(UPDATED_OCCURRENCE_DESCRIPTION);
        assertThat(testOccurrenceTreatment.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testOccurrenceTreatment.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testOccurrenceTreatment.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
        assertThat(testOccurrenceTreatment.getContactStatus()).isEqualTo(UPDATED_CONTACT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOccurrenceTreatment() throws Exception {
        int databaseSizeBeforeUpdate = occurrenceTreatmentRepository.findAll().size();

        // Create the OccurrenceTreatment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccurrenceTreatmentMockMvc.perform(put("/api/occurrence-treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrenceTreatment)))
            .andExpect(status().isBadRequest());

        // Validate the OccurrenceTreatment in the database
        List<OccurrenceTreatment> occurrenceTreatmentList = occurrenceTreatmentRepository.findAll();
        assertThat(occurrenceTreatmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOccurrenceTreatment() throws Exception {
        // Initialize the database
        occurrenceTreatmentRepository.saveAndFlush(occurrenceTreatment);

        int databaseSizeBeforeDelete = occurrenceTreatmentRepository.findAll().size();

        // Delete the occurrenceTreatment
        restOccurrenceTreatmentMockMvc.perform(delete("/api/occurrence-treatments/{id}", occurrenceTreatment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OccurrenceTreatment> occurrenceTreatmentList = occurrenceTreatmentRepository.findAll();
        assertThat(occurrenceTreatmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccurrenceTreatment.class);
        OccurrenceTreatment occurrenceTreatment1 = new OccurrenceTreatment();
        occurrenceTreatment1.setId(1L);
        OccurrenceTreatment occurrenceTreatment2 = new OccurrenceTreatment();
        occurrenceTreatment2.setId(occurrenceTreatment1.getId());
        assertThat(occurrenceTreatment1).isEqualTo(occurrenceTreatment2);
        occurrenceTreatment2.setId(2L);
        assertThat(occurrenceTreatment1).isNotEqualTo(occurrenceTreatment2);
        occurrenceTreatment1.setId(null);
        assertThat(occurrenceTreatment1).isNotEqualTo(occurrenceTreatment2);
    }
}
