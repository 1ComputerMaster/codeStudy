package com.ecommerce.user.framework.adapter.driving;

import com.ecommerce.user.application.usecase.UserUsecase;
import com.ecommerce.user.domain.vo.RequestUser;
import com.ecommerce.user.domain.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserDrivingAdapter {
    private final Environment env;
    private final UserUsecase userUsecase;
    @GetMapping("/health_check")
    private String healthCheck(){
        return String.format("PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<ResponseUser> getUserById(@PathVariable String id){
        ResponseUser responseUser = userUsecase.getByUserId(id);
        return new ResponseEntity<ResponseUser>(responseUser, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/users")
    private ResponseEntity<List<ResponseUser>> getUsers(){
        List<ResponseUser> responseUserList = userUsecase.getAllUser();
        return new ResponseEntity<List<ResponseUser>>(responseUserList, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/users")
    private ResponseEntity<String> createUser(@RequestBody RequestUser requestUser){
        if(userUsecase.createUser(requestUser)) {
            return new ResponseEntity<>("SAVED", HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>("FAILED", HttpStatusCode.valueOf(400));
        }
    }
}
