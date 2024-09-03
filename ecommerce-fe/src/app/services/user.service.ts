import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterDTO } from '../dtos/user/register.dto';
import { Observable } from 'rxjs';
import { env } from '../envs/env';
import { LoginDTO } from '../dtos/user/login.dto';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiRegister = `${env.apiBaseUrl}/users/register`;
  private apiLogin = `${env.apiBaseUrl}/users/login`;

  private apiConfig = {
    headers: this.createHeaders(),
  };

  constructor(private http: HttpClient) {}
  private createHeaders() {
    return {
      'Content-Type': 'application/json',
      "Accept-Language": "en",
    };
  }

  register(registerDTO: RegisterDTO): Observable<any> {
    return this.http.post(this.apiRegister, registerDTO, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.apiLogin, loginDTO, this.apiConfig);
  }
}
