package com.jhipster.demo.pet.web.rest;

import com.jhipster.demo.pet.repository.SpeciesRepository;
import com.jhipster.demo.pet.service.SpeciesService;
import com.jhipster.demo.pet.service.dto.SpeciesDTO;
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
 * REST controller for managing {@link com.jhipster.demo.pet.domain.Species}.
 */
@RestController
@RequestMapping("/api")
public class SpeciesResource {

    private final Logger log = LoggerFactory.getLogger(SpeciesResource.class);

    private static final String ENTITY_NAME = "petSpecies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeciesService speciesService;

    private final SpeciesRepository speciesRepository;

    public SpeciesResource(SpeciesService speciesService, SpeciesRepository speciesRepository) {
        this.speciesService = speciesService;
        this.speciesRepository = speciesRepository;
    }

    /**
     * {@code POST  /species} : Create a new species.
     *
     * @param speciesDTO the speciesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speciesDTO, or with status {@code 400 (Bad Request)} if the species has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/species")
    public ResponseEntity<SpeciesDTO> createSpecies(@Valid @RequestBody SpeciesDTO speciesDTO) throws URISyntaxException {
        log.debug("REST request to save Species : {}", speciesDTO);
        if (speciesDTO.getId() != null) {
            throw new BadRequestAlertException("A new species cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeciesDTO result = speciesService.save(speciesDTO);
        return ResponseEntity
            .created(new URI("/api/species/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /species/:id} : Updates an existing species.
     *
     * @param id the id of the speciesDTO to save.
     * @param speciesDTO the speciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speciesDTO,
     * or with status {@code 400 (Bad Request)} if the speciesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/species/{id}")
    public ResponseEntity<SpeciesDTO> updateSpecies(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody SpeciesDTO speciesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Species : {}, {}", id, speciesDTO);
        if (speciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speciesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speciesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpeciesDTO result = speciesService.update(speciesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speciesDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /species/:id} : Partial updates given fields of an existing species, field will ignore if it is null
     *
     * @param id the id of the speciesDTO to save.
     * @param speciesDTO the speciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speciesDTO,
     * or with status {@code 400 (Bad Request)} if the speciesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the speciesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the speciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/species/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpeciesDTO> partialUpdateSpecies(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody SpeciesDTO speciesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Species partially : {}, {}", id, speciesDTO);
        if (speciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speciesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!speciesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpeciesDTO> result = speciesService.partialUpdate(speciesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speciesDTO.getId())
        );
    }

    /**
     * {@code GET  /species} : get all the species.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of species in body.
     */
    @GetMapping("/species")
    public ResponseEntity<List<SpeciesDTO>> getAllSpecies(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Species");
        Page<SpeciesDTO> page = speciesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /species/:id} : get the "id" species.
     *
     * @param id the id of the speciesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speciesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/species/{id}")
    public ResponseEntity<SpeciesDTO> getSpecies(@PathVariable String id) {
        log.debug("REST request to get Species : {}", id);
        Optional<SpeciesDTO> speciesDTO = speciesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speciesDTO);
    }

    /**
     * {@code DELETE  /species/:id} : delete the "id" species.
     *
     * @param id the id of the speciesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/species/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable String id) {
        log.debug("REST request to delete Species : {}", id);
        speciesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
