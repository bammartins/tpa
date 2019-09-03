package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProdService.
 */
@Entity
@Table(name = "prod_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProdService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prod_service_description")
    private String prodServiceDescription;

    @ManyToOne
    @JsonIgnoreProperties("prodServices")
    private Journey journey;

    @OneToMany(mappedBy = "prodService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JourneyProductSubject> journeyProductSubjects = new HashSet<>();

    @OneToMany(mappedBy = "prodService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Subject> subjects = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("prodServices")
    private Occurrence occurrence;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdServiceDescription() {
        return prodServiceDescription;
    }

    public ProdService prodServiceDescription(String prodServiceDescription) {
        this.prodServiceDescription = prodServiceDescription;
        return this;
    }

    public void setProdServiceDescription(String prodServiceDescription) {
        this.prodServiceDescription = prodServiceDescription;
    }

    public Journey getJourney() {
        return journey;
    }

    public ProdService journey(Journey journey) {
        this.journey = journey;
        return this;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public Set<JourneyProductSubject> getJourneyProductSubjects() {
        return journeyProductSubjects;
    }

    public ProdService journeyProductSubjects(Set<JourneyProductSubject> journeyProductSubjects) {
        this.journeyProductSubjects = journeyProductSubjects;
        return this;
    }

    public ProdService addJourneyProductSubject(JourneyProductSubject journeyProductSubject) {
        this.journeyProductSubjects.add(journeyProductSubject);
        journeyProductSubject.setProdService(this);
        return this;
    }

    public ProdService removeJourneyProductSubject(JourneyProductSubject journeyProductSubject) {
        this.journeyProductSubjects.remove(journeyProductSubject);
        journeyProductSubject.setProdService(null);
        return this;
    }

    public void setJourneyProductSubjects(Set<JourneyProductSubject> journeyProductSubjects) {
        this.journeyProductSubjects = journeyProductSubjects;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public ProdService subjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public ProdService addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.setProdService(this);
        return this;
    }

    public ProdService removeSubject(Subject subject) {
        this.subjects.remove(subject);
        subject.setProdService(null);
        return this;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public ProdService occurrence(Occurrence occurrence) {
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
        if (!(o instanceof ProdService)) {
            return false;
        }
        return id != null && id.equals(((ProdService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProdService{" +
            "id=" + getId() +
            ", prodServiceDescription='" + getProdServiceDescription() + "'" +
            "}";
    }
}
