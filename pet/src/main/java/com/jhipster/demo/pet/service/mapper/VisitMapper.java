package com.jhipster.demo.pet.service.mapper;

import com.jhipster.demo.pet.domain.Pet;
import com.jhipster.demo.pet.domain.Visit;
import com.jhipster.demo.pet.service.dto.PetDTO;
import com.jhipster.demo.pet.service.dto.VisitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Visit} and its DTO {@link VisitDTO}.
 */
@Mapper(componentModel = "spring")
public interface VisitMapper extends EntityMapper<VisitDTO, Visit> {
    @Mapping(target = "pet", source = "pet", qualifiedByName = "petName")
    VisitDTO toDto(Visit s);

    @Named("petName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PetDTO toDtoPetName(Pet pet);
}
