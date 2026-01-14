import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { authGuard } from './auth.guard';
import { AuthService } from '../auth/auth.service';

describe('authGuard', () => {

  let authMock: any;
  let routerMock: any;

  beforeEach(() => {
    authMock = {
      isAuthenticated: () => false
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

  it('should allow when authenticated', () => {
    authMock.isAuthenticated = () => true;

const result = TestBed.runInInjectionContext(() =>
  authGuard({} as any, {} as any)
);

    expect(result).toBeTrue();
  });

  it('should block and redirect when not authenticated', () => {
    authMock.isAuthenticated = () => false;

const result = TestBed.runInInjectionContext(() =>
  authGuard({} as any, {} as any)
);

    expect(result).toBeFalse();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });

});
