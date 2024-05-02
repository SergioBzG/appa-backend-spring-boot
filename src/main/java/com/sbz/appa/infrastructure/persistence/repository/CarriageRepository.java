package com.sbz.appa.infrastructure.persistence.repository;

import com.sbz.appa.infrastructure.persistence.entity.CarriageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarriageRepository extends JpaRepository<CarriageEntity, Long> {
}
