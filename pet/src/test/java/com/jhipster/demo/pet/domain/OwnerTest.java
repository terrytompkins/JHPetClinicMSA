package com.jhipster.demo.pet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pet.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner.class);
        Owner owner1 = new Owner();
        owner1.setId("id1");
        Owner owner2 = new Owner();
        owner2.setId(owner1.getId());
        assertThat(owner1).isEqualTo(owner2);
        owner2.setId("id2");
        assertThat(owner1).isNotEqualTo(owner2);
        owner1.setId(null);
        assertThat(owner1).isNotEqualTo(owner2);
    }
}
