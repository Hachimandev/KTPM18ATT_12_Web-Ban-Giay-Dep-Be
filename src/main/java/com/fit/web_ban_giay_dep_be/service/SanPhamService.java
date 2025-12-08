package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.SanPham;

import java.util.List;
import java.util.Optional;

public interface SanPhamService {
    List<SanPham> getAllSanPham(String searchTerm, String category, String gender, String brand, List<String> sizes, String sort, Double minPrice, Double maxPrice);

    Optional<SanPham> getSanPhamById(String id);

    SanPham addSanPham(SanPham sanPham);

    SanPham updateSanPham(String id, SanPham sanPham);

    void deleteSanPham(String id);
}
