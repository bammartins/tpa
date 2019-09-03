package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.OccurrenceTi;
import io.github.jhipster.application.repository.OccurrenceTiRepository;
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
 * Integration tests for the {@link OccurrenceTiResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class OccurrenceTiResourceIT {

    private static final String DEFAULT_OS_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_OS_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_APP_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_APP_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SLA = 1;
    private static final Integer UPDATED_SLA = 2;
    private static final Integer SMALLER_SLA = 1 - 1;

    private static final Integer DEFAULT_SLA_TYPE = 1;
    private static final Integer UPDATED_SLA_TYPE = 2;
    private static final Integer SMALLER_SLA_TYPE = 1 - 1;

    private static final Instant DEFAULT_ERROR_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ERROR_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_ERROR_TIME = Instant.ofEpochMilli(-1L);

    private static final Integer DEFAULT_PRIORITY_CODE = 1;
    private static final Integer UPDATED_PRIORITY_CODE = 2;
    private static final Integer SMALLER_PRIORITY_CODE = 1 - 1;

    private static final Integer DEFAULT_STATUS_CODE = 1;
    private static final Integer UPDATED_STATUS_CODE = 2;
    private static final Integer SMALLER_STATUS_CODE = 1 - 1;

    private static final String DEFAULT_OCCURRENCE_TI_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_OCCURRENCE_TI_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OccurrenceTiRepository occurrenceTiRepository;

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

    private MockMvc restOccurrenceTiMockMvc;

    private OccurrenceTi occurrenceTi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OccurrenceTiResource occurrenceTiResource = new OccurrenceTiResource(occurrenceTiRepository);
        this.restOccurrenceTiMockMvc = MockMvcBuilders.standaloneSetup(occurrenceTiResource)
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
    public static OccurrenceTi createEntity(EntityManager em) {
        OccurrenceTi occurrenceTi = new OccurrenceTi()
            .osVersion(DEFAULT_OS_VERSION)
            .appVersion(DEFAULT_APP_VERSION)
            .phoneModel(DEFAULT_PHONE_MODEL)
            .sla(DEFAULT_SLA)
            .slaType(DEFAULT_SLA_TYPE)
            .errorTime(DEFAULT_ERROR_TIME)
            .priorityCode(DEFAULT_PRIORITY_CODE)
            .statusCode(DEFAULT_STATUS_CODE)
            .occurrenceTiDescription(DEFAULT_OCCURRENCE_TI_DESCRIPTION);
        return occurrenceTi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OccurrenceTi createUpdatedEntity(EntityManager em) {
        OccurrenceTi occurrenceTi = new OccurrenceTi()
            .osVersion(UPDATED_OS_VERSION)
            .appVersion(UPDATED_APP_VERSION)
            .phoneModel(UPDATED_PHONE_MODEL)
            .sla(UPDATED_SLA)
            .slaType(UPDATED_SLA_TYPE)
            .errorTime(UPDATED_ERROR_TIME)
            .priorityCode(UPDATED_PRIORITY_CODE)
            .statusCode(UPDATED_STATUS_CODE)
            .occurrenceTiDescription(UPDATED_OCCURRENCE_TI_DESCRIPTION);
        return occurrenceTi;
    }

    @BeforeEach
    public void initTest() {
        occurrenceTi = createEntity(em);
    }

    @Test
    @Transactional
    public void createOccurrenceTi() throws Exception {
        int databaseSizeBeforeCreate = occurrenceTiRepository.findAll().size();

        // Create the OccurrenceTi
        restOccurrenceTiMockMvc.perform(post("/api/occurrence-tis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrenceTi)))
            .andExpect(status().isCreated());

        // Validate the OccurrenceTi in the database
        List<OccurrenceTi> occurrenceTiList = occurrenceTiRepository.findAll();
        assertThat(occurrenceTiList).hasSize(databaseSizeBeforeCreate + 1);
        OccurrenceTi testOccurrenceTi = occurrenceTiList.get(occurrenceTiList.size() - 1);
        assertThat(testOccurrenceTi.getOsVersion()).isEqualTo(DEFAULT_OS_VERSION);
        assertThat(testOccurrenceTi.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(testOccurrenceTi.getPhoneModel()).isEqualTo(DEFAULT_PHONE_MODEL);
        assertThat(testOccurrenceTi.getSla()).isEqualTo(DEFAULT_SLA);
        assertThat(testOccurrenceTi.getSlaType()).isEqualTo(DEFAULT_SLA_TYPE);
        assertThat(testOccurrenceTi.getErrorTime()).isEqualTo(DEFAULT_ERROR_TIME);
        assertThat(testOccurrenceTi.getPriorityCode()).isEqualTo(DEFAULT_PRIORITY_CODE);
        assertThat(testOccurrenceTi.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testOccurrenceTi.getOccurrenceTiDescription()).isEqualTo(DEFAULT_OCCURRENCE_TI_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOccurrenceTiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = occurrenceTiRepository.findAll().size();

        // Create the OccurrenceTi with an existing ID
        occurrenceTi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccurrenceTiMockMvc.perform(post("/api/occurrence-tis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrenceTi)))
            .andExpect(status().isBadRequest());

        // Validate the OccurrenceTi in the database
        List<OccurrenceTi> occurrenceTiList = occurrenceTiRepository.findAll();
        assertThat(occurrenceTiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOccurrenceTis() throws Exception {
        // Initialize the database
        occurrenceTiRepository.saveAndFlush(occurrenceTi);

        // Get all the occurrenceTiList
        restOccurrenceTiMockMvc.perform(get("/api/occurrence-tis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occurrenceTi.getId().intValue())))
            .andExpect(jsonPath("$.[*].osVersion").value(hasItem(DEFAULT_OS_VERSION.toString())))
            .andExpect(jsonPath("$.[*].appVersion").value(hasItem(DEFAULT_APP_VERSION.toString())))
            .andExpect(jsonPath("$.[*].phoneModel").value(hasItem(DEFAULT_PHONE_MODEL.toString())))
            .andExpect(jsonPath("$.[*].sla").value(hasItem(DEFAULT_SLA)))
            .andExpect(jsonPath("$.[*].slaType").value(hasItem(DEFAULT_SLA_TYPE)))
            .andExpect(jsonPath("$.[*].errorTime").value(hasItem(DEFAULT_ERROR_TIME.toString())))
            .andExpect(jsonPath("$.[*].priorityCode").value(hasItem(DEFAULT_PRIORITY_CODE)))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].occurrenceTiDescription").value(hasItem(DEFAULT_OCCURRENCE_TI_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getOccurrenceTi() throws Exception {
        // Initialize the database
        occurrenceTiRepository.saveAndFlush(occurrenceTi);

        // Get the occurrenceTi
        restOccurrenceTiMockMvc.perform(get("/api/occurrence-tis/{id}", occurrenceTi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(occurrenceTi.getId().intValue()))
            .andExpect(jsonPath("$.osVersion").value(DEFAULT_OS_VERSION.toString()))
            .andExpect(jsonPath("$.appVersion").value(DEFAULT_APP_VERSION.toString()))
            .andExpect(jsonPath("$.phoneModel").value(DEFAULT_PHONE_MODEL.toString()))
            .andExpect(jsonPath("$.sla").value(DEFAULT_SLA))
            .andExpect(jsonPath("$.slaType").value(DEFAULT_SLA_TYPE))
            .andExpect(jsonPath("$.errorTime").value(DEFAULT_ERROR_TIME.toString()))
            .andExpect(jsonPath("$.priorityCode").value(DEFAULT_PRIORITY_CODE))
            .andExpect(jsonPath("$.statusCode").value(DEFAULT_STATUS_CODE))
            .andExpect(jsonPath("$.occurrenceTiDescription").value(DEFAULT_OCCURRENCE_TI_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOccurrenceTi() throws Exception {
        // Get the occurrenceTi
        restOccurrenceTiMockMvc.perform(get("/api/occurrence-tis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOccurrenceTi() throws Exception {
        // Initialize the database
        occurrenceTiRepository.saveAndFlush(occurrenceTi);

        int databaseSizeBeforeUpdate = occurrenceTiRepository.findAll().size();

        // Update the occurrenceTi
        OccurrenceTi updatedOccurrenceTi = occurrenceTiRepository.findById(occurrenceTi.getId()).get();
        // Disconnect from session so that the updates on updatedOccurrenceTi are not directly saved in db
        em.detach(updatedOccurrenceTi);
        updatedOccurrenceTi
            .osVersion(UPDATED_OS_VERSION)
            .appVersion(UPDATED_APP_VERSION)
            .phoneModel(UPDATED_PHONE_MODEL)
            .sla(UPDATED_SLA)
            .slaType(UPDATED_SLA_TYPE)
            .errorTime(UPDATED_ERROR_TIME)
            .priorityCode(UPDATED_PRIORITY_CODE)
            .statusCode(UPDATED_STATUS_CODE)
            .occurrenceTiDescription(UPDATED_OCCURRENCE_TI_DESCRIPTION);

        restOccurrenceTiMockMvc.perform(put("/api/occurrence-tis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOccurrenceTi)))
            .andExpect(status().isOk());

        // Validate the OccurrenceTi in the database
        List<OccurrenceTi> occurrenceTiList = occurrenceTiRepository.findAll();
        assertThat(occurrenceTiList).hasSize(databaseSizeBeforeUpdate);
        OccurrenceTi testOccurrenceTi = occurrenceTiList.get(occurrenceTiList.size() - 1);
        assertThat(testOccurrenceTi.getOsVersion()).isEqualTo(UPDATED_OS_VERSION);
        assertThat(testOccurrenceTi.getAppVersion()).isEqualTo(UPDATED_APP_VERSION);
        assertThat(testOccurrenceTi.getPhoneModel()).isEqualTo(UPDATED_PHONE_MODEL);
        assertThat(testOccurrenceTi.getSla()).isEqualTo(UPDATED_SLA);
        assertThat(testOccurrenceTi.getSlaType()).isEqualTo(UPDATED_SLA_TYPE);
        assertThat(testOccurrenceTi.getErrorTime()).isEqualTo(UPDATED_ERROR_TIME);
        assertThat(testOccurrenceTi.getPriorityCode()).isEqualTo(UPDATED_PRIORITY_CODE);
        assertThat(testOccurrenceTi.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testOccurrenceTi.getOccurrenceTiDescription()).isEqualTo(UPDATED_OCCURRENCE_TI_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOccurrenceTi() throws Exception {
        int databaseSizeBeforeUpdate = occurrenceTiRepository.findAll().size();

        // Create the OccurrenceTi

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccurrenceTiMockMvc.perform(put("/api/occurrence-tis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurrenceTi)))
            .andExpect(status().isBadRequest());

        // Validate the OccurrenceTi in the database
        List<OccurrenceTi> occurrenceTiList = occurrenceTiRepository.findAll();
        assertThat(occurrenceTiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOccurrenceTi() throws Exception {
        // Initialize the database
        occurrenceTiRepository.saveAndFlush(occurrenceTi);

        int databaseSizeBeforeDelete = occurrenceTiRepository.findAll().size();

        // Delete the occurrenceTi
        restOccurrenceTiMockMvc.perform(delete("/api/occurrence-tis/{id}", occurrenceTi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OccurrenceTi> occurrenceTiList = occurrenceTiRepository.findAll();
        assertThat(occurrenceTiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccurrenceTi.class);
        OccurrenceTi occurrenceTi1 = new OccurrenceTi();
        occurrenceTi1.setId(1L);
        OccurrenceTi occurrenceTi2 = new OccurrenceTi();
        occurrenceTi2.setId(occurrenceTi1.getId());
        assertThat(occurrenceTi1).isEqualTo(occurrenceTi2);
        occurrenceTi2.setId(2L);
        assertThat(occurrenceTi1).isNotEqualTo(occurrenceTi2);
        occurrenceTi1.setId(null);
        assertThat(occurrenceTi1).isNotEqualTo(occurrenceTi2);
    }
}
