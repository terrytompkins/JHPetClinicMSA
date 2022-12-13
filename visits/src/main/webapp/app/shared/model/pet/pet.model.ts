import dayjs from 'dayjs';
import { ISpecies } from 'app/shared/model/pet/species.model';
import { IOwner } from 'app/shared/model/pet/owner.model';

export interface IPet {
  id?: number;
  name?: string;
  birthDate?: string | null;
  species?: ISpecies | null;
  owner?: IOwner | null;
}

export const defaultValue: Readonly<IPet> = {};
