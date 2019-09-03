package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.OccurrenceTi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OccurrenceTi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccurrenceTiRepository extends JpaRepository<OccurrenceTi, Long> {

}
