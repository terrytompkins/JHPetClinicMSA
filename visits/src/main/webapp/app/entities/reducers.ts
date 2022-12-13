import species from 'app/entities/pet/species/species.reducer';
import pet from 'app/entities/pet/pet/pet.reducer';
import specialty from 'app/entities/pet/specialty/specialty.reducer';
import visit from 'app/entities/visit/visit.reducer';
import vet from 'app/entities/pet/vet/vet.reducer';
import owner from 'app/entities/pet/owner/owner.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  species,
  pet,
  specialty,
  visit,
  vet,
  owner,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
