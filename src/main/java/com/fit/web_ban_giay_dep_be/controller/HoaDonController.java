package com.fit.web_ban_giay_dep_be.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fit.web_ban_giay_dep_be.dto.HoaDonResponseDTO;
import com.fit.web_ban_giay_dep_be.dto.OrderRequest;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;
import com.fit.web_ban_giay_dep_be.entity.TrangThaiHoaDon;
import com.fit.web_ban_giay_dep_be.service.DonHuyTraHangService;
import com.fit.web_ban_giay_dep_be.service.HoaDonService;
import com.fit.web_ban_giay_dep_be.service.KhachHangService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hoadon")
@RequiredArgsConstructor
public class HoaDonController {

    private final HoaDonService hoaDonService;
    private final KhachHangService khachHangService;
    private final DonHuyTraHangService donHuyTraHangService;

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

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomerOrder(
            @PathVariable String id,
            @RequestParam String username) {

        return hoaDonService.getAllHoaDon().stream()
                .filter(h -> h.getMaHoaDon().equals(id))
                .filter(h -> h.getKhachHang() != null &&
                        username.equals(h.getKhachHang().getTaiKhoan().getTenDangNhap()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build());
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

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentOrders(@RequestParam(defaultValue = "5") int limit) {
        try {
            return ResponseEntity.ok(hoaDonService.getRecentOrders(limit));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi Server khi tải đơn hàng gần đây.");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> body
    ) {
        try {
            String newStatusStr = body.get("trangThaiHoaDon");
            if (newStatusStr == null) {
                return ResponseEntity.badRequest().body("Missing 'trangThaiHoaDon' in request body");
            }

            TrangThaiHoaDon newStatus;
            try {
                newStatus = TrangThaiHoaDon.valueOf(newStatusStr);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid trangThaiHoaDon value");
            }

            HoaDon updated = hoaDonService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
        try {
            HoaDonResponseDTO hoaDon = hoaDonService.createHoaDonFromCart(request);
            return ResponseEntity.ok(hoaDon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/counts")
    public ResponseEntity<?> getOrderCountsByStatus(@RequestParam(required = false) String username) {
        List<HoaDon> all = hoaDonService.getAllHoaDon();
        if (username != null) {
            all = all.stream()
                    .filter(h -> h.getKhachHang() != null && username.equals(h.getKhachHang().getTaiKhoan().getTenDangNhap()))
                    .toList();
        }

        long choXacNhan = all.stream().filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.CHO_XAC_NHAN).count();
        long dangGiao = all.stream().filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.DANG_GIAO).count();
        long daHuy = all.stream().filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.DA_HUY).count();
        long daGiao = all.stream().filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.DA_GIAO).count();
        long traHang = all.stream().filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.TRA_HANG).count();
        long choHuy = all.stream().filter(h -> h.getTrangThaiHoaDon() == TrangThaiHoaDon.CHO_HUY).count();

        return ResponseEntity.ok(Map.of(
                "CHO_XAC_NHAN", choXacNhan,
                "DANG_GIAO", dangGiao,
                "DA_HUY", daHuy,
                "DA_GIAO", daGiao,
                "TRA_HANG", traHang,
                "CHO_HUY", choHuy
        ));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getOrderHistory(
            @RequestParam(required = false) TrangThaiHoaDon status,
            @RequestParam String username
    ) {
        List<HoaDon> all = hoaDonService.getAllHoaDon();
        List<HoaDon> list = all.stream()
                .filter(h -> h.getKhachHang() != null && username.equals(h.getKhachHang().getTaiKhoan().getTenDangNhap()))
                .toList();

        if (status != null) {
            list = list.stream()
                    .filter(h -> h.getTrangThaiHoaDon() == status)
                    .toList();
        }

        return ResponseEntity.ok(list);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(
            @PathVariable String id,
            @RequestParam String username
    ) {
        try {
            String maKhachHang = khachHangService.getKhachHangIdByUsername(username);

            HoaDon cancelledOrder = donHuyTraHangService.cancelOrder(id, maKhachHang);
            return ResponseEntity.ok(cancelledOrder);

        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("Lỗi 403: Không phải chủ đơn hàng hoặc không có quyền.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Controller
    @PostMapping("/confirm-cancel/{id}")
    public ResponseEntity<?> confirmCancellation(
      @PathVariable String id,
      @RequestBody Map<String, Boolean> body
    ) {
        try {
            boolean approve = body.getOrDefault("approve", false);
            HoaDon updatedOrder = hoaDonService.handleCancellationRequest(id, approve);
            return ResponseEntity.ok(updatedOrder);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi Server không xác định: " + e.getMessage());
        }
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        byte[] excelFile = hoaDonService.exportToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=HoaDon.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);
    }

}
