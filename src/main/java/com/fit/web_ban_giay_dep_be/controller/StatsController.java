package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.ChartDataDTO;
import com.fit.web_ban_giay_dep_be.dto.GlobalStatsDTO;
import com.fit.web_ban_giay_dep_be.service.HoaDonService;
import com.fit.web_ban_giay_dep_be.service.StatsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;
    private final HoaDonService hoaDonService;

    @GetMapping("/global")
    public ResponseEntity<GlobalStatsDTO> getGlobalStats() {
        return ResponseEntity.ok(statsService.getGlobalStats());
    }

    @GetMapping("/sales-monthly")
    public ResponseEntity<ChartDataDTO> getMonthlySalesData() {
        return ResponseEntity.ok(statsService.getMonthlySalesData());
    }

    @GetMapping("/orders-by-category")
    public ResponseEntity<ChartDataDTO> getOrdersByCategoryData() {
        return ResponseEntity.ok(statsService.getOrdersByCategoryData());
    }

    @GetMapping("/export/comprehensive-stats")
    public void exportComprehensiveStats(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Bao_cao_tong_hop.xlsx";

        response.setHeader(headerKey, headerValue);

        try {
            statsService.exportComprehensiveStatsToExcel(response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi khi tạo file báo cáo: " + e.getMessage());
        }
    }
}