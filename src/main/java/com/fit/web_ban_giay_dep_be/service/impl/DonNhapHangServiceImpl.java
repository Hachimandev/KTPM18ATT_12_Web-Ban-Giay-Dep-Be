package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.DonNhapHang;
import com.fit.web_ban_giay_dep_be.repository.DonNhapHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonNhapHangServiceImpl implements com.fit.web_ban_giay_dep_be.service.DonNhapHangService {

    @Autowired
    private DonNhapHangRepository repository;

    @Override
    public List<DonNhapHang> getAllDonNhapHang() {
        return repository.findAll();
    }

    @Override
    public Optional<DonNhapHang> getDonNhapHangById(String maDonNhap) {
        return repository.findById(maDonNhap);
    }

    @Override
    public DonNhapHang addDonNhapHang(DonNhapHang donNhapHang) {
        return repository.save(donNhapHang);
    }

    @Override
    public DonNhapHang updateDonNhapHang(String maDonNhap, DonNhapHang donNhapHang) {
        donNhapHang.setMaDonNhap(maDonNhap);
        return repository.save(donNhapHang);
    }

    @Override
    public void deleteDonNhapHang(String maDonNhap) {
        repository.deleteById(maDonNhap);
    }
}
