package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OccurTreatAttach.
 */
@Entity
@Table(name = "occur_treat_attach")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OccurTreatAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("occurTreatAttaches")
    private OccurrenceTreatment occurrenceTreatment;

    @ManyToOne
    @JsonIgnoreProperties("occurTreatAttaches")
    private Attachment attachment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OccurrenceTreatment getOccurrenceTreatment() {
        return occurrenceTreatment;
    }

    public OccurTreatAttach occurrenceTreatment(OccurrenceTreatment occurrenceTreatment) {
        this.occurrenceTreatment = occurrenceTreatment;
        return this;
    }

    public void setOccurrenceTreatment(OccurrenceTreatment occurrenceTreatment) {
        this.occurrenceTreatment = occurrenceTreatment;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public OccurTreatAttach attachment(Attachment attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OccurTreatAttach)) {
            return false;
        }
        return id != null && id.equals(((OccurTreatAttach) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OccurTreatAttach{" +
            "id=" + getId() +
            "}";
    }
}
