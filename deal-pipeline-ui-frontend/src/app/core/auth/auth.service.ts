import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, tap } from 'rxjs';
import { LoginRequest, LoginResponse, AuthUser } from './auth.models';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly API = environment.apiUrl;

  private userSubject = new BehaviorSubject<AuthUser | null>(null);
  user$ = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    this.restoreSession();
  }

  login(payload: LoginRequest) {
    return this.http
      .post<LoginResponse>(`${this.API}/auth/login`, payload)
      .pipe(
        tap(res => {
          sessionStorage.setItem('token', res.token);
          this.restoreSession();
        })
      );
  }

  restoreSession(): void {
    const token = sessionStorage.getItem('token');
    if (!token) return;

    const payload = JSON.parse(atob(token.split('.')[1]));

    const role = payload.roles?.includes('ROLE_ADMIN')
      ? 'ADMIN'
      : 'USER';

    this.userSubject.next({
      id: payload.sub,
      username: payload.sub,
      role
    });
  }

  logout(): void {
    sessionStorage.removeItem('token');
    this.userSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!sessionStorage.getItem('token');
  }

  get currentUser(): AuthUser | null {
    return this.userSubject.value;
  }

  isAdmin(): boolean {
    return this.currentUser?.role === 'ADMIN';
  }
}
