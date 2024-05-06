package com.ecommerce.user.application.port.in;

import com.ecommerce.user.application.port.out.UserDrivenRDB;
import com.ecommerce.user.application.usecase.UserUsecase;
import com.ecommerce.user.domain.vo.RequestUser;
import com.ecommerce.user.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInputPort implements UserUsecase {
    private final UserDrivenRDB userDrivenRDB;
    private final Validator validator;
    @Override
    public boolean createUser(RequestUser requestUser) {
        System.out.println(requestUser.getUserId());
        if(!validator.isUser(requestUser)){
            return false;
        }
        userDrivenRDB.insert(requestUser);
        return true;
    }
}
