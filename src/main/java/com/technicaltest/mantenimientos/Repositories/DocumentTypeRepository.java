package com.technicaltest.mantenimientos.Repositories;

import com.technicaltest.mantenimientos.Models.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

}
