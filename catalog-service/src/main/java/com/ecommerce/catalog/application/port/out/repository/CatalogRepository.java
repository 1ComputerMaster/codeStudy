package com.ecommerce.catalog.application.port.out.repository;

import com.ecommerce.catalog.domain.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String id);
}
