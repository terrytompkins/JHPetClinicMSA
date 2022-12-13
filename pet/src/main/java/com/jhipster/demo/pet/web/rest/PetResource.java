package com.jhipster.demo.pet.web.rest;

import com.jhipster.demo.pet.repository.PetRepository;
import com.jhipster.demo.pet.service.PetService;
import com.jhipster.demo.pet.service.dto.PetDTO;
import com.jhipster.demo.pet.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.demo.pet.domain.Pet}.
 */
@RestController
@RequestMapping("/api")
public class PetResource {

    private final Logger log = LoggerFactory.getLogger(PetResource.class);

    private static final String ENTITY_NAME = "petPet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetService petService;

    private final PetRepository petRepository;

    public PetResource(PetService petService, PetRepository petRepository) {
        this.petService = petService;
        this.petRepository = petRepository;
    }

    /**
     * {@code POST  /pets} : Create a new pet.
     *
     * @param petDTO the petDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petDTO, or with status {@code 400 (Bad Request)} if the pet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pets")
    public ResponseEntity<PetDTO> createPet(@Valid @RequestBody PetDTO petDTO) throws URISyntaxException {
        log.debug("REST request to save Pet : {}", petDTO);
        if (petDTO.getId() != null) {
            throw new BadRequestAlertException("A new pet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetDTO result = petService.save(petDTO);
        return ResponseEntity
            .created(new URI("/api/pets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /pets/:id} : Updates an existing pet.
     *
     * @param id the id of the petDTO to save.
     * @param petDTO the petDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petDTO,
     * or with status {@code 400 (Bad Request)} if the petDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pets/{id}")
    public ResponseEntity<PetDTO> updatePet(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PetDTO petDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pet : {}, {}", id, petDTO);
        if (petDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, petDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!petRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PetDTO result = petService.update(petDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, petDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /pets/:id} : Partial updates given fields of an existing pet, field will ignore if it is null
     *
     * @param id the id of the petDTO to save.
     * @param petDTO the petDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petDTO,
     * or with status {@code 400 (Bad Request)} if the petDTO is not valid,
     * or with status {@code 404 (Not Found)} if the petDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the petDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PetDTO> partialUpdatePet(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PetDTO petDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pet partially : {}, {}", id, petDTO);
        if (petDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, petDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!petRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PetDTO> result = petService.partialUpdate(petDTO);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, petDTO.getId()));
    }

    /**
     * {@code GET  /pets} : get all the pets.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pets in body.
     */
    @GetMapping("/pets")
    public ResponseEntity<List<PetDTO>> getAllPets(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Pets");
        Page<PetDTO> page;
        if (eagerload) {
            page = petService.findAllWithEagerRelationships(pageable);
        } else {
            page = petService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pets/:id} : get the "id" pet.
     *
     * @param id the id of the petDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable String id) {
        log.debug("REST request to get Pet : {}", id);
        Optional<PetDTO> petDTO = petService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petDTO);
    }

    /**
     * {@code DELETE  /pets/:id} : delete the "id" pet.
     *
     * @param id the id of the petDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pets/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable String id) {
        log.debug("REST request to delete Pet : {}", id);
        petService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
