import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProdottoService } from '../services/prodotto.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-aggiorna-prodotto',
  templateUrl: './aggiorna-prodotto.component.html',
  styleUrl: './aggiorna-prodotto.component.css'
})
export class AggiornaProdottoComponent {

  idProdotto!: number;
  newProductDto: any = {};
  private routeSubscription: Subscription;
 
  constructor(
    private prodottoService: ProdottoService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.idProdotto = +id;
        console.log(id);
        this.loadProductDetails();
      } else {
        console.error('Id del prodotto non valido');
      }
    });
  }
 
  ngOnInit() {
    // Optional: Puoi spostare la logica di caricamento del prodotto qui, se preferisci
  }
 
  private loadProductDetails() {
    this.prodottoService.getProductById(this.idProdotto).subscribe(
      (prodotto) => {
        this.newProductDto = {
          nomeProdotto: prodotto.nomeProdotto,
          descrizione: prodotto.descrizione,
          prezzo: prodotto.prezzo,
          immagine: prodotto.immagine,
          quantita: prodotto.quantita
        };
       
      },
     
      (error) => {
        console.error('Errore durante il recupero dei dettagli del prodotto:', error);
      }
    );
  }
 
  updateProduct() {
    this.prodottoService.updateProduct(this.idProdotto, this.newProductDto).subscribe(
      (updatedProduct) => {
        alert('Prodotto aggiornato con successo:');
        this.router.navigate(['/products']);
      },
      (error) => {
        console.error('Errore durante l\'aggiornamento del prodotto:', error);
      }
    );
  }
 
  ngOnDestroy() {
    this.routeSubscription.unsubscribe(); // Importante per evitare memory leaks
  }
}