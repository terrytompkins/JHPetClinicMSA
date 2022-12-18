import dayjs from 'dayjs';

export interface IVisit {
  id?: number;
  start?: string;
  end?: string;
  petId?: number;
  vetId?: number;
  description?: string | null;
}

export const defaultValue: Readonly<IVisit> = {};
