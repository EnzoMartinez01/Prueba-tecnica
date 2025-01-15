import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../Services/Authentication/auth.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  message: string = '';

  constructor(
        private authService: AuthService,
        private fb: FormBuilder,
        private router: Router,
        private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    })
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.authService.registerUser(this.registerForm.value).subscribe(
        (response) => {
          this.snackBar.open('Usuario registrado con éxito.', 'Cerrar', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });
          this.registerForm.reset();
          this.router.navigate(['/login']);
        },
        (error) => {
          this.snackBar.open(error.error.message || 'Ocurrió un error al registrar el usuario.', 'Cerrar', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
    }
  }
}
