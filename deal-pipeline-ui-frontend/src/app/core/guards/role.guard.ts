import { inject } from '@angular/core';
import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

export const roleGuard =
  (requiredRole: 'ADMIN' | 'USER'): CanActivateFn =>
  (route: ActivatedRouteSnapshot) => {

    const auth = inject(AuthService);
    const router = inject(Router);

    const user = auth.currentUser;

    if (!user) {
      router.navigate(['/login']);
      return false;
    }

    if (user.role === requiredRole) {
      return true;
    }

    router.navigate(['/dashboard']);
    return false;
  };
