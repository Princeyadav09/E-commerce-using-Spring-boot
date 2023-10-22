package com.e.commerce.service.controller;

import com.e.commerce.service.dto.RegistrationRequest;
import com.e.commerce.service.dto.user.UserResponse;
import com.e.commerce.service.mapper.AuthenticationMapper;
import com.e.commerce.service.service.implementation.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    public static final Logger Log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    AuthenticationMapper authenticationMapper;


    public ResponseEntity<UserResponse> getUserInfo() {
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registration(@Valid @RequestBody RegistrationRequest user){
      return ResponseEntity.ok(authenticationMapper.registerUser(user.getCaptcha(), user));
    }


}
