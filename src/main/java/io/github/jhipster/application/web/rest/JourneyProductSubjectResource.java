package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.JourneyProductSubject;
import io.github.jhipster.application.repository.JourneyProductSubjectRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.JourneyProductSubject}.
 */
@RestController
@RequestMapping("/api")
public class JourneyProductSubjectResource {

    private final Logger log = LoggerFactory.getLogger(JourneyProductSubjectResource.class);

    private static final String ENTITY_NAME = "tpaJourneyProductSubject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyProductSubjectRepository journeyProductSubjectRepository;

    public JourneyProductSubjectResource(JourneyProductSubjectRepository journeyProductSubjectRepository) {
        this.journeyProductSubjectRepository = journeyProductSubjectRepository;
    }

    /**
     * {@code POST  /journey-product-subjects} : Create a new journeyProductSubject.
     *
     * @param journeyProductSubject the journeyProductSubject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeyProductSubject, or with status {@code 400 (Bad Request)} if the journeyProductSubject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journey-product-subjects")
    public ResponseEntity<JourneyProductSubject> createJourneyProductSubject(@RequestBody JourneyProductSubject journeyProductSubject) throws URISyntaxException {
        log.debug("REST request to save JourneyProductSubject : {}", journeyProductSubject);
        if (journeyProductSubject.getId() != null) {
            throw new BadRequestAlertException("A new journeyProductSubject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyProductSubject result = journeyProductSubjectRepository.save(journeyProductSubject);
        return ResponseEntity.created(new URI("/api/journey-product-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journey-product-subjects} : Updates an existing journeyProductSubject.
     *
     * @param journeyProductSubject the journeyProductSubject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyProductSubject,
     * or with status {@code 400 (Bad Request)} if the journeyProductSubject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeyProductSubject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journey-product-subjects")
    public ResponseEntity<JourneyProductSubject> updateJourneyProductSubject(@RequestBody JourneyProductSubject journeyProductSubject) throws URISyntaxException {
        log.debug("REST request to update JourneyProductSubject : {}", journeyProductSubject);
        if (journeyProductSubject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JourneyProductSubject result = journeyProductSubjectRepository.save(journeyProductSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyProductSubject.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /journey-product-subjects} : get all the journeyProductSubjects.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeyProductSubjects in body.
     */
    @GetMapping("/journey-product-subjects")
    public List<JourneyProductSubject> getAllJourneyProductSubjects() {
        log.debug("REST request to get all JourneyProductSubjects");
        return journeyProductSubjectRepository.findAll();
    }

    /**
     * {@code GET  /journey-product-subjects/:id} : get the "id" journeyProductSubject.
     *
     * @param id the id of the journeyProductSubject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeyProductSubject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journey-product-subjects/{id}")
    public ResponseEntity<JourneyProductSubject> getJourneyProductSubject(@PathVariable Long id) {
        log.debug("REST request to get JourneyProductSubject : {}", id);
        Optional<JourneyProductSubject> journeyProductSubject = journeyProductSubjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(journeyProductSubject);
    }

    /**
     * {@code DELETE  /journey-product-subjects/:id} : delete the "id" journeyProductSubject.
     *
     * @param id the id of the journeyProductSubject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journey-product-subjects/{id}")
    public ResponseEntity<Void> deleteJourneyProductSubject(@PathVariable Long id) {
        log.debug("REST request to delete JourneyProductSubject : {}", id);
        journeyProductSubjectRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
