package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.OccurrenceTreatment;
import io.github.jhipster.application.repository.OccurrenceTreatmentRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.OccurrenceTreatment}.
 */
@RestController
@RequestMapping("/api")
public class OccurrenceTreatmentResource {

    private final Logger log = LoggerFactory.getLogger(OccurrenceTreatmentResource.class);

    private static final String ENTITY_NAME = "tpaOccurrenceTreatment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OccurrenceTreatmentRepository occurrenceTreatmentRepository;

    public OccurrenceTreatmentResource(OccurrenceTreatmentRepository occurrenceTreatmentRepository) {
        this.occurrenceTreatmentRepository = occurrenceTreatmentRepository;
    }

    /**
     * {@code POST  /occurrence-treatments} : Create a new occurrenceTreatment.
     *
     * @param occurrenceTreatment the occurrenceTreatment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new occurrenceTreatment, or with status {@code 400 (Bad Request)} if the occurrenceTreatment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/occurrence-treatments")
    public ResponseEntity<OccurrenceTreatment> createOccurrenceTreatment(@RequestBody OccurrenceTreatment occurrenceTreatment) throws URISyntaxException {
        log.debug("REST request to save OccurrenceTreatment : {}", occurrenceTreatment);
        if (occurrenceTreatment.getId() != null) {
            throw new BadRequestAlertException("A new occurrenceTreatment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OccurrenceTreatment result = occurrenceTreatmentRepository.save(occurrenceTreatment);
        return ResponseEntity.created(new URI("/api/occurrence-treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /occurrence-treatments} : Updates an existing occurrenceTreatment.
     *
     * @param occurrenceTreatment the occurrenceTreatment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occurrenceTreatment,
     * or with status {@code 400 (Bad Request)} if the occurrenceTreatment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the occurrenceTreatment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/occurrence-treatments")
    public ResponseEntity<OccurrenceTreatment> updateOccurrenceTreatment(@RequestBody OccurrenceTreatment occurrenceTreatment) throws URISyntaxException {
        log.debug("REST request to update OccurrenceTreatment : {}", occurrenceTreatment);
        if (occurrenceTreatment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OccurrenceTreatment result = occurrenceTreatmentRepository.save(occurrenceTreatment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, occurrenceTreatment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /occurrence-treatments} : get all the occurrenceTreatments.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of occurrenceTreatments in body.
     */
    @GetMapping("/occurrence-treatments")
    public List<OccurrenceTreatment> getAllOccurrenceTreatments() {
        log.debug("REST request to get all OccurrenceTreatments");
        return occurrenceTreatmentRepository.findAll();
    }

    /**
     * {@code GET  /occurrence-treatments/:id} : get the "id" occurrenceTreatment.
     *
     * @param id the id of the occurrenceTreatment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the occurrenceTreatment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/occurrence-treatments/{id}")
    public ResponseEntity<OccurrenceTreatment> getOccurrenceTreatment(@PathVariable Long id) {
        log.debug("REST request to get OccurrenceTreatment : {}", id);
        Optional<OccurrenceTreatment> occurrenceTreatment = occurrenceTreatmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(occurrenceTreatment);
    }

    /**
     * {@code DELETE  /occurrence-treatments/:id} : delete the "id" occurrenceTreatment.
     *
     * @param id the id of the occurrenceTreatment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/occurrence-treatments/{id}")
    public ResponseEntity<Void> deleteOccurrenceTreatment(@PathVariable Long id) {
        log.debug("REST request to delete OccurrenceTreatment : {}", id);
        occurrenceTreatmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
