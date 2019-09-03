package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.OccurrenceTi;
import io.github.jhipster.application.repository.OccurrenceTiRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.OccurrenceTi}.
 */
@RestController
@RequestMapping("/api")
public class OccurrenceTiResource {

    private final Logger log = LoggerFactory.getLogger(OccurrenceTiResource.class);

    private static final String ENTITY_NAME = "tpaOccurrenceTi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OccurrenceTiRepository occurrenceTiRepository;

    public OccurrenceTiResource(OccurrenceTiRepository occurrenceTiRepository) {
        this.occurrenceTiRepository = occurrenceTiRepository;
    }

    /**
     * {@code POST  /occurrence-tis} : Create a new occurrenceTi.
     *
     * @param occurrenceTi the occurrenceTi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new occurrenceTi, or with status {@code 400 (Bad Request)} if the occurrenceTi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/occurrence-tis")
    public ResponseEntity<OccurrenceTi> createOccurrenceTi(@RequestBody OccurrenceTi occurrenceTi) throws URISyntaxException {
        log.debug("REST request to save OccurrenceTi : {}", occurrenceTi);
        if (occurrenceTi.getId() != null) {
            throw new BadRequestAlertException("A new occurrenceTi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OccurrenceTi result = occurrenceTiRepository.save(occurrenceTi);
        return ResponseEntity.created(new URI("/api/occurrence-tis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /occurrence-tis} : Updates an existing occurrenceTi.
     *
     * @param occurrenceTi the occurrenceTi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated occurrenceTi,
     * or with status {@code 400 (Bad Request)} if the occurrenceTi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the occurrenceTi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/occurrence-tis")
    public ResponseEntity<OccurrenceTi> updateOccurrenceTi(@RequestBody OccurrenceTi occurrenceTi) throws URISyntaxException {
        log.debug("REST request to update OccurrenceTi : {}", occurrenceTi);
        if (occurrenceTi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OccurrenceTi result = occurrenceTiRepository.save(occurrenceTi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, occurrenceTi.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /occurrence-tis} : get all the occurrenceTis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of occurrenceTis in body.
     */
    @GetMapping("/occurrence-tis")
    public List<OccurrenceTi> getAllOccurrenceTis() {
        log.debug("REST request to get all OccurrenceTis");
        return occurrenceTiRepository.findAll();
    }

    /**
     * {@code GET  /occurrence-tis/:id} : get the "id" occurrenceTi.
     *
     * @param id the id of the occurrenceTi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the occurrenceTi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/occurrence-tis/{id}")
    public ResponseEntity<OccurrenceTi> getOccurrenceTi(@PathVariable Long id) {
        log.debug("REST request to get OccurrenceTi : {}", id);
        Optional<OccurrenceTi> occurrenceTi = occurrenceTiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(occurrenceTi);
    }

    /**
     * {@code DELETE  /occurrence-tis/:id} : delete the "id" occurrenceTi.
     *
     * @param id the id of the occurrenceTi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/occurrence-tis/{id}")
    public ResponseEntity<Void> deleteOccurrenceTi(@PathVariable Long id) {
        log.debug("REST request to delete OccurrenceTi : {}", id);
        occurrenceTiRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
