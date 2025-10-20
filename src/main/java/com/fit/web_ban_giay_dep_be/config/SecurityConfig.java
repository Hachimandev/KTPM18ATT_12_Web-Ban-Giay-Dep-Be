package com.fit.web_ban_giay_dep_be.config;

import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;
import com.fit.web_ban_giay_dep_be.repository.TaiKhoanRepository;
import com.fit.web_ban_giay_dep_be.service.TaiKhoanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.fit.web_ban_giay_dep_be.entity.Role;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private TaiKhoanDetailsService taiKhoanDetailsService;
    @Bean
    CommandLineRunner init(TaiKhoanRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByTenDangNhap("admin").isEmpty()) {
                repo.save(TaiKhoan.builder()
                        .maTaiKhoan("TK001")
                        .tenDangNhap("admin")
                        .matKhau(encoder.encode("admin123"))
                        .role(Role.ROLE_ADMIN)
                        .build());
            }
            if (repo.findByTenDangNhap("user").isEmpty()) {
                repo.save(TaiKhoan.builder()
                        .maTaiKhoan("TK002")
                        .tenDangNhap("user")
                        .matKhau(encoder.encode("user123"))
                        .role(Role.ROLE_USER)
                        .build());
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}