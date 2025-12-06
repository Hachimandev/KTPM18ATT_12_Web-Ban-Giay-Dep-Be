package com.fit.web_ban_giay_dep_be.repository;


import com.fit.web_ban_giay_dep_be.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {
    KhachHang findByTaiKhoan_TenDangNhap(String tenDangNhap);
    Optional<KhachHang> findByEmail(String email);
    Optional<KhachHang> findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);
}
