import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  username: string | null = null;
  private isAuthenticated = false;

  private apiBaseUrl = 'http://localhost:8080/api/v1';


  constructor(private http: HttpClient) { }

  registerUser(user: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.apiBaseUrl}/auth/register/user`, user, { headers });
  }

  login (username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/auth/login`, {username, password}).pipe(
      map((response) => {
        this.isAuthenticated = true;
        return response;
      }),
      catchError((error) => {
        this.isAuthenticated = false;
        return throwError(() => error);
      })
    );
  }

  logout(): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
    return this.http.post(`${this.apiBaseUrl}/auth/logout`, {}, {headers});
  }

  saveToken(token: string): void {
    sessionStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return sessionStorage.getItem('authToken');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
