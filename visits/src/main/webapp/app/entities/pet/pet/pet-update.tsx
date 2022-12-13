import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISpecies } from 'app/shared/model/pet/species.model';
import { getEntities as getSpecies } from 'app/entities/pet/species/species.reducer';
import { IOwner } from 'app/shared/model/pet/owner.model';
import { getEntities as getOwners } from 'app/entities/pet/owner/owner.reducer';
import { IPet } from 'app/shared/model/pet/pet.model';
import { getEntity, updateEntity, createEntity, reset } from './pet.reducer';

export const PetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const species = useAppSelector(state => state.visits.species.entities);
  const owners = useAppSelector(state => state.visits.owner.entities);
  const petEntity = useAppSelector(state => state.visits.pet.entity);
  const loading = useAppSelector(state => state.visits.pet.loading);
  const updating = useAppSelector(state => state.visits.pet.updating);
  const updateSuccess = useAppSelector(state => state.visits.pet.updateSuccess);

  const handleClose = () => {
    navigate('/pet' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSpecies({}));
    dispatch(getOwners({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...petEntity,
      ...values,
      species: species.find(it => it.id.toString() === values.species.toString()),
      owner: owners.find(it => it.id.toString() === values.owner.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...petEntity,
          species: petEntity?.species?.id,
          owner: petEntity?.owner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="visitsApp.petPet.home.createOrEditLabel" data-cy="PetCreateUpdateHeading">
            <Translate contentKey="visitsApp.petPet.home.createOrEditLabel">Create or edit a Pet</Translate>
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
                  id="pet-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('visitsApp.petPet.name')}
                id="pet-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 30, message: translate('entity.validation.maxlength', { max: 30 }) },
                }}
              />
              <ValidatedField
                label={translate('visitsApp.petPet.birthDate')}
                id="pet-birthDate"
                name="birthDate"
                data-cy="birthDate"
                type="date"
              />
              <ValidatedField id="pet-species" name="species" data-cy="species" label={translate('visitsApp.petPet.species')} type="select">
                <option value="" key="0" />
                {species
                  ? species.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="pet-owner" name="owner" data-cy="owner" label={translate('visitsApp.petPet.owner')} type="select">
                <option value="" key="0" />
                {owners
                  ? owners.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.lastName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pet" replace color="info">
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

export default PetUpdate;
