package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ComplementAttachment;
import io.github.jhipster.application.repository.ComplementAttachmentRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.ComplementAttachment}.
 */
@RestController
@RequestMapping("/api")
public class ComplementAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(ComplementAttachmentResource.class);

    private static final String ENTITY_NAME = "tpaComplementAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplementAttachmentRepository complementAttachmentRepository;

    public ComplementAttachmentResource(ComplementAttachmentRepository complementAttachmentRepository) {
        this.complementAttachmentRepository = complementAttachmentRepository;
    }

    /**
     * {@code POST  /complement-attachments} : Create a new complementAttachment.
     *
     * @param complementAttachment the complementAttachment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complementAttachment, or with status {@code 400 (Bad Request)} if the complementAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/complement-attachments")
    public ResponseEntity<ComplementAttachment> createComplementAttachment(@RequestBody ComplementAttachment complementAttachment) throws URISyntaxException {
        log.debug("REST request to save ComplementAttachment : {}", complementAttachment);
        if (complementAttachment.getId() != null) {
            throw new BadRequestAlertException("A new complementAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComplementAttachment result = complementAttachmentRepository.save(complementAttachment);
        return ResponseEntity.created(new URI("/api/complement-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /complement-attachments} : Updates an existing complementAttachment.
     *
     * @param complementAttachment the complementAttachment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complementAttachment,
     * or with status {@code 400 (Bad Request)} if the complementAttachment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complementAttachment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/complement-attachments")
    public ResponseEntity<ComplementAttachment> updateComplementAttachment(@RequestBody ComplementAttachment complementAttachment) throws URISyntaxException {
        log.debug("REST request to update ComplementAttachment : {}", complementAttachment);
        if (complementAttachment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComplementAttachment result = complementAttachmentRepository.save(complementAttachment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, complementAttachment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /complement-attachments} : get all the complementAttachments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complementAttachments in body.
     */
    @GetMapping("/complement-attachments")
    public List<ComplementAttachment> getAllComplementAttachments() {
        log.debug("REST request to get all ComplementAttachments");
        return complementAttachmentRepository.findAll();
    }

    /**
     * {@code GET  /complement-attachments/:id} : get the "id" complementAttachment.
     *
     * @param id the id of the complementAttachment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complementAttachment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/complement-attachments/{id}")
    public ResponseEntity<ComplementAttachment> getComplementAttachment(@PathVariable Long id) {
        log.debug("REST request to get ComplementAttachment : {}", id);
        Optional<ComplementAttachment> complementAttachment = complementAttachmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(complementAttachment);
    }

    /**
     * {@code DELETE  /complement-attachments/:id} : delete the "id" complementAttachment.
     *
     * @param id the id of the complementAttachment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/complement-attachments/{id}")
    public ResponseEntity<Void> deleteComplementAttachment(@PathVariable Long id) {
        log.debug("REST request to delete ComplementAttachment : {}", id);
        complementAttachmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
