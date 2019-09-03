package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Complement;
import io.github.jhipster.application.repository.ComplementRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Complement}.
 */
@RestController
@RequestMapping("/api")
public class ComplementResource {

    private final Logger log = LoggerFactory.getLogger(ComplementResource.class);

    private static final String ENTITY_NAME = "tpaComplement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplementRepository complementRepository;

    public ComplementResource(ComplementRepository complementRepository) {
        this.complementRepository = complementRepository;
    }

    /**
     * {@code POST  /complements} : Create a new complement.
     *
     * @param complement the complement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complement, or with status {@code 400 (Bad Request)} if the complement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/complements")
    public ResponseEntity<Complement> createComplement(@RequestBody Complement complement) throws URISyntaxException {
        log.debug("REST request to save Complement : {}", complement);
        if (complement.getId() != null) {
            throw new BadRequestAlertException("A new complement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Complement result = complementRepository.save(complement);
        return ResponseEntity.created(new URI("/api/complements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /complements} : Updates an existing complement.
     *
     * @param complement the complement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complement,
     * or with status {@code 400 (Bad Request)} if the complement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/complements")
    public ResponseEntity<Complement> updateComplement(@RequestBody Complement complement) throws URISyntaxException {
        log.debug("REST request to update Complement : {}", complement);
        if (complement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Complement result = complementRepository.save(complement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, complement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /complements} : get all the complements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complements in body.
     */
    @GetMapping("/complements")
    public List<Complement> getAllComplements() {
        log.debug("REST request to get all Complements");
        return complementRepository.findAll();
    }

    /**
     * {@code GET  /complements/:id} : get the "id" complement.
     *
     * @param id the id of the complement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/complements/{id}")
    public ResponseEntity<Complement> getComplement(@PathVariable Long id) {
        log.debug("REST request to get Complement : {}", id);
        Optional<Complement> complement = complementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(complement);
    }

    /**
     * {@code DELETE  /complements/:id} : delete the "id" complement.
     *
     * @param id the id of the complement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/complements/{id}")
    public ResponseEntity<Void> deleteComplement(@PathVariable Long id) {
        log.debug("REST request to delete Complement : {}", id);
        complementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
