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
    @Column("start_time")
    private ZonedDateTime startTime;

    @NotNull(message = "must not be null")
    @Column("end_time")
    private ZonedDateTime endTime;

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

    public ZonedDateTime getStartTime() {
        return this.startTime;
    }

    public Visit startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return this.endTime;
    }

    public Visit endTime(ZonedDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
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
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", petId=" + getPetId() +
            ", vetId=" + getVetId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
