import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DealService } from './deal.service';
import { Deal } from '../models/deal.model';
import { environment } from '../../../../environments/environment';

describe('DealService', () => {
  let service: DealService;
  let http: HttpTestingController;

  const mockDeal: Deal = {
    id: '1',
    clientName: 'Acme Corp',
    dealType: 'MERGER' as any,
    sector: 'Finance',
    currentStage: 'PROSPECT' as any,
    notes: [],
    createdBy: 'john',
    createdAt: new Date().toISOString()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DealService]
    });

    service = TestBed.inject(DealService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should fetch all deals', () => {
    service.getAll().subscribe();

    const req = http.expectOne(`${environment.apiUrl}/deals`);
    expect(req.request.method).toBe('GET');

    req.flush([mockDeal]);
  });

  it('should fetch deal by id', () => {
    service.getById('1').subscribe();

    const req = http.expectOne(`${environment.apiUrl}/deals/1`);
    expect(req.request.method).toBe('GET');

    req.flush(mockDeal);
  });

  it('should update deal stage', () => {
    service.updateStage('1', { stage: 'CLOSED' }).subscribe();

    const req = http.expectOne(`${environment.apiUrl}/deals/1/stage`);
    expect(req.request.method).toBe('PATCH');
    expect(req.request.body).toEqual({ stage: 'CLOSED' });

    req.flush(mockDeal);
  });

  it('should update deal value', () => {
    service.updateValue('1', { dealValue: 500000 }).subscribe();

    const req = http.expectOne(`${environment.apiUrl}/deals/1/value`);
    expect(req.request.method).toBe('PATCH');
    expect(req.request.body).toEqual({ dealValue: 500000 });

    req.flush(mockDeal);
  });

  it('should add a note', () => {
    service.addNote('1', { note: 'Important update' }).subscribe();

    const req = http.expectOne(`${environment.apiUrl}/deals/1/notes`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ note: 'Important update' });

    req.flush(mockDeal);
  });

  it('should delete a deal', () => {
    service.delete('1').subscribe();

    const req = http.expectOne(`${environment.apiUrl}/deals/1`);
    expect(req.request.method).toBe('DELETE');

    req.flush({});
  });
});
