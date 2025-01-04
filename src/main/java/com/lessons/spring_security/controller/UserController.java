package com.lessons.spring_security.controller;

import com.lessons.spring_security.dto.AuthRequest;
import com.lessons.spring_security.entity.UserInfo;
import com.lessons.spring_security.service.JwtService;
import com.lessons.spring_security.service.UserInfoUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoUserDetailsService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserInfoUserDetailsService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
        public ResponseEntity<?> createUser(@RequestBody UserInfo userInfo){
        String s = userService.addUser(userInfo);
        return ResponseEntity.ok(s);
        }


        @PostMapping("/authenticate")
        public String authAndGetToken(@RequestBody AuthRequest authRequst){
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequst.getUserName(), authRequst.getPassword()));
            if(authenticate.isAuthenticated()){
                return jwtService.generateToken(authRequst.getUserName());
            }else{
                throw new UsernameNotFoundException("invalid user request");
            }
        }
}
