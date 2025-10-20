package com.fit.web_ban_giay_dep_be.config;

import com.fit.web_ban_giay_dep_be.entity.Role;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;

public class TestSecurity {
    public static void main(String[] args) {
        TaiKhoan tk = new TaiKhoan("TK002", "user", "user123", Role.ROLE_USER);
        TaiKhoanDetails details = new TaiKhoanDetails(tk);

        System.out.println("Username: " + details.getUsername());
        System.out.println("Password: " + details.getPassword());
        System.out.println("Authorities: " + details.getAuthorities());
        System.out.println("isEnabled: " + details.isEnabled());
    }
}
