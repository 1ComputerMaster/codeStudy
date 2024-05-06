package com.ecommerce.user.framework.adapter.driven;

import com.ecommerce.user.application.port.out.repository.UserRepository;
import com.ecommerce.user.domain.entity.User;
import com.ecommerce.user.domain.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDrivenRDB implements com.ecommerce.user.application.port.out.UserDrivenRDB {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
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
}
