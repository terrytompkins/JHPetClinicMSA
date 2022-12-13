import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pet.reducer';

export const PetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const petEntity = useAppSelector(state => state.visits.pet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="petDetailsHeading">
          <Translate contentKey="visitsApp.petPet.detail.title">Pet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{petEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="visitsApp.petPet.name">Name</Translate>
            </span>
          </dt>
          <dd>{petEntity.name}</dd>
          <dt>
            <span id="birthDate">
              <Translate contentKey="visitsApp.petPet.birthDate">Birth Date</Translate>
            </span>
          </dt>
          <dd>{petEntity.birthDate ? <TextFormat value={petEntity.birthDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="visitsApp.petPet.species">Species</Translate>
          </dt>
          <dd>{petEntity.species ? petEntity.species.name : ''}</dd>
          <dt>
            <Translate contentKey="visitsApp.petPet.owner">Owner</Translate>
          </dt>
          <dd>{petEntity.owner ? petEntity.owner.lastName : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet/${petEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PetDetail;
