package com.jhipster.demo.pet.web.rest;

import com.jhipster.demo.pet.repository.OwnerRepository;
import com.jhipster.demo.pet.service.OwnerService;
import com.jhipster.demo.pet.service.dto.OwnerDTO;
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
 * REST controller for managing {@link com.jhipster.demo.pet.domain.Owner}.
 */
@RestController
@RequestMapping("/api")
public class OwnerResource {

    private final Logger log = LoggerFactory.getLogger(OwnerResource.class);

    private static final String ENTITY_NAME = "petOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OwnerService ownerService;

    private final OwnerRepository ownerRepository;

    public OwnerResource(OwnerService ownerService, OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.ownerRepository = ownerRepository;
    }

    /**
     * {@code POST  /owners} : Create a new owner.
     *
     * @param ownerDTO the ownerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ownerDTO, or with status {@code 400 (Bad Request)} if the owner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/owners")
    public ResponseEntity<OwnerDTO> createOwner(@Valid @RequestBody OwnerDTO ownerDTO) throws URISyntaxException {
        log.debug("REST request to save Owner : {}", ownerDTO);
        if (ownerDTO.getId() != null) {
            throw new BadRequestAlertException("A new owner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OwnerDTO result = ownerService.save(ownerDTO);
        return ResponseEntity
            .created(new URI("/api/owners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /owners/:id} : Updates an existing owner.
     *
     * @param id the id of the ownerDTO to save.
     * @param ownerDTO the ownerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ownerDTO,
     * or with status {@code 400 (Bad Request)} if the ownerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ownerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/owners/{id}")
    public ResponseEntity<OwnerDTO> updateOwner(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OwnerDTO ownerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Owner : {}, {}", id, ownerDTO);
        if (ownerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ownerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ownerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OwnerDTO result = ownerService.update(ownerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ownerDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /owners/:id} : Partial updates given fields of an existing owner, field will ignore if it is null
     *
     * @param id the id of the ownerDTO to save.
     * @param ownerDTO the ownerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ownerDTO,
     * or with status {@code 400 (Bad Request)} if the ownerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ownerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ownerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/owners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OwnerDTO> partialUpdateOwner(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OwnerDTO ownerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Owner partially : {}, {}", id, ownerDTO);
        if (ownerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ownerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ownerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OwnerDTO> result = ownerService.partialUpdate(ownerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ownerDTO.getId())
        );
    }

    /**
     * {@code GET  /owners} : get all the owners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of owners in body.
     */
    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDTO>> getAllOwners(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Owners");
        Page<OwnerDTO> page = ownerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /owners/:id} : get the "id" owner.
     *
     * @param id the id of the ownerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ownerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable String id) {
        log.debug("REST request to get Owner : {}", id);
        Optional<OwnerDTO> ownerDTO = ownerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ownerDTO);
    }

    /**
     * {@code DELETE  /owners/:id} : delete the "id" owner.
     *
     * @param id the id of the ownerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/owners/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable String id) {
        log.debug("REST request to delete Owner : {}", id);
        ownerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
