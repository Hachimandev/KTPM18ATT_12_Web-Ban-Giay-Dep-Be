package com.fit.web_ban_giay_dep_be;

import com.fit.web_ban_giay_dep_be.config.TaiKhoanDetails;
import com.fit.web_ban_giay_dep_be.entity.Role;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;
import com.fit.web_ban_giay_dep_be.repository.TaiKhoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class WebBanGiayDepBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBanGiayDepBeApplication.class, args);
    }

    @Bean
    CommandLineRunner initAdmin(TaiKhoanRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByTenDangNhap("admin")) {
                TaiKhoan admin = TaiKhoan.builder()
                        .maTaiKhoan("TK001")
                        .tenDangNhap("admin")
                        .matKhau(encoder.encode("admin123"))
                        .email("admin@example.com")
                        .roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                        .build();
                repo.save(admin);
                System.out.println("Admin created with username=admin password=admin123");
            }
        };
    }

}
