import { Component } from '@angular/core';
import { ProdottoService } from '../services/prodotto.service';

@Component({
  selector: 'app-aggiungi-prodotto',
  templateUrl: './aggiungi-prodotto.component.html',
  styleUrls: ['./aggiungi-prodotto.component.css'],
})
export class AggiungiProdottoComponent {
  nuovoProdottoDto: any = {
    nomeProdotto: '',
    descrizione: '',
    immagine: '',
    prezzo: 0.0,
    quantita: 0,
  };
 
  constructor(private prodottoService: ProdottoService) {}
 
  addProduct() {
    console.log(this.nuovoProdottoDto);
    this.prodottoService.addProduct(this.nuovoProdottoDto).subscribe(
      (newProduct) => {
        console.log('Product added successfully:', newProduct);
        alert('Prodotto caricato con successo');
        // Puoi gestire eventuali azioni aggiuntive dopo l'aggiunta, ad esempio mostrare un messaggio all'utente
        this.clearNewProductDto(); // Pulisce i campi del nuovo prodotto
      },
      (error) => {
        alert('Non è possibile caricare il prodotto, elementi mancanti');
        console.error('Error adding product:', error);
        // Puoi gestire l'errore qui, ad esempio mostrando un messaggio all'utente
      }
    );
  }
 
  private clearNewProductDto() {
    // Pulisce i campi del nuovo prodotto dopo l'aggiunta
    this.nuovoProdottoDto = {
      nomeProdotto: '',
      descrizione: '',
      immagine: '',
      prezzo: 0.0,
      quantita: 0,
    };
  }
}