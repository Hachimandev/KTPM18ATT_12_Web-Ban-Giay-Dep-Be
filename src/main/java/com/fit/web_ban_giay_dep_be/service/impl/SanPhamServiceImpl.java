package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamServiceImpl implements com.fit.web_ban_giay_dep_be.service.SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;


    @Override
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    @Override
    public Optional<SanPham> getSanPhamById(String id) {
        return sanPhamRepository.findById(id);
    }


    @Override
    public SanPham addSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham);
    }


    @Override
    public SanPham updateSanPham(String id, SanPham sanPham) {
        sanPham.setMaSanPham(id);
        return sanPhamRepository.save(sanPham);
    }

    @Override
    public void deleteSanPham(String id) {
        sanPhamRepository.deleteById(id);
    }
}
