package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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
