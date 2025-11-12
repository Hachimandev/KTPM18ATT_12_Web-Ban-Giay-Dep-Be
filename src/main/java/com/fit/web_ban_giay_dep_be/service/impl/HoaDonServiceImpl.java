package com.fit.web_ban_giay_dep_be.service.impl;

import com.fit.web_ban_giay_dep_be.dto.Cart;
import com.fit.web_ban_giay_dep_be.entity.*;
import com.fit.web_ban_giay_dep_be.repository.*;
import com.fit.web_ban_giay_dep_be.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    @Override
    public List<HoaDon> getAllHoaDon() { return hoaDonRepository.findAll(); }

    @Override
    public Optional<HoaDon> getHoaDonById(String id) { return hoaDonRepository.findById(id); }

    @Override
    public HoaDon addHoaDon(HoaDon hoaDon) { return hoaDonRepository.save(hoaDon); }

    @Override
    public HoaDon updateHoaDon(String id, HoaDon hoaDon) {
        hoaDon.setMaHoaDon(id);
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public void deleteHoaDon(String id) { hoaDonRepository.deleteById(id); }

    @Override
    public double calculateFinalPrice(Cart cart) { return 0; }

    @Override
    public Object getCartSummary(Cart cart) { return null; }
}
