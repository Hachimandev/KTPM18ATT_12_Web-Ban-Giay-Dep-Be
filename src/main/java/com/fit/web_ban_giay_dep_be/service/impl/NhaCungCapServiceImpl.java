package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.NhaCungCap;
import com.fit.web_ban_giay_dep_be.repository.NhaCungCapRepository;
import com.fit.web_ban_giay_dep_be.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NhaCungCapServiceImpl implements NhaCungCapService {

    private final NhaCungCapRepository repository;

    @Override
    public List<NhaCungCap> getAll() {
        return repository.findAll();
    }
}
