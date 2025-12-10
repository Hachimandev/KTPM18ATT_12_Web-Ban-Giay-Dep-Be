package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.ChangePasswordRequest;
import com.fit.web_ban_giay_dep_be.dto.UpdateAccountRequest;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;
import com.fit.web_ban_giay_dep_be.service.TaiKhoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/taikhoan")
@RequiredArgsConstructor
public class TaiKhoanController {

    private final TaiKhoanService taiKhoanService;

    @GetMapping("/me")
    public ResponseEntity<TaiKhoan> getCurrentAccount(Authentication authentication) {
        TaiKhoan taiKhoan = taiKhoanService.getCurrentAccount(authentication.getName());
        return ResponseEntity.ok(taiKhoan);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(Authentication authentication, @RequestBody UpdateAccountRequest request) {
        try {
            TaiKhoan updatedAccount = taiKhoanService.updateAccount(authentication.getName(), request);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestBody ChangePasswordRequest request) {
        try {
            taiKhoanService.changePassword(authentication.getName(), request);
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}