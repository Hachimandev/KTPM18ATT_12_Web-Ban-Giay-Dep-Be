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

import java.text.DecimalFormat;
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

    private String generateNewMaKhuyenMai() {
        String maxId = khuyenMaiRepository.findMaxMaKhuyenMai();
        int nextNumber = 1;

        if (maxId != null && maxId.startsWith("KM")) {
            try {
                // Tách phần số (ví dụ: 001)
                String numberPart = maxId.substring(2);
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (NumberFormatException e) {
                // Nếu không tách được số, bắt đầu từ 1
                nextNumber = 1;
            }
        }

        // Định dạng số thành chuỗi 3 chữ số (ví dụ: 1 -> 001)
        DecimalFormat df = new DecimalFormat("000");
        return "KM" + df.format(nextNumber);
    }

    @Override
    @Transactional
    public KhuyenMai themKhuyenMai(KhuyenMaiRequest request) {

        // 💡 BỎ QUA KIỂM TRA request.getMaKhuyenMai() vì chúng ta tự sinh mã

        NhanVien nhanVien = null;
        if (request.getMaNhanVien() != null) {
            nhanVien = nhanVienRepository.findById(request.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        }

        // 💡 SỬ DỤNG HÀM TẠO MÃ MỚI
        String newMaKhuyenMai = generateNewMaKhuyenMai();

        KhuyenMai khuyenMai = KhuyenMai.builder()
                .maKhuyenMai(newMaKhuyenMai) // 💡 GÁN MÃ MỚI
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