package com.ecommerce.user.framework.adapter.driving;

import com.ecommerce.user.application.usecase.UserUsecase;
import com.ecommerce.user.domain.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserDrivingAdapter {
    private final UserUsecase userUsecase;
    @GetMapping("/health_check")
    private String healthCheck(){
        return "isOK";
    }

    @PostMapping("/user")
    private ResponseEntity<String> createUser(@RequestBody RequestUser requestUser){
        if(userUsecase.createUser(requestUser)) {
            return new ResponseEntity<>("SAVED", HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<>("FAILED", HttpStatusCode.valueOf(400));
        }
    }
}
