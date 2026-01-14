import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { AdminUserService } from '../services/admin-user.service';
import { User } from '../models/user.model';

export const usersResolver: ResolveFn<User[]> = () => {
  return inject(AdminUserService).getAll();
};
