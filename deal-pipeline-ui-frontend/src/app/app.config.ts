import { ApplicationConfig, APP_INITIALIZER } from '@angular/core';
import { provideRouter, withRouterConfig } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';

import { routes } from './app.routes';
import { AuthService } from './core/auth/auth.service';
import { authInterceptor } from './core/auth/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes, withRouterConfig({
      onSameUrlNavigation: 'reload'
    })),
    provideAnimations(),
    provideHttpClient(
      withInterceptors([authInterceptor]) // ✅ FUNCTION
    ),
    {
      provide: APP_INITIALIZER,
      useFactory: (auth: AuthService) => () => auth.restoreSession(),
      deps: [AuthService],
      multi: true
    }
  ]
};
