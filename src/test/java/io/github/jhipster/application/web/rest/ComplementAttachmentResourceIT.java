package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TpaApp;
import io.github.jhipster.application.domain.ComplementAttachment;
import io.github.jhipster.application.repository.ComplementAttachmentRepository;
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
 * Integration tests for the {@link ComplementAttachmentResource} REST controller.
 */
@SpringBootTest(classes = TpaApp.class)
public class ComplementAttachmentResourceIT {

    @Autowired
    private ComplementAttachmentRepository complementAttachmentRepository;

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

    private MockMvc restComplementAttachmentMockMvc;

    private ComplementAttachment complementAttachment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComplementAttachmentResource complementAttachmentResource = new ComplementAttachmentResource(complementAttachmentRepository);
        this.restComplementAttachmentMockMvc = MockMvcBuilders.standaloneSetup(complementAttachmentResource)
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
    public static ComplementAttachment createEntity(EntityManager em) {
        ComplementAttachment complementAttachment = new ComplementAttachment();
        return complementAttachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplementAttachment createUpdatedEntity(EntityManager em) {
        ComplementAttachment complementAttachment = new ComplementAttachment();
        return complementAttachment;
    }

    @BeforeEach
    public void initTest() {
        complementAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComplementAttachment() throws Exception {
        int databaseSizeBeforeCreate = complementAttachmentRepository.findAll().size();

        // Create the ComplementAttachment
        restComplementAttachmentMockMvc.perform(post("/api/complement-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complementAttachment)))
            .andExpect(status().isCreated());

        // Validate the ComplementAttachment in the database
        List<ComplementAttachment> complementAttachmentList = complementAttachmentRepository.findAll();
        assertThat(complementAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        ComplementAttachment testComplementAttachment = complementAttachmentList.get(complementAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    public void createComplementAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = complementAttachmentRepository.findAll().size();

        // Create the ComplementAttachment with an existing ID
        complementAttachment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplementAttachmentMockMvc.perform(post("/api/complement-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complementAttachment)))
            .andExpect(status().isBadRequest());

        // Validate the ComplementAttachment in the database
        List<ComplementAttachment> complementAttachmentList = complementAttachmentRepository.findAll();
        assertThat(complementAttachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComplementAttachments() throws Exception {
        // Initialize the database
        complementAttachmentRepository.saveAndFlush(complementAttachment);

        // Get all the complementAttachmentList
        restComplementAttachmentMockMvc.perform(get("/api/complement-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complementAttachment.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getComplementAttachment() throws Exception {
        // Initialize the database
        complementAttachmentRepository.saveAndFlush(complementAttachment);

        // Get the complementAttachment
        restComplementAttachmentMockMvc.perform(get("/api/complement-attachments/{id}", complementAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(complementAttachment.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComplementAttachment() throws Exception {
        // Get the complementAttachment
        restComplementAttachmentMockMvc.perform(get("/api/complement-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComplementAttachment() throws Exception {
        // Initialize the database
        complementAttachmentRepository.saveAndFlush(complementAttachment);

        int databaseSizeBeforeUpdate = complementAttachmentRepository.findAll().size();

        // Update the complementAttachment
        ComplementAttachment updatedComplementAttachment = complementAttachmentRepository.findById(complementAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedComplementAttachment are not directly saved in db
        em.detach(updatedComplementAttachment);

        restComplementAttachmentMockMvc.perform(put("/api/complement-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComplementAttachment)))
            .andExpect(status().isOk());

        // Validate the ComplementAttachment in the database
        List<ComplementAttachment> complementAttachmentList = complementAttachmentRepository.findAll();
        assertThat(complementAttachmentList).hasSize(databaseSizeBeforeUpdate);
        ComplementAttachment testComplementAttachment = complementAttachmentList.get(complementAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingComplementAttachment() throws Exception {
        int databaseSizeBeforeUpdate = complementAttachmentRepository.findAll().size();

        // Create the ComplementAttachment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplementAttachmentMockMvc.perform(put("/api/complement-attachments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(complementAttachment)))
            .andExpect(status().isBadRequest());

        // Validate the ComplementAttachment in the database
        List<ComplementAttachment> complementAttachmentList = complementAttachmentRepository.findAll();
        assertThat(complementAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComplementAttachment() throws Exception {
        // Initialize the database
        complementAttachmentRepository.saveAndFlush(complementAttachment);

        int databaseSizeBeforeDelete = complementAttachmentRepository.findAll().size();

        // Delete the complementAttachment
        restComplementAttachmentMockMvc.perform(delete("/api/complement-attachments/{id}", complementAttachment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComplementAttachment> complementAttachmentList = complementAttachmentRepository.findAll();
        assertThat(complementAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplementAttachment.class);
        ComplementAttachment complementAttachment1 = new ComplementAttachment();
        complementAttachment1.setId(1L);
        ComplementAttachment complementAttachment2 = new ComplementAttachment();
        complementAttachment2.setId(complementAttachment1.getId());
        assertThat(complementAttachment1).isEqualTo(complementAttachment2);
        complementAttachment2.setId(2L);
        assertThat(complementAttachment1).isNotEqualTo(complementAttachment2);
        complementAttachment1.setId(null);
        assertThat(complementAttachment1).isNotEqualTo(complementAttachment2);
    }
}
