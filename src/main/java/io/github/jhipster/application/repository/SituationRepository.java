package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Situation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Situation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituationRepository extends JpaRepository<Situation, Long> {

}
