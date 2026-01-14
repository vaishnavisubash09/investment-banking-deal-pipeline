export type UserRole = 'ADMIN' | 'USER';

export interface User {
  id: string;
  username: string;
  email: string;
  role: UserRole;
  active: boolean;
}
