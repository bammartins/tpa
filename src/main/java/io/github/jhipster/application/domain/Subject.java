package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_description")
    private String subjectDescription;

    @ManyToOne
    @JsonIgnoreProperties("subjects")
    private ProdService prodService;

    @OneToMany(mappedBy = "subject")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JourneyProductSubject> journeyProductSubjects = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("subjects")
    private Occurrence subjectDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public Subject subjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
        return this;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public ProdService getProdService() {
        return prodService;
    }

    public Subject prodService(ProdService prodService) {
        this.prodService = prodService;
        return this;
    }

    public void setProdService(ProdService prodService) {
        this.prodService = prodService;
    }

    public Set<JourneyProductSubject> getJourneyProductSubjects() {
        return journeyProductSubjects;
    }

    public Subject journeyProductSubjects(Set<JourneyProductSubject> journeyProductSubjects) {
        this.journeyProductSubjects = journeyProductSubjects;
        return this;
    }

    public Subject addJourneyProductSubject(JourneyProductSubject journeyProductSubject) {
        this.journeyProductSubjects.add(journeyProductSubject);
        journeyProductSubject.setSubject(this);
        return this;
    }

    public Subject removeJourneyProductSubject(JourneyProductSubject journeyProductSubject) {
        this.journeyProductSubjects.remove(journeyProductSubject);
        journeyProductSubject.setSubject(null);
        return this;
    }

    public void setJourneyProductSubjects(Set<JourneyProductSubject> journeyProductSubjects) {
        this.journeyProductSubjects = journeyProductSubjects;
    }

    public Occurrence getSubjectDescription() {
        return subjectDescription;
    }

    public Subject subjectDescription(Occurrence occurrence) {
        this.subjectDescription = occurrence;
        return this;
    }

    public void setSubjectDescription(Occurrence occurrence) {
        this.subjectDescription = occurrence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        return id != null && id.equals(((Subject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectDescription='" + getSubjectDescription() + "'" +
            "}";
    }
}
