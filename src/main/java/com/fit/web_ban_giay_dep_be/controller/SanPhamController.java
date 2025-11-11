package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class SanPhamController {

    private final SanPhamService sanPhamService;


    @GetMapping
    public ResponseEntity<List<SanPham>> getAllSanPham(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(sanPhamService.getAllSanPham(searchTerm, category, brand, sizes, sort, minPrice, maxPrice));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SanPham> getSanPhamById(@PathVariable String id) {
        return sanPhamService.getSanPhamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SanPham> createSanPham(@RequestBody SanPham sanPham) {
        return ResponseEntity.ok(sanPhamService.addSanPham(sanPham));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SanPham> updateSanPham(@PathVariable String id, @RequestBody SanPham sanPham) {
        return ResponseEntity.ok(sanPhamService.updateSanPham(id, sanPham));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSanPham(@PathVariable String id) {
        sanPhamService.deleteSanPham(id);
        return ResponseEntity.noContent().build();
    }
}
