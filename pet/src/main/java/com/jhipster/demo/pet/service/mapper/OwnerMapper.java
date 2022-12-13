package com.jhipster.demo.pet.service.mapper;

import com.jhipster.demo.pet.domain.Owner;
import com.jhipster.demo.pet.service.dto.OwnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {}
