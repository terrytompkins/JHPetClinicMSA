package com.jhipster.demo.visits.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Visit.
 */
@Table("visits")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("start")
    private ZonedDateTime start;

    @NotNull(message = "must not be null")
    @Column("end")
    private ZonedDateTime end;

    @NotNull(message = "must not be null")
    @Column("pet_id")
    private Long petId;

    @NotNull(message = "must not be null")
    @Column("vet_id")
    private Long vetId;

    @Column("description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Visit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return this.start;
    }

    public Visit start(ZonedDateTime start) {
        this.setStart(start);
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return this.end;
    }

    public Visit end(ZonedDateTime end) {
        this.setEnd(end);
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Long getPetId() {
        return this.petId;
    }

    public Visit petId(Long petId) {
        this.setPetId(petId);
        return this;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVetId() {
        return this.vetId;
    }

    public Visit vetId(Long vetId) {
        this.setVetId(vetId);
        return this;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    public String getDescription() {
        return this.description;
    }

    public Visit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visit)) {
            return false;
        }
        return id != null && id.equals(((Visit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Visit{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", petId=" + getPetId() +
            ", vetId=" + getVetId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
