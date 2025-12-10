package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.KhuyenMaiRequest;
import com.fit.web_ban_giay_dep_be.entity.KhuyenMai;
import com.fit.web_ban_giay_dep_be.entity.NhanVien;
import com.fit.web_ban_giay_dep_be.repository.KhuyenMaiRepository;
import com.fit.web_ban_giay_dep_be.repository.NhanVienRepository;
import com.fit.web_ban_giay_dep_be.service.KhuyenMaiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    private final KhuyenMaiRepository khuyenMaiRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<KhuyenMai> layTatCaKhuyenMai() {
        return khuyenMaiRepository.findAll();
    }

    @Override
    public KhuyenMai LayKhuyenMaiTheoMa(String maKhuyenMai) {
        return khuyenMaiRepository.findById(maKhuyenMai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));
    }

    @Override
    @Transactional
    public KhuyenMai themKhuyenMai(KhuyenMaiRequest request) {
        if (khuyenMaiRepository.existsById(request.getMaKhuyenMai())) {
            throw new RuntimeException("Mã khuyến mãi đã tồn tại");
        }

        NhanVien nhanVien = null;
        if (request.getMaNhanVien() != null) {
            nhanVien = nhanVienRepository.findById(request.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        }

        KhuyenMai khuyenMai = KhuyenMai.builder()
                .maKhuyenMai(request.getMaKhuyenMai())
                .ngayBatDau(request.getNgayBatDau())
                .ngayKetThuc(request.getNgayKetThuc())
                .dieuKien(request.getDieuKien())
                .chietKhau(request.getChietKhau())
                .nhanVien(nhanVien)
                .build();

        return khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    @Transactional
    public KhuyenMai capNhatKhuyenMai(String maKhuyenMai, KhuyenMaiRequest request) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(maKhuyenMai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi"));

        khuyenMai.setNgayBatDau(request.getNgayBatDau());
        khuyenMai.setNgayKetThuc(request.getNgayKetThuc());
        khuyenMai.setDieuKien(request.getDieuKien());
        khuyenMai.setChietKhau(request.getChietKhau());

        if (request.getMaNhanVien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(request.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
            khuyenMai.setNhanVien(nhanVien);
        }

        return khuyenMaiRepository.save(khuyenMai);
    }

    @Override
    public List<KhuyenMai> timKiemKhuyenMai(String tuKhoa) {
        return khuyenMaiRepository.findByMaKhuyenMaiContainingOrDieuKienContaining(tuKhoa, tuKhoa);
    }

    @Override
    public List<KhuyenMai> layKhuyenMaiHopLe() {
        return khuyenMaiRepository.findKhuyenMaiHopLe(new Date());
    }
}