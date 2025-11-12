package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.entity.KhuyenMai;
import com.fit.web_ban_giay_dep_be.service.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
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
}
