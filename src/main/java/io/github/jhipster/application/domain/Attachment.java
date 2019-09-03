package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Column(name = "gcc_id")
    private Integer gccId;

    @Column(name = "gcc_name")
    private String gccName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public Attachment attachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Integer getGccId() {
        return gccId;
    }

    public Attachment gccId(Integer gccId) {
        this.gccId = gccId;
        return this;
    }

    public void setGccId(Integer gccId) {
        this.gccId = gccId;
    }

    public String getGccName() {
        return gccName;
    }

    public Attachment gccName(String gccName) {
        this.gccName = gccName;
        return this;
    }

    public void setGccName(String gccName) {
        this.gccName = gccName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return id != null && id.equals(((Attachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", attachmentName='" + getAttachmentName() + "'" +
            ", gccId=" + getGccId() +
            ", gccName='" + getGccName() + "'" +
            "}";
    }
}
