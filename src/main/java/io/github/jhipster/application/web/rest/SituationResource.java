package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Situation;
import io.github.jhipster.application.repository.SituationRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Situation}.
 */
@RestController
@RequestMapping("/api")
public class SituationResource {

    private final Logger log = LoggerFactory.getLogger(SituationResource.class);

    private static final String ENTITY_NAME = "tpaSituation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationRepository situationRepository;

    public SituationResource(SituationRepository situationRepository) {
        this.situationRepository = situationRepository;
    }

    /**
     * {@code POST  /situations} : Create a new situation.
     *
     * @param situation the situation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situation, or with status {@code 400 (Bad Request)} if the situation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situations")
    public ResponseEntity<Situation> createSituation(@RequestBody Situation situation) throws URISyntaxException {
        log.debug("REST request to save Situation : {}", situation);
        if (situation.getId() != null) {
            throw new BadRequestAlertException("A new situation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Situation result = situationRepository.save(situation);
        return ResponseEntity.created(new URI("/api/situations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situations} : Updates an existing situation.
     *
     * @param situation the situation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situation,
     * or with status {@code 400 (Bad Request)} if the situation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situations")
    public ResponseEntity<Situation> updateSituation(@RequestBody Situation situation) throws URISyntaxException {
        log.debug("REST request to update Situation : {}", situation);
        if (situation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Situation result = situationRepository.save(situation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, situation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /situations} : get all the situations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situations in body.
     */
    @GetMapping("/situations")
    public List<Situation> getAllSituations() {
        log.debug("REST request to get all Situations");
        return situationRepository.findAll();
    }

    /**
     * {@code GET  /situations/:id} : get the "id" situation.
     *
     * @param id the id of the situation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situations/{id}")
    public ResponseEntity<Situation> getSituation(@PathVariable Long id) {
        log.debug("REST request to get Situation : {}", id);
        Optional<Situation> situation = situationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(situation);
    }

    /**
     * {@code DELETE  /situations/:id} : delete the "id" situation.
     *
     * @param id the id of the situation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situations/{id}")
    public ResponseEntity<Void> deleteSituation(@PathVariable Long id) {
        log.debug("REST request to delete Situation : {}", id);
        situationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
