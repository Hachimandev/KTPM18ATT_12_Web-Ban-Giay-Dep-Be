package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.dto.ChangePasswordRequest;
import com.fit.web_ban_giay_dep_be.dto.UpdateAccountRequest;
import com.fit.web_ban_giay_dep_be.entity.TaiKhoan;

public interface TaiKhoanService {
    TaiKhoan getCurrentAccount(String username);
    TaiKhoan updateAccount(String username, UpdateAccountRequest request);
    void changePassword(String username, ChangePasswordRequest request);
}