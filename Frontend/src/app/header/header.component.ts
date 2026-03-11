import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Utente, UtenteService } from '../services/utente.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn = false;
  isUser = false;
  isAdmin = false;
  userDetails?: Utente;
 
  constructor(private authService: AuthService, private utenteService: UtenteService) {}
 
  ngOnInit() {
    
    this.authService.loggedIn.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
 
      if (this.isLoggedIn) {
        this.loadUserDetails();
      } else {
        this.resetUserDetails();
      }
    });
 
    this.isLoggedIn = this.authService.isAuthenticated();
 
    if (this.isLoggedIn) {
      this.loadUserDetails();
    }
  }
 
  private loadUserDetails() {
    const userId = localStorage.getItem('id_utente');
 
    if (userId) {
      const userIdNumber = +userId;
 
      this.utenteService.getUserDetails(userIdNumber.toString()).subscribe(
        (response: any) => {
          console.log('User details:', response);
         
          this.userDetails = response;
 
          const userRole = this.authService.getUserRoleFromToken();
         
          console.log(userRole);
          this.isUser = userRole === 'UTENTE';
          this.isAdmin = userRole === 'ADMIN';
         
          console.log('user', this.isUser)
          console.log('admin', this.isAdmin)

        },
        (error: any) => {
          console.error('Error fetching user details:', error);
        }
      );
    }
  }
 
  private resetUserDetails() {
    this.userDetails = undefined;
    this.isUser = false;
    this.isAdmin = false;
  }
 
  logout() {
    this.authService.logout();
  }
}