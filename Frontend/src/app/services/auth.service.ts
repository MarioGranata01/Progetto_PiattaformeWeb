import { Injectable, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { first, tap } from 'rxjs/operators';
import { Utente } from './utente.service';
import { JwtHelperService } from '@auth0/angular-jwt';
 
  
interface LoginResponse {
  utente: Utente;
  jwt: string;
  role: string;
}
 
@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private apiUrl = 'http://localhost:8081/api/auth';
  loggedIn = new EventEmitter<boolean>();
  private jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient) {
    };
  login(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap((response) => {
        console.log('ID Utente:', response.utente.id);
        console.log('access_token', response.jwt);
        localStorage.setItem('access_token', response.jwt);
        localStorage.setItem('id_utente', response.utente.id);
        this.loggedIn.emit(true);
      })
    );
  }
 
 
  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { username, email, password });
  }
 
  logout(): void {
   
    localStorage.removeItem('access_token');
    localStorage.removeItem('id_utente');
    localStorage.removeItem('ruolo_utente');
    this.loggedIn.next(false);
  }
 
  isAuthenticated(): boolean {
    const token = localStorage.getItem('access_token');
    return !!token && !this.jwtHelper.isTokenExpired(token);
  }
 
  getUserRoleFromToken(): string | null {
    const token = localStorage.getItem('access_token');
    console.log('Token in getUserRoleFromToken:', token);
 
    if (token) {
      const tokenParts = token.split('.');
     
 
      if (tokenParts.length === 3) {
        const payload = JSON.parse(atob(tokenParts[1]));
        console.log('Payload:', payload.ruolo);
        localStorage.setItem('ruolo_utente', payload.ruolo);
        return payload['ruolo'] ;
      }
    }
 
    return null;
  } 

  getUserIdFromLocalStorage(): number | null {
    const idUtente = localStorage.getItem('id_utente');
    return idUtente ? +idUtente : null;
  }
}
