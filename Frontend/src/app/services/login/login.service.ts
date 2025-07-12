import { Injectable } from '@angular/core';
import {environment} from '../../enviroments/enviroments';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginData, Token} from '../../model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly apiUrl = environment.Api
  constructor(private httpClient: HttpClient) { }

  getToken(loginData: LoginData): Observable<Token>{
    return this.httpClient.post<Token>(`${this.apiUrl}/auth/login`, loginData);
  }
}
