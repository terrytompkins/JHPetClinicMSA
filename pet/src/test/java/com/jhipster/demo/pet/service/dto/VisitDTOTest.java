package com.jhipster.demo.pet.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pet.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VisitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitDTO.class);
        VisitDTO visitDTO1 = new VisitDTO();
        visitDTO1.setId("id1");
        VisitDTO visitDTO2 = new VisitDTO();
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
        visitDTO2.setId(visitDTO1.getId());
        assertThat(visitDTO1).isEqualTo(visitDTO2);
        visitDTO2.setId("id2");
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
        visitDTO1.setId(null);
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
    }
}
