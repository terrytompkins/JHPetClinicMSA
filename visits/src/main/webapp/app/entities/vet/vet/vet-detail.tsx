import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vet.reducer';

export const VetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vetEntity = useAppSelector(state => state.visits.vet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vetDetailsHeading">
          <Translate contentKey="visitsApp.vetVet.detail.title">Vet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vetEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="visitsApp.vetVet.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{vetEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="visitsApp.vetVet.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{vetEntity.lastName}</dd>
          <dt>
            <Translate contentKey="visitsApp.vetVet.specialties">Specialties</Translate>
          </dt>
          <dd>
            {vetEntity.specialties
              ? vetEntity.specialties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {vetEntity.specialties && i === vetEntity.specialties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/vet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vet/${vetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VetDetail;
