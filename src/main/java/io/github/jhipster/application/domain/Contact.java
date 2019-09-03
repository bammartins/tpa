package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_status")
    private String contactStatus;

    @Column(name = "contact_type")
    private Integer contactType;

    @Column(name = "contact_description")
    private String contactDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public Contact contactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
        return this;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    public Integer getContactType() {
        return contactType;
    }

    public Contact contactType(Integer contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public String getContactDescription() {
        return contactDescription;
    }

    public Contact contactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
        return this;
    }

    public void setContactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", contactStatus='" + getContactStatus() + "'" +
            ", contactType=" + getContactType() +
            ", contactDescription='" + getContactDescription() + "'" +
            "}";
    }
}
