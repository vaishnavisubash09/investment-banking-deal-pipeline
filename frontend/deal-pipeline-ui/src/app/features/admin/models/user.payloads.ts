import { UserRole } from './user.model';

export interface CreateUserRequest {
  username: string;
  email: string;
  password: string;
  role: UserRole;
}

export interface UpdateUserStatusRequest {
  active: boolean;
}
