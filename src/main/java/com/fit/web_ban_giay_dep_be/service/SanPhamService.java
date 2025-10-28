package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;


    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    public Optional<SanPham> getSanPhamById(String id) {
        return sanPhamRepository.findById(id);
    }


    public SanPham addSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }


    public SanPham updateSanPham(String id, SanPham sanPham) {
        sanPham.setMaSanPham(id);
        return sanPhamRepository.save(sanPham);
    }

    public void deleteSanPham(String id) {
        sanPhamRepository.deleteById(id);
    }
}
