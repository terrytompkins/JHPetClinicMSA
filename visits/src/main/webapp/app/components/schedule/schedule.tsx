import React, { useEffect, useMemo, useState } from 'react';
import { Calendar, luxonLocalizer, Views, Event, SlotInfo } from 'react-big-calendar';
import { DateTime } from 'luxon';
import getStore, { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getVisitEntities } from 'app/entities/visit/visit.reducer';
import { getEntities as getVetEntities } from 'app/entities/vet/vet/vet.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { IVet } from 'app/shared/model/vet/vet.model';

import './schedule.scss';
import entitiesReducers from 'app/entities/reducers';
import { combineReducers, ReducersMapObject } from 'redux';
import { Modal, ModalBody, ModalHeader } from 'reactstrap';
import { useNavigate } from 'react-router-dom';

import { VisitDetail } from 'app/entities/visit/visit-detail';

interface VisitEvent extends Event {
	id: number;
}


const Schedule = () => {
	const [showEvent, setShowEvent] = useState(false);
	const [selectedEvent, setSelectedEvent] = useState(undefined);
	
	const navigate = useNavigate();
	
	const store = getStore();
  	store.injectReducer('visits', combineReducers(entitiesReducers as ReducersMapObject));
	const dispatch = useAppDispatch();

	const localizer = luxonLocalizer(DateTime);
	const visitList: Array<IVisit> = useAppSelector(state => state.visits.visit.entities);
	const vetList: Array<IVet> = useAppSelector(state => state.visits.vet.entities);

	useEffect(() => {
		dispatch(getVetEntities({}));
		dispatch(getVisitEntities({}));
	}, []);

	const events = useMemo(() => visitList.map<VisitEvent>(visit => {
		return {
			id: visit.id,
			title: visit.description,
			start: new Date(visit.startTime),
			end: new Date(visit.endTime),
			resourceId: visit.vetId
		};
	}), [visitList]);

	const scheduleNewVisit = (slot: SlotInfo) => {
		navigate(`/visit/new?vetId=${slot.resourceId}&start=${slot.start.toISOString()}&end=${slot.end.toISOString()}`);
	};

	const viewEvent = (event: VisitEvent) => {
		setSelectedEvent(event.id);
		setShowEvent(true);
	}

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
					selectable={true}
					onSelectSlot={scheduleNewVisit}
					onSelectEvent={viewEvent}
				/>
				<Modal isOpen={showEvent} toggle={() => setShowEvent(!showEvent)}>
					<ModalHeader>Schedule a New Visit</ModalHeader>
					<ModalBody>
						{selectedEvent && <VisitDetail id={selectedEvent} />}
					</ModalBody>
				</Modal>
			</div>
		)
		: <div>Loading</div>
	
};

export default Schedule;