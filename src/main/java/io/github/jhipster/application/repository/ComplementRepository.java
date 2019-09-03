package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Complement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Complement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplementRepository extends JpaRepository<Complement, Long> {

}
