package com.ecommerce.catalog.framework.adapter.driving;

import com.ecommerce.catalog.application.usecase.CatalogUsecase;
import com.ecommerce.catalog.domain.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogDrivingAdapter {
    private final Environment env;
    private final CatalogUsecase catalogUsecase;
    @GetMapping("/health_check")
    private String healthCheck(){
        return String.format("PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    private ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        List<ResponseCatalog> requestCatalogList = catalogUsecase.getAllCatalogs();
        return new ResponseEntity<>(requestCatalogList, HttpStatus.valueOf(200));
    }


}
