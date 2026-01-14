import { DealStage, DealType } from './deal.enums';

export interface DealCreateRequest {
  clientName: string;
  dealType: DealType;
  sector: string;
  summary?: string;
}

export interface DealUpdateRequest {
  clientName?: string;
  dealType?: DealType;
  sector?: string;
  summary?: string;
}

export interface DealStageUpdateRequest {
  stage: DealStage;
}

export interface DealValueUpdateRequest {
  dealValue: number;
}

export interface DealNoteRequest {
  note: string;
}
