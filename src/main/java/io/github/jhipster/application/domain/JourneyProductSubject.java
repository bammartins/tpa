package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A JourneyProductSubject.
 */
@Entity
@Table(name = "journey_product_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JourneyProductSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sla")
    private Integer sla;

    @Column(name = "sla_type")
    private Integer slaType;

    @ManyToOne
    @JsonIgnoreProperties("journeyProductSubjects")
    private ProdService prodService;

    @ManyToOne
    @JsonIgnoreProperties("journeyProductSubjects")
    private Subject subject;

    @OneToMany(mappedBy = "journeyProductSubject")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Journey> journeys = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("journeyProductSubjects")
    private Priority priority;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSla() {
        return sla;
    }

    public JourneyProductSubject sla(Integer sla) {
        this.sla = sla;
        return this;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public Integer getSlaType() {
        return slaType;
    }

    public JourneyProductSubject slaType(Integer slaType) {
        this.slaType = slaType;
        return this;
    }

    public void setSlaType(Integer slaType) {
        this.slaType = slaType;
    }

    public ProdService getProdService() {
        return prodService;
    }

    public JourneyProductSubject prodService(ProdService prodService) {
        this.prodService = prodService;
        return this;
    }

    public void setProdService(ProdService prodService) {
        this.prodService = prodService;
    }

    public Subject getSubject() {
        return subject;
    }

    public JourneyProductSubject subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Journey> getJourneys() {
        return journeys;
    }

    public JourneyProductSubject journeys(Set<Journey> journeys) {
        this.journeys = journeys;
        return this;
    }

    public JourneyProductSubject addJourney(Journey journey) {
        this.journeys.add(journey);
        journey.setJourneyProductSubject(this);
        return this;
    }

    public JourneyProductSubject removeJourney(Journey journey) {
        this.journeys.remove(journey);
        journey.setJourneyProductSubject(null);
        return this;
    }

    public void setJourneys(Set<Journey> journeys) {
        this.journeys = journeys;
    }

    public Priority getPriority() {
        return priority;
    }

    public JourneyProductSubject priority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourneyProductSubject)) {
            return false;
        }
        return id != null && id.equals(((JourneyProductSubject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JourneyProductSubject{" +
            "id=" + getId() +
            ", sla=" + getSla() +
            ", slaType=" + getSlaType() +
            "}";
    }
}
