package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.entity.*;
import com.fit.web_ban_giay_dep_be.repository.*;
import com.fit.web_ban_giay_dep_be.service.DonHuyTraHangService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Sửa donHuyTraHangService.cancelOrder (Logic gốc của Khách hàng)
    @Override
    @Transactional
    public HoaDon cancelOrder(String maHoaDon, String maKhachHang) {
        HoaDon hoaDon = hoaDonRepository.findById(maHoaDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn " + maHoaDon));

        TrangThaiHoaDon currentStatus = hoaDon.getTrangThaiHoaDon();

        // KIỂM TRA QUYỀN VÀ TRẠNG THÁI KHÁCH HÀNG
        if (currentStatus == TrangThaiHoaDon.CHO_XAC_NHAN) {
            // 💡 Dùng cho KHÁCH TỰ HỦY: Phải kiểm tra quyền sở hữu
            if (!hoaDon.getKhachHang().getMaKhachHang().equals(maKhachHang)) {
                throw new SecurityException("Bạn không có quyền hủy đơn hàng này.");
            }
            // Tiếp tục HỦY ngay (nhảy xuống logic hoàn kho)
        }
        else if (currentStatus == TrangThaiHoaDon.DANG_GIAO) {
            // KHÁCH YÊU CẦU HỦY: Chuyển sang CHO_HUY
            if (!hoaDon.getKhachHang().getMaKhachHang().equals(maKhachHang)) {
                throw new SecurityException("Bạn không có quyền gửi yêu cầu hủy đơn này.");
            }
            hoaDon.setTrangThaiHoaDon(TrangThaiHoaDon.CHO_HUY);
            return hoaDonRepository.save(hoaDon);
        }
        // LỖI XẢY RA Ở ĐÂY: Nếu trạng thái là CHO_HUY (Admin đang gọi), ta không nên ném exception.
        else if (currentStatus != TrangThaiHoaDon.CHO_HUY) {
            throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái hiện tại (" + currentStatus + ").");
        }

        // --- LOGIC HOÀN KHO & HOÀN ĐIỂM ---
        // Logic này sẽ chạy nếu trạng thái là CHO_XAC_NHAN (sau khi kiểm tra quyền) HOẶC CHO_HUY (Admin gọi)

        // ... (Hoàn điểm và Trả lại tồn kho)
        // Tôi sẽ chỉ tập trung vào phần tồn kho vì nó gây lỗi 400/500

        // TRẢ LẠI TỒN KHO (Tối ưu hóa tránh lỗi NullPointer)
        List<ChiTietHoaDon> cthds = chiTietHoaDonRepository.findByHoaDon_MaHoaDon(maHoaDon);

        for (ChiTietHoaDon cthd : cthds) {
            // 1. Lấy mã CTHD an toàn
            String maCTSP = cthd.getChiTietSanPham() != null ? cthd.getChiTietSanPham().getMaChiTiet() : null;

            if (maCTSP == null) {
                throw new RuntimeException("Lỗi: CTHD không có ChiTietSanPham liên kết.");
            }

            // 2. Tải lại ChiTietSanPham (Cần thiết để tránh lỗi Lazy Loading/Context)
            ChiTietSanPham ctspToUpdate = chiTietSanPhamRepository.findById(maCTSP)
                    .orElseThrow(() -> new RuntimeException("Lỗi: Chi tiết sản phẩm " + maCTSP + " không tồn tại trong kho."));

            // 3. Cập nhật và lưu
            ctspToUpdate.setSoLuongTonKho(ctspToUpdate.getSoLuongTonKho() + cthd.getSoLuong());
            chiTietSanPhamRepository.save(ctspToUpdate); // Nếu lỗi 500 xảy ra ở đây -> Lỗi ràng buộc DB
        }

        // ... (Cập nhật trạng thái DA_HUY)
        hoaDon.setTrangThaiHoaDon(TrangThaiHoaDon.DA_HUY);
        return hoaDonRepository.save(hoaDon);
    }
}
