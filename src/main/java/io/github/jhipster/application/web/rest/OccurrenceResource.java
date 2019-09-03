package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Occurrence;
import io.github.jhipster.application.repository.OccurrenceRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Occurrence}.
 */
@RestController
@RequestMapping("/api")
public class OccurrenceResource {

    private final Logger log = LoggerFactory.getLogger(OccurrenceResource.class);

    private static final String ENTITY_NAME = "tpaOccurrence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OccurrenceRepository occurrenceRepository;

    public OccurrenceResource(OccurrenceRepository occurrenceRepository) {
        this.occurrenceRepository = occurrenceRepository;
    }

    /**
     * {@code POST  /occurrences} : Create a new occurrence.
     *
     * @param occurrence the occurrence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new occurrence, or with status {@code 400 (Bad Request)} if the occurrence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/occurrences")
    public ResponseEntity<Occurrence> createOccurrence(@RequestBody Occurrence occurrence) throws URISyntaxException {
        log.debug("REST request to save Occurrence : {}", occurrence);
        if (occurrence.getId() != null) {
            throw new BadRequestAlertException("A new occurrence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Occurrence result = occurrenceRepository.save(occurrence);
        return ResponseEntity.created(new URI("/api/occurrences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /occurrences} : Updates an existing occurrence.
     *
     * @param occurrence the occurrence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occurrence,
     * or with status {@code 400 (Bad Request)} if the occurrence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the occurrence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/occurrences")
    public ResponseEntity<Occurrence> updateOccurrence(@RequestBody Occurrence occurrence) throws URISyntaxException {
        log.debug("REST request to update Occurrence : {}", occurrence);
        if (occurrence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Occurrence result = occurrenceRepository.save(occurrence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, occurrence.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /occurrences} : get all the occurrences.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of occurrences in body.
     */
    @GetMapping("/occurrences")
    public List<Occurrence> getAllOccurrences() {
        log.debug("REST request to get all Occurrences");
        return occurrenceRepository.findAll();
    }

    /**
     * {@code GET  /occurrences/:id} : get the "id" occurrence.
     *
     * @param id the id of the occurrence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the occurrence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/occurrences/{id}")
    public ResponseEntity<Occurrence> getOccurrence(@PathVariable Long id) {
        log.debug("REST request to get Occurrence : {}", id);
        Optional<Occurrence> occurrence = occurrenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(occurrence);
    }

    /**
     * {@code DELETE  /occurrences/:id} : delete the "id" occurrence.
     *
     * @param id the id of the occurrence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/occurrences/{id}")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable Long id) {
        log.debug("REST request to delete Occurrence : {}", id);
        occurrenceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
