import { DealStage } from '../models/deal.enums';

export function normalizeStage(stage: string): DealStage {
  return stage
    .toUpperCase()
    .replace(/ /g, '_')
    .replace('-', '_') as DealStage;
}
