import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './visit.reducer';

export const VisitDetailContainer = () => {
  const { id } = useParams<'id'>();
  
  return (
    <Row>
      <Col md="8">
        <VisitDetail id={id} />
      </Col>
    </Row>
  );
};

export interface VisitDetailProps {
  id: number | string;
}

export const VisitDetail = ({id}: VisitDetailProps) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const visitEntity = useAppSelector(state => state.visits.visit.entity);

  return (
    <>
      <h2 data-cy="visitDetailsHeading">
        <Translate contentKey="visitsApp.visit.detail.title">Visit</Translate>
      </h2>
      <dl className="jh-entity-details">
        <dt>
          <span id="id">
            <Translate contentKey="global.field.id">ID</Translate>
          </span>
        </dt>
        <dd>{visitEntity.id}</dd>
        <dt>
          <span id="startTime">
            <Translate contentKey="visitsApp.visit.startTime">Start Time</Translate>
          </span>
        </dt>
        <dd>{visitEntity.startTime ? <TextFormat value={visitEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        <dt>
          <span id="endTime">
            <Translate contentKey="visitsApp.visit.endTime">End Time</Translate>
          </span>
        </dt>
        <dd>{visitEntity.endTime ? <TextFormat value={visitEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        <dt>
          <span id="petId">
            <Translate contentKey="visitsApp.visit.petId">Pet Id</Translate>
          </span>
        </dt>
        <dd>{visitEntity.petId}</dd>
        <dt>
          <span id="vetId">
            <Translate contentKey="visitsApp.visit.vetId">Vet Id</Translate>
          </span>
        </dt>
        <dd>{visitEntity.vetId}</dd>
        <dt>
          <span id="description">
            <Translate contentKey="visitsApp.visit.description">Description</Translate>
          </span>
        </dt>
        <dd>{visitEntity.description}</dd>
      </dl>
      <Button tag={Link} to="/visit" replace color="info" data-cy="entityDetailsBackButton">
        <FontAwesomeIcon icon="arrow-left" />{' '}
        <span className="d-none d-md-inline">
          <Translate contentKey="entity.action.back">Back</Translate>
        </span>
      </Button>
      &nbsp;
      <Button tag={Link} to={`/visit/${visitEntity.id}/edit`} replace color="primary">
        <FontAwesomeIcon icon="pencil-alt" />{' '}
        <span className="d-none d-md-inline">
          <Translate contentKey="entity.action.edit">Edit</Translate>
        </span>
      </Button>
    </>
  );

};

export default VisitDetailContainer;
