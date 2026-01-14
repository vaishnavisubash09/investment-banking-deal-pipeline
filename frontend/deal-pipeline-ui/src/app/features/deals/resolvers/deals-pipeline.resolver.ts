import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { DealService } from '../services/deal.service';
import { Deal } from '../models/deal.model';

export const dealPipelineResolver: ResolveFn<Deal[]> = () => {
  return inject(DealService).getAll();
};
