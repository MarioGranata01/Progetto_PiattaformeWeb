import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prodotto } from './prodotto.service';
import { ProdottoDto } from './carrello.service';
import { Utente } from './utente.service';
 
export interface Ordine {
  id: number;
  dataOrdine: Date;
  nomeProdotto: String;
  prezzoTotale: number;
}
export interface OrdineDto {
  id: number;
  prodotto: Prodotto;
  NomeProdotto: String;
  dataOrdine: Date;
  prezzoTotale: number;
  prodottiOrdineDto: ProdottiOrdineDto[];
}
export interface ProdottiOrdineDto{
  id: number;
  ordineDto: OrdineDto;
  prodottoDto: ProdottoDto;
  quantita:number;
  prezzo: number;
 
}
export interface ProdottiOrdine{
  ordine: Ordine
  utente: Utente;
  data: Date;
  prezzoTotale: number;
}
@Injectable({
  providedIn: 'root'
})


export class OrdineService {
  private apiUrl = 'http://localhost:8081/api/orders';
 
  constructor(private http: HttpClient) {}

  createOrderFromCart(idUtente: number, cartItems: any[]): Observable<any> {
    const url = `${this.apiUrl}/createFromCart?idUtente=${idUtente}`;
    return this.http.post<any>(url, cartItems);
  }
 
  getOrdersByUserId(idUtente: number): Observable<OrdineDto[]> { 
    return this.http.get<OrdineDto[]>(`${this.apiUrl}/${idUtente}`);
  }
}