package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.config.TaiKhoanDetails;
import com.fit.web_ban_giay_dep_be.dto.AuthRequest;
import com.fit.web_ban_giay_dep_be.dto.AuthResponse;
import com.fit.web_ban_giay_dep_be.dto.RegisterRequest;
import com.fit.web_ban_giay_dep_be.entity.Role;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;
import com.fit.web_ban_giay_dep_be.jwt.JwtUtil;
import com.fit.web_ban_giay_dep_be.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private TaiKhoanRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        var userDetails = (TaiKhoanDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return new AuthResponse(token, userDetails.getUsername());
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest r) {
        if (userRepository.existsByTenDangNhap(r.getUsername())) return "Username exists";
        if (r.getEmail() != null && userRepository.existsByEmail(r.getEmail())) return "Email exists";

        TaiKhoan user = TaiKhoan.builder()
                .maTaiKhoan(UUID.randomUUID().toString())
                .tenDangNhap(r.getUsername())
                .email(r.getEmail())
                .matKhau(passwordEncoder.encode(r.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .build();
        userRepository.save(user);
        return "Registered";
    }
}