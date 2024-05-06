package com.ecommerce.user.application.port.out;

import com.ecommerce.user.domain.vo.RequestUser;

public interface UserDrivenRDB {
    public void insert(RequestUser requestUser);
}
