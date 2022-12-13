import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import Species from './pet/species';
import Pet from './pet/pet';
import Specialty from './pet/specialty';
import Visit from './visit';
import Vet from './pet/vet';
import Owner from './pet/owner';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('visits', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="species/*" element={<Species />} />
        <Route path="pet/*" element={<Pet />} />
        <Route path="specialty/*" element={<Specialty />} />
        <Route path="visit/*" element={<Visit />} />
        <Route path="vet/*" element={<Vet />} />
        <Route path="owner/*" element={<Owner />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
