import { DealType, DealStage } from './deal.enums';
import { DealNote } from './deal-note.model';

export interface Deal {
  id: string;

  clientName: string;
  dealType: DealType;
  sector: string;

  dealValue?: number; // ADMIN only (optional in UI)

  currentStage: DealStage;

  summary?: string;

  notes: DealNote[];

  createdBy: string;
  assignedTo?: string;

  createdAt: string;
  updatedAt?: string;
}
