package com.technicaltest.mantenimientos.Repositories;

import com.technicaltest.mantenimientos.Models.Entidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Integer> {
    @Query("SELECT e FROM Entidad e " +
            "WHERE (:documentType IS NULL OR e.documentType.id = :documentType) " +
            "AND (:contributorType IS NULL OR e.contributorType.id = :contributorType) " +
            "AND (:active IS NULL OR e.active = :active) " +
            "AND (:searchTerm IS NULL OR " +
            "LOWER(e.documentNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.socialReason) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Entidad> findByFilters(
            @Param("documentType") Integer documentType,
            @Param("contributorType") Integer contributorType,
            @Param("active") Boolean active,
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}
