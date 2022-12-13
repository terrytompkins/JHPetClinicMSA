package com.jhipster.demo.pet.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.demo.pet.IntegrationTest;
import com.jhipster.demo.pet.domain.Visit;
import com.jhipster.demo.pet.repository.VisitRepository;
import com.jhipster.demo.pet.service.VisitService;
import com.jhipster.demo.pet.service.dto.VisitDTO;
import com.jhipster.demo.pet.service.mapper.VisitMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link VisitResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VisitResourceIT {

    private static final LocalDate DEFAULT_VISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/visits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private VisitRepository visitRepository;

    @Mock
    private VisitRepository visitRepositoryMock;

    @Autowired
    private VisitMapper visitMapper;

    @Mock
    private VisitService visitServiceMock;

    @Autowired
    private MockMvc restVisitMockMvc;

    private Visit visit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createEntity() {
        Visit visit = new Visit().visitDate(DEFAULT_VISIT_DATE).description(DEFAULT_DESCRIPTION);
        return visit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createUpdatedEntity() {
        Visit visit = new Visit().visitDate(UPDATED_VISIT_DATE).description(UPDATED_DESCRIPTION);
        return visit;
    }

    @BeforeEach
    public void initTest() {
        visitRepository.deleteAll();
        visit = createEntity();
    }

    @Test
    void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();
        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);
        restVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getVisitDate()).isEqualTo(DEFAULT_VISIT_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createVisitWithExistingId() throws Exception {
        // Create the Visit with an existing ID
        visit.setId("existing_id");
        VisitDTO visitDTO = visitMapper.toDto(visit);

        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().size();
        // set the field null
        visit.setDescription(null);

        // Create the Visit, which fails.
        VisitDTO visitDTO = visitMapper.toDto(visit);

        restVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllVisits() throws Exception {
        // Initialize the database
        visitRepository.save(visit);

        // Get all the visitList
        restVisitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId())))
            .andExpect(jsonPath("$.[*].visitDate").value(hasItem(DEFAULT_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVisitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(visitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVisitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(visitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVisitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(visitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVisitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(visitRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getVisit() throws Exception {
        // Initialize the database
        visitRepository.save(visit);

        // Get the visit
        restVisitMockMvc
            .perform(get(ENTITY_API_URL_ID, visit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visit.getId()))
            .andExpect(jsonPath("$.visitDate").value(DEFAULT_VISIT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingVisit() throws Exception {
        // Get the visit
        restVisitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingVisit() throws Exception {
        // Initialize the database
        visitRepository.save(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findById(visit.getId()).get();
        updatedVisit.visitDate(UPDATED_VISIT_DATE).description(UPDATED_DESCRIPTION);
        VisitDTO visitDTO = visitMapper.toDto(updatedVisit);

        restVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, visitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitDTO))
            )
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getVisitDate()).isEqualTo(UPDATED_VISIT_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(UUID.randomUUID().toString());

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, visitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(UUID.randomUUID().toString());

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(UUID.randomUUID().toString());

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateVisitWithPatch() throws Exception {
        // Initialize the database
        visitRepository.save(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit using partial update
        Visit partialUpdatedVisit = new Visit();
        partialUpdatedVisit.setId(visit.getId());

        partialUpdatedVisit.description(UPDATED_DESCRIPTION);

        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisit))
            )
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getVisitDate()).isEqualTo(DEFAULT_VISIT_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void fullUpdateVisitWithPatch() throws Exception {
        // Initialize the database
        visitRepository.save(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit using partial update
        Visit partialUpdatedVisit = new Visit();
        partialUpdatedVisit.setId(visit.getId());

        partialUpdatedVisit.visitDate(UPDATED_VISIT_DATE).description(UPDATED_DESCRIPTION);

        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisit))
            )
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getVisitDate()).isEqualTo(UPDATED_VISIT_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(UUID.randomUUID().toString());

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, visitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(UUID.randomUUID().toString());

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();
        visit.setId(UUID.randomUUID().toString());

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteVisit() throws Exception {
        // Initialize the database
        visitRepository.save(visit);

        int databaseSizeBeforeDelete = visitRepository.findAll().size();

        // Delete the visit
        restVisitMockMvc
            .perform(delete(ENTITY_API_URL_ID, visit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
