package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Journey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Journey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {

}
