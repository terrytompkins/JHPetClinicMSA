import { IPet } from 'app/shared/model/pet/pet.model';

export interface IOwner {
  id?: number;
  firstName?: string;
  lastName?: string;
  address?: string;
  city?: string;
  telephone?: string;
  pets?: IPet[] | null;
}

export const defaultValue: Readonly<IOwner> = {};
