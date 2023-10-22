package com.e.commerce.service.dto.user;

import com.e.commerce.service.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class BaseUserResponse {
    private Long id;
    private String email;
    private String firstName;
    private Set<Role> roles;
    private String provider;
}
