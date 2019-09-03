package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.Journey;
import io.github.jhipster.application.repository.JourneyRepository;
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
 * Integration tests for the {@link JourneyResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class JourneyResourceIT {

    private static final String DEFAULT_JOURNEY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JOURNEY_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private JourneyRepository journeyRepository;

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

    private MockMvc restJourneyMockMvc;

    private Journey journey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourneyResource journeyResource = new JourneyResource(journeyRepository);
        this.restJourneyMockMvc = MockMvcBuilders.standaloneSetup(journeyResource)
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
    public static Journey createEntity(EntityManager em) {
        Journey journey = new Journey()
            .journeyDescription(DEFAULT_JOURNEY_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return journey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journey createUpdatedEntity(EntityManager em) {
        Journey journey = new Journey()
            .journeyDescription(UPDATED_JOURNEY_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return journey;
    }

    @BeforeEach
    public void initTest() {
        journey = createEntity(em);
    }

    @Test
    @Transactional
    public void createJourney() throws Exception {
        int databaseSizeBeforeCreate = journeyRepository.findAll().size();

        // Create the Journey
        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journey)))
            .andExpect(status().isCreated());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeCreate + 1);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getJourneyDescription()).isEqualTo(DEFAULT_JOURNEY_DESCRIPTION);
        assertThat(testJourney.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createJourneyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = journeyRepository.findAll().size();

        // Create the Journey with an existing ID
        journey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyMockMvc.perform(post("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journey)))
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJourneys() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        // Get all the journeyList
        restJourneyMockMvc.perform(get("/api/journeys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journey.getId().intValue())))
            .andExpect(jsonPath("$.[*].journeyDescription").value(hasItem(DEFAULT_JOURNEY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        // Get the journey
        restJourneyMockMvc.perform(get("/api/journeys/{id}", journey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(journey.getId().intValue()))
            .andExpect(jsonPath("$.journeyDescription").value(DEFAULT_JOURNEY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJourney() throws Exception {
        // Get the journey
        restJourneyMockMvc.perform(get("/api/journeys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Update the journey
        Journey updatedJourney = journeyRepository.findById(journey.getId()).get();
        // Disconnect from session so that the updates on updatedJourney are not directly saved in db
        em.detach(updatedJourney);
        updatedJourney
            .journeyDescription(UPDATED_JOURNEY_DESCRIPTION)
            .active(UPDATED_ACTIVE);

        restJourneyMockMvc.perform(put("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJourney)))
            .andExpect(status().isOk());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getJourneyDescription()).isEqualTo(UPDATED_JOURNEY_DESCRIPTION);
        assertThat(testJourney.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Create the Journey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyMockMvc.perform(put("/api/journeys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(journey)))
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        int databaseSizeBeforeDelete = journeyRepository.findAll().size();

        // Delete the journey
        restJourneyMockMvc.perform(delete("/api/journeys/{id}", journey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Journey.class);
        Journey journey1 = new Journey();
        journey1.setId(1L);
        Journey journey2 = new Journey();
        journey2.setId(journey1.getId());
        assertThat(journey1).isEqualTo(journey2);
        journey2.setId(2L);
        assertThat(journey1).isNotEqualTo(journey2);
        journey1.setId(null);
        assertThat(journey1).isNotEqualTo(journey2);
    }
}
