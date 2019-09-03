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
 * A Occurrence.
 */
@Entity
@Table(name = "occurrence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Occurrence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occurrence_code_nbr")
    private Integer occurrenceCodeNbr;

    @Column(name = "cpf")
    private Integer cpf;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "media")
    private Integer media;

    @Column(name = "type")
    private Integer type;

    @Column(name = "final_comment")
    private String finalComment;

    @Column(name = "entry_way")
    private String entryWay;

    @Column(name = "agility")
    private Boolean agility;

    @Column(name = "prediction")
    private Instant prediction;

    @Column(name = "resolve")
    private Instant resolve;

    @OneToMany(mappedBy = "occurrence")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Justification> justifications = new HashSet<>();

    @OneToMany(mappedBy = "occurrence")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("occurrences")
    private OccurrenceTi occurrenceTi;

    @ManyToOne
    @JsonIgnoreProperties("occurrences")
    private OccurrenceTreatment occurrenceTreatment;

    @ManyToOne
    @JsonIgnoreProperties("occurrences")
    private Situation situation;

    @ManyToOne
    @JsonIgnoreProperties("occurrences")
    private Complement complement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOccurrenceCodeNbr() {
        return occurrenceCodeNbr;
    }

    public Occurrence occurrenceCodeNbr(Integer occurrenceCodeNbr) {
        this.occurrenceCodeNbr = occurrenceCodeNbr;
        return this;
    }

    public void setOccurrenceCodeNbr(Integer occurrenceCodeNbr) {
        this.occurrenceCodeNbr = occurrenceCodeNbr;
    }

    public Integer getCpf() {
        return cpf;
    }

    public Occurrence cpf(Integer cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Occurrence customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Occurrence employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Occurrence employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getMedia() {
        return media;
    }

    public Occurrence media(Integer media) {
        this.media = media;
        return this;
    }

    public void setMedia(Integer media) {
        this.media = media;
    }

    public Integer getType() {
        return type;
    }

    public Occurrence type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFinalComment() {
        return finalComment;
    }

    public Occurrence finalComment(String finalComment) {
        this.finalComment = finalComment;
        return this;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public String getEntryWay() {
        return entryWay;
    }

    public Occurrence entryWay(String entryWay) {
        this.entryWay = entryWay;
        return this;
    }

    public void setEntryWay(String entryWay) {
        this.entryWay = entryWay;
    }

    public Boolean isAgility() {
        return agility;
    }

    public Occurrence agility(Boolean agility) {
        this.agility = agility;
        return this;
    }

    public void setAgility(Boolean agility) {
        this.agility = agility;
    }

    public Instant getPrediction() {
        return prediction;
    }

    public Occurrence prediction(Instant prediction) {
        this.prediction = prediction;
        return this;
    }

    public void setPrediction(Instant prediction) {
        this.prediction = prediction;
    }

    public Instant getResolve() {
        return resolve;
    }

    public Occurrence resolve(Instant resolve) {
        this.resolve = resolve;
        return this;
    }

    public void setResolve(Instant resolve) {
        this.resolve = resolve;
    }

    public Set<Justification> getJustifications() {
        return justifications;
    }

    public Occurrence justifications(Set<Justification> justifications) {
        this.justifications = justifications;
        return this;
    }

    public Occurrence addJustification(Justification justification) {
        this.justifications.add(justification);
        justification.setOccurrence(this);
        return this;
    }

    public Occurrence removeJustification(Justification justification) {
        this.justifications.remove(justification);
        justification.setOccurrence(null);
        return this;
    }

    public void setJustifications(Set<Justification> justifications) {
        this.justifications = justifications;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public Occurrence attachments(Set<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Occurrence addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setOccurrence(this);
        return this;
    }

    public Occurrence removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setOccurrence(null);
        return this;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public OccurrenceTi getOccurrenceTi() {
        return occurrenceTi;
    }

    public Occurrence occurrenceTi(OccurrenceTi occurrenceTi) {
        this.occurrenceTi = occurrenceTi;
        return this;
    }

    public void setOccurrenceTi(OccurrenceTi occurrenceTi) {
        this.occurrenceTi = occurrenceTi;
    }

    public OccurrenceTreatment getOccurrenceTreatment() {
        return occurrenceTreatment;
    }

    public Occurrence occurrenceTreatment(OccurrenceTreatment occurrenceTreatment) {
        this.occurrenceTreatment = occurrenceTreatment;
        return this;
    }

    public void setOccurrenceTreatment(OccurrenceTreatment occurrenceTreatment) {
        this.occurrenceTreatment = occurrenceTreatment;
    }

    public Situation getSituation() {
        return situation;
    }

    public Occurrence situation(Situation situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public Complement getComplement() {
        return complement;
    }

    public Occurrence complement(Complement complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(Complement complement) {
        this.complement = complement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Occurrence)) {
            return false;
        }
        return id != null && id.equals(((Occurrence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Occurrence{" +
            "id=" + getId() +
            ", occurrenceCodeNbr=" + getOccurrenceCodeNbr() +
            ", cpf=" + getCpf() +
            ", customerId=" + getCustomerId() +
            ", employeeId=" + getEmployeeId() +
            ", employeeName='" + getEmployeeName() + "'" +
            ", media=" + getMedia() +
            ", type=" + getType() +
            ", finalComment='" + getFinalComment() + "'" +
            ", entryWay='" + getEntryWay() + "'" +
            ", agility='" + isAgility() + "'" +
            ", prediction='" + getPrediction() + "'" +
            ", resolve='" + getResolve() + "'" +
            "}";
    }
}
