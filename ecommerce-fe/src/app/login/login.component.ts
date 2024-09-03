import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { UserService } from '../services/user.service';
import { LoginDTO } from '../dtos/user/login.dto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  constructor(private router: Router, private userService: UserService) {
    this.phoneNumber = '';
    this.password = '';
  }

  onPhoneNumberChange() {
    console.log('phone:', this.phoneNumber);
  }

  login() {
    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: 2,
    };

    this.userService.login(loginDTO).subscribe({
      next: (data: any) => {
        alert('Login success:' + data.data);
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.log('error:', error.error.text);
        alert('Login failed: ' + error.error);
      },
    });
  }
}
