package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.KhuyenMaiRequest;
import com.fit.web_ban_giay_dep_be.entity.KhuyenMai;
import com.fit.web_ban_giay_dep_be.service.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khuyenmai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KhuyenMaiController {

    private final KhuyenMaiService khuyenMaiService;

    @GetMapping
    public List<KhuyenMai> layTatCaKhuyenMai() {
        return khuyenMaiService.layTatCaKhuyenMai();
    }

    @GetMapping("/{maKhuyenMai}")
    public KhuyenMai layKhuyenMaiTheoMa(@PathVariable String maKhuyenMai) {
        return khuyenMaiService.LayKhuyenMaiTheoMa(maKhuyenMai);
    }

    @PostMapping
    public ResponseEntity<?> themKhuyenMai(@RequestBody KhuyenMaiRequest request) {
        try {
            KhuyenMai khuyenMai = khuyenMaiService.themKhuyenMai(request);
            return ResponseEntity.status(201).body(khuyenMai);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{maKhuyenMai}")
    public ResponseEntity<?> capNhatKhuyenMai(@PathVariable String maKhuyenMai, @RequestBody KhuyenMaiRequest request) {
        try {
            KhuyenMai khuyenMai = khuyenMaiService.capNhatKhuyenMai(maKhuyenMai, request);
            return ResponseEntity.ok(khuyenMai);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<List<KhuyenMai>> timKiemKhuyenMai(@RequestParam String tuKhoa) {
        List<KhuyenMai> result = khuyenMaiService.timKiemKhuyenMai(tuKhoa);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/hop-le")
    public ResponseEntity<List<KhuyenMai>> layKhuyenMaiHopLe() {
        List<KhuyenMai> result = khuyenMaiService.layKhuyenMaiHopLe();
        return ResponseEntity.ok(result);
    }
}