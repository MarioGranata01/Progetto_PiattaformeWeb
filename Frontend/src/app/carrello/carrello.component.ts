import { Component, OnInit } from '@angular/core';
import { CarrelloDto, CarrelloService,Carrello,ProdottiCarrello} from '../services/carrello.service';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { OrdineService } from '../services/ordine.service';
import { Router } from '@angular/router';
 
 
@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.css']
})
export class CarrelloComponent implements OnInit {
 
  carrelloDto: CarrelloDto = { carrello: { id: 0, idUtente: 0, prezzoTotale: 0, prodottiCarrelloDto: [] }, numberOfItemsInCart: 0 };
  carrelloDto$: Observable<CarrelloDto> | undefined;
 
  constructor(private router: Router,private authService: AuthService, private carrelloService: CarrelloService, private ordineService: OrdineService) {}
 
  ngOnInit() {
    // Ottieni l'ID utente da localStorage utilizzando il servizio AuthService
    const idUtente = this.authService.getUserIdFromLocalStorage();
 
    if (idUtente !== null) {
      // Chiamare il servizio del carrello per ottenere i dati del carrello
      this.carrelloDto$ = this.carrelloService.getCartByUserId(idUtente);
 
      this.carrelloService.getCartByUserId(idUtente).subscribe(
        (response: any) => {
          console.log('Risposta del carrello dal server:', response);
          if (response && response.carrello && response.carrello.id) {
            this.carrelloDto = response.carrello;
           
          } else {
            console.error('La risposta dal server non ha la struttura attesa:', response);
          }
        },
        (error) => {
          console.error('Errore nel recupero del carrello:', error);
        }
      );
    } else {
      console.error('ID utente non disponibile da localStorage.');
    }
  }
 
  removeProductFromCart(idProdotto: number) {
    const idUtente = this.authService.getUserIdFromLocalStorage();
 
    if (idUtente !== null) {
      this.carrelloService.removeProductFromCart(idUtente, idProdotto).subscribe(
        (response: any) => {
          console.log('Prodotto rimosso dal carrello:', response);
          this.carrelloDto = response.carrello;
        },
        (error) => {
          console.error('Errore nella rimozione del prodotto dal carrello:', error);
        },
        () => {
          // Aggiorna esplicitamente la variabile cartData dopo la rimozione
          this.carrelloDto$ = this.carrelloService.getCartByUserId(idUtente);
        }
      );
    } else {
      console.error('ID utente non disponibile da localStorage.');
    }
  }
  
  clearCart() {
    const idUtente = this.authService.getUserIdFromLocalStorage();
    console.log(idUtente);
    if (idUtente !== null) {
      this.carrelloService.clearCart(idUtente).subscribe(
        () => {
          console.log('Carrello cancellato con successo');
          console.log(this.carrelloDto);
          this.carrelloDto$ = this.carrelloService.getCartByUserId(idUtente);
          
          console.log(this.carrelloDto);
        },
        (error) => {
          console.error('Errore nella cancellazione del carrello:', error);
        }
      );
    } else {
      console.error('ID utente non disponibile da localStorage.');
    }
  }
   
  buySingleProduct(idProdotto: number) {
    const idUtente = this.authService.getUserIdFromLocalStorage();
 
    if (idUtente !== null) {
      // Chiamare il servizio degli ordini per creare un ordine dal carrello
      this.carrelloService.getCartByUserId(idUtente).subscribe(
        (response: any) => {
          const cartItems = response.carrello.prodottiCarrelloDto.filter((item: { idProdotto: number; }) => item.idProdotto === idProdotto);
 
          this.ordineService.createOrderFromCart(idUtente, cartItems).subscribe(
            (orderResponse: any) => {
              console.log('Ordine creato con successo:', orderResponse);
            },
            (orderError) => {
              console.error('Errore durante la creazione dell\'ordine:', orderError);
            }
          );
        },
        (cartError) => {
          console.error('Errore nel recupero del carrello:', cartError);
          // Aggiungi la logica per gestire gli errori nel recupero del carrello, se necessario
        }
      );
    } else {
      console.error('ID utente non disponibile da localStorage.');
    }
  }
 
  confirmPurchase(idProdotto: number): void {
    const confirmation = window.confirm('Sei sicuro di voler procedere con l\'acquisto?');
 
    if (confirmation) {
      this.buySingleProduct(idProdotto);
      this.router.navigate(['']);
    }
  }

  proceedToOrder(): void {
    const confirmation = window.confirm('Sei sicuro di voler procedere con l\'acquisto?');
 
    if (confirmation) {
        const idUtente = this.authService.getUserIdFromLocalStorage();
      if (idUtente !== null) {
        // Chiamare il servizio degli ordini per creare un ordine dal carrello
        this.carrelloService.getCartByUserId(idUtente).subscribe(
          (response: any) => {
            const cartItems = response.carrello.prodottiCarrelloDto;
            this.ordineService.createOrderFromCart(idUtente, cartItems).subscribe(
              (orderResponse: any) => {
                if(orderResponse==null) alert("Il carrello è vuoto.");
                else{
                console.log('Ordine creato con successo:', orderResponse);
                this.router.navigate(['/order']);}
              },
              (orderError) => {
                console.error('Errore durante la creazione dell\'ordine:', orderError);
              }
            );
          },
          (cartError) => {
            console.error('Errore nel recupero del carrello:', cartError);
            // Aggiungi la logica per gestire gli errori nel recupero del carrello, se necessario
          }
        );
      } else {
        console.error('ID utente non disponibile da localStorage.');
      }
    }
  }
}