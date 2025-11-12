package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.ChiTietDonNhapHang;
import com.fit.web_ban_giay_dep_be.service.ChiTietDonNhapHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chitietdonnhaphang")
public class ChiTietDonNhapHangController {

    @Autowired
    private ChiTietDonNhapHangService chiTietDonNhapHangService;

    // ✅ Lấy danh sách tất cả chi tiết đơn nhập hàng
    @GetMapping
    public ResponseEntity<List<ChiTietDonNhapHang>> getAllChiTietDonNhapHang() {
        List<ChiTietDonNhapHang> list = chiTietDonNhapHangService.getAllChiTietDonNhapHang();
        return ResponseEntity.ok(list);
    }

    // ✅ Lấy chi tiết đơn nhập hàng theo ID
    @GetMapping("/{maChiTietDonNhapHang}")
    public ResponseEntity<ChiTietDonNhapHang> getChiTietDonNhapHangById(@PathVariable String maChiTietDonNhapHang) {
        Optional<ChiTietDonNhapHang> chiTiet = chiTietDonNhapHangService.getChiTietDonNhapHangById(maChiTietDonNhapHang);
        return chiTiet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Lấy danh sách chi tiết theo mã đơn nhập hàng
    @GetMapping("/donnhaphang/{maDonNhap}")
    public ResponseEntity<List<ChiTietDonNhapHang>> getChiTietByDonNhapHang(@PathVariable String maDonNhap) {
        List<ChiTietDonNhapHang> list = chiTietDonNhapHangService.getChiTietByDonNhapHang(maDonNhap);
        return ResponseEntity.ok(list);
    }

    // ✅ Thêm mới chi tiết đơn nhập hàng (chỉ ADMIN mới được thêm)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietDonNhapHang> createChiTietDonNhapHang(@RequestBody ChiTietDonNhapHang chiTietDonNhapHang) {
        ChiTietDonNhapHang created = chiTietDonNhapHangService.addChiTietDonNhapHang(chiTietDonNhapHang);
        return ResponseEntity.ok(created);
    }

    // ✅ Cập nhật chi tiết đơn nhập hàng
    @PutMapping("/{maChiTietDonNhapHang}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietDonNhapHang> updateChiTietDonNhapHang(@PathVariable String maChiTietDonNhapHang, @RequestBody ChiTietDonNhapHang chiTietDonNhapHang) {
        ChiTietDonNhapHang updated = chiTietDonNhapHangService.updateChiTietDonNhapHang(maChiTietDonNhapHang, chiTietDonNhapHang);
        return ResponseEntity.ok(updated);
    }

    // ✅ Xóa chi tiết đơn nhập hàng
    @DeleteMapping("/{maChiTietDonNhapHang}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChiTietDonNhapHang(@PathVariable String maChiTietDonNhapHang) {
        chiTietDonNhapHangService.deleteChiTietDonNhapHang(maChiTietDonNhapHang);
        return ResponseEntity.noContent().build();
    }
}
