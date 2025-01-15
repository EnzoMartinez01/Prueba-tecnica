package com.technicaltest.mantenimientos.Services;

import com.technicaltest.mantenimientos.Dto.EntidadDto;
import com.technicaltest.mantenimientos.Models.Entidad;
import com.technicaltest.mantenimientos.Repositories.ContributorTypeRepository;
import com.technicaltest.mantenimientos.Repositories.DocumentTypeRepository;
import com.technicaltest.mantenimientos.Repositories.EntidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EntidadServices {
    private static final Logger logger = LoggerFactory.getLogger(EntidadServices.class);

    private final EntidadRepository entidadRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ContributorTypeRepository contributorTypeRepository;

    public EntidadServices(EntidadRepository entidadRepository,
                           DocumentTypeRepository documentTypeRepository,
                           ContributorTypeRepository contributorTypeRepository) {
        this.entidadRepository = entidadRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.contributorTypeRepository = contributorTypeRepository;
    }

    public Page<EntidadDto> findAllFiltered(
            Integer documentType,
            Integer contributorType,
            Boolean active,
            String searchTerm,
            int page,
            int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            String search = (searchTerm != null && !searchTerm.trim().isEmpty()) ? searchTerm.trim() : null;
            Page<Entidad> entidades = entidadRepository.findByFilters(
                    documentType, contributorType, active, search, pageable
            );
            return entidades.map(this::mapToDto);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de entidades", e);
            throw new RuntimeException("Error al obtener la lista de entidades");
        }
    }

    public EntidadDto findById(Integer id) {
        try {
            return entidadRepository.findById(id).map(this::mapToDto).orElse(null);
        } catch (Exception e) {
            logger.error("Error al obtener la entidad con id {}", id, e);
            throw new RuntimeException("Error al obtener la entidad con id " + id);
        }
    }

    public EntidadDto mapToDto(Entidad entidad) {
        EntidadDto dto = new EntidadDto();
        dto.setIdEntidad(entidad.getId());
        dto.setDocumentTypeId(entidad.getDocumentType().getId());
        dto.setDocumentTypeName(entidad.getDocumentType().getName());
        dto.setDocumentNumber(entidad.getDocumentNumber());
        dto.setSocialReason(entidad.getSocialReason());
        dto.setNameCommercial(entidad.getNameCommercial());
        dto.setContributorTypeId(entidad.getContributorType().getId());
        dto.setContributorTypeName(entidad.getContributorType().getName());
        dto.setAddress(entidad.getAddress());
        dto.setPhone(entidad.getPhone());
        dto.setActive(entidad.getActive());
        return dto;
    }

    public Entidad saveEntidad(Entidad entidad) {
        try {
            entidad.setActive(true);
            return entidadRepository.save(entidad);
        } catch (Exception e) {
            logger.error("Error al guardar la entidad {}", entidad, e);
            throw new RuntimeException("Error al guardar la entidad " + entidad);
        }
    }

    public Entidad updateEntidad(Integer id, Entidad entidad) {
        try {
            return entidadRepository.findById(id).map(existingEntidad -> {
                if (entidad.getDocumentType() != null) {
                    existingEntidad.setDocumentType(documentTypeRepository.findById(entidad.getDocumentType().getId()).orElseThrow(() -> new RuntimeException("Document Type not found with ID: " + entidad.getDocumentType().getId())));
                }
                if (entidad.getDocumentNumber() != null) {
                    existingEntidad.setDocumentNumber(entidad.getDocumentNumber());
                }
                if (entidad.getSocialReason() != null) {
                    existingEntidad.setSocialReason(entidad.getSocialReason());
                }
                if (entidad.getNameCommercial() != null) {
                    existingEntidad.setNameCommercial(entidad.getNameCommercial());
                }
                if (entidad.getContributorType() != null) {
                    existingEntidad.setContributorType(contributorTypeRepository.findById(entidad.getContributorType().getId()).orElseThrow(() -> new RuntimeException("Contributor Type not found with ID: " + entidad.getContributorType().getId())));
                }
                if (entidad.getAddress() != null) {
                    existingEntidad.setAddress(entidad.getAddress());
                }
                if (entidad.getPhone() != null) {
                    existingEntidad.setPhone(entidad.getPhone());
                }
                if (entidad.getActive() != null) {
                    existingEntidad.setActive(entidad.getActive());
                }
                return entidadRepository.save(existingEntidad);
            }).orElseThrow(() -> new RuntimeException("Error al actualizar la entidad " + entidad));
        } catch (Exception e) {
            logger.error("Error al actualizar la entidad {}", entidad, e);
            throw new RuntimeException("Error al actualizar la entidad " + entidad);
        }
    }

    public void deleteEntidad(Integer id) {
        try {
            entidadRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar la entidad {}", id, e);
            throw new RuntimeException("Error al eliminar la entidad " + id);
        }
    }
}
