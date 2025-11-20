package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.DonHuyTraHang;
import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.service.DonHuyTraHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donhuytrahang")
public class DonHuyTraHangController {

    @Autowired
    private DonHuyTraHangService donHuyTraHangService;

    @GetMapping
    public ResponseEntity<List<DonHuyTraHang>> getAllDonHuyTraHang() { return ResponseEntity.ok(donHuyTraHangService.getAllDonHuyTraHang());}

    @GetMapping("/{id}")
    public ResponseEntity<DonHuyTraHang> getDonHuyTraHangById(@PathVariable String id) {
        return donHuyTraHangService.getDonHuyTraHangById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonHuyTraHang> createDonHuyTraHang(@RequestBody DonHuyTraHang donHuyTraHang){
        return ResponseEntity.ok(donHuyTraHangService.addDonHuyTraHang(donHuyTraHang));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonHuyTraHang> updateDonHuyTraHang(@PathVariable String id, @RequestBody DonHuyTraHang donHuyTraHang){
        return ResponseEntity.ok(donHuyTraHangService.updateDonHuyTraHang(id,donHuyTraHang));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDonHuyTraHang(@PathVariable String id){
        donHuyTraHangService.deleteDonHuyTraHang(id);
        return ResponseEntity.noContent().build();
    }
}
