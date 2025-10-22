package com.fit.web_ban_giay_dep_be.dto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private List<String> roles;
}
