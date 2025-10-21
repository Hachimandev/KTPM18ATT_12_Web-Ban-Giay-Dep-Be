package com.fit.web_ban_giay_dep_be.dto;

import lombok.*;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
}
