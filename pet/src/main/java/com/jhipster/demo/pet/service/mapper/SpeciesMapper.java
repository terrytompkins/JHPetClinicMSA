package com.jhipster.demo.pet.service.mapper;

import com.jhipster.demo.pet.domain.Species;
import com.jhipster.demo.pet.service.dto.SpeciesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Species} and its DTO {@link SpeciesDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpeciesMapper extends EntityMapper<SpeciesDTO, Species> {}
