package com.sbz.appa.infrastructure.persistence.repository;

import com.sbz.appa.infrastructure.persistence.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findFirstByArrivedIsNullAndUserBisonIsNullOrderByCreatedAsc();
    Optional<ServiceEntity> findFirstByArrivedIsNullAndUserBisonId(Long id);
    Optional<ServiceEntity> findByGuideId(UUID guideId);
}
