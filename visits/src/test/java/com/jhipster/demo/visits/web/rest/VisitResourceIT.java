package com.jhipster.demo.visits.web.rest;

import static com.jhipster.demo.visits.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.jhipster.demo.visits.IntegrationTest;
import com.jhipster.demo.visits.domain.Visit;
import com.jhipster.demo.visits.repository.EntityManager;
import com.jhipster.demo.visits.repository.VisitRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link VisitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class VisitResourceIT {

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_PET_ID = 1L;
    private static final Long UPDATED_PET_ID = 2L;

    private static final Long DEFAULT_VET_ID = 1L;
    private static final Long UPDATED_VET_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/visits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Visit visit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createEntity(EntityManager em) {
        Visit visit = new Visit()
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .petId(DEFAULT_PET_ID)
            .vetId(DEFAULT_VET_ID)
            .description(DEFAULT_DESCRIPTION);
        return visit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createUpdatedEntity(EntityManager em) {
        Visit visit = new Visit()
            .start(UPDATED_START)
            .end(UPDATED_END)
            .petId(UPDATED_PET_ID)
            .vetId(UPDATED_VET_ID)
            .description(UPDATED_DESCRIPTION);
        return visit;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Visit.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        visit = createEntity(em);
    }

    @Test
    void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().collectList().block().size();
        // Create the Visit
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testVisit.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testVisit.getPetId()).isEqualTo(DEFAULT_PET_ID);
        assertThat(testVisit.getVetId()).isEqualTo(DEFAULT_VET_ID);
        assertThat(testVisit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createVisitWithExistingId() throws Exception {
        // Create the Visit with an existing ID
        visit.setId(1L);

        int databaseSizeBeforeCreate = visitRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().collectList().block().size();
        // set the field null
        visit.setStart(null);

        // Create the Visit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().collectList().block().size();
        // set the field null
        visit.setEnd(null);

        // Create the Visit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().collectList().block().size();
        // set the field null
        visit.setPetId(null);

        // Create the Visit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkVetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitRepository.findAll().collectList().block().size();
        // set the field null
        visit.setVetId(null);

        // Create the Visit, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllVisitsAsStream() {
        // Initialize the database
        visitRepository.save(visit).block();

        List<Visit> visitList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Visit.class)
            .getResponseBody()
            .filter(visit::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(visitList).isNotNull();
        assertThat(visitList).hasSize(1);
        Visit testVisit = visitList.get(0);
        assertThat(testVisit.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testVisit.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testVisit.getPetId()).isEqualTo(DEFAULT_PET_ID);
        assertThat(testVisit.getVetId()).isEqualTo(DEFAULT_VET_ID);
        assertThat(testVisit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void getAllVisits() {
        // Initialize the database
        visitRepository.save(visit).block();

        // Get all the visitList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(visit.getId().intValue()))
            .jsonPath("$.[*].start")
            .value(hasItem(sameInstant(DEFAULT_START)))
            .jsonPath("$.[*].end")
            .value(hasItem(sameInstant(DEFAULT_END)))
            .jsonPath("$.[*].petId")
            .value(hasItem(DEFAULT_PET_ID.intValue()))
            .jsonPath("$.[*].vetId")
            .value(hasItem(DEFAULT_VET_ID.intValue()))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @Test
    void getVisit() {
        // Initialize the database
        visitRepository.save(visit).block();

        // Get the visit
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, visit.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(visit.getId().intValue()))
            .jsonPath("$.start")
            .value(is(sameInstant(DEFAULT_START)))
            .jsonPath("$.end")
            .value(is(sameInstant(DEFAULT_END)))
            .jsonPath("$.petId")
            .value(is(DEFAULT_PET_ID.intValue()))
            .jsonPath("$.vetId")
            .value(is(DEFAULT_VET_ID.intValue()))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingVisit() {
        // Get the visit
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingVisit() throws Exception {
        // Initialize the database
        visitRepository.save(visit).block();

        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findById(visit.getId()).block();
        updatedVisit.start(UPDATED_START).end(UPDATED_END).petId(UPDATED_PET_ID).vetId(UPDATED_VET_ID).description(UPDATED_DESCRIPTION);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedVisit.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedVisit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getStart()).isEqualTo(UPDATED_START);
        assertThat(testVisit.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testVisit.getPetId()).isEqualTo(UPDATED_PET_ID);
        assertThat(testVisit.getVetId()).isEqualTo(UPDATED_VET_ID);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();
        visit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, visit.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();
        visit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();
        visit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateVisitWithPatch() throws Exception {
        // Initialize the database
        visitRepository.save(visit).block();

        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();

        // Update the visit using partial update
        Visit partialUpdatedVisit = new Visit();
        partialUpdatedVisit.setId(visit.getId());

        partialUpdatedVisit.end(UPDATED_END).petId(UPDATED_PET_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedVisit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedVisit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testVisit.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testVisit.getPetId()).isEqualTo(UPDATED_PET_ID);
        assertThat(testVisit.getVetId()).isEqualTo(DEFAULT_VET_ID);
        assertThat(testVisit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateVisitWithPatch() throws Exception {
        // Initialize the database
        visitRepository.save(visit).block();

        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();

        // Update the visit using partial update
        Visit partialUpdatedVisit = new Visit();
        partialUpdatedVisit.setId(visit.getId());

        partialUpdatedVisit
            .start(UPDATED_START)
            .end(UPDATED_END)
            .petId(UPDATED_PET_ID)
            .vetId(UPDATED_VET_ID)
            .description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedVisit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedVisit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getStart()).isEqualTo(UPDATED_START);
        assertThat(testVisit.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testVisit.getPetId()).isEqualTo(UPDATED_PET_ID);
        assertThat(testVisit.getVetId()).isEqualTo(UPDATED_VET_ID);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();
        visit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, visit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();
        visit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().collectList().block().size();
        visit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(visit))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteVisit() {
        // Initialize the database
        visitRepository.save(visit).block();

        int databaseSizeBeforeDelete = visitRepository.findAll().collectList().block().size();

        // Delete the visit
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, visit.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Visit> visitList = visitRepository.findAll().collectList().block();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
