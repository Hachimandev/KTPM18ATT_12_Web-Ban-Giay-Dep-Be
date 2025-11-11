package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.ChiTietSanPham;

import java.util.List;
import java.util.Optional;

public interface ChiTietSanPhamService {
    List<ChiTietSanPham> getAllChiTietSanPham();

    Optional<ChiTietSanPham> getChiTietSanPhamById(String id);

    ChiTietSanPham addChiTietSanPham(ChiTietSanPham chiTietSanPham);

    ChiTietSanPham updateChiTietSanPham(String id, ChiTietSanPham chiTietSanPham);

    void deleteChiTietSanPham(String id);
    List<ChiTietSanPham> getChiTietSanPhamBySanPham(String maSanPham);
}
