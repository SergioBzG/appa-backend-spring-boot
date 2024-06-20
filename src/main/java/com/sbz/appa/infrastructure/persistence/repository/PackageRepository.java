package com.sbz.appa.infrastructure.persistence.repository;

import com.sbz.appa.infrastructure.persistence.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
}
