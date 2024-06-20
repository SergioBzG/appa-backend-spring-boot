package com.sbz.appa.infrastructure.persistence.repository;

import com.sbz.appa.infrastructure.persistence.entity.GuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuideRepository extends JpaRepository<GuideEntity, UUID> {
}
