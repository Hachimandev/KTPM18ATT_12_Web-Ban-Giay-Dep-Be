package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.DonNhapHang;

import java.util.List;
import java.util.Optional;

public interface DonNhapHangService {
    List<DonNhapHang> getAllDonNhapHang();

    Optional<DonNhapHang> getDonNhapHangById(String maDonNhap);

    DonNhapHang addDonNhapHang(DonNhapHang donNhapHang);

    DonNhapHang updateDonNhapHang(String maDonNhap, DonNhapHang donNhapHang);

    void deleteDonNhapHang(String maDonNhap);
}
