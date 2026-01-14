import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './core/auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('deal-pipeline-ui');

  constructor(private auth: AuthService) {
  this.auth.restoreSession(); // 🔥 REQUIRED
}

}
