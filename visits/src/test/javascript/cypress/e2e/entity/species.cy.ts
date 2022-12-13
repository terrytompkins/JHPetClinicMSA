import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Species e2e test', () => {
  const speciesPageUrl = '/species';
  const speciesPageUrlPattern = new RegExp('/species(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const speciesSample = { name: 'of' };

  let species;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/pet/api/species+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/pet/api/species').as('postEntityRequest');
    cy.intercept('DELETE', '/services/pet/api/species/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (species) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/pet/api/species/${species.id}`,
      }).then(() => {
        species = undefined;
      });
    }
  });

  it('Species menu should load Species page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('species');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Species').should('exist');
    cy.url().should('match', speciesPageUrlPattern);
  });

  describe('Species page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(speciesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Species page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/species/new$'));
        cy.getEntityCreateUpdateHeading('Species');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speciesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/pet/api/species',
          body: speciesSample,
        }).then(({ body }) => {
          species = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/pet/api/species+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/pet/api/species?page=0&size=20>; rel="last",<http://localhost/services/pet/api/species?page=0&size=20>; rel="first"',
              },
              body: [species],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(speciesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Species page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('species');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speciesPageUrlPattern);
      });

      it('edit button click should load edit Species page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Species');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speciesPageUrlPattern);
      });

      it('edit button click should load edit Species page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Species');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speciesPageUrlPattern);
      });

      it('last delete button click should delete instance of Species', () => {
        cy.intercept('GET', '/services/pet/api/species/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('species').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speciesPageUrlPattern);

        species = undefined;
      });
    });
  });

  describe('new Species page', () => {
    beforeEach(() => {
      cy.visit(`${speciesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Species');
    });

    it('should create an instance of Species', () => {
      cy.get(`[data-cy="name"]`).type('SSL National copying').should('have.value', 'SSL National copying');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        species = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', speciesPageUrlPattern);
    });
  });
});
