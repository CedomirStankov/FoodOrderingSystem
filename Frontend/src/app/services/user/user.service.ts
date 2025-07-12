import { Injectable } from '@angular/core';
import {environment} from '../../enviroments/enviroments';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginData, Token, User} from '../../model';
import {Observable, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly apiUrl = environment.Api
  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<User[]>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<User[]>(`${this.apiUrl}/api/users/all`, {headers});
  }

  addUser(newUser: User): Observable<User>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.post<User>(`${this.apiUrl}/api/users/add`, newUser, { headers });
  }

  getUserById(id: number): Observable<User>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<User>(`${this.apiUrl}/api/users/${id}`, { headers });
  }

  getUserByEmail(email: string): Observable<User>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<User>(`${this.apiUrl}/api/users/email/${email}`, { headers });
  }

  updateUser(user: User, id: string): Observable<User>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put<User>(`${this.apiUrl}/api/users/update/${id}`, user, { headers });
  }

  deleteUser(id: number | undefined): Observable<User>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.delete<User>(`${this.apiUrl}/api/users/remove/${id}`, { headers });
  }
}
