package com.rcksrs.delivery.core.repository;

import com.rcksrs.delivery.core.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByIdAndActiveTrue(Long id);
    Optional<Store> findByNameIgnoreCaseAndActiveTrue(String name);

}
