package com.ecommerce.user.application.usecase;

import com.ecommerce.user.domain.vo.RequestUser;
import com.ecommerce.user.domain.vo.ResponseUser;

import java.util.List;

public interface UserUsecase {
    public boolean createUser(RequestUser requestUser);

    public ResponseUser getByUserId(String id);

    List<ResponseUser> getAllUser();
}
