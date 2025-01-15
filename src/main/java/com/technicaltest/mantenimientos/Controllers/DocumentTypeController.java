package com.technicaltest.mantenimientos.Controllers;

import com.technicaltest.mantenimientos.Models.DocumentType;
import com.technicaltest.mantenimientos.Services.DocumentTypeServices;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/document-type")
public class DocumentTypeController {
    private final DocumentTypeServices documentTypeServices;

    public DocumentTypeController(DocumentTypeServices documentTypeServices) {
        this.documentTypeServices = documentTypeServices;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<DocumentType>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Page<DocumentType> documentTypes = documentTypeServices.findAll(page, size);
            return ResponseEntity.ok(documentTypes);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<DocumentType> getById(@RequestParam int id) {
        try {
            DocumentType documentType = documentTypeServices.findById(id);
            return ResponseEntity.ok(documentType);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/saveDocumentType")
    public ResponseEntity<Map<String, String>> saveDocumentType(@RequestBody DocumentType documentType) {
        try {
            DocumentType savedDocumentType = documentTypeServices.saveDocumentType(documentType);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tipo de Documento registrado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateDocumentType/{id}")
    public ResponseEntity<Map<String, String>> updateDocumentType(@RequestBody DocumentType documentType, @PathVariable Integer id) {
        try {
            DocumentType updatedDocumentType = documentTypeServices.updateDocumentType(id, documentType);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tipo de Documento " + documentType.getName() + " actualizado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteDocumentType/{id}")
    public ResponseEntity<Map<String, String>> deleteDocumentType(@PathVariable int id) {
        try {
            documentTypeServices.deleteDocumentType(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Documento " + id + " eliminado satisfactoriamente.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
