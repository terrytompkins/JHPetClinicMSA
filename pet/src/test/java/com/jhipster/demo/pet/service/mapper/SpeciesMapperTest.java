package com.jhipster.demo.pet.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpeciesMapperTest {

    private SpeciesMapper speciesMapper;

    @BeforeEach
    public void setUp() {
        speciesMapper = new SpeciesMapperImpl();
    }
}
