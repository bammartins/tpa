package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ComplementAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ComplementAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplementAttachmentRepository extends JpaRepository<ComplementAttachment, Long> {

}
