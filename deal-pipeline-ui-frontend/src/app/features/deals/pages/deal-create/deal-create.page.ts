import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

import { DealService } from '../../services/deal.service';
import { DEAL_TYPES, DealType } from '../../models/deal.enums';
import { AuthService } from '../../../../core/auth/auth.service';
import { DealCreateRequest } from '../../models/deal.payloads';
import { MatCardModule } from '@angular/material/card';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule
  ],
  templateUrl: './deal-create.page.html',
  styleUrl: 'deal-create.page.scss'
})
export class DealCreatePage implements OnInit {

  private fb = inject(FormBuilder);
  private dealService = inject(DealService);
  private authService = inject(AuthService);
  private router = inject(Router);

  dealTypes = DEAL_TYPES;

  // IMPORTANT: dealValue is OPTIONAL, not nullable
form = this.fb.nonNullable.group({
  clientName: ['', Validators.required],
  dealType: ['MERGER' as DealType, Validators.required],
  sector: ['', Validators.required],
  summary: ['', Validators.required],
  dealValue: [undefined as number | undefined]
});


  ngOnInit(): void {
    // nothing to remove — we handle payload instead
  }

  isAdmin(): boolean {
    return this.authService.currentUser?.role === 'ADMIN';
  }


  submit(): void {
  if (this.form.invalid) return;

  const raw = this.form.getRawValue();

  const payload: DealCreateRequest = this.isAdmin()
    ? raw
    : {
        clientName: raw.clientName,
        dealType: raw.dealType,
        sector: raw.sector,
        summary: raw.summary
      };

  this.dealService.create(payload).subscribe({
    next: () => this.router.navigate(['/deals'])
  });
}


}
