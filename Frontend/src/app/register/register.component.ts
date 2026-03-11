import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  confirmPassword = '';
  passwordVisible = false;
  confirmPasswordVisible = false;

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    // Verifica se la password soddisfa le condizioni della regex
    if (!this.isValidPassword()) {
      alert("La password deve contenere almeno un numero, una lettera maiuscola, un carattere speciale e deve essere lunga almeno 6 caratteri.");
      return;
    }

    // Verifica se le password corrispondono
    if (this.password !== this.confirmPassword) {
      alert("Le password non corrispondono");
      return;
    }

    // Verifica se l'email è valida
    if (!this.isValidEmail()) {
      alert("L'indirizzo email non è valido");
      return;
    }

    // Effettua la registrazione
    this.authService.register(this.username, this.email, this.password).subscribe({
      next: (response) => {
        alert("Registrazione completata, effettua il login per continuare!");
        console.log(response);
        this.router.navigate(['/login']);
      },
      error: (error) => {
        alert("Registrazione fallita!");
        console.error(error);
      }
    });
  }

  // Funzione per verificare se la password soddisfa le condizioni della regex
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

  // Funzione per verificare se l'email è valida
  private isValidEmail(): boolean {
    // Utilizza una regex semplice per controllare la presenza di @ e almeno un punto dopo @
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(this.email);
  }

  // Metodo per toggle la visualizzazione/nascondimento della password
  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

  // Metodo per toggle la visualizzazione/nascondimento della conferma password
  toggleConfirmPasswordVisibility() {
    this.confirmPasswordVisible = !this.confirmPasswordVisible;
  }
}
