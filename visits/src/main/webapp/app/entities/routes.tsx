import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import Species from './pet/species';
import Visit from './visit';
import Owner from './pet/owner';
import Specialty from './pet/specialty';
import Pet from './pet/pet';
import Vet from './pet/vet';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('visits', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="species/*" element={<Species />} />
        <Route path="visit/*" element={<Visit />} />
        <Route path="owner/*" element={<Owner />} />
        <Route path="specialty/*" element={<Specialty />} />
        <Route path="pet/*" element={<Pet />} />
        <Route path="vet/*" element={<Vet />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
