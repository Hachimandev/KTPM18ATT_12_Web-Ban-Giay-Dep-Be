package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.ChartDataDTO;
import com.fit.web_ban_giay_dep_be.dto.GlobalStatsDTO;
import com.fit.web_ban_giay_dep_be.entity.ChiTietHoaDon;
import com.fit.web_ban_giay_dep_be.entity.GioiTinh;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;
import com.fit.web_ban_giay_dep_be.entity.TrangThaiHoaDon;
import com.fit.web_ban_giay_dep_be.repository.*;
import com.fit.web_ban_giay_dep_be.service.StatsService;
import com.lowagie.text.pdf.BaseFont;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
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
    public void exportComprehensiveStatsToExcel(HttpServletResponse response) throws IOException {

        GlobalStatsDTO globalStats = getGlobalStats();
        ChartDataDTO salesData = getMonthlySalesData();
        List<HoaDon> allOrders = hoaDonRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {

            createGlobalStatsSheet(workbook, globalStats);

            createMonthlySalesSheet(workbook, salesData);

            createAllOrdersSheet(workbook, allOrders);

            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi dữ liệu báo cáo Excel.", e);
        }
    }
    private void createGlobalStatsSheet(Workbook workbook, GlobalStatsDTO stats) {
        Sheet sheet = workbook.createSheet("TongQuan");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Chỉ số");
        header.createCell(1).setCellValue("Giá trị");

        int rowNum = 1;

        Row r1 = sheet.createRow(rowNum++);
        r1.createCell(0).setCellValue("Tổng Doanh Thu");
        r1.createCell(1).setCellValue(stats.getTotalRevenue());

        Row r2 = sheet.createRow(rowNum++);
        r2.createCell(0).setCellValue("Tổng Đơn Hàng");
        r2.createCell(1).setCellValue(stats.getTotalOrders());

        Row r3 = sheet.createRow(rowNum++);
        r3.createCell(0).setCellValue("Tổng Tồn Kho");
        r3.createCell(1).setCellValue(stats.getTotalStock());

        Row r4 = sheet.createRow(rowNum++);
        r4.createCell(0).setCellValue("Tổng Khách Hàng");
        r4.createCell(1).setCellValue(stats.getTotalCustomers());

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
    private void createMonthlySalesSheet(Workbook workbook, ChartDataDTO salesData) {
        Sheet sheet = workbook.createSheet("DoanhThuThang");
        List<String> labels = salesData.getLabels();
        List<Number> data = salesData.getData();

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Tháng");
        header.createCell(1).setCellValue("Doanh Thu (VNĐ)");

        for (int i = 0; i < labels.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(labels.get(i));
            row.createCell(1).setCellValue(data.get(i).doubleValue());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }

    private void createAllOrdersSheet(Workbook workbook, List<HoaDon> orders) {
        Sheet sheet = workbook.createSheet("DanhSachDonHang");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Mã HĐ");
        header.createCell(1).setCellValue("Ngày Đặt");
        header.createCell(2).setCellValue("Khách Hàng");
        header.createCell(3).setCellValue("SĐT");
        header.createCell(4).setCellValue("Tổng Tiền");
        header.createCell(5).setCellValue("Trạng Thái");

        int rowNum = 1;
        for (HoaDon hd : orders) {
            Row row = sheet.createRow(rowNum++);

            String khachHangName = (hd.getKhachHang() != null) ? hd.getKhachHang().getHoTen() : "Khách vãng lai";
            String khachHangPhone = (hd.getKhachHang() != null && hd.getKhachHang().getSdt() != null) ? hd.getKhachHang().getSdt() : "";

            row.createCell(0).setCellValue(hd.getMaHoaDon());
            row.createCell(1).setCellValue(hd.getNgayDat().toString());
            row.createCell(2).setCellValue(khachHangName);
            row.createCell(3).setCellValue(khachHangPhone);
            row.createCell(4).setCellValue(hd.getThanhTien());
            row.createCell(5).setCellValue(hd.getTrangThaiHoaDon().toString());
        }

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}