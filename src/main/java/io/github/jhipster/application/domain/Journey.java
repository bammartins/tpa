package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Journey.
 */
@Entity
@Table(name = "journey")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "journey_description")
    private String journeyDescription;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJourneyDescription() {
        return journeyDescription;
    }

    public Journey journeyDescription(String journeyDescription) {
        this.journeyDescription = journeyDescription;
        return this;
    }

    public void setJourneyDescription(String journeyDescription) {
        this.journeyDescription = journeyDescription;
    }

    public Boolean isActive() {
        return active;
    }

    public Journey active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Journey)) {
            return false;
        }
        return id != null && id.equals(((Journey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Journey{" +
            "id=" + getId() +
            ", journeyDescription='" + getJourneyDescription() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
