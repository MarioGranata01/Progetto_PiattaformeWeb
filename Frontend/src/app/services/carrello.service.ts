import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prodotto } from './prodotto.service';

export interface Carrello {
  id: number;
  idUtente: number;
  prezzoTotale: number;
  prodottiCarrello: ProdottiCarrello[];
}
export interface ProdottiCarrelloDto {
  quantita: number;
  immagine: string;
  idProdotto: number;
  nomeProdotto: string;
  prezzo: number;
  subTotal: number;
  prodotto: ProdottiCarrelloDto;
}
export interface ProdottoDto {
  id: number;
  nomeProdotto: string;
  descrizione: string;
  prezzo: number;
  immagine: string;
  quantita: number;
}
export interface ProdottiCarrello {
  id: number;
  idProdotto: number;
  nomeProdotto: string;
  quantita: number;
  prezzo: number;
  subTotal: number;
  prodotto: Prodotto;
}
export interface CarrelloDto {
  carrello:{
  id: number;
  idUtente: number;
  prezzoTotale: number;
  prodottiCarrelloDto: ProdottiCarrelloDto[];
 };
numberOfItemsInCart: number;
}
@Injectable({
  providedIn: 'root'
})
export class CarrelloService {
  private baseApiUrl = 'http://localhost:8081/api/cart';
  private carrello: Prodotto[] = [];

  constructor(private http: HttpClient) {}

  updateCartProdCount(count: number) {
    console.log('Updated cart item count:', count);
  }

  getCartProdCount(idUtente: number): Observable<number> {
    const apiUrl = `${this.baseApiUrl}/cart/${idUtente}`;
    return this.http.get<number>(apiUrl);
  }

  getCartProd(idUtente: number): Observable<ProdottiCarrello[]> {
    const apiUrl = `${this.baseApiUrl}/cart/${idUtente}`;
    return this.http.get<ProdottiCarrello[]>(apiUrl);
  }

  removeCartItem(idUtente: number, idProdotto: number): Observable<ProdottiCarrello[]> {
    const apiUrl = `${this.baseApiUrl}/cart/${idUtente}/${idProdotto}`;
    return this.http.delete<ProdottiCarrello[]>(apiUrl);
  }

  addItemToCart(idUtente: number, idProdotto: number, quantita: number): Observable<CarrelloDto> {
    return this.http.post<CarrelloDto>(`${this.baseApiUrl}/${idUtente}/${idProdotto}/${quantita}`, {})
  }

  getCartByUserId(idUtente: number): Observable<CarrelloDto> {
    return this.http.get<CarrelloDto>(`${this.baseApiUrl}/${idUtente}`);
  }

  removeProductFromCart(idUtente: number, idProdotto: number): Observable<CarrelloDto> {
    return this.http.delete<CarrelloDto>(`${this.baseApiUrl}/${idUtente}/${idProdotto}`);
  }
 
  clearCart(idUtente: number): Observable<void> {
    return this.http.delete<void>(`${this.baseApiUrl}/clear/${idUtente}`, {})
  }
 
  createCartForUser(idUtente: number): Observable<void> {
    return this.http.post<void>(`${this.baseApiUrl}/create/${idUtente}`, {});
  }
}
