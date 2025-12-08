package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.BinhLuanRequest;
import com.fit.web_ban_giay_dep_be.entity.BinhLuan;
import com.fit.web_ban_giay_dep_be.entity.KhachHang;
import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.repository.BinhLuanRepository;
import com.fit.web_ban_giay_dep_be.repository.KhachHangRepository;
import com.fit.web_ban_giay_dep_be.repository.SanPhamRepository;
import com.fit.web_ban_giay_dep_be.service.BinhLuanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BinhLuanServiceImpl implements BinhLuanService {

    private final BinhLuanRepository binhLuanRepository;
    private final SanPhamRepository sanPhamRepository;
    private final KhachHangRepository khachHangRepository;

    @Override
    public List<BinhLuan> getCommentsByProductId(String maSanPham) {
        return binhLuanRepository.findBySanPhamIdWithKhachHang(maSanPham);
    }

    @Override
    @Transactional
    public BinhLuan addComment(BinhLuanRequest request) {
        SanPham sanPham = sanPhamRepository.findById(request.getMaSanPham())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại."));

        KhachHang khachHang = khachHangRepository.findByTaiKhoan_TenDangNhap(request.getUsername());

        BinhLuan binhLuan = BinhLuan.builder()
                .noiDung(request.getNoiDung())
                .diemDanhGia(request.getDiemDanhGia())
                .ngayTao(LocalDateTime.now())
                .sanPham(sanPham)
                .khachHang(khachHang)
                .build();

        return binhLuanRepository.save(binhLuan);
    }
}