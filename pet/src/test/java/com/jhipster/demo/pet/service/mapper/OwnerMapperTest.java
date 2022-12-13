package com.jhipster.demo.pet.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OwnerMapperTest {

    private OwnerMapper ownerMapper;

    @BeforeEach
    public void setUp() {
        ownerMapper = new OwnerMapperImpl();
    }
}
