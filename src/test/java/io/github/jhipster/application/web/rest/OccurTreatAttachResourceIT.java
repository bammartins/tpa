package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.OccurTreatAttach;
import io.github.jhipster.application.repository.OccurTreatAttachRepository;
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
 * Integration tests for the {@link OccurTreatAttachResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class OccurTreatAttachResourceIT {

    @Autowired
    private OccurTreatAttachRepository occurTreatAttachRepository;

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

    private MockMvc restOccurTreatAttachMockMvc;

    private OccurTreatAttach occurTreatAttach;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OccurTreatAttachResource occurTreatAttachResource = new OccurTreatAttachResource(occurTreatAttachRepository);
        this.restOccurTreatAttachMockMvc = MockMvcBuilders.standaloneSetup(occurTreatAttachResource)
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
    public static OccurTreatAttach createEntity(EntityManager em) {
        OccurTreatAttach occurTreatAttach = new OccurTreatAttach();
        return occurTreatAttach;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OccurTreatAttach createUpdatedEntity(EntityManager em) {
        OccurTreatAttach occurTreatAttach = new OccurTreatAttach();
        return occurTreatAttach;
    }

    @BeforeEach
    public void initTest() {
        occurTreatAttach = createEntity(em);
    }

    @Test
    @Transactional
    public void createOccurTreatAttach() throws Exception {
        int databaseSizeBeforeCreate = occurTreatAttachRepository.findAll().size();

        // Create the OccurTreatAttach
        restOccurTreatAttachMockMvc.perform(post("/api/occur-treat-attaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurTreatAttach)))
            .andExpect(status().isCreated());

        // Validate the OccurTreatAttach in the database
        List<OccurTreatAttach> occurTreatAttachList = occurTreatAttachRepository.findAll();
        assertThat(occurTreatAttachList).hasSize(databaseSizeBeforeCreate + 1);
        OccurTreatAttach testOccurTreatAttach = occurTreatAttachList.get(occurTreatAttachList.size() - 1);
    }

    @Test
    @Transactional
    public void createOccurTreatAttachWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = occurTreatAttachRepository.findAll().size();

        // Create the OccurTreatAttach with an existing ID
        occurTreatAttach.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccurTreatAttachMockMvc.perform(post("/api/occur-treat-attaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurTreatAttach)))
            .andExpect(status().isBadRequest());

        // Validate the OccurTreatAttach in the database
        List<OccurTreatAttach> occurTreatAttachList = occurTreatAttachRepository.findAll();
        assertThat(occurTreatAttachList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOccurTreatAttaches() throws Exception {
        // Initialize the database
        occurTreatAttachRepository.saveAndFlush(occurTreatAttach);

        // Get all the occurTreatAttachList
        restOccurTreatAttachMockMvc.perform(get("/api/occur-treat-attaches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occurTreatAttach.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getOccurTreatAttach() throws Exception {
        // Initialize the database
        occurTreatAttachRepository.saveAndFlush(occurTreatAttach);

        // Get the occurTreatAttach
        restOccurTreatAttachMockMvc.perform(get("/api/occur-treat-attaches/{id}", occurTreatAttach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(occurTreatAttach.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOccurTreatAttach() throws Exception {
        // Get the occurTreatAttach
        restOccurTreatAttachMockMvc.perform(get("/api/occur-treat-attaches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOccurTreatAttach() throws Exception {
        // Initialize the database
        occurTreatAttachRepository.saveAndFlush(occurTreatAttach);

        int databaseSizeBeforeUpdate = occurTreatAttachRepository.findAll().size();

        // Update the occurTreatAttach
        OccurTreatAttach updatedOccurTreatAttach = occurTreatAttachRepository.findById(occurTreatAttach.getId()).get();
        // Disconnect from session so that the updates on updatedOccurTreatAttach are not directly saved in db
        em.detach(updatedOccurTreatAttach);

        restOccurTreatAttachMockMvc.perform(put("/api/occur-treat-attaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOccurTreatAttach)))
            .andExpect(status().isOk());

        // Validate the OccurTreatAttach in the database
        List<OccurTreatAttach> occurTreatAttachList = occurTreatAttachRepository.findAll();
        assertThat(occurTreatAttachList).hasSize(databaseSizeBeforeUpdate);
        OccurTreatAttach testOccurTreatAttach = occurTreatAttachList.get(occurTreatAttachList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingOccurTreatAttach() throws Exception {
        int databaseSizeBeforeUpdate = occurTreatAttachRepository.findAll().size();

        // Create the OccurTreatAttach

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccurTreatAttachMockMvc.perform(put("/api/occur-treat-attaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occurTreatAttach)))
            .andExpect(status().isBadRequest());

        // Validate the OccurTreatAttach in the database
        List<OccurTreatAttach> occurTreatAttachList = occurTreatAttachRepository.findAll();
        assertThat(occurTreatAttachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOccurTreatAttach() throws Exception {
        // Initialize the database
        occurTreatAttachRepository.saveAndFlush(occurTreatAttach);

        int databaseSizeBeforeDelete = occurTreatAttachRepository.findAll().size();

        // Delete the occurTreatAttach
        restOccurTreatAttachMockMvc.perform(delete("/api/occur-treat-attaches/{id}", occurTreatAttach.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OccurTreatAttach> occurTreatAttachList = occurTreatAttachRepository.findAll();
        assertThat(occurTreatAttachList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccurTreatAttach.class);
        OccurTreatAttach occurTreatAttach1 = new OccurTreatAttach();
        occurTreatAttach1.setId(1L);
        OccurTreatAttach occurTreatAttach2 = new OccurTreatAttach();
        occurTreatAttach2.setId(occurTreatAttach1.getId());
        assertThat(occurTreatAttach1).isEqualTo(occurTreatAttach2);
        occurTreatAttach2.setId(2L);
        assertThat(occurTreatAttach1).isNotEqualTo(occurTreatAttach2);
        occurTreatAttach1.setId(null);
        assertThat(occurTreatAttach1).isNotEqualTo(occurTreatAttach2);
    }
}
