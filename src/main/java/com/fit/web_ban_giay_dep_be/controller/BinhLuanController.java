package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.BinhLuanRequest;
import com.fit.web_ban_giay_dep_be.entity.BinhLuan;
import com.fit.web_ban_giay_dep_be.service.BinhLuanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/binhluan")
@RequiredArgsConstructor
public class BinhLuanController {

    private final BinhLuanService binhLuanService;

    @GetMapping("/by-product/{maSanPham}")
    public ResponseEntity<List<BinhLuan>> getCommentsByProductId(@PathVariable String maSanPham) {
        List<BinhLuan> comments = binhLuanService.getCommentsByProductId(maSanPham);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody BinhLuanRequest request) {
        try {
            BinhLuan newComment = binhLuanService.addComment(request);
            return ResponseEntity.status(201).body(newComment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}