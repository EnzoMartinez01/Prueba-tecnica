import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UsersService} from "../../Services/Users/users.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements  OnInit{
  userInfo: any;
  username: string | null = null;

  selectedUser: any | null = null;

  isEditUserDialogOpen = false;
  editUser = {
    idUser: 0,
    name: '',
    lastName: '',
    email: '',
    username: '',
    password: '',
    active: true,
  }

  isDeleteConfirmDialogOpen = false;
  userToDelete: any | null = null;

  constructor(
              private route: ActivatedRoute,
              private router: Router,
              private userService: UsersService,
              private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.userService.getUserInfoFromToken().subscribe(
      (data) => {
        this.userInfo = data;
      },
      (error) => {
        console.error('Error fetching user info:', error);
      }
    )
  }


  // Editar usuario
  openEditUserDialog(user: any): void {
    this.isEditUserDialogOpen = true;
    this.editUser = { ...user };
  }

  closeEditUserDialog(): void {
    this.isEditUserDialogOpen = false;
    this.editUser = {
      idUser: 0,
      name: '',
      lastName: '',
      email: '',
      username: '',
      password: '',
      active: true,
    };
  }

  submitEditForm(): void {
    this.userService.updateUser(this.editUser.idUser, this.editUser).subscribe({
      next: () => {
        this.snackBar.open('Usuario actualizado con éxito', 'Cerrar', { duration: 3000 });
        this.userInfo();
        this.closeEditUserDialog();
        window.location.reload();
      },
      error: (err) => {
        this.snackBar.open('Error al actualizar el usuario', 'Cerrar', { duration: 3000 });
        console.error(err);
      },
    });
  }

  // Eliminar usuario
  openDeleteConfirmDialog(user: any): void {
    this.isDeleteConfirmDialogOpen = true;
    this.userToDelete = user;
  }

  closeDeleteConfirmDialog(): void {
    this.isDeleteConfirmDialogOpen = false;
    this.userToDelete = null;
  }

  confirmDelete(): void {
    if (this.userToDelete) {
      this.deleteUser(this.userToDelete.idUser);
      this.closeDeleteConfirmDialog();
    }
  }

  deleteUser(idUser: number): void {
    this.userService.deleteUser(idUser).subscribe({
      next: (response) => {
        const message = response.message;
        this.snackBar.open(message, 'Cerrar', { duration: 3000 });
        this.router.navigate(['/login']);
        this.userInfo();
      },
      error: (err) => {
        this.snackBar.open('Error al eliminar el usuario', 'Cerrar', { duration: 3000 });
        console.error(err);
      }
    });
  }
}
