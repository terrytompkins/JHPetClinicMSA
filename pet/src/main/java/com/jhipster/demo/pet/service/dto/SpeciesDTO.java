package com.jhipster.demo.pet.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jhipster.demo.pet.domain.Species} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpeciesDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 80)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpeciesDTO)) {
            return false;
        }

        SpeciesDTO speciesDTO = (SpeciesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, speciesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpeciesDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
