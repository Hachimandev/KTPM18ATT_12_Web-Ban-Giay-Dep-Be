package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.KhachHangDTO;
import com.fit.web_ban_giay_dep_be.dto.UpdateKhachHangDTO;
import com.fit.web_ban_giay_dep_be.entity.KhachHang;
import com.fit.web_ban_giay_dep_be.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khachhang")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KhachHangController {

    private final KhachHangService khachHangService;

    @GetMapping("/diem/{username}")
    public int layDiemTichLuy(@PathVariable String username) {
        return khachHangService.layDiemTichLuyTheoUsername(username);
    }

    @GetMapping("/{username}")
    public KhachHang getKhachHang(@PathVariable String username) {
        return khachHangService.findByTaiKhoan_TenDangNhap(username);
    }

    @GetMapping("/info/{username}")
    public KhachHangDTO layThongTinKhachHang(@PathVariable String username) {
        KhachHang kh = khachHangService.findByTaiKhoan_TenDangNhap(username);
        if (kh == null) return null;

        String[] parts = kh.getDiaChi() != null ? kh.getDiaChi().split(",") : new String[0];
        String phuongXa = parts.length > 0 ? parts[0].trim() : "";
        String quanHuyen = parts.length > 1 ? parts[1].trim() : "";
        String tinhThanh = parts.length > 2 ? parts[2].trim() : "";

        return new KhachHangDTO(kh.getHoTen(), kh.getEmail(), kh.getSdt(), phuongXa, quanHuyen, tinhThanh);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> capNhatThongTinKhachHang(
            @PathVariable String username,
            @RequestBody UpdateKhachHangDTO dto
    ) {
        KhachHang kh = khachHangService.findByTaiKhoan_TenDangNhap(username);
        if (kh == null) return ResponseEntity.notFound().build();

        if (dto.getHoTen() != null) kh.setHoTen(dto.getHoTen());
        if (dto.getEmail() != null) kh.setEmail(dto.getEmail());
        if (dto.getSdt() != null) kh.setSdt(dto.getSdt());
        if (dto.getDiaChi() != null && !dto.getDiaChi().isBlank()) kh.setDiaChi(dto.getDiaChi());

        khachHangService.save(kh);
        return ResponseEntity.ok(kh);
    }

}
