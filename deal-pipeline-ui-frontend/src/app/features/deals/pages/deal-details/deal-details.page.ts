import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

import { Deal } from '../../models/deal.model';
import { DEAL_STAGES, DealStage } from '../../models/deal.enums';
import { DealService } from '../../services/deal.service';
import { AuthService } from '../../../../core/auth/auth.service';
import { ConfirmDialogComponent } from '../../../../shared/confirm-dialog.component';

@Component({
  selector: 'app-deal-details-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatDividerModule,
    MatSnackBarModule
  ],
  templateUrl: './deal-details.page.html',
  styleUrl: './deal-details.page.scss'
})
export class DealDetailsPage implements OnInit {

  deal!: Deal;
  newNote = '';
  stages = DEAL_STAGES;
  loading = true;

  private route = inject(ActivatedRoute);
  private dealService = inject(DealService);
  private auth = inject(AuthService);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  ngOnInit(): void {
    // ✅ Auto refresh via resolver
    this.route.data.subscribe(({ deal }) => {
      this.deal = deal;
      this.loading = false;
    });
  }

  isAdmin(): boolean {
    return this.auth.currentUser?.role === 'ADMIN';
  }

  onStageChange(stage: DealStage): void {
    this.dealService.updateStage(this.deal.id, { stage }).subscribe(() => {
      this.deal.currentStage = stage;
      this.snackBar.open('Stage updated', 'Close', { duration: 2000 });
    });
  }

  addNote(): void {
    if (!this.newNote.trim()) return;

    const ref = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Add Note',
        message: 'Are you sure you want to add this note?'
      }
    });

    ref.afterClosed().subscribe(confirm => {
      if (!confirm) return;

      this.dealService.addNote(this.deal.id, { note: this.newNote })
        .subscribe(updatedDeal => {
          this.deal.notes = updatedDeal.notes;
          this.newNote = '';
          this.snackBar.open('Note added', 'Close', { duration: 2000 });
        });
    });
  }
}
