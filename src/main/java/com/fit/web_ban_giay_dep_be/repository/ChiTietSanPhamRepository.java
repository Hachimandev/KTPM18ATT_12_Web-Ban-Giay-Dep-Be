package com.fit.web_ban_giay_dep_be.repository;


import com.fit.web_ban_giay_dep_be.entity.ChiTietSanPham;
import com.fit.web_ban_giay_dep_be.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, String> {
    List<ChiTietSanPham> findBySanPham_MaSanPham(String maSanPham);
}
