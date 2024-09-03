import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { env } from '../envs/env';
import { Role } from '../models/role.model';

@Injectable({
    providedIn: 'root'
  })
  export class RoleService {
    private apiGetRoles  = `${env.apiBaseUrl}/roles`;
  
    constructor(private http: HttpClient) { }
  
    getRoles(): Observable<Role[]> {
      return this.http.get<Role[]>(this.apiGetRoles);
    }
  }