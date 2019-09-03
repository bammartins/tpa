package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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
