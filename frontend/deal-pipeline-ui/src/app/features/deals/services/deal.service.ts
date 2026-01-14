// src/app/features/deals/services/deal.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Deal } from '../models/deal.model';
import {
  DealCreateRequest,
  DealUpdateRequest,
  DealStageUpdateRequest,
  DealValueUpdateRequest,
  DealNoteRequest
} from '../models/deal.payloads';
import { environment } from '../../../../environments/environment';


@Injectable({ providedIn: 'root' })
export class DealService {

  private readonly baseUrl = `${environment.apiUrl}/deals`;

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Deal[]>(this.baseUrl);
  }

  getById(id: string) {
    return this.http.get<Deal>(`${this.baseUrl}/${id}`);
  }

  create(payload: DealCreateRequest) {
    return this.http.post<Deal>(this.baseUrl, payload);
  }

  update(id: string, payload: DealUpdateRequest) {
    return this.http.put<Deal>(`${this.baseUrl}/${id}`, payload);
  }

  updateStage(id: string, payload: DealStageUpdateRequest) {
    return this.http.patch<Deal>(`${this.baseUrl}/${id}/stage`, payload);
  }

  updateValue(id: string, payload: DealValueUpdateRequest) {
    return this.http.patch<Deal>(`${this.baseUrl}/${id}/value`, payload);
  }

  addNote(id: string, payload: DealNoteRequest) {
    return this.http.post<Deal>(`${this.baseUrl}/${id}/notes`, payload);
  }

  delete(id: string) {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
