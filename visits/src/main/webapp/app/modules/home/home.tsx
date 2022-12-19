import './home.scss';

import React, { Fragment } from 'react';
import { Helmet } from 'react-helmet';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import { Schedule } from 'app/components';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Fragment>
      <Helmet>
        <title>PetClinic - Home</title>
      </Helmet>
        {account?.login ? (
          <Schedule />
        ) : (
          <Row>
            <Col>
              <div className="welcome">
                <h2>
                  <Translate contentKey="home.title">Welcome to JHipster PetClinic!</Translate>
                </h2>
                <img className="pets" src='../../../content/images/pets.png' />
                <p>
                  <Link to="/login" className="alert-link">
                    <Button color="primary" size="lg">
                      <Translate contentKey="global.messages.info.authenticated.link">Sign in</Translate>
                    </Button>
                  </Link>
                </p>
              </div>
            </Col>
          </Row>
        )}
    </Fragment>
  );
};

export default Home;
