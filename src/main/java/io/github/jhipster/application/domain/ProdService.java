package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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
