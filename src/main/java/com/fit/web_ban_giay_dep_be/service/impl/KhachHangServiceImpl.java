package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.KhachHang;
import com.fit.web_ban_giay_dep_be.repository.KhachHangRepository;
import com.fit.web_ban_giay_dep_be.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachHangRepository;

    @Override
    public int layDiemTichLuyTheoUsername(String username) {
        KhachHang kh = khachHangRepository.findByTaiKhoan_TenDangNhap(username);
        return kh != null ? kh.getDiemTichLuy() : 0;
    }

    @Override
    public KhachHang findByTaiKhoan_TenDangNhap(String username) {
        return khachHangRepository.findByTaiKhoan_TenDangNhap(username);
    }

    @Override
    public KhachHang save(KhachHang kh) {
        return khachHangRepository.save(kh);
    }
}
