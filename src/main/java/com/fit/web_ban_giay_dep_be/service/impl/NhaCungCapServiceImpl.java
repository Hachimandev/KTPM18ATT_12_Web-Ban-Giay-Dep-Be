package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.NhaCungCap;
import com.fit.web_ban_giay_dep_be.repository.NhaCungCapRepository;
import com.fit.web_ban_giay_dep_be.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NhaCungCapServiceImpl implements NhaCungCapService {

    private final NhaCungCapRepository repository;

    @Override
    public List<NhaCungCap> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<NhaCungCap> getNhaCungCapById(String id) {
        return repository.findById(id);
    }
    // phát sinh mã NCC
    private String phatSinhMaNCC() {
        return "NCC" + System.currentTimeMillis();
    }
    @Override
    public NhaCungCap addNhaCungCap(NhaCungCap ncc) {
        ncc.setMaNhaCungCap(phatSinhMaNCC());
        return repository.save(ncc);
    }

    @Override
    public NhaCungCap updateNhaCungCap(String id, NhaCungCap ncc) {
        ncc.setMaNhaCungCap(id);
        return repository.save(ncc);
    }
    @Override
    public void deleteNhaCungCap(String id) {
        repository.deleteById(id);
    }
}
