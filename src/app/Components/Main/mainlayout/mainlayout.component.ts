import { Component } from '@angular/core';
import {AuthService} from "../../../Services/Authentication/auth.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-mainlayout',
  templateUrl: './mainlayout.component.html',
  styleUrls: ['./mainlayout.component.css']
})
export class MainlayoutComponent {
  menuOpen = false;

  constructor(private authService: AuthService,
              private router: Router,
              private snackBar: MatSnackBar) {}

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu(): void {
    this.menuOpen = false;
  }

  logout(): void {
    this.authService.logout().subscribe(
      (response) => {
        this.snackBar.open('Sesión cerrada con éxito', 'Cerrar', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
        console.log('Logout exitoso', response);
        sessionStorage.removeItem('authToken');
        this.router.navigate(['/login']);
      },
      (error) => {
        this.snackBar.open('Error al cerrar sesión', 'Cerrar', {
          duration: 3000,
          panelClass: ['snackbar-error']
        });
        console.error('Error al hacer logout', error);
      }
    );
  }
}
