package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.ProdService;
import io.github.jhipster.application.repository.ProdServiceRepository;
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
 * REST controller for managing {@link io.github.jhipster.application.domain.ProdService}.
 */
@RestController
@RequestMapping("/api")
public class ProdServiceResource {

    private final Logger log = LoggerFactory.getLogger(ProdServiceResource.class);

    private static final String ENTITY_NAME = "tpaProdService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdServiceRepository prodServiceRepository;

    public ProdServiceResource(ProdServiceRepository prodServiceRepository) {
        this.prodServiceRepository = prodServiceRepository;
    }

    /**
     * {@code POST  /prod-services} : Create a new prodService.
     *
     * @param prodService the prodService to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodService, or with status {@code 400 (Bad Request)} if the prodService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prod-services")
    public ResponseEntity<ProdService> createProdService(@RequestBody ProdService prodService) throws URISyntaxException {
        log.debug("REST request to save ProdService : {}", prodService);
        if (prodService.getId() != null) {
            throw new BadRequestAlertException("A new prodService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdService result = prodServiceRepository.save(prodService);
        return ResponseEntity.created(new URI("/api/prod-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prod-services} : Updates an existing prodService.
     *
     * @param prodService the prodService to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodService,
     * or with status {@code 400 (Bad Request)} if the prodService is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodService couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prod-services")
    public ResponseEntity<ProdService> updateProdService(@RequestBody ProdService prodService) throws URISyntaxException {
        log.debug("REST request to update ProdService : {}", prodService);
        if (prodService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProdService result = prodServiceRepository.save(prodService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prodService.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prod-services} : get all the prodServices.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prodServices in body.
     */
    @GetMapping("/prod-services")
    public List<ProdService> getAllProdServices() {
        log.debug("REST request to get all ProdServices");
        return prodServiceRepository.findAll();
    }

    /**
     * {@code GET  /prod-services/:id} : get the "id" prodService.
     *
     * @param id the id of the prodService to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodService, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prod-services/{id}")
    public ResponseEntity<ProdService> getProdService(@PathVariable Long id) {
        log.debug("REST request to get ProdService : {}", id);
        Optional<ProdService> prodService = prodServiceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prodService);
    }

    /**
     * {@code DELETE  /prod-services/:id} : delete the "id" prodService.
     *
     * @param id the id of the prodService to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prod-services/{id}")
    public ResponseEntity<Void> deleteProdService(@PathVariable Long id) {
        log.debug("REST request to delete ProdService : {}", id);
        prodServiceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
