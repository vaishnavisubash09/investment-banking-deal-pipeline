import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { DealService } from '../services/deal.service';
import { Deal } from '../models/deal.model';

export const dealsResolver: ResolveFn<Deal[]> = () => {
  return inject(DealService).getAll();
};
