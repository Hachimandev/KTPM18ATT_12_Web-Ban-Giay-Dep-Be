package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.LoaiSanPham;

import java.util.List;
import java.util.Optional;

public interface LoaiSanPhamService {
    List<LoaiSanPham> getAllLoaiSanPham();

    Optional<LoaiSanPham> getLoaiSanPhamById(String id);

    LoaiSanPham addLoaiSanPham(LoaiSanPham loaiSanPham);

    LoaiSanPham updateLoaiSanPham(String id, LoaiSanPham loaiSanPham);

    void deleteLoaiSanPham(String id);
}
