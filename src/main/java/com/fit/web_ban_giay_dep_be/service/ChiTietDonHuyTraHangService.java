package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.ChiTietDonHuyTraHang;

import java.util.List;
import java.util.Optional;

public interface ChiTietDonHuyTraHangService {
    List<ChiTietDonHuyTraHang> getAllChiTietDonHuyTraHang();

    Optional<ChiTietDonHuyTraHang> getChiTietDonHuyTraHangById(String id);

    ChiTietDonHuyTraHang addChiTietDonHuyTraHang(ChiTietDonHuyTraHang chiTietDonHuyTraHang);

    ChiTietDonHuyTraHang updateChiTietDonHuyTraHang(String id, ChiTietDonHuyTraHang chiTietDonHuyTraHang);

    void deleteChiTietDonHuyTraHang(String id);
}
