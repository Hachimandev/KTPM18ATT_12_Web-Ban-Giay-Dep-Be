package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.DonNhapHang;
import com.fit.web_ban_giay_dep_be.service.DonNhapHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donnhaphang")
public class DonNhapHangController {

    @Autowired
    private DonNhapHangService donNhapHangService;

    // Lấy tất cả đơn nhập hàng
    @GetMapping
    public ResponseEntity<List<DonNhapHang>> getAllDonNhapHang() {
        return ResponseEntity.ok(donNhapHangService.getAllDonNhapHang());
    }

    // Lấy đơn nhập hàng theo ID
    @GetMapping("/{maDonNhap}")
    public ResponseEntity<DonNhapHang> getDonNhapHangById(@PathVariable String maDonNhap) {
        Optional<DonNhapHang> donNhapHang = donNhapHangService.getDonNhapHangById(maDonNhap);
        return donNhapHang.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Thêm mới đơn nhập hàng (ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonNhapHang> createDonNhapHang(@RequestBody DonNhapHang donNhapHang) {
        DonNhapHang created = donNhapHangService.addDonNhapHang(donNhapHang);
        return ResponseEntity.ok(created);
    }

    // Cập nhật đơn nhập hàng (ADMIN)
    @PutMapping("/{maDonNhap}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonNhapHang> updateDonNhapHang(@PathVariable String maDonNhap, @RequestBody DonNhapHang donNhapHang) {
        DonNhapHang updated = donNhapHangService.updateDonNhapHang(maDonNhap, donNhapHang);
        return ResponseEntity.ok(updated);
    }

    // Xóa đơn nhập hàng (ADMIN)
    @DeleteMapping("/{maDonNhap}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDonNhapHang(@PathVariable String maDonNhap) {
        donNhapHangService.deleteDonNhapHang(maDonNhap);
        return ResponseEntity.noContent().build();
    }
}
