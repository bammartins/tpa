package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.OccurTreatAttach;
import io.github.jhipster.application.repository.OccurTreatAttachRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.OccurTreatAttach}.
 */
@RestController
@RequestMapping("/api")
public class OccurTreatAttachResource {

    private final Logger log = LoggerFactory.getLogger(OccurTreatAttachResource.class);

    private static final String ENTITY_NAME = "tpaOccurTreatAttach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OccurTreatAttachRepository occurTreatAttachRepository;

    public OccurTreatAttachResource(OccurTreatAttachRepository occurTreatAttachRepository) {
        this.occurTreatAttachRepository = occurTreatAttachRepository;
    }

    /**
     * {@code POST  /occur-treat-attaches} : Create a new occurTreatAttach.
     *
     * @param occurTreatAttach the occurTreatAttach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new occurTreatAttach, or with status {@code 400 (Bad Request)} if the occurTreatAttach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/occur-treat-attaches")
    public ResponseEntity<OccurTreatAttach> createOccurTreatAttach(@RequestBody OccurTreatAttach occurTreatAttach) throws URISyntaxException {
        log.debug("REST request to save OccurTreatAttach : {}", occurTreatAttach);
        if (occurTreatAttach.getId() != null) {
            throw new BadRequestAlertException("A new occurTreatAttach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OccurTreatAttach result = occurTreatAttachRepository.save(occurTreatAttach);
        return ResponseEntity.created(new URI("/api/occur-treat-attaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /occur-treat-attaches} : Updates an existing occurTreatAttach.
     *
     * @param occurTreatAttach the occurTreatAttach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occurTreatAttach,
     * or with status {@code 400 (Bad Request)} if the occurTreatAttach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the occurTreatAttach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/occur-treat-attaches")
    public ResponseEntity<OccurTreatAttach> updateOccurTreatAttach(@RequestBody OccurTreatAttach occurTreatAttach) throws URISyntaxException {
        log.debug("REST request to update OccurTreatAttach : {}", occurTreatAttach);
        if (occurTreatAttach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OccurTreatAttach result = occurTreatAttachRepository.save(occurTreatAttach);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, occurTreatAttach.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /occur-treat-attaches} : get all the occurTreatAttaches.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of occurTreatAttaches in body.
     */
    @GetMapping("/occur-treat-attaches")
    public List<OccurTreatAttach> getAllOccurTreatAttaches() {
        log.debug("REST request to get all OccurTreatAttaches");
        return occurTreatAttachRepository.findAll();
    }

    /**
     * {@code GET  /occur-treat-attaches/:id} : get the "id" occurTreatAttach.
     *
     * @param id the id of the occurTreatAttach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the occurTreatAttach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/occur-treat-attaches/{id}")
    public ResponseEntity<OccurTreatAttach> getOccurTreatAttach(@PathVariable Long id) {
        log.debug("REST request to get OccurTreatAttach : {}", id);
        Optional<OccurTreatAttach> occurTreatAttach = occurTreatAttachRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(occurTreatAttach);
    }

    /**
     * {@code DELETE  /occur-treat-attaches/:id} : delete the "id" occurTreatAttach.
     *
     * @param id the id of the occurTreatAttach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/occur-treat-attaches/{id}")
    public ResponseEntity<Void> deleteOccurTreatAttach(@PathVariable Long id) {
        log.debug("REST request to delete OccurTreatAttach : {}", id);
        occurTreatAttachRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
