import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProdottoComponent } from './prodotto/prodotto.component';
import { CarrelloComponent } from './carrello/carrello.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { UtenteComponent } from './utente/utente.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { DettagliProdottoComponent } from './dettagli-prodotto/dettagli-prodotto.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { AggiungiProdottoComponent } from './aggiungi-prodotto/aggiungi-prodotto.component';
import { OrdineComponent } from './ordine/ordine.component';
import { AggiornaProdottoComponent } from './aggiorna-prodotto/aggiorna-prodotto.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
    UtenteComponent,
    ProdottoComponent,
    CarrelloComponent,
    HomeComponent,
    AdminComponent,
    DettagliProdottoComponent,
    AggiungiProdottoComponent,
    OrdineComponent,
    AggiornaProdottoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatIconModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatIconModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }