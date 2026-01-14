import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdminUserService } from './admin-user.service';
import { environment } from '../../../../environments/environment';
import { User } from '../models/user.model';

describe('AdminUserService', () => {
  let service: AdminUserService;
  let http: HttpTestingController;

  const mockUsers: User[] = [
    {
      id: '1',
      username: 'admin',
      role: 'ADMIN',
      active: true
    } as User
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AdminUserService]
    });

    service = TestBed.inject(AdminUserService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should fetch all users', () => {
    service.getAll().subscribe(users => {
      expect(users.length).toBe(1);
      expect(users[0].username).toBe('admin');
    });

    const req = http.expectOne(`${environment.apiUrl}/admin/users`);
    expect(req.request.method).toBe('GET');

    req.flush(mockUsers);
  });

  it('should create a user', () => {
    const payload = {
      username: 'user1',
      password: 'pass123',
      role: 'USER'
    };

    service.create(payload as any).subscribe();

    const req = http.expectOne(`${environment.apiUrl}/admin/users`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(payload);

    req.flush({});
  });

  it('should update user status', () => {
    const payload = { active: false };

    service.updateStatus('1', payload as any).subscribe();

    const req = http.expectOne(`${environment.apiUrl}/admin/users/1/status`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(payload);

    req.flush({});
  });
});
