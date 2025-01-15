import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Entidad} from "../../Models/entidad.model";
import {MantenimientosService} from "../../Services/mantenimientos.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit{
  entidades: any[] = [];
  documentTypes: any[] = [];
  contributorTypes: any[] = [];

  displayedColumns: string[] = ['idEntidad','socialReason', 'documentTypeName', 'documentNumber' , 'contributorTypeName', 'active', 'actions']
  selectedEntidad: any | null = null;

  isAddEntidadDialogOpen = false;
  newEntidad = {
    documentType: 0,
    documentNumber: '',
    socialReason: '',
    nameCommercial: '',
    contributorType: 0,
    address: '',
    phone: '',
    active: true,
  };

  isEditEntidadDialogOpen = false;
  editEntidad = {
    idEntidad: 0,
    documentType: 0,
    documentNumber: '',
    socialReason: '',
    nameCommercial: '',
    contributorType: 0,
    address: '',
    phone: '',
    active: true,
  };

  isDeleteConfirmDialogOpen = false;
  entidadToDelete: any | null = null;

  filters = {
    documentType: null as number | null,
    contributorType: null as number | null,
    active: null as boolean | null,
    page: 0,
    size: 10,
    searchTerm: null as string | null,
  };

  totalElements: number = 0;
  pageSize: number = 10;
  dataSource = new MatTableDataSource<Entidad>();

  constructor(
    private mantenimientosService: MantenimientosService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
  this.loadEntidad();
  this.loadDocumentTypes();
  this.loadContributorTypes();
  }

  // Cargar entidades
  loadEntidad(): void {
    const { documentType, contributorType, active, page, size, searchTerm } = this.filters;

    this.mantenimientosService.getEntidadesFilter(
      documentType,
      contributorType,
      active,
      page,
      size,
      searchTerm
    ).subscribe((data) => {
      console.log('Entidades data:', data);
      if (data.content) {
        this.dataSource.data = data.content;
        this.totalElements = data.totalElements;
      }
    }, (error) => {
      console.error('Error al cargar entidades:', error);
    });
  }

  onPaginateChange(event: any): void {
    this.filters.page = event.pageIndex;
    this.filters.size = event.pageSize;
    this.loadEntidad();
  }

  onFilterChange(): void {
    this.filters.page = 0;
    this.loadEntidad();
  }

  // Información de la entidad
  viewInfo(entidad: any): void {
    this.selectedEntidad = entidad;
  }
  closeInfo(): void {
    this.selectedEntidad = null;
  }

  // Añadir Entidad
  submitAddEntidad(): void {
    this.mantenimientosService.registerEntidad(this.newEntidad).subscribe(
      (response) => {
        console.log('Entidad registrada:', response);
        alert('Entidad registrada con éxito.');
        this.loadEntidad();
        this.closeAddEntidadDialog();
        window.location.reload();
      },
      (error) => {
        console.error('Error al registrar la entidad:', error);
        alert('Error al registrar la entidad.');
      }
    );
  }

  submitForm() {
    this.submitAddEntidad();
  }
  openAddEntidadDialog(): void {
    this.isAddEntidadDialogOpen = true;
  }

  closeAddEntidadDialog(): void {
    this.isAddEntidadDialogOpen = false;
    this.newEntidad = {
      documentType: 0,
      documentNumber: '',
      socialReason: '',
      nameCommercial: '',
      contributorType: 0,
      address: '',
      phone: '',
      active: true,
    };
  }

  // Editar Entidad
  openEditEntidadDialog(entidad: any): void {
    this.isEditEntidadDialogOpen = true;
    this.editEntidad = { ...entidad };
  }

  closeEditEntidadDialog(): void {
    this.isEditEntidadDialogOpen = false;
    this.editEntidad = {
      idEntidad: 0,
      documentType: 0,
      documentNumber: '',
      socialReason: '',
      nameCommercial: '',
      contributorType: 0,
      address: '',
      phone: '',
      active: true,
    };
  }

  submitEditForm(): void {
    this.mantenimientosService.updateEntidad(this.editEntidad.idEntidad, this.editEntidad).subscribe({
      next: () => {
        this.snackBar.open('Entidad actualizada con éxito', 'Cerrar', { duration: 3000 });
        this.loadEntidad();
        this.closeEditEntidadDialog();
        window.location.reload();
      },
      error: (err) => {
      this.snackBar.open('Error al actualizar la entidad', 'Cerrar', { duration: 3000 });
      console.error(err);
      },
    });
  }

  // Eliminar Entidad
  openDeleteConfirmDialog(entidad: any): void {
    this.isDeleteConfirmDialogOpen = true;
    this.entidadToDelete = entidad;
  }

  closeDeleteConfirmDialog(): void {
    this.isDeleteConfirmDialogOpen = false;
    this.entidadToDelete = null;
  }

  confirmDelete(): void {
    if (this.entidadToDelete) {
      this.deleteEntidad(this.entidadToDelete.idEntidad);
      this.closeDeleteConfirmDialog();
    }
  }

  deleteEntidad(idEntidad: number): void {
    this.mantenimientosService.deleteEntidad(idEntidad).subscribe({
      next: (response) => {
        const message = response.message;
        this.snackBar.open(message, 'Cerrar', { duration: 3000 });
        this.loadEntidad();
      },
      error: (err) => {
        this.snackBar.open('Error al eliminar la entidad', 'Cerrar', { duration: 3000 });
        console.error(err);
      }
    });
  }

  // Cargar Tipos Documentos - Contribuidor
  loadDocumentTypes(): void {
    this.mantenimientosService.getAllDocumentTypes(0, 10).subscribe(
      (data) => {
        this.documentTypes = data.content;
      },
      (error) => {
        console.error('Error al cargar tipos de documentos:', error);
      }
    );
  }

  // Cargar Tipos Contribuidores
  loadContributorTypes(): void {
    this.mantenimientosService.getAllContributorTypes(0, 10).subscribe(
      (data) => {
        this.contributorTypes = data.content;
      },
      (error) => {
        console.error('Error al cargar tipos de contribuidores:', error);
      }
    );
  }
}

