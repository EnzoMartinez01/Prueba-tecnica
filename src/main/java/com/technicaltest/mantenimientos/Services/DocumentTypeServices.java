package com.technicaltest.mantenimientos.Services;

import com.technicaltest.mantenimientos.Models.DocumentType;
import com.technicaltest.mantenimientos.Repositories.DocumentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentTypeServices {
    private static final Logger logger = LoggerFactory.getLogger(DocumentTypeServices.class);

    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeServices(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    public Page<DocumentType> findAll(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return documentTypeRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de documentos", e);
            throw new RuntimeException("Error al obtener la lista de documentos");
        }
    }

    public DocumentType findById(int id) {
        try {
            return documentTypeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error al obtener el documento con id {}", id, e);
            throw new RuntimeException("Error al obtener el documento con id " + id);
        }
    }

    public DocumentType saveDocumentType(DocumentType documentType) {
        try {
            return documentTypeRepository.save(documentType);
        } catch (Exception e) {
            logger.error("Error al guardar el documento {}", documentType, e);
            throw new RuntimeException("Error al guardar el documento " + documentType);
        }
    }

    public DocumentType updateDocumentType(Integer id, DocumentType documentType) {
        try {
            return documentTypeRepository.findById(id).map(existingDocumentType -> {
                if (documentType.getName() != null) {
                    existingDocumentType.setName(documentType.getName());
                }
                if (documentType.getDescription() != null) {
                    existingDocumentType.setDescription(documentType.getDescription());
                }
                if (documentType.getCode() != null) {
                    existingDocumentType.setCode(documentType.getCode());
                }
                if (documentType.getActive() != null) {
                    existingDocumentType.setActive(documentType.getActive());
                }
                return documentTypeRepository.save(existingDocumentType);
            }).orElseThrow(() -> new RuntimeException("Error al actualizar el documento " + documentType));
            } catch (Exception e) {
            logger.error("Error al actualizar el documento {}", documentType, e);
            throw new RuntimeException("Error al actualizar el documento " + documentType);
        }
    }

    public void deleteDocumentType(Integer id) {
        try {
            documentTypeRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar el documento {}", id, e);
            throw new RuntimeException("Error al eliminar el documento " + id);
        }
    }
}
