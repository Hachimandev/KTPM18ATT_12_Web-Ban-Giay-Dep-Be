package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.dto.ChartDataDTO;
import com.fit.web_ban_giay_dep_be.dto.GlobalStatsDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface StatsService {
    GlobalStatsDTO getGlobalStats();
    ChartDataDTO getMonthlySalesData();
    ChartDataDTO getOrdersByCategoryData();
    void exportMonthlySalesToExcel(HttpServletResponse response) throws IOException; // 💡 Phương thức mới
}