package com.technicaltest.mantenimientos.Repositories;

import com.technicaltest.mantenimientos.Models.ContributorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorTypeRepository extends JpaRepository<ContributorType, Integer> {
}
