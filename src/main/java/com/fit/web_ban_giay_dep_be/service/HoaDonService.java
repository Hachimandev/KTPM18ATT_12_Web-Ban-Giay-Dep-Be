package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.dto.Cart;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;
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
}
