import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080/user";
  constructor(private http:HttpClient) { }

  login(user: Object): Observable<Object> {
    console.log(user);
    return this.http.post(`${this.baseUrl}/login`, user);
  }

  register(user: Object): Observable<Object> {
    console.log(user);
    return this.http.post(`${this.baseUrl}/register`, user);
  }
}
