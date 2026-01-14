import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

import { User } from '../../models/user.model';
import { AdminUserService } from '../../services/admin-user.service';
import { ConfirmDialogComponent } from '../../../../shared/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule
  ],
  templateUrl: './user-management.page.html',
  styleUrl: './user-management.page.scss'
})
export class UserManagementPage {

  users: User[] = [];

  private route = inject(ActivatedRoute);
  private adminUserService = inject(AdminUserService);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  constructor() {
    // ✅ ALWAYS AVAILABLE ON FIRST CLICK
    this.users = this.route.snapshot.data['users'];
  }

  toggleUser(user: User): void {
    const action = user.active ? 'Deactivate' : 'Activate';

    const ref = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: `${action} User`,
        message: `Are you sure you want to ${action.toLowerCase()} ${user.username}?`
      }
    });

    ref.afterClosed().subscribe(confirm => {
      if (!confirm) return;

      this.adminUserService.updateStatus(user.id, {
        active: !user.active
      }).subscribe(() => {
        user.active = !user.active; // ✅ instant UI update
        this.snackBar.open(`User ${action}d`, 'Close', { duration: 2000 });
      });
    });
  }
}
