import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  username: string | null = null;
  isAuthenticated = false;

  private apiBaseUrl = 'http://localhost:8080/api/v1/users';

  constructor(private http: HttpClient) { }

  getUserInfoFromToken(): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token not found');
    }

    const decodedToken: any = jwt_decode(token);
    const username = decodedToken.sub;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });

    const url = `${this.apiBaseUrl}/me`;
    return this.http.get(url, { headers });
  }

  updateUser(idUser: number, updatedUser: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<any>(`${this.apiBaseUrl}/updateUser/${idUser}`, updatedUser, { headers });
  }

  deleteUser(idUser: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete(`${this.apiBaseUrl}/deleteUser/${idUser}`, { headers });
  }
}
