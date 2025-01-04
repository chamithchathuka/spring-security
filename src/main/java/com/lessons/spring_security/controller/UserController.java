package com.lessons.spring_security.controller;

import com.lessons.spring_security.dto.AuthRequest;
import com.lessons.spring_security.dto.JwtResponse;
import com.lessons.spring_security.entity.RefreshToken;
import com.lessons.spring_security.entity.UserInfo;
import com.lessons.spring_security.service.JwtService;
import com.lessons.spring_security.service.RefreshTokenService;
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
  private final RefreshTokenService refreshTokenService;

  public UserController(
      UserInfoUserDetailsService userService,
      JwtService jwtService,
      AuthenticationManager authenticationManager,
      RefreshTokenService refreshTokenService) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.refreshTokenService = refreshTokenService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createUser(@RequestBody UserInfo userInfo) {
    String s = userService.addUser(userInfo);
    return ResponseEntity.ok(s);
  }

  @PostMapping("/authenticate")
  public JwtResponse authAndGetToken(@RequestBody AuthRequest authRequst) {
    Authentication authenticate =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequst.getUserName(), authRequst.getPassword()));
    if (authenticate.isAuthenticated()) {
      return JwtResponse.builder()
          .accessToken(jwtService.generateToken(authRequst.getUserName()))
          .refreshToken(refreshTokenService.createRefreshToken(authRequst.getUserName()).getToken())
          .build();

    } else {
      throw new UsernameNotFoundException("invalid user request");
    }
  }

  @PostMapping("/refresh")
  public JwtResponse refreshToken(@RequestBody RefreshToken refreshToken) {

    return refreshTokenService
        .findByToken(refreshToken.getToken())
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUserInfo)
        .map(
            userInfo -> {
              String token = jwtService.generateToken(userInfo.getName());
              return JwtResponse.builder()
                  .accessToken(token)
                  .refreshToken(refreshToken.getToken())
                  .build();
            })
        .orElseThrow(() -> new RuntimeException("Refresh token is not in the database"));
  }
}
