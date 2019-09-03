package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A OccurrenceTi.
 */
@Entity
@Table(name = "occurrence_ti")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OccurrenceTi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "os_version")
    private String osVersion;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "phone_model")
    private String phoneModel;

    @Column(name = "sla")
    private Integer sla;

    @Column(name = "sla_type")
    private Integer slaType;

    @Column(name = "error_time")
    private Instant errorTime;

    @Column(name = "priority_code")
    private Integer priorityCode;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "occurrence_ti_description")
    private String occurrenceTiDescription;

    @OneToMany(mappedBy = "occurrenceTi")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Occurrence> occurrences = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("occurrenceTis")
    private Priority priority;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public OccurrenceTi osVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public OccurrenceTi appVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public OccurrenceTi phoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
        return this;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public Integer getSla() {
        return sla;
    }

    public OccurrenceTi sla(Integer sla) {
        this.sla = sla;
        return this;
    }

    public void setSla(Integer sla) {
        this.sla = sla;
    }

    public Integer getSlaType() {
        return slaType;
    }

    public OccurrenceTi slaType(Integer slaType) {
        this.slaType = slaType;
        return this;
    }

    public void setSlaType(Integer slaType) {
        this.slaType = slaType;
    }

    public Instant getErrorTime() {
        return errorTime;
    }

    public OccurrenceTi errorTime(Instant errorTime) {
        this.errorTime = errorTime;
        return this;
    }

    public void setErrorTime(Instant errorTime) {
        this.errorTime = errorTime;
    }

    public Integer getPriorityCode() {
        return priorityCode;
    }

    public OccurrenceTi priorityCode(Integer priorityCode) {
        this.priorityCode = priorityCode;
        return this;
    }

    public void setPriorityCode(Integer priorityCode) {
        this.priorityCode = priorityCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public OccurrenceTi statusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getOccurrenceTiDescription() {
        return occurrenceTiDescription;
    }

    public OccurrenceTi occurrenceTiDescription(String occurrenceTiDescription) {
        this.occurrenceTiDescription = occurrenceTiDescription;
        return this;
    }

    public void setOccurrenceTiDescription(String occurrenceTiDescription) {
        this.occurrenceTiDescription = occurrenceTiDescription;
    }

    public Set<Occurrence> getOccurrences() {
        return occurrences;
    }

    public OccurrenceTi occurrences(Set<Occurrence> occurrences) {
        this.occurrences = occurrences;
        return this;
    }

    public OccurrenceTi addOccurrence(Occurrence occurrence) {
        this.occurrences.add(occurrence);
        occurrence.setOccurrenceTi(this);
        return this;
    }

    public OccurrenceTi removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
        occurrence.setOccurrenceTi(null);
        return this;
    }

    public void setOccurrences(Set<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public Priority getPriority() {
        return priority;
    }

    public OccurrenceTi priority(Priority priority) {
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
        if (!(o instanceof OccurrenceTi)) {
            return false;
        }
        return id != null && id.equals(((OccurrenceTi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OccurrenceTi{" +
            "id=" + getId() +
            ", osVersion='" + getOsVersion() + "'" +
            ", appVersion='" + getAppVersion() + "'" +
            ", phoneModel='" + getPhoneModel() + "'" +
            ", sla=" + getSla() +
            ", slaType=" + getSlaType() +
            ", errorTime='" + getErrorTime() + "'" +
            ", priorityCode=" + getPriorityCode() +
            ", statusCode=" + getStatusCode() +
            ", occurrenceTiDescription='" + getOccurrenceTiDescription() + "'" +
            "}";
    }
}
