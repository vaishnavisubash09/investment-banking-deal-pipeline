import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { Observable } from 'rxjs';

import { AuthService } from '../../core/auth/auth.service';
import { AuthUser } from '../../core/auth/auth.models';
import { DashboardService, DashboardSummary } from './services/dashboard.service';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './dashboard.page.html',
  styleUrls: ['./dashboard.page.scss']
})
export class DashboardPage {

  // ✅ SAFE
  user: AuthUser | null;

  // ✅ ASYNC DATA (NO SUBSCRIBE)
  summary$!: Observable<DashboardSummary>;

  constructor(
    private auth: AuthService,
    private dashboardService: DashboardService
  ) {
    // safe sync read
    this.user = this.auth.currentUser;

    // async stream
    this.summary$ = this.dashboardService.getSummary();


  // 🔴 ADD THIS
  this.summary$.subscribe(data => {
    console.log('DASHBOARD SUMMARY:', data);});
 
}

  isAdmin(): boolean {
    return this.user?.role === 'ADMIN';
  }

}