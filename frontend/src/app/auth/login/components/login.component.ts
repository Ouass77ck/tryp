import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: false
})
export class LoginComponent {
  email = '';
  password = '';

  onSubmit() {
    console.log('Login:', this.email, this.password);
    // AuthService login() ici plus tard
  }
}
