package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.ChangePasswordRequest;
import com.fit.web_ban_giay_dep_be.dto.UpdateAccountRequest;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;
import com.fit.web_ban_giay_dep_be.service.TaiKhoanService;
import com.fit.web_ban_giay_dep_be.service.KhachHangService; // 💡 CẦN IMPORT VÀ INJECT
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/taikhoan")
@RequiredArgsConstructor
public class TaiKhoanController {

    private final TaiKhoanService taiKhoanService;
    @GetMapping("/me/{username}")
    public ResponseEntity<TaiKhoan> getCurrentAccount(@PathVariable String username) {
        TaiKhoan taiKhoan = taiKhoanService.getCurrentAccount(username);
        return ResponseEntity.ok(taiKhoan);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateAccount(@PathVariable String username, @RequestBody UpdateAccountRequest request) {
        try {
            TaiKhoan updatedAccount = taiKhoanService.updateAccount(username, request);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/change-password/{username}")
    public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody ChangePasswordRequest request) {
        try {

            taiKhoanService.changePassword(username, request);


            return ResponseEntity.ok(Map.of("message", "Đổi mật khẩu thành công"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}