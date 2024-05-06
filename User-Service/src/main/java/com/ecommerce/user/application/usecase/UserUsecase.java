package com.ecommerce.user.application.usecase;

import com.ecommerce.user.domain.vo.RequestUser;

public interface UserUsecase {
    public boolean createUser(RequestUser requestUser);
}
