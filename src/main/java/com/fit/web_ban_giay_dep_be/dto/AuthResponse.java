package com.fit.web_ban_giay_dep_be.dto;
import lombok.*;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
}
