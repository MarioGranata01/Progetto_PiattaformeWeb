import { Component, OnInit } from "@angular/core";
import { Prodotto, ProdottoService } from "../services/prodotto.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  prodotto: Prodotto[] = [];
 
  constructor(private prodottoService: ProdottoService) { }
 
  ngOnInit() {
    this.prodottoService.getAll()
      .subscribe((prodotti: Prodotto[]) => {
        this.prodotto = prodotti;
      });
  }
}