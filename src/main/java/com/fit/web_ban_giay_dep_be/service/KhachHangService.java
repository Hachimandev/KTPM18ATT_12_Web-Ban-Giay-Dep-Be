package com.fit.web_ban_giay_dep_be.service;


import com.fit.web_ban_giay_dep_be.entity.KhachHang;

public interface KhachHangService {
    int layDiemTichLuyTheoUsername(String username);
    KhachHang findByTaiKhoan_TenDangNhap(String username);
    KhachHang save(KhachHang kh);
}
