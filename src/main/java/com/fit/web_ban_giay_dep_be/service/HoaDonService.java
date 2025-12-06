package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.dto.Cart;
import com.fit.web_ban_giay_dep_be.dto.HoaDonResponseDTO;
import com.fit.web_ban_giay_dep_be.dto.OrderRequest;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;
import com.fit.web_ban_giay_dep_be.entity.TrangThaiHoaDon;

import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    List<HoaDon> getAllHoaDon();
    Optional<HoaDon> getHoaDonById(String id);
    HoaDon addHoaDon(HoaDon hoaDon);
    HoaDon updateHoaDon(String id, HoaDon hoaDon);
    void deleteHoaDon(String id);

    double calculateFinalPrice(Cart cart);

    Object getCartSummary(Cart cart);

    HoaDonResponseDTO createHoaDonFromCart(OrderRequest request);
    HoaDon updateOrderStatus(String maHoaDon, TrangThaiHoaDon newStatus);
    String getKhachHangIdByUsername(String username);
    HoaDon handleCancellationRequest(String maHoaDon, boolean approve);
    List<HoaDon> getRecentOrders(int limit);
}
