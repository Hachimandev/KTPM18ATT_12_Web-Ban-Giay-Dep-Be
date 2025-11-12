package com.fit.web_ban_giay_dep_be.repository;



import com.fit.web_ban_giay_dep_be.entity.ChiTietDonNhapHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietDonNhapHangRepository extends JpaRepository<ChiTietDonNhapHang, String> {
    List<ChiTietDonNhapHang> findByDonNhapHang_MaDonNhap(String maDonNhap);
}
