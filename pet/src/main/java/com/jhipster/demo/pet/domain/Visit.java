package com.jhipster.demo.pet.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Visit.
 */
@Entity
@Table(name = "visits")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @NotNull
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @NotNull
    @Column(name = "vet_id", nullable = false)
    private Long vetId;

    @Column(name = "description")
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

    public LocalDate getVisitDate() {
        return this.visitDate;
    }

    public Visit visitDate(LocalDate visitDate) {
        this.setVisitDate(visitDate);
        return this;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
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
            ", visitDate='" + getVisitDate() + "'" +
            ", petId=" + getPetId() +
            ", vetId=" + getVetId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
