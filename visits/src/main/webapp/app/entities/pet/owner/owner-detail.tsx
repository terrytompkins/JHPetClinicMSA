import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './owner.reducer';

export const OwnerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ownerEntity = useAppSelector(state => state.visits.owner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ownerDetailsHeading">
          <Translate contentKey="visitsApp.petOwner.detail.title">Owner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="visitsApp.petOwner.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="visitsApp.petOwner.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.lastName}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="visitsApp.petOwner.address">Address</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="visitsApp.petOwner.city">City</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.city}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="visitsApp.petOwner.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.telephone}</dd>
        </dl>
        <Button tag={Link} to="/owner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/owner/${ownerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OwnerDetail;
