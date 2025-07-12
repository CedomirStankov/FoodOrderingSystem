import { Injectable } from '@angular/core';
import {environment} from '../../enviroments/enviroments';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ErrorMessage, Order} from '../../model';

@Injectable({
  providedIn: 'root'
})
export class ErrorMessageService {

  private readonly apiUrl = environment.Api
  constructor(private httpClient: HttpClient) { }

  getErrorMessages(page: number, size: number): Observable<any>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(`${this.apiUrl}/api/errorMessages/all?page=${page}&size=${size}`, {headers});
  }

  getErrorMessagesForUser(page: number, size: number): Observable<any>{
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<any>(`${this.apiUrl}/api/errorMessages/${userId}?page=${page}&size=${size}`, {headers});
  }
}
