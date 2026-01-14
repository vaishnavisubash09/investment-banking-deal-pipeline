import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../models/user.model';
import {
  CreateUserRequest,
  UpdateUserStatusRequest
} from '../models/user.payloads';
import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AdminUserService {

  private readonly API = `${environment.apiUrl}/admin/users`;

  constructor(private http: HttpClient) {}

  /**
   * Get all users (ADMIN only)
   */
  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.API);
  }

  /**
   * Create a new user (ADMIN only)
   */
  create(payload: CreateUserRequest): Observable<void> {
    return this.http.post<void>(this.API, payload);
  }

  /**
   * Activate / deactivate a user account
   */
  updateStatus(id: string, payload: UpdateUserStatusRequest): Observable<void> {
    return this.http.put<void>(`${this.API}/${id}/status`, payload);
  }
}
