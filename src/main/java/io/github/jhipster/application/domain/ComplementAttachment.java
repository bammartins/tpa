package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ComplementAttachment.
 */
@Entity
@Table(name = "complement_attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ComplementAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("complementAttachments")
    private Attachment attachment;

    @ManyToOne
    @JsonIgnoreProperties("complementAttachments")
    private Complement complement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public ComplementAttachment attachment(Attachment attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Complement getComplement() {
        return complement;
    }

    public ComplementAttachment complement(Complement complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(Complement complement) {
        this.complement = complement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplementAttachment)) {
            return false;
        }
        return id != null && id.equals(((ComplementAttachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ComplementAttachment{" +
            "id=" + getId() +
            "}";
    }
}
