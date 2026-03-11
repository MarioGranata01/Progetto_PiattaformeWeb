import { Component, OnInit } from '@angular/core';
import { Prodotto, ProdottoService } from '../services/prodotto.service';
import { CarrelloService } from '../services/carrello.service';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dettagli-prodotto',
  templateUrl: './dettagli-prodotto.component.html',
  styleUrls: ['./dettagli-prodotto.component.css']
})
export class DettagliProdottoComponent implements OnInit {
  idProdotto: number | undefined;
  prodotto: any; // Assicurati di definire correttamente il tipo del prodotto
  quantityToBuy: number = 1;
 
  constructor(
    private route: ActivatedRoute,
    private prodottoService: ProdottoService,
    private carrelloService: CarrelloService,
    private location: Location,
    private authService: AuthService,
    private router: Router,
   
  ) {}
 
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const idProdotto = parseInt(params.get('id')!, 10);
      if (!isNaN(idProdotto)) {
        this.idProdotto = idProdotto;
        this.getProductDetails(this.idProdotto);
        this.getCartQuantityAndInitialize();
      } else {
        console.error('ID prodotto non valido.');
      }
    });
  }
 
 
 
  private async getCartQuantityAndInitialize() {
    const idUtente = this.authService.getUserIdFromLocalStorage();
   
    if (idUtente !== null) {
      try {
        const carrello = await this.carrelloService.getCartByUserId(idUtente).toPromise();
 
        // Verifica se cart è definito prima di accedere a cart.cart.cartProdDto
        if (carrello && carrello.carrello && carrello.carrello.prodottiCarrelloDto) {
          const prodottiCarrello = carrello.carrello.prodottiCarrelloDto.find(prodottiCarrello => prodottiCarrello.idProdotto === this.idProdotto);
 
          if (prodottiCarrello) {
            this.quantityToBuy = prodottiCarrello.quantita;
          }
        }
      } catch (error) {
        console.log(this.carrelloService.getCartByUserId(idUtente).toPromise());
        console.error('Errore nel recupero del carrello:', error);
        
      }
    }
  }
 
 
  isAdmin(): boolean {
    // Ottieni il ruolo dall'AuthService e verifica se è 'admin'
    const userRole = this.authService.getUserRoleFromToken();
    return userRole === 'ADMIN';
  }
 
  getProductDetails(idProdotto: number) {
    this.prodottoService.getProductById(idProdotto).subscribe(
      (response: any) => {
        this.prodotto = response;
       
      },
      (error) => {
        console.error('Errore nel recupero dei dettagli del prodotto:', error);
      }
    );
  }
  async addItemToCart() {
    console.log(this.authService.getUserIdFromLocalStorage());
    
    if (this.idProdotto) {
      const idUtente = this.authService.getUserIdFromLocalStorage();
 
      if (idUtente !== null) {
        try {
          // Invia al server la nuova quantità desiderata dall'utente
          const response = await this.carrelloService.addItemToCart(idUtente, this.idProdotto, this.quantityToBuy || 1).toPromise();
 
          console.log('Risposta dal server:', response);
 
          if (response == null) {
            alert("Quantità elevata! Massima quantità disponibile: " + this.prodotto.quantita + "\nControlla il carrello!");
          } else {
            alert("Prodotto aggiunto al carrello!");
 
            // Verifica se 'response.cart' è definito prima di accedere a 'response.cart.cartProdDto'
            if (response.carrello) {
              // Aggiorna la quantitàToBuy con la quantità effettivamente aggiunta
              this.quantityToBuy = response.carrello.prodottiCarrelloDto.find(prodottiCarrello => prodottiCarrello.idProdotto === this.idProdotto)?.quantita || 1;
 
              // Aggiorna il prodotto con i nuovi dettagli (se necessario)
              this.getProductDetails(this.idProdotto);
 
              // Aggiorna la quantità nel carrello ogni volta che viene modificata
              this.getCartQuantityAndInitialize();
            }
          }
        } catch (error) {
          console.error('Errore nell\'aggiunta del prodotto al carrello:', error);
        }
      } else {
        console.error('ID utente non disponibile.');
        this.router.navigate(['/login']);
      }
    }
 
  }

  goBack() {
    this.location.back();
  }
}
