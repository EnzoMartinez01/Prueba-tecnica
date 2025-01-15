import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {ContributorType} from "../../Models/contributorType.model";
import {MantenimientosService} from "../../Services/mantenimientos.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-contributors',
  templateUrl: './contributors.component.html',
  styleUrls: ['./contributors.component.css']
})
export class ContributorsComponent implements OnInit{
  contributorType: any[] = [];

  displayedColumns: string[] = ['id', 'name', 'active', 'actions'];
  selectedContributor: any | null = null;

  isAddContributorTypeDialogOpen = false;
  newContributorType = {
    name: '',
    active: true
  };

  isEditContributorTypeDialogOpen = false;
  editContributorType = {
    id: 0,
    name: '',
    active: true
  };

  isDeleteConfirmDialogOpen = false;
  contributorToDelete: any | null = null;

  filters = {
    page: 0,
    size: 10
  };

  totalElements: number = 0;
  pageSize: number = 10;
  dataSource = new MatTableDataSource<ContributorType>();

  constructor(private mantenimientoService: MantenimientosService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadContributorTypes();
  }

  // Cargar Tipos de Contribuidores
  loadContributorTypes(): void {
    const { page, size } = this.filters;
    this.mantenimientoService.getAllContributorTypes(page, size).subscribe(
      (data) => {
        if (data.content) {
          this.dataSource.data = data.content;
          this.totalElements = data.totalElements;
        }
      },
      (error) => {
        console.error('Error al cargar tipos de contribuidores:', error);
      }
    );
  }

  onPaginateChange(event: any): void {
    this.filters.page = event.pageIndex;
    this.filters.size = event.pageSize;
    this.loadContributorTypes();
  }

  //Información del tipo de contribuidor
  viewInfo(contributorType: any): void {
    this.selectedContributor = contributorType;
  }

  closeInfo(): void {
    this.selectedContributor = null;
  }

  //Añadir Tipo de Contribuidor
  submitAddContributorType(): void {
    this.mantenimientoService.registerContributorType(this.newContributorType).subscribe(
      (response) => {
        console.log('ContributorType registrado:', response);
        alert('ContributorType registrado con éxito.');
        this.loadContributorTypes();
        this.closeAddContributorTypeDialog();
        window.location.reload();
      },
      (error) => {
        console.error('Error al registrar el contributorType:', error);
        alert('Error al registrar el contributorType.');
      }
    );
  }

  submitForm() {
    this.submitAddContributorType();
  }

  openAddContributorTypeDialog(): void {
    this.isAddContributorTypeDialogOpen = true;
  }

  closeAddContributorTypeDialog(): void {
    this.isAddContributorTypeDialogOpen = false;
    this.newContributorType = {
      name: '',
      active: true
    };
  }

  //Editar Tipo de Contribuidor
  openEditContributorTypeDialog(contributorType: any): void {
    this.isEditContributorTypeDialogOpen = true;
    this.editContributorType = { ...contributorType };
  }

  closeEditContributorTypeDialog(): void {
    this.isEditContributorTypeDialogOpen = false;
    this.editContributorType = {
      id: 0,
      name: '',
      active: true
    };
  }

  submitEditForm(): void {
    this.mantenimientoService.updateContributorType(this.editContributorType.id, this.editContributorType).subscribe({
      next: () => {
        this.snackBar.open('ContributorType actualizado con éxito', 'Cerrar', { duration: 3000 });
        this.loadContributorTypes();
        this.closeEditContributorTypeDialog();
        window.location.reload();
      },
      error: (err) => {
        this.snackBar.open('Error al actualizar el contributorType', 'Cerrar', { duration: 3000 });
        console.error(err);
      },
    });
  }

  // Eliminar Tipo de Contribuidor
  openDeleteConfirmDialog(contributorType: any): void {
    this.isDeleteConfirmDialogOpen = true;
    this.contributorToDelete = contributorType;
  }

  closeDeleteConfirmDialog(): void {
    this.isDeleteConfirmDialogOpen = false;
    this.contributorToDelete = null;
  }

  confirmDelete(): void {
    if (this.contributorToDelete) {
      this.deleteContributorType(this.contributorToDelete.id);
      this.closeDeleteConfirmDialog();
    }
  }

  deleteContributorType(idContributorType: number): void {
    this.mantenimientoService.deleteContributorType(idContributorType).subscribe({
      next: (response) => {
        const message = response.message;
        this.snackBar.open(message, 'Cerrar', { duration: 3000 });
        this.loadContributorTypes();
      },
      error: (err) => {
        this.snackBar.open('Error al eliminar el contributorType', 'Cerrar', { duration: 3000 });
        console.error(err);
      }
    });
  }
}
