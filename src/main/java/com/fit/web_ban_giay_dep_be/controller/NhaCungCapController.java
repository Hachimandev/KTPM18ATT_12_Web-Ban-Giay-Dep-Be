package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.NhaCungCap;
import com.fit.web_ban_giay_dep_be.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}