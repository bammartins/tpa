package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.ProdService;
import io.github.jhipster.application.repository.ProdServiceRepository;
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
 * Integration tests for the {@link ProdServiceResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class ProdServiceResourceIT {

    private static final String DEFAULT_PROD_SERVICE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROD_SERVICE_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProdServiceRepository prodServiceRepository;

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

    private MockMvc restProdServiceMockMvc;

    private ProdService prodService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdServiceResource prodServiceResource = new ProdServiceResource(prodServiceRepository);
        this.restProdServiceMockMvc = MockMvcBuilders.standaloneSetup(prodServiceResource)
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
    public static ProdService createEntity(EntityManager em) {
        ProdService prodService = new ProdService()
            .prodServiceDescription(DEFAULT_PROD_SERVICE_DESCRIPTION);
        return prodService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdService createUpdatedEntity(EntityManager em) {
        ProdService prodService = new ProdService()
            .prodServiceDescription(UPDATED_PROD_SERVICE_DESCRIPTION);
        return prodService;
    }

    @BeforeEach
    public void initTest() {
        prodService = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdService() throws Exception {
        int databaseSizeBeforeCreate = prodServiceRepository.findAll().size();

        // Create the ProdService
        restProdServiceMockMvc.perform(post("/api/prod-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodService)))
            .andExpect(status().isCreated());

        // Validate the ProdService in the database
        List<ProdService> prodServiceList = prodServiceRepository.findAll();
        assertThat(prodServiceList).hasSize(databaseSizeBeforeCreate + 1);
        ProdService testProdService = prodServiceList.get(prodServiceList.size() - 1);
        assertThat(testProdService.getProdServiceDescription()).isEqualTo(DEFAULT_PROD_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProdServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prodServiceRepository.findAll().size();

        // Create the ProdService with an existing ID
        prodService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdServiceMockMvc.perform(post("/api/prod-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodService)))
            .andExpect(status().isBadRequest());

        // Validate the ProdService in the database
        List<ProdService> prodServiceList = prodServiceRepository.findAll();
        assertThat(prodServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProdServices() throws Exception {
        // Initialize the database
        prodServiceRepository.saveAndFlush(prodService);

        // Get all the prodServiceList
        restProdServiceMockMvc.perform(get("/api/prod-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodService.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodServiceDescription").value(hasItem(DEFAULT_PROD_SERVICE_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProdService() throws Exception {
        // Initialize the database
        prodServiceRepository.saveAndFlush(prodService);

        // Get the prodService
        restProdServiceMockMvc.perform(get("/api/prod-services/{id}", prodService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prodService.getId().intValue()))
            .andExpect(jsonPath("$.prodServiceDescription").value(DEFAULT_PROD_SERVICE_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProdService() throws Exception {
        // Get the prodService
        restProdServiceMockMvc.perform(get("/api/prod-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdService() throws Exception {
        // Initialize the database
        prodServiceRepository.saveAndFlush(prodService);

        int databaseSizeBeforeUpdate = prodServiceRepository.findAll().size();

        // Update the prodService
        ProdService updatedProdService = prodServiceRepository.findById(prodService.getId()).get();
        // Disconnect from session so that the updates on updatedProdService are not directly saved in db
        em.detach(updatedProdService);
        updatedProdService
            .prodServiceDescription(UPDATED_PROD_SERVICE_DESCRIPTION);

        restProdServiceMockMvc.perform(put("/api/prod-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdService)))
            .andExpect(status().isOk());

        // Validate the ProdService in the database
        List<ProdService> prodServiceList = prodServiceRepository.findAll();
        assertThat(prodServiceList).hasSize(databaseSizeBeforeUpdate);
        ProdService testProdService = prodServiceList.get(prodServiceList.size() - 1);
        assertThat(testProdService.getProdServiceDescription()).isEqualTo(UPDATED_PROD_SERVICE_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProdService() throws Exception {
        int databaseSizeBeforeUpdate = prodServiceRepository.findAll().size();

        // Create the ProdService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdServiceMockMvc.perform(put("/api/prod-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prodService)))
            .andExpect(status().isBadRequest());

        // Validate the ProdService in the database
        List<ProdService> prodServiceList = prodServiceRepository.findAll();
        assertThat(prodServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProdService() throws Exception {
        // Initialize the database
        prodServiceRepository.saveAndFlush(prodService);

        int databaseSizeBeforeDelete = prodServiceRepository.findAll().size();

        // Delete the prodService
        restProdServiceMockMvc.perform(delete("/api/prod-services/{id}", prodService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProdService> prodServiceList = prodServiceRepository.findAll();
        assertThat(prodServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdService.class);
        ProdService prodService1 = new ProdService();
        prodService1.setId(1L);
        ProdService prodService2 = new ProdService();
        prodService2.setId(prodService1.getId());
        assertThat(prodService1).isEqualTo(prodService2);
        prodService2.setId(2L);
        assertThat(prodService1).isNotEqualTo(prodService2);
        prodService1.setId(null);
        assertThat(prodService1).isNotEqualTo(prodService2);
    }
}
