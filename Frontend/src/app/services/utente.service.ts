import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

 
export interface Utente {
  id: string;
  username: string;
  email: string;
}
 
export interface UtenteDto {
  username: string;
  email: string;
  password: string;
}
 
@Injectable({
  providedIn: 'root'
})
export class UtenteService {
  private baseUrl = 'http://localhost:8081/api/user';
 
  constructor(private http: HttpClient) { }
 
  getUserDetails(idUtente: string): Observable<Utente> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
    const options = { headers };
    const url = `${this.baseUrl}/${idUtente}`;
    return this.http.get<Utente>(url, options);
  }
 
  updateUser(idUtente: string, utenteDto: UtenteDto): Observable<Utente> {
    const token = localStorage.getItem('access_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
    const options = { headers };
    const url = `${this.baseUrl}/update/${idUtente}`;
    return this.http.put<Utente>(url, utenteDto, options);
  }
}