import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Deal } from '../../models/deal.model';
import { DealService } from '../../services/deal.service';
import { AuthService } from '../../../../core/auth/auth.service';
import { ConfirmDialogComponent } from '../../../../shared/confirm-dialog.component';

@Component({
  selector: 'app-deal-list-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './deal-list.page.html',
  styleUrl: './deal-list.page.scss'
})
export class DealListPage {

  deals: Deal[] = [];
  isAdmin = false;

  constructor(
    private route: ActivatedRoute,
    private dealService: DealService,
    auth: AuthService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.isAdmin = auth.currentUser?.role === 'ADMIN';

    // 🔥 Resolver is the ONLY source of data
    this.route.data.subscribe(data => {
      this.deals = data['deals'];
    });
  }

  deleteDeal(deal: Deal): void {
    const ref = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Delete Deal',
        message: `Delete deal "${deal.clientName}"? This cannot be undone.`
      }
    });

    ref.afterClosed().subscribe(confirm => {
      if (!confirm) return;

      this.dealService.delete(deal.id).subscribe(() => {
        // 🔥 Instant UI update (no refresh)
        this.deals = this.deals.filter(d => d.id !== deal.id);
        this.snackBar.open('Deal deleted', 'Close', { duration: 2500 });
      });
    });
  }
}
