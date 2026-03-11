import { Component, OnInit } from '@angular/core';
import { Prodotto, ProdottoService } from '../services/prodotto.service';
import { CarrelloService } from '../services/carrello.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-prodotto',
  templateUrl: './prodotto.component.html',
  styleUrls: ['./prodotto.component.css'],
})
export class ProdottoComponent implements OnInit {
  prodotti: Prodotto[] = [];
  filteredProdotti: Prodotto[] = []; // Array per i prodotti filtrati
  newProductDto: any = {
    nomeProdotto: '',
    descrizione: '',
    immagine: '',
    prezzo: 0.0,
    quantita: 0,
  };
  selectedProductId: number | null = null;
  searchTerm: string = ''; // Termine di ricerca
  isAdmin = false;

  constructor(
    private prodottoService: ProdottoService,
    private carrelloService: CarrelloService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const idProdotto = params.get('id');
      this.selectedProductId = idProdotto ? +idProdotto : null;
      if (this.selectedProductId !== null) {
        this.prodottoService.getProductById(this.selectedProductId).subscribe((prodotto) => {
          // Implementa la logica se necessario
        });
      } else {
        this.getAll();
      }
    });
  }

  getAll() {
    const userRole = this.authService.getUserRoleFromToken();
    this.isAdmin = userRole === 'ADMIN';
    this.prodottoService.getAll().subscribe(
      (prodotti) => {
        this.prodotti = prodotti;
        this.filteredProdotti = prodotti; // Inizializza l'array filtrato
      },
      (error) => {
        console.error('Error fetching products:', error);
        // Puoi gestire l'errore qui, ad esempio mostrando un messaggio all'utente
      }
    );
  }

  // Funzione per filtrare i prodotti in base al termine di ricerca
  filterProducts() {
    this.filteredProdotti = this.prodotti.filter((product) =>
      this.matchesSearch(product)
    );
  }

  // Funzione per verificare se un prodotto corrisponde al termine di ricerca
  matchesSearch(product: Prodotto): boolean {
    const search = this.searchTerm.toLowerCase();
    return (
      product.nomeProdotto.toLowerCase().includes(search) ||
      product.descrizione.toLowerCase().includes(search)
    );
  }

  removeProduct(idProdotto: number): void {
    if (confirm('Sei sicuro di voler eliminare questo prodotto?')) {
      this.prodottoService.deleteById(idProdotto, this.newProductDto).subscribe(
        () => {
          // Rimuovi il prodotto dalla lista locale dopo l'eliminazione
          this.prodotti = this.prodotti.filter(product => product.id !== idProdotto);
          alert("Aggiorna la pagina per visualizzare i prodotti rimanenti, altrimenti elimina gli altri");
        },
        (error) => {
          console.error('Error deleting product:', error);
          // Puoi gestire l'errore qui, ad esempio mostrando un messaggio all'utente
        }
      );
    }
  }

  updateProduct(productId: number) {
    this.router.navigate(['/products/update/' ,productId]);
  }
}
