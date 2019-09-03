package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Complement.
 */
@Entity
@Table(name = "complement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Complement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complement_description")
    private String complementDescription;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplementDescription() {
        return complementDescription;
    }

    public Complement complementDescription(String complementDescription) {
        this.complementDescription = complementDescription;
        return this;
    }

    public void setComplementDescription(String complementDescription) {
        this.complementDescription = complementDescription;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Complement employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Complement employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Complement)) {
            return false;
        }
        return id != null && id.equals(((Complement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Complement{" +
            "id=" + getId() +
            ", complementDescription='" + getComplementDescription() + "'" +
            ", employeeId=" + getEmployeeId() +
            ", employeeName='" + getEmployeeName() + "'" +
            "}";
    }
}
