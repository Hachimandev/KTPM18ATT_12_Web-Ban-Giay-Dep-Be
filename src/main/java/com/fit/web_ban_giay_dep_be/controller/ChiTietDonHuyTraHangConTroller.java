package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.ChiTietDonHuyTraHang;
import com.fit.web_ban_giay_dep_be.service.ChiTietDonHuyTraHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietdonhuytrahang")
public class ChiTietDonHuyTraHangConTroller {

    @Autowired
    private ChiTietDonHuyTraHangService chiTietDonHuyTraHangService;

    @GetMapping
    public ResponseEntity<List<ChiTietDonHuyTraHang>> getAllChiTietDonHuyTraHang(){
        return ResponseEntity.ok(chiTietDonHuyTraHangService.getAllChiTietDonHuyTraHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietDonHuyTraHang> getChiTietDonHuyTraHangById(@PathVariable String id){
        return chiTietDonHuyTraHangService.getChiTietDonHuyTraHangById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietDonHuyTraHang> createChiTietDonHuyTraHang(@RequestBody ChiTietDonHuyTraHang chiTietDonHuyTraHang){
        return ResponseEntity.ok(chiTietDonHuyTraHangService.addChiTietDonHuyTraHang(chiTietDonHuyTraHang));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietDonHuyTraHang> updateChiTietDonHuyTraHang(@PathVariable String id, @RequestBody ChiTietDonHuyTraHang chiTietDonHuyTraHang){
        return ResponseEntity.ok(chiTietDonHuyTraHangService.updateChiTietDonHuyTraHang(id,chiTietDonHuyTraHang));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChiTietDonHuyTraHang(@PathVariable String id){
        chiTietDonHuyTraHangService.deleteChiTietDonHuyTraHang(id);
        return ResponseEntity.noContent().build();
    }


}
