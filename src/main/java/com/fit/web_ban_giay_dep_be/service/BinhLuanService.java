package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.BinhLuan;
import com.fit.web_ban_giay_dep_be.dto.BinhLuanRequest; // Cần tạo DTO này

import java.util.List;

public interface BinhLuanService {
    List<BinhLuan> getCommentsByProductId(String maSanPham);
    BinhLuan addComment(BinhLuanRequest request);
}