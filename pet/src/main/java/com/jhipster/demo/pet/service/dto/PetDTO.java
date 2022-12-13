package com.jhipster.demo.pet.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jhipster.demo.pet.domain.Pet} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PetDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 30)
    private String name;

    private LocalDate birthDate;

    private SpeciesDTO species;

    private OwnerDTO owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public SpeciesDTO getSpecies() {
        return species;
    }

    public void setSpecies(SpeciesDTO species) {
        this.species = species;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PetDTO)) {
            return false;
        }

        PetDTO petDTO = (PetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, petDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PetDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", species=" + getSpecies() +
            ", owner=" + getOwner() +
            "}";
    }
}
