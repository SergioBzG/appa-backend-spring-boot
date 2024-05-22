package com.sbz.appa.infrastructure.persistence.repository;

import com.sbz.appa.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhone(String phone);

    Optional<UserEntity> findFirstByAvailableIsTrueAndRoleNameAndLastDeliveryIsNull(String roleName);
    Optional<UserEntity> findFirstByAvailableIsTrueAndRoleNameAndLastDeliveryIsNotNullOrderByLastDeliveryAsc(String roleName);
}
