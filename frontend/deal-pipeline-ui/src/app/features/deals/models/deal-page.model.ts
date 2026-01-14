import { Deal } from './deal.model';

export interface DealPage {
  content: Deal[];
  totalElements: number;
  totalPages: number;
}
