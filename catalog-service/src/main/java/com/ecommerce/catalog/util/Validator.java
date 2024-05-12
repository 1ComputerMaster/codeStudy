package com.ecommerce.catalog.util;

import com.ecommerce.catalog.domain.vo.RequestCatalog;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class Validator {
    public boolean isUser(RequestCatalog requestCatalog){
        if(Objects.isNull(requestCatalog)){
            return false;
        }
        return true;
    }
}
