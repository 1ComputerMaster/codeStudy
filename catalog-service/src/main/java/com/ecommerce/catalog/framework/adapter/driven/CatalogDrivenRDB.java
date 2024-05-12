package com.ecommerce.catalog.framework.adapter.driven;

import com.ecommerce.catalog.application.port.out.CatalogOutputRDB;
import com.ecommerce.catalog.application.port.out.repository.CatalogRepository;
import com.ecommerce.catalog.domain.entity.CatalogEntity;
import com.ecommerce.catalog.domain.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CatalogDrivenRDB implements CatalogOutputRDB {
    private final CatalogRepository catalogRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ResponseCatalog> findAll() {
        List<CatalogEntity> catalog = catalogRepository.findAll();
        return catalog.stream().map(catalogEntity -> modelMapper.map(catalogEntity, ResponseCatalog.class)).collect(Collectors.toList());
    }
}
