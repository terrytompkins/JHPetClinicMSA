package com.jhipster.demo.pet.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pet.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetDTO.class);
        PetDTO petDTO1 = new PetDTO();
        petDTO1.setId("id1");
        PetDTO petDTO2 = new PetDTO();
        assertThat(petDTO1).isNotEqualTo(petDTO2);
        petDTO2.setId(petDTO1.getId());
        assertThat(petDTO1).isEqualTo(petDTO2);
        petDTO2.setId("id2");
        assertThat(petDTO1).isNotEqualTo(petDTO2);
        petDTO1.setId(null);
        assertThat(petDTO1).isNotEqualTo(petDTO2);
    }
}
