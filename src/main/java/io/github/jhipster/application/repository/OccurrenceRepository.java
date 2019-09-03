package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Occurrence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Occurrence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

}
