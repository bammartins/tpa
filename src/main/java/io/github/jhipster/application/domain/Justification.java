package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Justification.
 */
@Entity
@Table(name = "justification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Justification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "justification_description")
    private String justificationDescription;

    @ManyToOne
    @JsonIgnoreProperties("justifications")
    private Occurrence occurrence;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJustificationDescription() {
        return justificationDescription;
    }

    public Justification justificationDescription(String justificationDescription) {
        this.justificationDescription = justificationDescription;
        return this;
    }

    public void setJustificationDescription(String justificationDescription) {
        this.justificationDescription = justificationDescription;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public Justification occurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
        return this;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Justification)) {
            return false;
        }
        return id != null && id.equals(((Justification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Justification{" +
            "id=" + getId() +
            ", justificationDescription='" + getJustificationDescription() + "'" +
            "}";
    }
}
