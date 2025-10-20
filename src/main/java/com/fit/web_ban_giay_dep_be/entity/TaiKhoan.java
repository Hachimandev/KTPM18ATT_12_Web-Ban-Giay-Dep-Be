package com.fit.web_ban_giay_dep_be.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoan {

    @Id
    private String maTaiKhoan;

    @Column(nullable = false, unique = true)
    private String tenDangNhap;

    @Column(nullable = false)
    private String matKhau;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String toString() {
        return tenDangNhap + " - " + role;
    }
}

