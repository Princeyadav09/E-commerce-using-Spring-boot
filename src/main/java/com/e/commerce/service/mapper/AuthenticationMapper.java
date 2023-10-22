package com.e.commerce.service.mapper;

import com.e.commerce.service.domain.User;
import com.e.commerce.service.dto.RegistrationRequest;
import com.e.commerce.service.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {
    public static final Logger Log = LoggerFactory.getLogger(AuthenticationMapper.class);

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CommonMapper commonMapper;

    public ResponseEntity<Object> registerUser(String captcha, RegistrationRequest registrationRequest) {
        User user = commonMapper.convertToEntity(registrationRequest, User.class);
        return authenticationService.registerUser(user, captcha, registrationRequest.getPassword2());
    }
}
