package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.Justification;
import io.github.jhipster.application.repository.JustificationRepository;
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
 * Integration tests for the {@link JustificationResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class JustificationResourceIT {

    private static final String DEFAULT_JUSTIFICATION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATION_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private JustificationRepository justificationRepository;

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

    private MockMvc restJustificationMockMvc;

    private Justification justification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JustificationResource justificationResource = new JustificationResource(justificationRepository);
        this.restJustificationMockMvc = MockMvcBuilders.standaloneSetup(justificationResource)
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
    public static Justification createEntity(EntityManager em) {
        Justification justification = new Justification()
            .justificationDescription(DEFAULT_JUSTIFICATION_DESCRIPTION);
        return justification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Justification createUpdatedEntity(EntityManager em) {
        Justification justification = new Justification()
            .justificationDescription(UPDATED_JUSTIFICATION_DESCRIPTION);
        return justification;
    }

    @BeforeEach
    public void initTest() {
        justification = createEntity(em);
    }

    @Test
    @Transactional
    public void createJustification() throws Exception {
        int databaseSizeBeforeCreate = justificationRepository.findAll().size();

        // Create the Justification
        restJustificationMockMvc.perform(post("/api/justifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(justification)))
            .andExpect(status().isCreated());

        // Validate the Justification in the database
        List<Justification> justificationList = justificationRepository.findAll();
        assertThat(justificationList).hasSize(databaseSizeBeforeCreate + 1);
        Justification testJustification = justificationList.get(justificationList.size() - 1);
        assertThat(testJustification.getJustificationDescription()).isEqualTo(DEFAULT_JUSTIFICATION_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createJustificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = justificationRepository.findAll().size();

        // Create the Justification with an existing ID
        justification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJustificationMockMvc.perform(post("/api/justifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(justification)))
            .andExpect(status().isBadRequest());

        // Validate the Justification in the database
        List<Justification> justificationList = justificationRepository.findAll();
        assertThat(justificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJustifications() throws Exception {
        // Initialize the database
        justificationRepository.saveAndFlush(justification);

        // Get all the justificationList
        restJustificationMockMvc.perform(get("/api/justifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(justification.getId().intValue())))
            .andExpect(jsonPath("$.[*].justificationDescription").value(hasItem(DEFAULT_JUSTIFICATION_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getJustification() throws Exception {
        // Initialize the database
        justificationRepository.saveAndFlush(justification);

        // Get the justification
        restJustificationMockMvc.perform(get("/api/justifications/{id}", justification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(justification.getId().intValue()))
            .andExpect(jsonPath("$.justificationDescription").value(DEFAULT_JUSTIFICATION_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJustification() throws Exception {
        // Get the justification
        restJustificationMockMvc.perform(get("/api/justifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJustification() throws Exception {
        // Initialize the database
        justificationRepository.saveAndFlush(justification);

        int databaseSizeBeforeUpdate = justificationRepository.findAll().size();

        // Update the justification
        Justification updatedJustification = justificationRepository.findById(justification.getId()).get();
        // Disconnect from session so that the updates on updatedJustification are not directly saved in db
        em.detach(updatedJustification);
        updatedJustification
            .justificationDescription(UPDATED_JUSTIFICATION_DESCRIPTION);

        restJustificationMockMvc.perform(put("/api/justifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJustification)))
            .andExpect(status().isOk());

        // Validate the Justification in the database
        List<Justification> justificationList = justificationRepository.findAll();
        assertThat(justificationList).hasSize(databaseSizeBeforeUpdate);
        Justification testJustification = justificationList.get(justificationList.size() - 1);
        assertThat(testJustification.getJustificationDescription()).isEqualTo(UPDATED_JUSTIFICATION_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingJustification() throws Exception {
        int databaseSizeBeforeUpdate = justificationRepository.findAll().size();

        // Create the Justification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJustificationMockMvc.perform(put("/api/justifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(justification)))
            .andExpect(status().isBadRequest());

        // Validate the Justification in the database
        List<Justification> justificationList = justificationRepository.findAll();
        assertThat(justificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJustification() throws Exception {
        // Initialize the database
        justificationRepository.saveAndFlush(justification);

        int databaseSizeBeforeDelete = justificationRepository.findAll().size();

        // Delete the justification
        restJustificationMockMvc.perform(delete("/api/justifications/{id}", justification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Justification> justificationList = justificationRepository.findAll();
        assertThat(justificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Justification.class);
        Justification justification1 = new Justification();
        justification1.setId(1L);
        Justification justification2 = new Justification();
        justification2.setId(justification1.getId());
        assertThat(justification1).isEqualTo(justification2);
        justification2.setId(2L);
        assertThat(justification1).isNotEqualTo(justification2);
        justification1.setId(null);
        assertThat(justification1).isNotEqualTo(justification2);
    }
}
