package com.jhipster.demo.pet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Visit.
 */
@Document(collection = "visits")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("visit_date")
    private LocalDate visitDate;

    @NotNull
    @Size(max = 255)
    @Field("description")
    private String description;

    @DBRef
    @Field("pet")
    @JsonIgnoreProperties(value = { "visits", "species", "owner" }, allowSetters = true)
    private Pet pet;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Visit id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
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

    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Visit pet(Pet pet) {
        this.setPet(pet);
        return this;
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
            ", description='" + getDescription() + "'" +
            "}";
    }
}
