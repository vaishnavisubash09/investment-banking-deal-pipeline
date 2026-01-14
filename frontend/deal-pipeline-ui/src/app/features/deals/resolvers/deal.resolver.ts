import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { DealService } from '../services/deal.service';
import { Deal } from '../models/deal.model';

export const dealResolver: ResolveFn<Deal> = (route) => {
  const id = route.paramMap.get('id')!;
  return inject(DealService).getById(id);
};
