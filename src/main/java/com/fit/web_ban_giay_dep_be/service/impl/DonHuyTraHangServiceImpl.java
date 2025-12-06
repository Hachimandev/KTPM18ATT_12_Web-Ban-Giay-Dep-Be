package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.*;
import com.fit.web_ban_giay_dep_be.repository.*;
import com.fit.web_ban_giay_dep_be.service.DonHuyTraHangService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonHuyTraHangServiceImpl implements DonHuyTraHangService {

    private final DonHuyTraHangRepository donHuyTraHangRepository;
    private final HoaDonRepository hoaDonRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;
    private final ChiTietDonHuyTraHangRepository chiTietDonHuyTraHangRepository;

    @Override
    public List<DonHuyTraHang> getAllDonHuyTraHang() {
        return donHuyTraHangRepository.findAll();
    }

    @Override
    public Optional<DonHuyTraHang> getDonHuyTraHangById(String id) {
        return donHuyTraHangRepository.findById(id);
    }

    @Override
    public DonHuyTraHang addDonHuyTraHang(DonHuyTraHang donHuyTraHang) {
        return donHuyTraHangRepository.save(donHuyTraHang);
    }

    @Override
    public DonHuyTraHang updateDonHuyTraHang(String id, DonHuyTraHang donHuyTraHang){
        donHuyTraHang.setMaDonHuyTraHang(id);
        return donHuyTraHangRepository.save(donHuyTraHang);
    }

    @Override
    public void deleteDonHuyTraHang(String id) {
        donHuyTraHangRepository.deleteById(id);
    }

    @Override
    @Transactional
    public HoaDon cancelOrder(String maHoaDon, String maKhachHang) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn " + maHoaDon));

        TrangThaiHoaDon currentStatus = hoaDon.getTrangThaiHoaDon();

        // [LOGIC KIỂM TRA QUYỀN VÀ TRẠNG THÁI (Giữ nguyên)]
        if (currentStatus == TrangThaiHoaDon.CHO_XAC_NHAN) {
            if (!hoaDon.getKhachHang().getMaKhachHang().equals(maKhachHang)) {
                throw new SecurityException("Bạn không có quyền hủy đơn hàng này.");
            }
        }
        else if (currentStatus == TrangThaiHoaDon.DANG_GIAO) {
            if (!hoaDon.getKhachHang().getMaKhachHang().equals(maKhachHang)) {
                throw new SecurityException("Bạn không có quyền gửi yêu cầu hủy đơn này.");
            }
            hoaDon.setTrangThaiHoaDon(TrangThaiHoaDon.CHO_HUY);
            return hoaDonRepository.save(hoaDon);
        } else if (currentStatus != TrangThaiHoaDon.CHO_HUY) {
            throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái hiện tại (" + currentStatus + ").");
        }

        // --- BẮT ĐẦU LOGIC HỦY VÀ HOÀN KHO/ĐIỂM ---

        // 1. TẠO ĐỐI TƯỢNG DonHuyTraHang
        DonHuyTraHang donHuyTraHang = new DonHuyTraHang();
        // 💡 TẠO MÃ ĐƠN HỦY (Cần hàm generateMaDonHuyTraHang)
        donHuyTraHang.setMaDonHuyTraHang(generateMaDonHuyTraHang());
        donHuyTraHang.setNgayHuyTraHang(LocalDateTime.now());
        donHuyTraHang.setKhachHang(hoaDon.getKhachHang());
        donHuyTraHang.setHoaDon(hoaDon);

        // 💡 TÍNH TIỀN HOÀN: 0 nếu COD (Cần kiểm tra enum PhuongThucThanhToan)
        double tienHoan = 0.0;
        if (hoaDon.getPhuongThucThanhToan() != PhuongThucThanhToan.COD) {
            // Tạm thời set bằng tổng tiền nếu không phải COD
            tienHoan = hoaDon.getThanhTien();
        }
        donHuyTraHang.setTienHoan(tienHoan);

        // 2. TẠO CHI TIẾT ĐƠN HỦY & HOÀN KHO/ĐIỂM

        // 2.1. HOÀN ĐIỂM (Giữ nguyên logic của bạn)
        // ... (Logic hoàn điểm của bạn)

        // 2.2. TRẢ LẠI TỒN KHO & TẠO CHI TIẾT ĐƠN HỦY
        List<ChiTietDonHuyTraHang> chiTiets = new java.util.ArrayList<>();
        List<ChiTietHoaDon> cthds = chiTietHoaDonRepository.findByHoaDon_MaHoaDon(maHoaDon);

        for (ChiTietHoaDon cthd : cthds) {
            // [LOGIC TRẢ LẠI TỒN KHO (Giữ nguyên logic sửa lỗi cuối cùng của bạn)]
            String maCTSP = cthd.getChiTietSanPham() != null ? cthd.getChiTietSanPham().getMaChiTiet() : null;
            if (maCTSP == null) {
                throw new RuntimeException("Lỗi: CTHD không có ChiTietSanPham liên kết.");
            }
            ChiTietSanPham ctspToUpdate = chiTietSanPhamRepository.findById(maCTSP)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Chi tiết sản phẩm " + maCTSP + " không tồn tại trong kho."));
            ctspToUpdate.setSoLuongTonKho(ctspToUpdate.getSoLuongTonKho() + cthd.getSoLuong());
            chiTietSanPhamRepository.save(ctspToUpdate);

            // TẠO CHI TIẾT ĐƠN HỦY
            ChiTietDonHuyTraHang ctdht = new ChiTietDonHuyTraHang();
            // 💡 TẠO MÃ CHI TIẾT ĐƠN HỦY (Cần hàm generateMaChiTietDonHuyTraHang)
            ctdht.setMaChiTietDonHuyTraHang(generateMaChiTietDonHuyTraHang());
            ctdht.setSoLuong(cthd.getSoLuong());
            ctdht.setTongTien(cthd.getTongTien());
            ctdht.setChiTietSanPham(cthd.getChiTietSanPham());
            ctdht.setDonHuyTraHang(donHuyTraHang);
            chiTiets.add(ctdht);
        }

        // 3. LƯU CÁC THAY ĐỔI
        donHuyTraHangRepository.save(donHuyTraHang);
        chiTietDonHuyTraHangRepository.saveAll(chiTiets);

        // 4. CẬP NHẬT TRẠNG THÁI CUỐI CÙNG
        hoaDon.setTrangThaiHoaDon(TrangThaiHoaDon.DA_HUY);
        return hoaDonRepository.save(hoaDon);
    }

    // 💡 Cần bổ sung các hàm tạo mã tự động:

    private String generateMaDonHuyTraHang() {
        // Implement logic to generate unique MaDonHuyTraHang (e.g., DH[date][sequence])
        // Tương tự hàm generateMaHoaDon trong HoaDonServiceImpl
        return "DH" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")); // Dùng time stamp tạm thời
    }

    private String generateMaChiTietDonHuyTraHang() {
        // Implement logic to generate unique MaChiTietDonHuyTraHang (e.g., CTHDHT[sequence])
        return "CTDHT" + System.currentTimeMillis(); // Dùng time stamp tạm thời
    }
}
