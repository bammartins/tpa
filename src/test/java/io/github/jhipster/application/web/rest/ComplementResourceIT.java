package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.Complement;
import io.github.jhipster.application.repository.ComplementRepository;
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
 * Integration tests for the {@link ComplementResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class ComplementResourceIT {

    private static final String DEFAULT_COMPLEMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_EMPLOYEE_ID = 1;
    private static final Integer UPDATED_EMPLOYEE_ID = 2;
    private static final Integer SMALLER_EMPLOYEE_ID = 1 - 1;

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    @Autowired
    private ComplementRepository complementRepository;

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

    private MockMvc restComplementMockMvc;

    private Complement complement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComplementResource complementResource = new ComplementResource(complementRepository);
        this.restComplementMockMvc = MockMvcBuilders.standaloneSetup(complementResource)
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
    public static Complement createEntity(EntityManager em) {
        Complement complement = new Complement()
            .complementDescription(DEFAULT_COMPLEMENT_DESCRIPTION)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .employeeName(DEFAULT_EMPLOYEE_NAME);
        return complement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Complement createUpdatedEntity(EntityManager em) {
        Complement complement = new Complement()
            .complementDescription(UPDATED_COMPLEMENT_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .employeeName(UPDATED_EMPLOYEE_NAME);
        return complement;
    }

    @BeforeEach
    public void initTest() {
        complement = createEntity(em);
    }

    @Test
    @Transactional
    public void createComplement() throws Exception {
        int databaseSizeBeforeCreate = complementRepository.findAll().size();

        // Create the Complement
        restComplementMockMvc.perform(post("/api/complements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complement)))
            .andExpect(status().isCreated());

        // Validate the Complement in the database
        List<Complement> complementList = complementRepository.findAll();
        assertThat(complementList).hasSize(databaseSizeBeforeCreate + 1);
        Complement testComplement = complementList.get(complementList.size() - 1);
        assertThat(testComplement.getComplementDescription()).isEqualTo(DEFAULT_COMPLEMENT_DESCRIPTION);
        assertThat(testComplement.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testComplement.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    public void createComplementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = complementRepository.findAll().size();

        // Create the Complement with an existing ID
        complement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplementMockMvc.perform(post("/api/complements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complement)))
            .andExpect(status().isBadRequest());

        // Validate the Complement in the database
        List<Complement> complementList = complementRepository.findAll();
        assertThat(complementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComplements() throws Exception {
        // Initialize the database
        complementRepository.saveAndFlush(complement);

        // Get all the complementList
        restComplementMockMvc.perform(get("/api/complements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complement.getId().intValue())))
            .andExpect(jsonPath("$.[*].complementDescription").value(hasItem(DEFAULT_COMPLEMENT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getComplement() throws Exception {
        // Initialize the database
        complementRepository.saveAndFlush(complement);

        // Get the complement
        restComplementMockMvc.perform(get("/api/complements/{id}", complement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(complement.getId().intValue()))
            .andExpect(jsonPath("$.complementDescription").value(DEFAULT_COMPLEMENT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComplement() throws Exception {
        // Get the complement
        restComplementMockMvc.perform(get("/api/complements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplement() throws Exception {
        // Initialize the database
        complementRepository.saveAndFlush(complement);

        int databaseSizeBeforeUpdate = complementRepository.findAll().size();

        // Update the complement
        Complement updatedComplement = complementRepository.findById(complement.getId()).get();
        // Disconnect from session so that the updates on updatedComplement are not directly saved in db
        em.detach(updatedComplement);
        updatedComplement
            .complementDescription(UPDATED_COMPLEMENT_DESCRIPTION)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .employeeName(UPDATED_EMPLOYEE_NAME);

        restComplementMockMvc.perform(put("/api/complements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComplement)))
            .andExpect(status().isOk());

        // Validate the Complement in the database
        List<Complement> complementList = complementRepository.findAll();
        assertThat(complementList).hasSize(databaseSizeBeforeUpdate);
        Complement testComplement = complementList.get(complementList.size() - 1);
        assertThat(testComplement.getComplementDescription()).isEqualTo(UPDATED_COMPLEMENT_DESCRIPTION);
        assertThat(testComplement.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testComplement.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingComplement() throws Exception {
        int databaseSizeBeforeUpdate = complementRepository.findAll().size();

        // Create the Complement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplementMockMvc.perform(put("/api/complements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complement)))
            .andExpect(status().isBadRequest());

        // Validate the Complement in the database
        List<Complement> complementList = complementRepository.findAll();
        assertThat(complementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComplement() throws Exception {
        // Initialize the database
        complementRepository.saveAndFlush(complement);

        int databaseSizeBeforeDelete = complementRepository.findAll().size();

        // Delete the complement
        restComplementMockMvc.perform(delete("/api/complements/{id}", complement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Complement> complementList = complementRepository.findAll();
        assertThat(complementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Complement.class);
        Complement complement1 = new Complement();
        complement1.setId(1L);
        Complement complement2 = new Complement();
        complement2.setId(complement1.getId());
        assertThat(complement1).isEqualTo(complement2);
        complement2.setId(2L);
        assertThat(complement1).isNotEqualTo(complement2);
        complement1.setId(null);
        assertThat(complement1).isNotEqualTo(complement2);
    }
}
