import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let http: HttpTestingController;

  const fakeJwt =
    'eyJhbGciOiJIUzI1NiJ9.' +
    btoa(JSON.stringify({
      sub: 'john',
      roles: ['ROLE_USER']
    })) +
    '.signature';

  beforeEach(() => {
    sessionStorage.clear();

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should send login request and store token in sessionStorage', () => {
    service.login({ username: 'john', password: '1234' }).subscribe();

    const req = http.expectOne(`${environment.apiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');

    req.flush({ token: fakeJwt });

    expect(sessionStorage.getItem('token')).toBe(fakeJwt);
  });

  it('should restore user from JWT', () => {
    sessionStorage.setItem('token', fakeJwt);

    service.restoreSession();

    const user = service.currentUser;

    expect(user).toBeTruthy();
    expect(user?.username).toBe('john');
    expect(user?.role).toBe('USER');
  });

  it('should return true when authenticated', () => {
    sessionStorage.setItem('token', fakeJwt);
    expect(service.isAuthenticated()).toBeTrue();
  });

  it('should clear session on logout', () => {
    sessionStorage.setItem('token', fakeJwt);

    service.logout();

    expect(sessionStorage.getItem('token')).toBeNull();
    expect(service.currentUser).toBeNull();
  });
});
