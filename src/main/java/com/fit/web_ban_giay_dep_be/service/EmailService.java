package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.HoaDon;

public interface EmailService {
    void sendOrderEmail(String to, HoaDon hoaDon);
}

