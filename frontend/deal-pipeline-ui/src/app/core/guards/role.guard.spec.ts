import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { roleGuard } from './role.guard';
import { AuthService } from '../auth/auth.service';

describe('roleGuard', () => {

  let authMock: any;
  let routerMock: any;

  beforeEach(() => {
    authMock = {
      currentUser: null
    };

    routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: AuthService, useValue: authMock },
        { provide: Router, useValue: routerMock }
      ]
    });
  });

  it('should allow ADMIN when role is ADMIN', () => {
    authMock.currentUser = { role: 'ADMIN' };

    const guard = TestBed.runInInjectionContext(() =>
roleGuard('ADMIN')({} as any, {} as any)
    );

    expect(guard).toBeTrue();
  });

  it('should block USER from ADMIN route', () => {
    authMock.currentUser = { role: 'USER' };

    const guard = TestBed.runInInjectionContext(() =>
      roleGuard('ADMIN')({} as any, {} as any)

    );

    expect(guard).toBeFalse();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/dashboard']);
  });

  it('should redirect to login if not logged in', () => {
    authMock.currentUser = null;

    const guard = TestBed.runInInjectionContext(() =>
      roleGuard('ADMIN')({} as any, {} as any)

    );

    expect(guard).toBeFalse();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });

});
