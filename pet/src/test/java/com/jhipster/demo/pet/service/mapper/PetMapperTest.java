package com.jhipster.demo.pet.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PetMapperTest {

    private PetMapper petMapper;

    @BeforeEach
    public void setUp() {
        petMapper = new PetMapperImpl();
    }
}
