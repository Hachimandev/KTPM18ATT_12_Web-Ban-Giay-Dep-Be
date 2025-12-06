package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.DonHuyTraHang;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;

import java.util.List;
import java.util.Optional;

public interface DonHuyTraHangService {
    List<DonHuyTraHang> getAllDonHuyTraHang();

    Optional<DonHuyTraHang> getDonHuyTraHangById(String id);

    DonHuyTraHang addDonHuyTraHang(DonHuyTraHang donHuyTraHang);

    DonHuyTraHang updateDonHuyTraHang(String id, DonHuyTraHang donHuyTraHang);

    void deleteDonHuyTraHang(String id);

    HoaDon cancelOrder(String maHoaDon, String maKhachHang);

}
