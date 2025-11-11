package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.LoaiSanPham;
import com.fit.web_ban_giay_dep_be.repository.LoaiSanPhamRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoaiSanPhamServiceImpl implements com.fit.web_ban_giay_dep_be.service.LoaiSanPhamService {

    private final LoaiSanPhamRepository repository;

    public LoaiSanPhamServiceImpl(LoaiSanPhamRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LoaiSanPham> getAllLoaiSanPham() {
        return repository.findAll();
    }

    @Override
    public Optional<LoaiSanPham> getLoaiSanPhamById(String id) {
        return repository.findById(id);
    }

    @Override
    public LoaiSanPham addLoaiSanPham(LoaiSanPham loaiSanPham) {
        return repository.save(loaiSanPham);
    }

    @Override
    public LoaiSanPham updateLoaiSanPham(String id, LoaiSanPham loaiSanPham) {
        loaiSanPham.setMaLoai(id);
        return repository.save(loaiSanPham);
    }

    @Override
    public void deleteLoaiSanPham(String id) {
        repository.deleteById(id);
    }
}
