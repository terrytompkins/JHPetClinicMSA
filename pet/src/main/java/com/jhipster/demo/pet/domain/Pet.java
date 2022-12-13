package com.jhipster.demo.pet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Pet.
 */
@Document(collection = "pets")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("name")
    private String name;

    @Field("birth_date")
    private LocalDate birthDate;

    @DBRef
    @Field("visits")
    @JsonIgnoreProperties(value = { "pet" }, allowSetters = true)
    private Set<Visit> visits = new HashSet<>();

    @DBRef
    @Field("species")
    private Species species;

    @DBRef
    @Field("owner")
    @JsonIgnoreProperties(value = { "pets" }, allowSetters = true)
    private Owner owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Pet id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Pet name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public Pet birthDate(LocalDate birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public void setVisits(Set<Visit> visits) {
        if (this.visits != null) {
            this.visits.forEach(i -> i.setPet(null));
        }
        if (visits != null) {
            visits.forEach(i -> i.setPet(this));
        }
        this.visits = visits;
    }

    public Pet visits(Set<Visit> visits) {
        this.setVisits(visits);
        return this;
    }

    public Pet addVisits(Visit visit) {
        this.visits.add(visit);
        visit.setPet(this);
        return this;
    }

    public Pet removeVisits(Visit visit) {
        this.visits.remove(visit);
        visit.setPet(null);
        return this;
    }

    public Species getSpecies() {
        return this.species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Pet species(Species species) {
        this.setSpecies(species);
        return this;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Pet owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pet)) {
            return false;
        }
        return id != null && id.equals(((Pet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            "}";
    }
}
