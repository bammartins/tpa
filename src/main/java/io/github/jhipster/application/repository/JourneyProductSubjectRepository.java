package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.JourneyProductSubject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JourneyProductSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneyProductSubjectRepository extends JpaRepository<JourneyProductSubject, Long> {

}
