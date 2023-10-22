package com.e.commerce.service.service;

import com.e.commerce.service.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    ResponseEntity<Object> registerUser(User user, String captcha, String password2);
}
