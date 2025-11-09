package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.DonHuyTraHang;
import com.fit.web_ban_giay_dep_be.repository.DonHuyTraHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonHuyTraHangServiceImpl implements com.fit.web_ban_giay_dep_be.service.DonHuyTraHangService {

    @Autowired
    private DonHuyTraHangRepository donHuyTraHangRepository;

    @Override
    public List<DonHuyTraHang> getAllDonHuyTraHang() {
        return donHuyTraHangRepository.findAll();
    }

    @Override
    public Optional<DonHuyTraHang> getDonHuyTraHangById(String id) {
        return donHuyTraHangRepository.findById(id);
    }

    @Override
    public DonHuyTraHang addDonHuyTraHang(DonHuyTraHang donHuyTraHang) {
        return donHuyTraHangRepository.save(donHuyTraHang);
    }

    @Override
    public DonHuyTraHang updateDonHuyTraHang(String id, DonHuyTraHang donHuyTraHang){
        donHuyTraHang.setMaDonHuyTraHang(id);
        return donHuyTraHangRepository.save(donHuyTraHang);
    }

    @Override
    public void deleteDonHuyTraHang(String id) {
        donHuyTraHangRepository.deleteById(id);
    }
}
