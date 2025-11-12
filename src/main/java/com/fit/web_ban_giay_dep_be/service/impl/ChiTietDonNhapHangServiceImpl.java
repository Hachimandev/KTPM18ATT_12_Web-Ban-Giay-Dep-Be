package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.ChiTietDonNhapHang;
import com.fit.web_ban_giay_dep_be.repository.ChiTietDonNhapHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietDonNhapHangServiceImpl implements com.fit.web_ban_giay_dep_be.service.ChiTietDonNhapHangService {

    @Autowired
    private ChiTietDonNhapHangRepository repository;

    @Override
    public List<ChiTietDonNhapHang> getAllChiTietDonNhapHang() {
        return repository.findAll();
    }

    @Override
    public Optional<ChiTietDonNhapHang> getChiTietDonNhapHangById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<ChiTietDonNhapHang> getChiTietByDonNhapHang(String maDonNhap) {
        return repository.findByDonNhapHang_MaDonNhap(maDonNhap);
    }

    @Override
    public ChiTietDonNhapHang addChiTietDonNhapHang(ChiTietDonNhapHang chiTietDonNhapHang) {
        return repository.save(chiTietDonNhapHang);
    }

    @Override
    public ChiTietDonNhapHang updateChiTietDonNhapHang(String id, ChiTietDonNhapHang chiTietDonNhapHang) {
        chiTietDonNhapHang.setMaChiTietDonNhapHang(id);
        return repository.save(chiTietDonNhapHang);
    }

    @Override
    public void deleteChiTietDonNhapHang(String id) {
        repository.deleteById(id);
    }
}
