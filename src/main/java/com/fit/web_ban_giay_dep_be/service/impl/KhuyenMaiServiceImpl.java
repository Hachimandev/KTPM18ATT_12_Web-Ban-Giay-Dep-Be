package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.KhuyenMai;
import com.fit.web_ban_giay_dep_be.repository.KhuyenMaiRepository;
import com.fit.web_ban_giay_dep_be.service.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    private final KhuyenMaiRepository khuyenMaiRepository;

    @Override
    public List<KhuyenMai> layTatCaKhuyenMai() {
        return khuyenMaiRepository.findAll();
    }

    @Override
    public KhuyenMai LayKhuyenMaiTheoMa(String maKhuyenMai) {
        return khuyenMaiRepository.findById(maKhuyenMai).orElse(null);
    }


}
