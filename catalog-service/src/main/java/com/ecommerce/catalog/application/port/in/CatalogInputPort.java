package com.ecommerce.catalog.application.port.in;

import com.ecommerce.catalog.application.port.out.CatalogOutputRDB;
import com.ecommerce.catalog.application.usecase.CatalogUsecase;
import com.ecommerce.catalog.domain.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogInputPort implements CatalogUsecase {
    private final CatalogOutputRDB catalogOutputRDB;

    @Override
    public List<ResponseCatalog> getAllCatalogs() {
        return catalogOutputRDB.findAll();
    }
}
