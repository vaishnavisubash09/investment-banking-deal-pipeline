import { Routes } from '@angular/router';
import { MainLayoutComponent } from './layout/main-layout.component';
import { LoginPage } from './pages/login.page/login.page';
import { DashboardPage } from './pages/dashboard.page/dashboard.page';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [

  // PUBLIC
  { path: 'login', component: LoginPage },

  // PROTECTED APP SHELL
  {
  path: '',
  component: MainLayoutComponent,
  canActivate: [authGuard],
  children: [
    { path: 'dashboard', component: DashboardPage },

    {
      path: 'deals',
      loadChildren: () =>
        import('./features/deals/deals.routes')
          .then(m => m.DEALS_ROUTES)
    },

    {
  path: 'admin',
  loadChildren: () =>
    import('./features/admin/admin.routes')
      .then(m => m.ADMIN_ROUTES)
}

  ]
},


  // FALLBACK
  { path: '**', redirectTo: 'login' }
];
