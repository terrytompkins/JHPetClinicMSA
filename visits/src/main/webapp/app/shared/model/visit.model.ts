import dayjs from 'dayjs';

export interface IVisit {
  id?: number;
  startTime?: string;
  endTime?: string;
  petId?: number;
  vetId?: number;
  description?: string | null;
}

export const defaultValue: Readonly<IVisit> = {};
