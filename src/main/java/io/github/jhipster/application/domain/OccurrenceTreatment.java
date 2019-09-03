package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OccurrenceTreatment.
 */
@Entity
@Table(name = "occurrence_treatment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OccurrenceTreatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occurrence_description")
    private String occurrenceDescription;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "contact_type")
    private Integer contactType;

    @Column(name = "contact_status")
    private Integer contactStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOccurrenceDescription() {
        return occurrenceDescription;
    }

    public OccurrenceTreatment occurrenceDescription(String occurrenceDescription) {
        this.occurrenceDescription = occurrenceDescription;
        return this;
    }

    public void setOccurrenceDescription(String occurrenceDescription) {
        this.occurrenceDescription = occurrenceDescription;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public OccurrenceTreatment employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public OccurrenceTreatment employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getContactType() {
        return contactType;
    }

    public OccurrenceTreatment contactType(Integer contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public Integer getContactStatus() {
        return contactStatus;
    }

    public OccurrenceTreatment contactStatus(Integer contactStatus) {
        this.contactStatus = contactStatus;
        return this;
    }

    public void setContactStatus(Integer contactStatus) {
        this.contactStatus = contactStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OccurrenceTreatment)) {
            return false;
        }
        return id != null && id.equals(((OccurrenceTreatment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OccurrenceTreatment{" +
            "id=" + getId() +
            ", occurrenceDescription='" + getOccurrenceDescription() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeName='" + getEmployeeName() + "'" +
            ", contactType=" + getContactType() +
            ", contactStatus=" + getContactStatus() +
            "}";
    }
}
