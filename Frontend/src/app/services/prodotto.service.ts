import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

 
export interface Prodotto {
  id: number;
  nomeProdotto: string;
  descrizione: string;
  prezzo: number;
  immagine: string;
  quantita: number;
}
 
@Injectable({
  providedIn: 'root',
})
export class ProdottoService {
  private baseUrl = 'http://localhost:8081/api/products';
 
  constructor(private http: HttpClient) {}
 
 
  getAll(): Observable<Prodotto[]> {
    return this.http.get<Prodotto[]>(`${this.baseUrl}/all`);
  }
 
  getProductById(id: number): Observable<Prodotto> {
    return this.http.get<Prodotto>(`${this.baseUrl}/${id}`);
  }
 
  addProduct(prodottoDto: any): Observable<Prodotto> {
    return this.http.post<Prodotto>(`${this.baseUrl}/add`, prodottoDto);
  }
 
  deleteById(id: number, updatedProductDto:any): Observable<Prodotto> {
    const url = `${this.baseUrl}/delete/${id}`;
    return this.http.put<Prodotto>(url, updatedProductDto);
  }
  searchProducts(searchTerm: string): Observable<Prodotto[]> {
    return this.http.get<Prodotto[]>(`${this.baseUrl}/search?term=${searchTerm}`);
  }

  updateProduct(id: number, updatedProductDto: any): Observable<Prodotto> {
    const url = `${this.baseUrl}/update/${id}`;
    return this.http.put<Prodotto>(url, updatedProductDto);
  }
}