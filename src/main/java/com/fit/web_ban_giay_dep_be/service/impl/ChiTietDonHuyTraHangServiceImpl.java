package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.ChiTietDonHuyTraHang;
import com.fit.web_ban_giay_dep_be.repository.ChiTietDonHuyTraHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietDonHuyTraHangServiceImpl implements com.fit.web_ban_giay_dep_be.service.ChiTietDonHuyTraHangService {

    @Autowired
    private ChiTietDonHuyTraHangRepository chiTietDonHuyTraHangRepository;

    @Override
    public List<ChiTietDonHuyTraHang> getAllChiTietDonHuyTraHang(){
        return chiTietDonHuyTraHangRepository.findAll();
    }

    @Override
    public Optional<ChiTietDonHuyTraHang> getChiTietDonHuyTraHangById(String id){
        return chiTietDonHuyTraHangRepository.findById(id);
    }

    @Override
    public ChiTietDonHuyTraHang addChiTietDonHuyTraHang(ChiTietDonHuyTraHang chiTietDonHuyTraHang){
        return chiTietDonHuyTraHangRepository.save(chiTietDonHuyTraHang);
    }

    @Override
    public ChiTietDonHuyTraHang updateChiTietDonHuyTraHang(String id, ChiTietDonHuyTraHang chiTietDonHuyTraHang){
        chiTietDonHuyTraHang.setMaChiTietDonHuyTraHang(id);
        return chiTietDonHuyTraHangRepository.save(chiTietDonHuyTraHang);
    }

    @Override
    public void deleteChiTietDonHuyTraHang(String id) {
        chiTietDonHuyTraHangRepository.deleteById(id);
    }
}
