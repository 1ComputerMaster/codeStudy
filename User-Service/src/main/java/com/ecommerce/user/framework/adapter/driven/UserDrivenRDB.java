package com.ecommerce.user.framework.adapter.driven;

import com.ecommerce.user.application.port.out.UserOutputRDB;
import com.ecommerce.user.application.port.out.repository.UserRepository;
import com.ecommerce.user.domain.entity.User;
import com.ecommerce.user.domain.vo.RequestUser;
import com.ecommerce.user.domain.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDrivenRDB implements UserOutputRDB {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public void insert(RequestUser requestUser) {
        User user = User.builder()
                .email(requestUser.getEmail())
                .userId(String.valueOf(UUID.randomUUID()))
                .pwd(passwordEncoder.encode(requestUser.getUserPw()))
                .createAt(LocalDate.now())
                .name(requestUser.getName())
                .build();

        userRepository.save(user);
    }

    @Override
    public ResponseUser findById(String id) {
        User user = userRepository.findByUserId(id);
        ResponseUser responseUser = modelMapper.map(user, ResponseUser.class);
        responseUser.setOrders(new ArrayList<>()); // Order Service 정의가 되지 않았음
        return responseUser;
    }

    @Override
    public List<ResponseUser> findAll() {
        return userRepository.<User>findAll().stream()
                .map(user -> modelMapper.map(user, ResponseUser.class))
                .collect(Collectors.toList());
    }
}
