package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.ChiTietDonNhapHang;

import java.util.List;
import java.util.Optional;

public interface ChiTietDonNhapHangService {
    List<ChiTietDonNhapHang> getAllChiTietDonNhapHang();

    Optional<ChiTietDonNhapHang> getChiTietDonNhapHangById(String id);

    List<ChiTietDonNhapHang> getChiTietByDonNhapHang(String maDonNhap);

    ChiTietDonNhapHang addChiTietDonNhapHang(ChiTietDonNhapHang chiTietDonNhapHang);

    ChiTietDonNhapHang updateChiTietDonNhapHang(String id, ChiTietDonNhapHang chiTietDonNhapHang);

    void deleteChiTietDonNhapHang(String id);
}
