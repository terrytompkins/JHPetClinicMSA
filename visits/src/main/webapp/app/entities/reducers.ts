import species from 'app/entities/pet/species/species.reducer';
import visit from 'app/entities/visit/visit.reducer';
import owner from 'app/entities/pet/owner/owner.reducer';
import specialty from 'app/entities/vet/specialty/specialty.reducer';
import pet from 'app/entities/pet/pet/pet.reducer';
import vet from 'app/entities/vet/vet/vet.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  species,
  visit,
  owner,
  specialty,
  pet,
  vet,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
