export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface AuthUser {
  id: string;
  username: string;
  role: 'ADMIN' | 'USER';
}
