import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProdottoComponent } from './prodotto/prodotto.component';
import { UtenteComponent } from './utente/utente.component';
import { AdminComponent } from './admin/admin.component';
import { CarrelloComponent } from './carrello/carrello.component';
import { DettagliProdottoComponent } from './dettagli-prodotto/dettagli-prodotto.component';
import { AggiungiProdottoComponent } from './aggiungi-prodotto/aggiungi-prodotto.component';
import { OrdineComponent } from './ordine/ordine.component';
import { AggiornaProdottoComponent } from './aggiorna-prodotto/aggiorna-prodotto.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'api/admin', component: AdminComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products', component: ProdottoComponent },
  { path: 'user', component: UtenteComponent },
  { path: 'order', component: OrdineComponent },
  { path: 'cart', component:CarrelloComponent},
  { path: 'products/add', component:AggiungiProdottoComponent},
  { path: 'products/:id', component:DettagliProdottoComponent},
  { path: 'products/update/:id', component:AggiornaProdottoComponent}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }