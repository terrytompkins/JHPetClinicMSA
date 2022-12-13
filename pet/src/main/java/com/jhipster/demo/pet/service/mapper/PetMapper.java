package com.jhipster.demo.pet.service.mapper;

import com.jhipster.demo.pet.domain.Owner;
import com.jhipster.demo.pet.domain.Pet;
import com.jhipster.demo.pet.domain.Species;
import com.jhipster.demo.pet.service.dto.OwnerDTO;
import com.jhipster.demo.pet.service.dto.PetDTO;
import com.jhipster.demo.pet.service.dto.SpeciesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pet} and its DTO {@link PetDTO}.
 */
@Mapper(componentModel = "spring")
public interface PetMapper extends EntityMapper<PetDTO, Pet> {
    @Mapping(target = "species", source = "species", qualifiedByName = "speciesName")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownerLastName")
    PetDTO toDto(Pet s);

    @Named("speciesName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SpeciesDTO toDtoSpeciesName(Species species);

    @Named("ownerLastName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "lastName", source = "lastName")
    OwnerDTO toDtoOwnerLastName(Owner owner);
}
