package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Justification;
import io.github.jhipster.application.repository.JustificationRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Justification}.
 */
@RestController
@RequestMapping("/api")
public class JustificationResource {

    private final Logger log = LoggerFactory.getLogger(JustificationResource.class);

    private static final String ENTITY_NAME = "tpaJustification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JustificationRepository justificationRepository;

    public JustificationResource(JustificationRepository justificationRepository) {
        this.justificationRepository = justificationRepository;
    }

    /**
     * {@code POST  /justifications} : Create a new justification.
     *
     * @param justification the justification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new justification, or with status {@code 400 (Bad Request)} if the justification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/justifications")
    public ResponseEntity<Justification> createJustification(@RequestBody Justification justification) throws URISyntaxException {
        log.debug("REST request to save Justification : {}", justification);
        if (justification.getId() != null) {
            throw new BadRequestAlertException("A new justification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Justification result = justificationRepository.save(justification);
        return ResponseEntity.created(new URI("/api/justifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /justifications} : Updates an existing justification.
     *
     * @param justification the justification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated justification,
     * or with status {@code 400 (Bad Request)} if the justification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the justification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/justifications")
    public ResponseEntity<Justification> updateJustification(@RequestBody Justification justification) throws URISyntaxException {
        log.debug("REST request to update Justification : {}", justification);
        if (justification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Justification result = justificationRepository.save(justification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, justification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /justifications} : get all the justifications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of justifications in body.
     */
    @GetMapping("/justifications")
    public List<Justification> getAllJustifications() {
        log.debug("REST request to get all Justifications");
        return justificationRepository.findAll();
    }

    /**
     * {@code GET  /justifications/:id} : get the "id" justification.
     *
     * @param id the id of the justification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the justification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/justifications/{id}")
    public ResponseEntity<Justification> getJustification(@PathVariable Long id) {
        log.debug("REST request to get Justification : {}", id);
        Optional<Justification> justification = justificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(justification);
    }

    /**
     * {@code DELETE  /justifications/:id} : delete the "id" justification.
     *
     * @param id the id of the justification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/justifications/{id}")
    public ResponseEntity<Void> deleteJustification(@PathVariable Long id) {
        log.debug("REST request to delete Justification : {}", id);
        justificationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
