import { ISpecialty } from 'app/shared/model/pet/specialty.model';

export interface IVet {
  id?: number;
  firstName?: string;
  lastName?: string;
  specialties?: ISpecialty[] | null;
}

export const defaultValue: Readonly<IVet> = {};
