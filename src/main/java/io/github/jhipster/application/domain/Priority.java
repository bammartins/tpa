package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Priority.
 */
@Entity
@Table(name = "priority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Priority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priority_description")
    private String priorityDescription;

    @OneToMany(mappedBy = "priority")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OccurrenceTi> occurrenceTis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriorityDescription() {
        return priorityDescription;
    }

    public Priority priorityDescription(String priorityDescription) {
        this.priorityDescription = priorityDescription;
        return this;
    }

    public void setPriorityDescription(String priorityDescription) {
        this.priorityDescription = priorityDescription;
    }

    public Set<OccurrenceTi> getOccurrenceTis() {
        return occurrenceTis;
    }

    public Priority occurrenceTis(Set<OccurrenceTi> occurrenceTis) {
        this.occurrenceTis = occurrenceTis;
        return this;
    }

    public Priority addOccurrenceTi(OccurrenceTi occurrenceTi) {
        this.occurrenceTis.add(occurrenceTi);
        occurrenceTi.setPriority(this);
        return this;
    }

    public Priority removeOccurrenceTi(OccurrenceTi occurrenceTi) {
        this.occurrenceTis.remove(occurrenceTi);
        occurrenceTi.setPriority(null);
        return this;
    }

    public void setOccurrenceTis(Set<OccurrenceTi> occurrenceTis) {
        this.occurrenceTis = occurrenceTis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Priority)) {
            return false;
        }
        return id != null && id.equals(((Priority) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Priority{" +
            "id=" + getId() +
            ", priorityDescription='" + getPriorityDescription() + "'" +
            "}";
    }
}
