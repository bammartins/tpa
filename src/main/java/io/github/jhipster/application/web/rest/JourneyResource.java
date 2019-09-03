package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Journey;
import io.github.jhipster.application.repository.JourneyRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Journey}.
 */
@RestController
@RequestMapping("/api")
public class JourneyResource {

    private final Logger log = LoggerFactory.getLogger(JourneyResource.class);

    private static final String ENTITY_NAME = "tpaJourney";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyRepository journeyRepository;

    public JourneyResource(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    /**
     * {@code POST  /journeys} : Create a new journey.
     *
     * @param journey the journey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journey, or with status {@code 400 (Bad Request)} if the journey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journeys")
    public ResponseEntity<Journey> createJourney(@RequestBody Journey journey) throws URISyntaxException {
        log.debug("REST request to save Journey : {}", journey);
        if (journey.getId() != null) {
            throw new BadRequestAlertException("A new journey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Journey result = journeyRepository.save(journey);
        return ResponseEntity.created(new URI("/api/journeys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journeys} : Updates an existing journey.
     *
     * @param journey the journey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journey,
     * or with status {@code 400 (Bad Request)} if the journey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journeys")
    public ResponseEntity<Journey> updateJourney(@RequestBody Journey journey) throws URISyntaxException {
        log.debug("REST request to update Journey : {}", journey);
        if (journey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Journey result = journeyRepository.save(journey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /journeys} : get all the journeys.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeys in body.
     */
    @GetMapping("/journeys")
    public List<Journey> getAllJourneys() {
        log.debug("REST request to get all Journeys");
        return journeyRepository.findAll();
    }

    /**
     * {@code GET  /journeys/:id} : get the "id" journey.
     *
     * @param id the id of the journey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journeys/{id}")
    public ResponseEntity<Journey> getJourney(@PathVariable Long id) {
        log.debug("REST request to get Journey : {}", id);
        Optional<Journey> journey = journeyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(journey);
    }

    /**
     * {@code DELETE  /journeys/:id} : delete the "id" journey.
     *
     * @param id the id of the journey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journeys/{id}")
    public ResponseEntity<Void> deleteJourney(@PathVariable Long id) {
        log.debug("REST request to delete Journey : {}", id);
        journeyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
