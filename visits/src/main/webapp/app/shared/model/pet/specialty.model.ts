import { IVet } from 'app/shared/model/pet/vet.model';

export interface ISpecialty {
  id?: number;
  name?: string;
  vets?: IVet[] | null;
}

export const defaultValue: Readonly<ISpecialty> = {};
