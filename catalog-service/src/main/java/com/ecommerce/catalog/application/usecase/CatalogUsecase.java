package com.ecommerce.catalog.application.usecase;

import com.ecommerce.catalog.domain.vo.ResponseCatalog;

import java.util.List;

public interface CatalogUsecase {
    List<ResponseCatalog> getAllCatalogs();
}
