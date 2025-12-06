package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.ChartDataDTO;
import com.fit.web_ban_giay_dep_be.dto.GlobalStatsDTO;
import com.fit.web_ban_giay_dep_be.entity.ChiTietHoaDon;
import com.fit.web_ban_giay_dep_be.entity.GioiTinh;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;
import com.fit.web_ban_giay_dep_be.entity.TrangThaiHoaDon;
import com.fit.web_ban_giay_dep_be.repository.*;
import com.fit.web_ban_giay_dep_be.service.StatsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HoaDonRepository hoaDonRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;

    @Override
    public GlobalStatsDTO getGlobalStats() {
        List<HoaDon> allOrders = hoaDonRepository.findAll();

        long totalRevenue = (long) allOrders.stream()
                .filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.DA_GIAO)
                .mapToDouble(HoaDon::getThanhTien)
                .sum();

        long totalOrders = allOrders.stream()
                .filter(h -> h.getTrangThaiHoaDon() != TrangThaiHoaDon.DA_HUY)
                .count();

        long totalStock = chiTietSanPhamRepository.findAll().stream()
                .mapToLong(ctsp -> ctsp.getSoLuongTonKho() < 0 ? 0 : ctsp.getSoLuongTonKho())
                .sum();

        long totalCustomers = khachHangRepository.count();

        return GlobalStatsDTO.builder()
                .totalRevenue(totalRevenue)
                .totalOrders(totalOrders)
                .totalStock(totalStock)
                .totalCustomers(totalCustomers)
                .build();
    }

    @Override
    public ChartDataDTO getMonthlySalesData() {
        List<HoaDon> deliveredOrders = hoaDonRepository.findAll().stream()
                .filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.DA_GIAO &&
                        h.getNgayDat().getYear() == Year.now().getValue())
                .toList();

        Map<Month, Double> salesByMonth = deliveredOrders.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getNgayDat().getMonth(),
                        Collectors.summingDouble(HoaDon::getThanhTien)
                ));

        List<String> labels = new ArrayList<>();
        List<Number> data = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            Month month = Month.of(i);
            labels.add("Tháng " + i);
            data.add(salesByMonth.getOrDefault(month, 0.0));
        }

        return ChartDataDTO.builder()
                .labels(labels)
                .data(data)
                .build();
    }

    @Override
    public ChartDataDTO getOrdersByCategoryData() {
        List<HoaDon> validOrders = hoaDonRepository.findAll().stream()
                .filter(h -> h.getTrangThaiHoaDon() != TrangThaiHoaDon.DA_HUY &&
                        h.getTrangThaiHoaDon() != TrangThaiHoaDon.CHO_HUY)
                .toList();

        List<String> validOrderIds = validOrders.stream().map(HoaDon::getMaHoaDon).toList();
        List<ChiTietHoaDon> allCthd = chiTietHoaDonRepository.findAll().stream()
                .filter(c -> validOrderIds.contains(c.getHoaDon().getMaHoaDon()))
                .toList();

        Map<String, Long> quantityByLoaiSanPham = allCthd.stream()
                .filter(c -> c.getSanPham() != null && c.getSanPham().getLoaiSanPham() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getSanPham().getLoaiSanPham().getTenLoai(),
                        Collectors.summingLong(ChiTietHoaDon::getSoLuong)
                ));

        List<String> labels = new ArrayList<>(quantityByLoaiSanPham.keySet());
        List<Number> data = new ArrayList<>(quantityByLoaiSanPham.values());

        return ChartDataDTO.builder()
                .labels(labels)
                .data(data)
                .build();
    }
    @Override
    public void exportMonthlySalesToExcel(HttpServletResponse response) throws IOException {

        ChartDataDTO salesData = getMonthlySalesData();
        List<String> labels = salesData.getLabels();
        List<Number> data = salesData.getData();

        try (Workbook workbook = new XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("DoanhThu");

            org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Tháng");
            header.createCell(1).setCellValue("Doanh Thu (VNĐ)");

            for (int i = 0; i < labels.size(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(labels.get(i));
                // Chuyển Number sang Double để ghi vào Excel
                row.createCell(1).setCellValue(data.get(i).doubleValue());
            }

            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi dữ liệu báo cáo Excel.", e);
        }
    }
}