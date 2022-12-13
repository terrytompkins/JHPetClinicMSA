package com.jhipster.demo.pet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pet.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpeciesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Species.class);
        Species species1 = new Species();
        species1.setId("id1");
        Species species2 = new Species();
        species2.setId(species1.getId());
        assertThat(species1).isEqualTo(species2);
        species2.setId("id2");
        assertThat(species1).isNotEqualTo(species2);
        species1.setId(null);
        assertThat(species1).isNotEqualTo(species2);
    }
}
