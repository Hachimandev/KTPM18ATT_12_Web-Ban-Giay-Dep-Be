package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.BinhLuan;
import com.fit.web_ban_giay_dep_be.dto.BinhLuanRequest;

import java.util.List;

public interface BinhLuanService {
    List<BinhLuan> getCommentsByProductId(String maSanPham);
    BinhLuan addComment(BinhLuanRequest request);
    BinhLuan updateComment(Long id, BinhLuanRequest request);
    void deleteComment(Long id);
}