package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.Situation;
import io.github.jhipster.application.repository.SituationRepository;
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
 * Integration tests for the {@link SituationResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class SituationResourceIT {

    private static final String DEFAULT_SITUATION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SITUATION_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS_CODE = 1;
    private static final Integer UPDATED_STATUS_CODE = 2;
    private static final Integer SMALLER_STATUS_CODE = 1 - 1;

    @Autowired
    private SituationRepository situationRepository;

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

    private MockMvc restSituationMockMvc;

    private Situation situation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SituationResource situationResource = new SituationResource(situationRepository);
        this.restSituationMockMvc = MockMvcBuilders.standaloneSetup(situationResource)
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
    public static Situation createEntity(EntityManager em) {
        Situation situation = new Situation()
            .situationDescription(DEFAULT_SITUATION_DESCRIPTION)
            .statusCode(DEFAULT_STATUS_CODE);
        return situation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createUpdatedEntity(EntityManager em) {
        Situation situation = new Situation()
            .situationDescription(UPDATED_SITUATION_DESCRIPTION)
            .statusCode(UPDATED_STATUS_CODE);
        return situation;
    }

    @BeforeEach
    public void initTest() {
        situation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSituation() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();

        // Create the Situation
        restSituationMockMvc.perform(post("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isCreated());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate + 1);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getSituationDescription()).isEqualTo(DEFAULT_SITUATION_DESCRIPTION);
        assertThat(testSituation.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
    }

    @Test
    @Transactional
    public void createSituationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();

        // Create the Situation with an existing ID
        situation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMockMvc.perform(post("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSituations() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get all the situationList
        restSituationMockMvc.perform(get("/api/situations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situation.getId().intValue())))
            .andExpect(jsonPath("$.[*].situationDescription").value(hasItem(DEFAULT_SITUATION_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)));
    }
    
    @Test
    @Transactional
    public void getSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get the situation
        restSituationMockMvc.perform(get("/api/situations/{id}", situation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(situation.getId().intValue()))
            .andExpect(jsonPath("$.situationDescription").value(DEFAULT_SITUATION_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.statusCode").value(DEFAULT_STATUS_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingSituation() throws Exception {
        // Get the situation
        restSituationMockMvc.perform(get("/api/situations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Update the situation
        Situation updatedSituation = situationRepository.findById(situation.getId()).get();
        // Disconnect from session so that the updates on updatedSituation are not directly saved in db
        em.detach(updatedSituation);
        updatedSituation
            .situationDescription(UPDATED_SITUATION_DESCRIPTION)
            .statusCode(UPDATED_STATUS_CODE);

        restSituationMockMvc.perform(put("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSituation)))
            .andExpect(status().isOk());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getSituationDescription()).isEqualTo(UPDATED_SITUATION_DESCRIPTION);
        assertThat(testSituation.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Create the Situation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc.perform(put("/api/situations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeDelete = situationRepository.findAll().size();

        // Delete the situation
        restSituationMockMvc.perform(delete("/api/situations/{id}", situation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Situation.class);
        Situation situation1 = new Situation();
        situation1.setId(1L);
        Situation situation2 = new Situation();
        situation2.setId(situation1.getId());
        assertThat(situation1).isEqualTo(situation2);
        situation2.setId(2L);
        assertThat(situation1).isNotEqualTo(situation2);
        situation1.setId(null);
        assertThat(situation1).isNotEqualTo(situation2);
    }
}
