// =========================
// DEAL TYPES
// =========================

export const DEAL_TYPES = [
  'MERGER',
  'ACQUISITION',
  'IPO',
  'DEBT',
  'EQUITY'
] as const;

export type DealType = typeof DEAL_TYPES[number];


// =========================
// DEAL STAGES
// =========================

export const DEAL_STAGES = [
  'PROSPECT',
  'UNDER_EVALUATION',
  'TERM_SHEET_SUBMITTED',
  'CLOSED',
  'LOST'
] as const;

export type DealStage = typeof DEAL_STAGES[number];
