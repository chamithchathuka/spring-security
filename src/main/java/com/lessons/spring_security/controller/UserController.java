package com.lessons.spring_security.controller;

import com.lessons.spring_security.entity.UserInfo;
import com.lessons.spring_security.service.UserInfoUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoUserDetailsService userService;


    public UserController(UserInfoUserDetailsService userService) {
        this.userService = userService;

    }

    @PostMapping("/create")
        public ResponseEntity<?> createUser(@RequestBody UserInfo userInfo){
        UserInfo userInfoR = userService.saveUser(userInfo);
        return ResponseEntity.ok(userInfoR);
        }

}
