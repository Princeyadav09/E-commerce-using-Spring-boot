package com.e.commerce.service.service.implementation;

import com.e.commerce.service.domain.User;
import com.e.commerce.service.dto.CaptchaResponse;
import com.e.commerce.service.enums.AuthProvider;
import com.e.commerce.service.enums.Role;
import com.e.commerce.service.exception.EmailException;
import com.e.commerce.service.exception.PasswordException;
import com.e.commerce.service.exception.QueryException;
import com.e.commerce.service.respository.UserRepository;
import com.e.commerce.service.service.AuthenticationService;
import com.e.commerce.service.service.email.MailSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.e.commerce.service.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    public static final Logger Log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final RestTemplate restTemplate;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${hostname}")
    private String hostname;

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.url}")
    private String captchaUrl;

//    @Override
//    public Map<String, Object> login(String email, String password) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//            User user = userRepository.findByEmail(email)
//                    .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
//            String userRole = user.getRoles().iterator().next().name();
//            String token = jwtProvider.createToken(email, userRole);
//            Map<String, Object> response = new HashMap<>();
//            response.put("user", user);
//            response.put("token", token);
//            return response;
//        } catch (AuthenticationException e) {
//            throw new ApiRequestException(INCORRECT_PASSWORD, HttpStatus.FORBIDDEN);
//        }
//    }

    @Override
    @Transactional
    public ResponseEntity<Object> registerUser(User user, String captcha, String password2) {
        String url = String.format(captchaUrl, secret, captcha);
        restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if (user.getPassword() != null && !user.getPassword().equals(password2)) {
            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
        }
        Log.info("ok    ->    3");

        Boolean is_present = false;

        try{
           is_present = userRepository.findByEmail(user.getEmail()).isPresent();
        } catch (Exception ex) {
            throw new QueryException("error in database ! ");
        }

        if (is_present) {
            throw new EmailException(EMAIL_IN_USE);
        }
        Log.info("ok    ->    4");
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setProvider(AuthProvider.LOCAL);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Log.info("ok    ->    5");
        userRepository.save(user);
        Log.info("ok    ->    4");
//        sendEmail(user, "Activation code", "registration-template", "registrationUrl", "/activate/" + user.getActivationCode());
        return ResponseEntity.ok("User successfully registered.");
    }

//    @Override
//    @Transactional
//    public User registerOauth2User(String provider, OAuth2UserInfo oAuth2UserInfo) {
//        User user = new User();
//        user.setEmail(oAuth2UserInfo.getEmail());
//        user.setFirstName(oAuth2UserInfo.getFirstName());
//        user.setLastName(oAuth2UserInfo.getLastName());
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//        return userRepository.save(user);
//    }
//
//    @Override
//    @Transactional
//    public User updateOauth2User(User user, String provider, OAuth2UserInfo oAuth2UserInfo) {
//        user.setFirstName(oAuth2UserInfo.getFirstName());
//        user.setLastName(oAuth2UserInfo.getLastName());
//        user.setProvider(AuthProvider.valueOf(provider.toUpperCase()));
//        return userRepository.save(user);
//    }
//
//    @Override
//    public String getEmailByPasswordResetCode(String code) {
//        return userRepository.getEmailByPasswordResetCode(code)
//                .orElseThrow(() -> new ApiRequestException(INVALID_PASSWORD_CODE, HttpStatus.BAD_REQUEST));
//    }
//
//    @Override
//    @Transactional
//    public String sendPasswordResetCode(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
//        user.setPasswordResetCode(UUID.randomUUID().toString());
//        userRepository.save(user);
//
//        sendEmail(user, "Password reset", "password-reset-template", "resetUrl", "/reset/" + user.getPasswordResetCode());
//        return "Reset password code is send to your E-mail";
//    }
//
//    @Override
//    @Transactional
//    public String passwordReset(String email, String password, String password2) {
//        if (StringUtils.isEmpty(password2)) {
//            throw new PasswordConfirmationException(EMPTY_PASSWORD_CONFIRMATION);
//        }
//        if (password != null && !password.equals(password2)) {
//            throw new PasswordException(PASSWORDS_DO_NOT_MATCH);
//        }
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ApiRequestException(EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
//        user.setPassword(passwordEncoder.encode(password));
//        user.setPasswordResetCode(null);
//        userRepository.save(user);
//        return "Password successfully changed!";
//    }
//
//    @Override
//    @Transactional
//    public String activateUser(String code) {
//        User user = userRepository.findByActivationCode(code)
//                .orElseThrow(() -> new ApiRequestException(ACTIVATION_CODE_NOT_FOUND, HttpStatus.NOT_FOUND));
//        user.setActivationCode(null);
//        user.setActive(true);
//        userRepository.save(user);
//        return "User successfully activated.";
//    }
//
    private void sendEmail(User user, String subject, String template, String urlAttribute, String urlPath) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("firstName", user.getFirstName());
        attributes.put(urlAttribute, "http://" + hostname + urlPath);
        mailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
    }
}