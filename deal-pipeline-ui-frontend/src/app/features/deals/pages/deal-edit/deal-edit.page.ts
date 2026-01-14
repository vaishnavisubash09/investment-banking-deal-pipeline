import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { Deal } from '../../models/deal.model';
import { DEAL_STAGES } from '../../models/deal.enums';
import { DealService } from '../../services/deal.service';

@Component({
  selector: 'app-deal-edit',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './deal-edit.page.html',
  styleUrl: './deal-edit.page.scss'
})

export class DealEditPage {

  deal!: Deal;   // ✅ FIXED
  stages = DEAL_STAGES;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private dealService = inject(DealService);
  private snackBar = inject(MatSnackBar);

  constructor() {
    this.route.data.subscribe(data => {
      this.deal = data['deal'];
    });
  }

  save(): void {
  const confirmed = confirm('Are you sure you want to save changes?');
  if (!confirmed) return;

  // 1️⃣ Update main deal fields
  this.dealService.update(this.deal.id, {
    dealType: this.deal.dealType,
    sector: this.deal.sector,
    summary: this.deal.summary
  }).subscribe({
    next: () => {

      // 2️⃣ Update stage
      this.dealService.updateStage(this.deal.id, {
        stage: this.deal.currentStage
      }).subscribe({

        next: () => {

          // 3️⃣ Update deal value (admin)
          if (this.deal.dealValue !== undefined) {
            this.dealService.updateValue(this.deal.id, {
              dealValue: this.deal.dealValue
            }).subscribe(() => this.finish());
          } else {
            this.finish();
          }

        },

        error: () => {
          this.snackBar.open('Failed to update stage', 'Close', { duration: 3000 });
        }

      });

    },
    error: () => {
      this.snackBar.open('Failed to update deal', 'Close', { duration: 3000 });
    }
  });
}



  finish(): void {
    this.snackBar.open('Deal updated successfully', 'Close', {
      duration: 2500
    });
    this.router.navigate(['/deals', this.deal.id]);
  }

  cancel(): void {
    this.router.navigate(['/deals', this.deal.id]);
  }
}
