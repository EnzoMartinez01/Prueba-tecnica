import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MantenimientosService} from "../../Services/mantenimientos.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {
  documentTypes: any[] = [];

  displayedColumns: string[] = ['id', 'name', 'code', 'active', 'actions'];
  selectedDocument: any | null = null;

  isAddDocumentTypeDialogOpen = false;
  newDocumentType = {
    code: '',
    name: '',
    description: '',
    active: true
  };

  isEditDocumentTypeDialogOpen = false;
  editDocumentType = {
    id: 0,
    code: '',
    name: '',
    description: '',
    active: true
  };

  isDeleteConfirmDialogOpen = false;
  documentToDelete: any | null = null;

  filters = {
    page: 0,
    size: 10
  };

  totalElements: number = 0;
  pageSize: number = 10;
  dataSource = new MatTableDataSource<DocumentType>();

  constructor(private mantenimientosService: MantenimientosService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadDocumentTypes();
  }

  // Cargar Tipos de Documentos
  loadDocumentTypes(): void {
    const { page, size } = this.filters;
    this.mantenimientosService.getAllDocumentTypes(page, size).subscribe(
      (data) => {
        if (data.content) {
          this.dataSource.data = data.content;
          this.totalElements = data.totalElements;
        }
      },
      (error) => {
        console.error('Error al cargar tipos de documentos:', error);
      }
    );
  }

  onPaginateChange(event: any): void {
    this.filters.page = event.pageIndex;
    this.filters.size = event.pageSize;
    this.loadDocumentTypes();
  }

  //Información del tipo de documento
  viewInfo(documentType: any): void {
    this.selectedDocument = documentType;
  }

  closeInfo(): void {
    this.selectedDocument = null;
  }

  //Añadir Tipo de Documento
  submitAddDocumentType(): void {
    this.mantenimientosService.registerDocumentType(this.newDocumentType).subscribe(
      (response) => {
        console.log('DocumentType registrado:', response);
        alert('DocumentType registrado con éxito.');
        this.loadDocumentTypes();
        this.closeAddDocumentTypeDialog();
        window.location.reload();
      },
      (error) => {
        console.error('Error al registrar el documentType:', error);
        alert('Error al registrar el documentType.');
      }
    );
  }

  submitForm() {
    this.submitAddDocumentType();
  }

  openAddDocumentTypeDialog(): void {
    this.isAddDocumentTypeDialogOpen = true;
  }

  closeAddDocumentTypeDialog(): void {
    this.isAddDocumentTypeDialogOpen = false;
    this.newDocumentType = {
      code: '',
      name: '',
      description: '',
      active: true
    };
  }

  //Editar Tipo de Documento
  openEditDocumentTypeDialog(documentType: any): void {
    this.isEditDocumentTypeDialogOpen = true;
    this.editDocumentType = { ...documentType };
  }

  closeEditDocumentTypeDialog(): void {
    this.isEditDocumentTypeDialogOpen = false;
    this.editDocumentType = {
      id: 0,
      code: '',
      name: '',
      description: '',
      active: true
    };
  }

  submitEditForm(): void {
    this.mantenimientosService.updateDocumentType(this.editDocumentType.id, this.editDocumentType).subscribe({
      next: () => {
        this.snackBar.open('DocumentType actualizado con éxito', 'Cerrar', { duration: 3000 });
        this.loadDocumentTypes();
        this.closeEditDocumentTypeDialog();
        window.location.reload();
      },
      error: (err) => {
        this.snackBar.open('Error al actualizar el documentType', 'Cerrar', { duration: 3000 });
        console.error(err);
      },
    });
  }

  // Eliminar Tipo de Documento
  openDeleteConfirmDialog(documentType: any): void {
    this.isDeleteConfirmDialogOpen = true;
    this.documentToDelete = documentType;
  }

  closeDeleteConfirmDialog(): void {
    this.isDeleteConfirmDialogOpen = false;
    this.documentToDelete = null;
  }

  confirmDelete(): void {
    if (this.documentToDelete) {
      this.deleteDocumentType(this.documentToDelete.id);
      this.closeDeleteConfirmDialog();
    }
  }

  deleteDocumentType(idDocumentType: number): void {
    this.mantenimientosService.deleteDocumentType(idDocumentType).subscribe({
      next: (response) => {
        const message = response.message;
        this.snackBar.open(message, 'Cerrar', { duration: 3000 });
        this.loadDocumentTypes();
        window.location.reload();
      },
      error: (err) => {
        this.snackBar.open('Error al eliminar el documentType', 'Cerrar', { duration: 3000 });
        console.error(err);
      }
    });
  }
}
