package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.Cart;
import com.fit.web_ban_giay_dep_be.dto.HoaDonResponseDTO;
import com.fit.web_ban_giay_dep_be.dto.OrderRequest;
import com.fit.web_ban_giay_dep_be.entity.*;
import com.fit.web_ban_giay_dep_be.repository.*;
import com.fit.web_ban_giay_dep_be.service.HoaDonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepository;
    private final KhachHangRepository khachHangRepo;
    private final KhuyenMaiRepository khuyenMaiRepo;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;

    @Override
    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }

    @Override
    public Optional<HoaDon> getHoaDonById(String id) {
        return hoaDonRepository.findById(id);
    }

    @Override
    public HoaDon addHoaDon(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public HoaDon updateHoaDon(String id, HoaDon hoaDon) {
        hoaDon.setMaHoaDon(id);
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public void deleteHoaDon(String id) {
        hoaDonRepository.deleteById(id);
    }

    @Override
    public double calculateFinalPrice(Cart cart) {
        return 0;
    }

    @Override
    public Object getCartSummary(Cart cart) {
        return null;
    }

    @Transactional
    @Override
    public HoaDonResponseDTO createHoaDonFromCart(OrderRequest request) {
        HoaDon hoaDon = new HoaDon();

        hoaDon.setMaHoaDon(generateMaHoaDon());
        hoaDon.setNgayDat(LocalDateTime.now());
        hoaDon.setThanhTien(request.getThanhTien());
        hoaDon.setDiemSuDung(request.getCart().getDiemSuDung());
        hoaDon.setTrangThaiHoaDon(TrangThaiHoaDon.CHO_XAC_NHAN);

        try {
            hoaDon.setPhuongThucThanhToan(
                    PhuongThucThanhToan.valueOf(request.getUserInfo().getPhuongThucThanhToan())
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Phương thức thanh toán không hợp lệ");
        }

        KhachHang kh = khachHangRepo.findByEmail(request.getUserInfo().getEmail())
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
        hoaDon.setKhachHang(kh);

        if (request.getCart().getMaKhuyenMai() != null) {
            KhuyenMai km = khuyenMaiRepo.findById(request.getCart().getMaKhuyenMai())
                    .orElse(null);
            hoaDon.setKhuyenMai(km);
        }

        Pageable limitOne = PageRequest.of(0, 1);
        List<ChiTietHoaDon> list = chiTietHoaDonRepository.findAllOrderByMaChiTietHoaDonDesc(limitOne);
        AtomicInteger nextCTHD = new AtomicInteger(1);
        if (!list.isEmpty()) {
            String lastMa = list.get(0).getMaChiTietHoaDon();
            if (lastMa.startsWith("CTHD")) {
                try {
                    nextCTHD.set(Integer.parseInt(lastMa.substring(4)) + 1);
                } catch (NumberFormatException ignored) {}
            }
        }

        List<ChiTietHoaDon> chiTiets = request.getCart().getItems().stream().map(item -> {
            ChiTietHoaDon ct = new ChiTietHoaDon();
            ct.setMaChiTietHoaDon(String.format("CTHD%04d", nextCTHD.getAndIncrement()));
            ct.setSoLuong(item.getSoLuong());
            ct.setTongTien(item.getGiaBan() * item.getSoLuong());

            ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(item.getMaChiTiet())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));

            ct.setChiTietSanPham(ctsp);
            ct.setSanPham(ctsp.getSanPham());
            ct.setHoaDon(hoaDon);

            if (item.getSoLuong() > ctsp.getSoLuongTonKho()) {
                throw new RuntimeException("Số lượng đặt vượt quá tồn kho: " + ctsp.getMau());
            }
            ctsp.setSoLuongTonKho(ctsp.getSoLuongTonKho() - item.getSoLuong());
            chiTietSanPhamRepository.save(ctsp);

            return ct;
        }).toList();

        hoaDon.setChiTietHoaDons(chiTiets);
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        HoaDonResponseDTO dto = new HoaDonResponseDTO();
        dto.setMaHoaDon(savedHoaDon.getMaHoaDon());
        dto.setThanhTien(savedHoaDon.getThanhTien());
        dto.setTrangThaiHoaDon(savedHoaDon.getTrangThaiHoaDon().name());

        return dto;
    }


    private String generateMaHoaDon() {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        Pageable limitOne = PageRequest.of(0, 1);
        List<HoaDon> list = hoaDonRepository.findByMaHoaDonStartingWithOrderByMaHoaDonDesc("HD" + datePart, limitOne);

        int next = 1;
        if (!list.isEmpty()) {
            String lastMa = list.get(0).getMaHoaDon();
            String numPart = lastMa.substring(10);
            try {
                next = Integer.parseInt(numPart) + 1;
            } catch (NumberFormatException e) {
                next = 1;
            }
        }

        return String.format("HD%s%04d", datePart, next);
    }

    private String generateMaChiTietHoaDon() {
        Pageable limitOne = PageRequest.of(0, 1);
        List<ChiTietHoaDon> list = chiTietHoaDonRepository.findAllOrderByMaChiTietHoaDonDesc(limitOne);
        int next = 1;
        if (!list.isEmpty()) {
            String lastMa = list.get(0).getMaChiTietHoaDon();
            if (lastMa.startsWith("CTHD")) {
                try {
                    next = Integer.parseInt(lastMa.substring(4)) + 1;
                } catch (NumberFormatException e) {
                    next = 1;
                }
            }
        }
        return String.format("CTHD%04d", next);
    }
}
