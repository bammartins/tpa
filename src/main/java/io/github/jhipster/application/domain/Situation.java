package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Situation.
 */
@Entity
@Table(name = "situation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Situation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "situation_description")
    private String situationDescription;

    @Column(name = "status_code")
    private Integer statusCode;

    @OneToMany(mappedBy = "situation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Occurrence> occurrences = new HashSet<>();

    @OneToMany(mappedBy = "situation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OccurrenceTreatment> occurenceTreatments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSituationDescription() {
        return situationDescription;
    }

    public Situation situationDescription(String situationDescription) {
        this.situationDescription = situationDescription;
        return this;
    }

    public void setSituationDescription(String situationDescription) {
        this.situationDescription = situationDescription;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Situation statusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Set<Occurrence> getOccurrences() {
        return occurrences;
    }

    public Situation occurrences(Set<Occurrence> occurrences) {
        this.occurrences = occurrences;
        return this;
    }

    public Situation addOccurrence(Occurrence occurrence) {
        this.occurrences.add(occurrence);
        occurrence.setSituation(this);
        return this;
    }

    public Situation removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
        occurrence.setSituation(null);
        return this;
    }

    public void setOccurrences(Set<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public Set<OccurrenceTreatment> getOccurenceTreatments() {
        return occurenceTreatments;
    }

    public Situation occurenceTreatments(Set<OccurrenceTreatment> occurrenceTreatments) {
        this.occurenceTreatments = occurrenceTreatments;
        return this;
    }

    public Situation addOccurenceTreatment(OccurrenceTreatment occurrenceTreatment) {
        this.occurenceTreatments.add(occurrenceTreatment);
        occurrenceTreatment.setSituation(this);
        return this;
    }

    public Situation removeOccurenceTreatment(OccurrenceTreatment occurrenceTreatment) {
        this.occurenceTreatments.remove(occurrenceTreatment);
        occurrenceTreatment.setSituation(null);
        return this;
    }

    public void setOccurenceTreatments(Set<OccurrenceTreatment> occurrenceTreatments) {
        this.occurenceTreatments = occurrenceTreatments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Situation)) {
            return false;
        }
        return id != null && id.equals(((Situation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Situation{" +
            "id=" + getId() +
            ", situationDescription='" + getSituationDescription() + "'" +
            ", statusCode=" + getStatusCode() +
            "}";
    }
}
