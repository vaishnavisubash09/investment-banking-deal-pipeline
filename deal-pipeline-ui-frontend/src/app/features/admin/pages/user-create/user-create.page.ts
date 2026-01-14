import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

import { AdminUserService } from '../../services/admin-user.service';
import { CreateUserRequest } from '../../models/user.payloads';
import { ConfirmDialogComponent } from '../../../../shared/confirm-dialog.component';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './user-create.page.html',
  styleUrl: './user-create.page.scss'
})
export class UserCreatePage {

  newUser: CreateUserRequest = {
    username: '',
    email: '',
    password: '',
    role: 'USER'
  };

  private adminUserService = inject(AdminUserService);
  private router = inject(Router);
  private snackBar = inject(MatSnackBar);
  private dialog = inject(MatDialog);

  create() {
    const ref = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Create User',
        message: 'Are you sure you want to create this user?'
      }
    });

    ref.afterClosed().subscribe(confirm => {
      if (!confirm) return;

      this.adminUserService.create(this.newUser).subscribe(() => {
        this.snackBar.open('User created successfully', 'Close', { duration: 2500 });
        this.router.navigate(['/admin/users']);
      });
    });
  }
}
