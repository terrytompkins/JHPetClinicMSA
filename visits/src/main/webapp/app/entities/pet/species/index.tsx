import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Species from './species';
import SpeciesDetail from './species-detail';
import SpeciesUpdate from './species-update';
import SpeciesDeleteDialog from './species-delete-dialog';

const SpeciesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Species />} />
    <Route path="new" element={<SpeciesUpdate />} />
    <Route path=":id">
      <Route index element={<SpeciesDetail />} />
      <Route path="edit" element={<SpeciesUpdate />} />
      <Route path="delete" element={<SpeciesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpeciesRoutes;
