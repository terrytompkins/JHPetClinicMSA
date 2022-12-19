import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/species">
        <Translate contentKey="global.menu.entities.petSpecies" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/visit">
        <Translate contentKey="global.menu.entities.visit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/owner">
        <Translate contentKey="global.menu.entities.petOwner" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/specialty">
        <Translate contentKey="global.menu.entities.vetSpecialty" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pet">
        <Translate contentKey="global.menu.entities.petPet" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vet">
        <Translate contentKey="global.menu.entities.vetVet" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
