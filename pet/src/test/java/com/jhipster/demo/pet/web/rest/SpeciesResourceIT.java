package com.jhipster.demo.pet.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.demo.pet.IntegrationTest;
import com.jhipster.demo.pet.domain.Species;
import com.jhipster.demo.pet.repository.SpeciesRepository;
import com.jhipster.demo.pet.service.dto.SpeciesDTO;
import com.jhipster.demo.pet.service.mapper.SpeciesMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SpeciesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpeciesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/species";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private SpeciesMapper speciesMapper;

    @Autowired
    private MockMvc restSpeciesMockMvc;

    private Species species;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Species createEntity() {
        Species species = new Species().name(DEFAULT_NAME);
        return species;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Species createUpdatedEntity() {
        Species species = new Species().name(UPDATED_NAME);
        return species;
    }

    @BeforeEach
    public void initTest() {
        speciesRepository.deleteAll();
        species = createEntity();
    }

    @Test
    void createSpecies() throws Exception {
        int databaseSizeBeforeCreate = speciesRepository.findAll().size();
        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);
        restSpeciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speciesDTO)))
            .andExpect(status().isCreated());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeCreate + 1);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createSpeciesWithExistingId() throws Exception {
        // Create the Species with an existing ID
        species.setId("existing_id");
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        int databaseSizeBeforeCreate = speciesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setName(null);

        // Create the Species, which fails.
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        restSpeciesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speciesDTO)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSpecies() throws Exception {
        // Initialize the database
        speciesRepository.save(species);

        // Get all the speciesList
        restSpeciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(species.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getSpecies() throws Exception {
        // Initialize the database
        speciesRepository.save(species);

        // Get the species
        restSpeciesMockMvc
            .perform(get(ENTITY_API_URL_ID, species.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(species.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingSpecies() throws Exception {
        // Get the species
        restSpeciesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSpecies() throws Exception {
        // Initialize the database
        speciesRepository.save(species);

        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();

        // Update the species
        Species updatedSpecies = speciesRepository.findById(species.getId()).get();
        updatedSpecies.name(UPDATED_NAME);
        SpeciesDTO speciesDTO = speciesMapper.toDto(updatedSpecies);

        restSpeciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, speciesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speciesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();
        species.setId(UUID.randomUUID().toString());

        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, speciesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();
        species.setId(UUID.randomUUID().toString());

        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(speciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();
        species.setId(UUID.randomUUID().toString());

        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeciesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(speciesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSpeciesWithPatch() throws Exception {
        // Initialize the database
        speciesRepository.save(species);

        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();

        // Update the species using partial update
        Species partialUpdatedSpecies = new Species();
        partialUpdatedSpecies.setId(species.getId());

        partialUpdatedSpecies.name(UPDATED_NAME);

        restSpeciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecies))
            )
            .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void fullUpdateSpeciesWithPatch() throws Exception {
        // Initialize the database
        speciesRepository.save(species);

        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();

        // Update the species using partial update
        Species partialUpdatedSpecies = new Species();
        partialUpdatedSpecies.setId(species.getId());

        partialUpdatedSpecies.name(UPDATED_NAME);

        restSpeciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecies.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecies))
            )
            .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();
        species.setId(UUID.randomUUID().toString());

        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, speciesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(speciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();
        species.setId(UUID.randomUUID().toString());

        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(speciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();
        species.setId(UUID.randomUUID().toString());

        // Create the Species
        SpeciesDTO speciesDTO = speciesMapper.toDto(species);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpeciesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(speciesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSpecies() throws Exception {
        // Initialize the database
        speciesRepository.save(species);

        int databaseSizeBeforeDelete = speciesRepository.findAll().size();

        // Delete the species
        restSpeciesMockMvc
            .perform(delete(ENTITY_API_URL_ID, species.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
