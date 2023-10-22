package com.e.commerce.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;



import static com.e.commerce.service.constants.ErrorMessage.*;

@Data
public class RegistrationRequest {

    @NotBlank(message = "Fill captcha.")
    private String captcha;

    @NotNull
    @NotBlank(message = EMPTY_FIRST_NAME)
    private String firstName;

    @NotBlank(message = EMPTY_LAST_NAME)
    private String lastName;

    @Size(min = 6, max = 16, message = PASSWORD_CHARACTER_LENGTH)
    private String password;

    @Size(min = 6, max = 16, message = PASSWORD2_CHARACTER_LENGTH)
    private String password2;


    @NotBlank(message = EMAIL_CANNOT_BE_EMPTY)
    @Email(message = INCORRECT_EMAIL)
    private String email;
}