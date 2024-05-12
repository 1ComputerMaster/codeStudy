package com.ecommerce.catalog.application.port.out;

import com.ecommerce.catalog.domain.vo.ResponseCatalog;

import java.util.List;

public interface CatalogOutputRDB {

    List<ResponseCatalog> findAll();
}
