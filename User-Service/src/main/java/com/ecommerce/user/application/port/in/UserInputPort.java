package com.ecommerce.user.application.port.in;

import com.ecommerce.user.application.port.out.UserOutputRDB;
import com.ecommerce.user.application.usecase.UserUsecase;
import com.ecommerce.user.domain.vo.RequestUser;
import com.ecommerce.user.domain.vo.ResponseUser;
import com.ecommerce.user.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInputPort implements UserUsecase {
    private final UserOutputRDB userOutputRDB;
    private final Validator validator;
    @Override
    public boolean createUser(RequestUser requestUser) {
        if(!validator.isUser(requestUser)){
            return false;
        }
        userOutputRDB.insert(requestUser);
        return true;
    }

    @Override
    public ResponseUser getByUserId(String id) {

        return userOutputRDB.findById(id);
    }

    @Override
    public List<ResponseUser> getAllUser() {
        return userOutputRDB.findAll();
    }
}
