package com.ecommerce.user.util;

import com.ecommerce.user.domain.vo.RequestUser;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class Validator {
    public boolean isUser(RequestUser requestUser){
        if(Objects.isNull(requestUser)){
            return false;
        }
        return true;
    }
}
