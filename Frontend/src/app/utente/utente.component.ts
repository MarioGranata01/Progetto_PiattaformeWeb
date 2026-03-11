import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UtenteDto, UtenteService} from '../services/utente.service';
import { AuthService } from '../services/auth.service';
 
@Component({
  selector: 'app-utente',
  templateUrl: './utente.component.html',
  styleUrls: ['./utente.component.css']
})
export class UtenteComponent implements OnInit {

  userDetails: any = {};
  password = '';
  confirmPassword = '';
  passwordVisible = false;
  confirmPasswordVisible = false;
  constructor(
    private utenteService: UtenteService,
    private authService: AuthService,
    private router: Router 
  ) {}
 
  ngOnInit(): void {
    const idUtente = localStorage.getItem('id_utente');
    if (idUtente) {
      const numeroIdUtente = +idUtente;
 
      this.utenteService.getUserDetails( numeroIdUtente.toString()).subscribe(
        (response: any) => {
          console.log('User details:', response);
          this.userDetails = response;
        },
        (error: any) => {
          console.error('Error fetching user details:', error);
        }
      );
    }
  }
 
  updateUser(): void {
    if (!this.isValidPassword()) {
      alert("La password deve contenere almeno un numero, una lettera maiuscola, un carattere speciale e deve essere lunga almeno 6 caratteri.");
      return;
    }
    if (this.password !== this.confirmPassword) {
      alert("Le password non corrispondono");
      return;
    }
    const idUtente = localStorage.getItem('id_utente');
    if (idUtente) {
      this.userDetails.newPassword=this.password;
      console.log(this.userDetails.newPassword);
      const utenteDto: UtenteDto = {
        username: this.userDetails.username,
        password: this.userDetails.newPassword || undefined,
        email: ''
      }; 
      console.log(this.password);
      this.utenteService.updateUser(idUtente, utenteDto).subscribe(
        (response: any) => {
          console.log('Password aggiornata:', response);
          this.userDetails.username = response.username;
          this.userDetails.newPassword = '';
          alert('Il tuo account è stato aggiornato con successo! Effettua di nuovo il login.');
 
          this.authService.loggedIn.emit(false);
          this.router.navigate(['/login']);
 
        },
        (error: any) => {
          console.error('Errore:', error);
        }
      );
    } 
  }
  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

  // Metodo per toggle la visualizzazione/nascondimento della conferma password
  toggleConfirmPasswordVisibility() {
    this.confirmPasswordVisible = !this.confirmPasswordVisible;
  }

  private isValidPassword(): boolean {
    const hasNumber = /\d/.test(this.password);
    const hasUppercase = /[A-Z]/.test(this.password);
    const hasSpecialChar = /[.,;_\-@#$%^&+=!]/.test(this.password);
    const hasNoWhitespace = /\S/.test(this.password);
    const hasValidLength = this.password.length >= 6;
    const isValid = hasNumber && hasUppercase && hasSpecialChar && hasNoWhitespace && hasValidLength;

    console.log('Numero:', hasNumber);
    console.log('Maiuscola:', hasUppercase);
    console.log('Carattere speciale:', hasSpecialChar);
    console.log('Nessuno spazio:', hasNoWhitespace);
    console.log('Lunghezza:', hasValidLength);
    console.log('Totale:', isValid);

    return isValid;
  }
}