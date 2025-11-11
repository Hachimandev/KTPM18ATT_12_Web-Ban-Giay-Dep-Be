package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.ChiTietSanPham;
import com.fit.web_ban_giay_dep_be.repository.ChiTietSanPhamRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietSanPhamServiceImpl implements com.fit.web_ban_giay_dep_be.service.ChiTietSanPhamService {

    private final ChiTietSanPhamRepository repository;

    public ChiTietSanPhamServiceImpl(ChiTietSanPhamRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChiTietSanPham> getAllChiTietSanPham() {
        return repository.findAll();
    }

    @Override
    public Optional<ChiTietSanPham> getChiTietSanPhamById(String id) {
        return repository.findById(id);
    }

    @Override
    public ChiTietSanPham addChiTietSanPham(ChiTietSanPham chiTietSanPham) {
        return repository.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham updateChiTietSanPham(String id, ChiTietSanPham chiTietSanPham) {
        chiTietSanPham.setMaChiTiet(id);
        return repository.save(chiTietSanPham);
    }

    @Override
    public void deleteChiTietSanPham(String id) {
        repository.deleteById(id);
    }
}
