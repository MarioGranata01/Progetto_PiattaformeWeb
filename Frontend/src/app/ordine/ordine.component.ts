import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { OrdineDto, OrdineService } from '../services/ordine.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-ordine',
  templateUrl: './ordine.component.html',
  styleUrl: './ordine.component.css'
})
export class OrdineComponent {

  ordini$: Observable<OrdineDto[]> | undefined;
 
  constructor(private authService: AuthService, private ordineService: OrdineService) { }
 
  ngOnInit() {
    const idUtente = this.authService.getUserIdFromLocalStorage();
 
    if (idUtente !== null) {
      this.ordini$ = this.ordineService.getOrdersByUserId(idUtente);
    } else {
      console.error('ID utente non disponibile da localStorage.');
    }
  }
}
