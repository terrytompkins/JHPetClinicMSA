import React, { useEffect, useMemo } from 'react';
import { Calendar, luxonLocalizer, Views, Event } from 'react-big-calendar';
import { DateTime } from 'luxon';
import getStore, { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/visit/visit.reducer';
import { IVisit } from 'app/shared/model/visit.model';

import './schedule.scss';
import entitiesReducers from '../../entities/reducers';
import { combineReducers, ReducersMapObject } from 'redux';

const Schedule = () => {
	const store = getStore();
  	store.injectReducer('visits', combineReducers(entitiesReducers as ReducersMapObject));
	const dispatch = useAppDispatch();
	const localizer = luxonLocalizer(DateTime);
	const visitList: Array<IVisit> = useAppSelector(state => state.visits.visit.entities);

	useEffect(() => {
		dispatch(getEntities({}));
	}, []);

	const vetList = [{
		id: 1,
		lastName: 'Tompkins'
	}, {
		id: 2,
		lastName: 'Smith'
	}, {
		id: 3,
		lastName: 'White'
	}];

	const events = useMemo(() => visitList.map<Event>(visit => {
		return {
			title: visit.description,
			start: new Date(visit.startTime),
			end: new Date(visit.endTime),
			resourceId: visit.vetId
		};
	}), [visitList]);

	return (visitList && visitList.length > 0) ? 
		(
			<div className="schedule-container">
				<Calendar 
					localizer={localizer}
					defaultDate={new Date(2022, 11, 20)}
					defaultView={Views.DAY}
					events={events}
					resources={vetList}
					resourceIdAccessor="id"
					resourceTitleAccessor="lastName"
					step={60}
					timeslots={1}
					min={new Date(1972, 0, 1, 8, 0, 0)}
					max={new Date(1972, 0, 1, 20, 0, 0)}
				/>
			</div>
		)
		: <div>Loading</div>
	
};

export default Schedule;