package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.NhaCungCap;
import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class NhaCungCapController {

    private final NhaCungCapService service;

    @GetMapping
    public ResponseEntity<List<NhaCungCap>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhaCungCap> getNhaCungCapById(@PathVariable String id) {
        return service.getNhaCungCapById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NhaCungCap> createNhaCungCap(@RequestBody NhaCungCap nhaCungCap) {
        return ResponseEntity.ok(service.addNhaCungCap(nhaCungCap));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhaCungCap> updateNhaCungCap(
            @PathVariable String id,
            @RequestBody NhaCungCap nhaCungCap
    ) {
        return ResponseEntity.ok(service.updateNhaCungCap(id, nhaCungCap));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhaCungCap(@PathVariable String id) {
        service.deleteNhaCungCap(id);
        return ResponseEntity.noContent().build();
    }
}