package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Justification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Justification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JustificationRepository extends JpaRepository<Justification, Long> {

}
