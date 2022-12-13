package com.jhipster.demo.pet.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.demo.pet.IntegrationTest;
import com.jhipster.demo.pet.domain.Owner;
import com.jhipster.demo.pet.repository.OwnerRepository;
import com.jhipster.demo.pet.service.dto.OwnerDTO;
import com.jhipster.demo.pet.service.mapper.OwnerMapper;
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
 * Integration tests for the {@link OwnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OwnerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/owners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private MockMvc restOwnerMockMvc;

    private Owner owner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createEntity() {
        Owner owner = new Owner()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .telephone(DEFAULT_TELEPHONE);
        return owner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner createUpdatedEntity() {
        Owner owner = new Owner()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .telephone(UPDATED_TELEPHONE);
        return owner;
    }

    @BeforeEach
    public void initTest() {
        ownerRepository.deleteAll();
        owner = createEntity();
    }

    @Test
    void createOwner() throws Exception {
        int databaseSizeBeforeCreate = ownerRepository.findAll().size();
        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);
        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isCreated());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate + 1);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOwner.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testOwner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOwner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOwner.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    void createOwnerWithExistingId() throws Exception {
        // Create the Owner with an existing ID
        owner.setId("existing_id");
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        int databaseSizeBeforeCreate = ownerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setFirstName(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setLastName(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setAddress(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setCity(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = ownerRepository.findAll().size();
        // set the field null
        owner.setTelephone(null);

        // Create the Owner, which fails.
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        restOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isBadRequest());

        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllOwners() throws Exception {
        // Initialize the database
        ownerRepository.save(owner);

        // Get all the ownerList
        restOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @Test
    void getOwner() throws Exception {
        // Initialize the database
        ownerRepository.save(owner);

        // Get the owner
        restOwnerMockMvc
            .perform(get(ENTITY_API_URL_ID, owner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(owner.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }

    @Test
    void getNonExistingOwner() throws Exception {
        // Get the owner
        restOwnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingOwner() throws Exception {
        // Initialize the database
        ownerRepository.save(owner);

        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Update the owner
        Owner updatedOwner = ownerRepository.findById(owner.getId()).get();
        updatedOwner
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .telephone(UPDATED_TELEPHONE);
        OwnerDTO ownerDTO = ownerMapper.toDto(updatedOwner);

        restOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOwner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOwner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOwner.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    void putNonExistingOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();
        owner.setId(UUID.randomUUID().toString());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ownerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();
        owner.setId(UUID.randomUUID().toString());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();
        owner.setId(UUID.randomUUID().toString());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOwnerWithPatch() throws Exception {
        // Initialize the database
        ownerRepository.save(owner);

        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Update the owner using partial update
        Owner partialUpdatedOwner = new Owner();
        partialUpdatedOwner.setId(owner.getId());

        partialUpdatedOwner.lastName(UPDATED_LAST_NAME).address(UPDATED_ADDRESS).city(UPDATED_CITY).telephone(UPDATED_TELEPHONE);

        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOwner))
            )
            .andExpect(status().isOk());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOwner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOwner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOwner.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    void fullUpdateOwnerWithPatch() throws Exception {
        // Initialize the database
        ownerRepository.save(owner);

        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();

        // Update the owner using partial update
        Owner partialUpdatedOwner = new Owner();
        partialUpdatedOwner.setId(owner.getId());

        partialUpdatedOwner
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .telephone(UPDATED_TELEPHONE);

        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOwner))
            )
            .andExpect(status().isOk());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
        Owner testOwner = ownerList.get(ownerList.size() - 1);
        assertThat(testOwner.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOwner.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOwner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOwner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOwner.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    void patchNonExistingOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();
        owner.setId(UUID.randomUUID().toString());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ownerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();
        owner.setId(UUID.randomUUID().toString());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ownerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOwner() throws Exception {
        int databaseSizeBeforeUpdate = ownerRepository.findAll().size();
        owner.setId(UUID.randomUUID().toString());

        // Create the Owner
        OwnerDTO ownerDTO = ownerMapper.toDto(owner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOwnerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ownerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Owner in the database
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOwner() throws Exception {
        // Initialize the database
        ownerRepository.save(owner);

        int databaseSizeBeforeDelete = ownerRepository.findAll().size();

        // Delete the owner
        restOwnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, owner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Owner> ownerList = ownerRepository.findAll();
        assertThat(ownerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
