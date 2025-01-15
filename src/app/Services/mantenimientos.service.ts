import { Injectable } from '@angular/core';
import {Entidad} from "../Models/entidad.model";
import {ContributorType} from "../Models/contributorType.model";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

export interface EntidadResponse {
  content: Entidad[];
  totalElements: number;
}

export interface DocumentTypeResponse {
  content: DocumentType[];
  totalElements: number;
}

export interface ContributorTypeResponse {
  content: ContributorType[];
  totalElements: number;
}

@Injectable({
  providedIn: 'root'
})
export class MantenimientosService {

  private apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) { }

  // Entidad
  getEntidadesFilter(
    documentType: number | null,
    contributorType: number | null,
    active: boolean | null,
    page: number,
    size: number,
    searchTerm: string | null
  ): Observable<EntidadResponse> {
    const token = sessionStorage.getItem('authToken');

    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (documentType !== null && documentType !== undefined) {
      params = params.set('documentType', documentType.toString());
    }

    if (contributorType !== null && contributorType !== undefined) {
      params = params.set('contributorType', contributorType.toString());
    }

    if (active !== null && active !== undefined) {
      params = params.set('active', active.toString());
    }

    if (searchTerm !== null && searchTerm !== undefined && searchTerm.trim() !== '') {
      params = params.set('searchTerm', searchTerm.toString());
    }

    return this.http.get<any>(`${this.apiUrl}/entidades/getAll`, { headers, params });
  }

  registerEntidad(entidadData: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<any>(`${this.apiUrl}/entidades/saveEntidad`, entidadData, { headers });
  }

  updateEntidad(idEntidad: number, updatedEntidad: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<any>(`${this.apiUrl}/entidades/updateEntidad/${idEntidad}`, updatedEntidad, { headers });
  }

  deleteEntidad(entidadId: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete(`${this.apiUrl}/entidades/deleteEntidad/${entidadId}`, { headers });
  }

  // Tipos de Documentos
  getAllDocumentTypes(page: number, size: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(`${this.apiUrl}/document-type/getAll`, { headers, params });
  }

  registerDocumentType(documentData: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<any>(`${this.apiUrl}/document-type/saveDocumentType`, documentData, { headers });
  }

  updateDocumentType(idDocumentType: number, updatedDocumentType: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<any>(`${this.apiUrl}/document-type/updateDocumentType/${idDocumentType}`, updatedDocumentType, { headers });
  }

  deleteDocumentType(idDocumentType: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete(`${this.apiUrl}/document-type/deleteDocumentType/${idDocumentType}`, { headers });
  }


  // Tipos de Contribuidores
  getAllContributorTypes(page: number, size: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(`${this.apiUrl}/contributor-type/getAll`, { headers, params });
  }

  registerContributorType(contributorData: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<any>(`${this.apiUrl}/contributor-type/saveContributor`, contributorData, { headers });
  }

  updateContributorType(idContributorType: number, updatedContributorType: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<any>(`${this.apiUrl}/contributor-type/updateContributorType/${idContributorType}`, updatedContributorType, { headers });
  }

  deleteContributorType(idContributorType: number): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    if (!token) {
      throw new Error('Token no encontrado');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete(`${this.apiUrl}/contributor-type/deleteContributor/${idContributorType}`, { headers });
  }
}
