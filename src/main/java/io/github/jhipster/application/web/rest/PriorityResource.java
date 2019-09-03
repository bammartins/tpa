package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Priority;
import io.github.jhipster.application.repository.PriorityRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.Priority}.
 */
@RestController
@RequestMapping("/api")
public class PriorityResource {

    private final Logger log = LoggerFactory.getLogger(PriorityResource.class);

    private static final String ENTITY_NAME = "tpaPriority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriorityRepository priorityRepository;

    public PriorityResource(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    /**
     * {@code POST  /priorities} : Create a new priority.
     *
     * @param priority the priority to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priority, or with status {@code 400 (Bad Request)} if the priority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/priorities")
    public ResponseEntity<Priority> createPriority(@RequestBody Priority priority) throws URISyntaxException {
        log.debug("REST request to save Priority : {}", priority);
        if (priority.getId() != null) {
            throw new BadRequestAlertException("A new priority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Priority result = priorityRepository.save(priority);
        return ResponseEntity.created(new URI("/api/priorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /priorities} : Updates an existing priority.
     *
     * @param priority the priority to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priority,
     * or with status {@code 400 (Bad Request)} if the priority is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priority couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/priorities")
    public ResponseEntity<Priority> updatePriority(@RequestBody Priority priority) throws URISyntaxException {
        log.debug("REST request to update Priority : {}", priority);
        if (priority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Priority result = priorityRepository.save(priority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priority.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /priorities} : get all the priorities.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priorities in body.
     */
    @GetMapping("/priorities")
    public List<Priority> getAllPriorities() {
        log.debug("REST request to get all Priorities");
        return priorityRepository.findAll();
    }

    /**
     * {@code GET  /priorities/:id} : get the "id" priority.
     *
     * @param id the id of the priority to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priority, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/priorities/{id}")
    public ResponseEntity<Priority> getPriority(@PathVariable Long id) {
        log.debug("REST request to get Priority : {}", id);
        Optional<Priority> priority = priorityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(priority);
    }

    /**
     * {@code DELETE  /priorities/:id} : delete the "id" priority.
     *
     * @param id the id of the priority to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/priorities/{id}")
    public ResponseEntity<Void> deletePriority(@PathVariable Long id) {
        log.debug("REST request to delete Priority : {}", id);
        priorityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
