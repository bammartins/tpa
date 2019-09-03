package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.OccurrenceTreatment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OccurrenceTreatment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccurrenceTreatmentRepository extends JpaRepository<OccurrenceTreatment, Long> {

}
