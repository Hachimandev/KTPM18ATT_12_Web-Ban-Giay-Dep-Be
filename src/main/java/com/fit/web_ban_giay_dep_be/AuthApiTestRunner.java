package com.fit.web_ban_giay_dep_be;

import com.fit.web_ban_giay_dep_be.dto.AuthRequest;
import com.fit.web_ban_giay_dep_be.dto.AuthResponse;
import com.fit.web_ban_giay_dep_be.dto.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthApiTestRunner implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/auth";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==== Testing Auth API ====");

        // 1. Test register
        RegisterRequest regReq = new RegisterRequest();
        regReq.setUsername("testuser2");
        regReq.setEmail("test2@example.com");
        regReq.setPassword("123456");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RegisterRequest> regEntity = new HttpEntity<>(regReq, headers);

        try {
            ResponseEntity<String> regResponse = restTemplate.postForEntity(baseUrl + "/register", regEntity, String.class);
            System.out.println("Register response: " + regResponse.getBody());
        } catch (Exception e) {
            System.out.println("Register failed: " + e.getMessage());
        }

        // 2. Test login
        AuthRequest loginReq = new AuthRequest();
        loginReq.setUsername("testuser2");
        loginReq.setPassword("123456");

        HttpEntity<AuthRequest> loginEntity = new HttpEntity<>(loginReq, headers);
        try {
            ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(baseUrl + "/login", loginEntity, AuthResponse.class);
            AuthResponse auth = loginResponse.getBody();
            if (auth != null) {
                System.out.println("Login success! Username: " + auth.getUsername());
                System.out.println("JWT Token: " + auth.getToken());
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        System.out.println("==== Auth API Test Completed ====");
    }
}