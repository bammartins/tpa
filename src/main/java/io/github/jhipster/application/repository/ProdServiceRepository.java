package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ProdService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProdService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdServiceRepository extends JpaRepository<ProdService, Long> {

}
