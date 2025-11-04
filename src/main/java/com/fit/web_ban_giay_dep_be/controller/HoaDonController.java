package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.HoaDon;
import com.fit.web_ban_giay_dep_be.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoadon")
@RequiredArgsConstructor
public class HoaDonController {

    private final HoaDonService hoaDonService;

    @GetMapping
    public ResponseEntity<List<HoaDon>> getAllHoaDon() {
        return ResponseEntity.ok(hoaDonService.getAllHoaDon());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDon> getHoaDonById(@PathVariable String id) {
        return hoaDonService.getHoaDonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HoaDon> createHoaDon(@RequestBody HoaDon hoaDon) {
        return ResponseEntity.ok(hoaDonService.addHoaDon(hoaDon));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HoaDon> updateHoaDon(@PathVariable String id, @RequestBody HoaDon hoaDon) {
        return ResponseEntity.ok(hoaDonService.updateHoaDon(id, hoaDon));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHoaDon(@PathVariable String id) {
        hoaDonService.deleteHoaDon(id);
        return ResponseEntity.noContent().build();
    }
}
