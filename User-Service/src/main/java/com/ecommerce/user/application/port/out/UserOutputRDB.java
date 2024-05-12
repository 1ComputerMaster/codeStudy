package com.ecommerce.user.application.port.out;

import com.ecommerce.user.domain.entity.User;
import com.ecommerce.user.domain.vo.RequestUser;
import com.ecommerce.user.domain.vo.ResponseUser;

import java.util.List;

public interface UserOutputRDB {
    public void insert(RequestUser requestUser);

    public ResponseUser findById(String id);

    List<ResponseUser> findAll();
}
