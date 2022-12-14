import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPet } from 'app/shared/model/pet/pet.model';
import { getEntities as getPets } from 'app/entities/pet/pet/pet.reducer';
import { getEntity as getOwner } from 'app/entities/pet/owner/owner.reducer';

import { IVet } from 'app/shared/model/vet/vet.model';
import { getEntities as getVets } from 'app/entities/vet/vet/vet.reducer';


import { IVisit } from 'app/shared/model/visit.model';
import { getEntity, updateEntity, createEntity, reset } from './visit.reducer';
import { IOwner } from '../../shared/model/pet/owner.model';

export const VisitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const [ query ] = useSearchParams();
  
  const isNew = id === undefined;

  const pets: Array<IPet> = useAppSelector(state => state.visits.pet.entities);
  const vets: Array<IVet> = useAppSelector(state => state.visits.vet.entities);

  const visitEntity = useAppSelector(state => state.visits.visit.entity);
  const loading = useAppSelector(state => state.visits.visit.loading);
  const updating = useAppSelector(state => state.visits.visit.updating);
  const updateSuccess = useAppSelector(state => state.visits.visit.updateSuccess);

  const handleClose = () => {
    navigate('/visit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPets({}));
    dispatch(getVets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);

    const selectedPet = pets.find(p => p.id === parseInt(values.petId));
    const selectedVet = vets.find(v => v.id === parseInt(values.vetId));

    const description = `${selectedPet.name} ${selectedPet.owner.lastName} with Dr. ${selectedVet.lastName}`

    const entity = {
      ...visitEntity,
      ...values,
      description
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          startTime: query.get('start') ? convertDateTimeFromServer(query.get('start')) : displayDefaultDateTime(),
          endTime: query.get('end') ? convertDateTimeFromServer(query.get('end')) : displayDefaultDateTime(),
          vetId: query.get('vetId')
        }
      : {
          ...visitEntity,
          startTime: convertDateTimeFromServer(visitEntity.startTime),
          endTime: convertDateTimeFromServer(visitEntity.endTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="visitsApp.visit.home.createOrEditLabel" data-cy="VisitCreateUpdateHeading">
            <Translate contentKey="visitsApp.visit.home.createOrEditLabel">Create or edit a Visit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="visit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('visitsApp.visit.startTime')}
                id="visit-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('visitsApp.visit.endTime')}
                id="visit-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="visit-petId" name="petId" data-cy="petId" label={translate('visitsApp.visit.petId')} type="select">
                <option value="" key="0" />
                {pets
                  ? pets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="visit-vetId" name="vetId" data-cy="vetId" label={translate('visitsApp.visit.vetId')} type="select">
                <option value="" key="0" />
                {vets
                  ? vets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/visit" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VisitUpdate;
