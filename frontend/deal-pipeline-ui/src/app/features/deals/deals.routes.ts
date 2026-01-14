import { Routes } from '@angular/router';
import { dealsResolver } from './resolvers/deals.resolver';
import { dealResolver } from './resolvers/deal.resolver';
import { dealPipelineResolver } from './resolvers/deals-pipeline.resolver';


export const DEALS_ROUTES: Routes = [

  {
    path: '',
    loadComponent: () =>
      import('./pages/deal-list/deal-list.page')
        .then(m => m.DealListPage),
        resolve: {deals: dealsResolver}
  },

  {
    path: 'pipeline',
    loadComponent: () =>
      import('./pages/deal-pipeline/deal-pipeline.page')
        .then(m => m.DealPipelinePage),
        resolve: {deals: dealPipelineResolver}
  },

  {
    path: 'create',
    loadComponent: () =>
      import('./pages/deal-create/deal-create.page')
        .then(m => m.DealCreatePage)
  },

  {
    path: ':id/edit',
    loadComponent: () =>
      import('./pages/deal-edit/deal-edit.page')
        .then(m => m.DealEditPage),
    resolve: { deal: dealResolver }
  },

  {
    path: ':id',
    loadComponent: () =>
      import('./pages/deal-details/deal-details.page')
        .then(m => m.DealDetailsPage),
    resolve: { deal: dealResolver }
  }
];
