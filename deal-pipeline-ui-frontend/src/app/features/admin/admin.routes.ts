import { Routes } from '@angular/router';
import { roleGuard } from '../../core/guards/role.guard';
import { usersResolver } from './resolvers/users.resolver';

export const ADMIN_ROUTES: Routes = [
  {
    path: 'users',
    loadComponent: () =>
      import('./pages/user-management/user-management.page')
        .then(m => m.UserManagementPage),
    resolve: {
      users: usersResolver
    },
    canActivate: [roleGuard('ADMIN')],
    runGuardsAndResolvers: 'always'
  },

  {
    path: 'users/create',
    loadComponent: () =>
      import('./pages/user-create/user-create.page')
        .then(m => m.UserCreatePage),
    canActivate: [roleGuard('ADMIN')]
  }
];
