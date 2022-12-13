import dayjs from 'dayjs';

export interface IVisit {
  id?: number;
  visitDate?: string;
  petId?: number;
  vetId?: number;
  description?: string | null;
}

export const defaultValue: Readonly<IVisit> = {};
