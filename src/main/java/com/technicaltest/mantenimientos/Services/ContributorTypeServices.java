package com.technicaltest.mantenimientos.Services;

import com.technicaltest.mantenimientos.Models.ContributorType;
import com.technicaltest.mantenimientos.Repositories.ContributorTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContributorTypeServices {
    private static final Logger logger = LoggerFactory.getLogger(ContributorTypeServices.class);

    private final ContributorTypeRepository contributorTypeRepository;

    public ContributorTypeServices(ContributorTypeRepository contributorTypeRepository) {
        this.contributorTypeRepository = contributorTypeRepository;
    }

    public Page<ContributorType> findAll(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return contributorTypeRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de documentos", e);
            throw new RuntimeException("Error al obtener la lista de documentos");
        }
    }

    public ContributorType findById(int id) {
        try {
            return contributorTypeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error al obtener el documento con id {}", id, e);
            throw new RuntimeException("Error al obtener el documento con id " + id);
        }
    }

    public ContributorType saveContributorType(ContributorType contributorType) {
        try {
            return contributorTypeRepository.save(contributorType);
        } catch (Exception e) {
            logger.error("Error al guardar el documento {}", contributorType, e);
            throw new RuntimeException("Error al guardar el documento " + contributorType);
        }
    }

    public ContributorType updateContributorType(Integer id, ContributorType contributorType) {
        try {
            return contributorTypeRepository.findById(id).map(existingContributorType -> {
                if (contributorType.getName() != null) {
                    existingContributorType.setName(contributorType.getName());
                }
                if (contributorType.getActive() != null) {
                    existingContributorType.setActive(contributorType.getActive());
                }
                return contributorTypeRepository.save(existingContributorType);
            }).orElseThrow(() -> new RuntimeException("Error al actualizar el documento " + contributorType));
            } catch (Exception e) {
            logger.error("Error al actualizar el documento {}", contributorType, e);
            throw new RuntimeException("Error al actualizar el documento " + contributorType);
        }
    }

    public void deleteContributorType(Integer id) {
        try {
            contributorTypeRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error al eliminar el documento {}", id, e);
            throw new RuntimeException("Error al eliminar el documento " + id);
        }
    }
}
