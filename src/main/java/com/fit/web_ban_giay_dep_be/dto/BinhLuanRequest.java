package com.fit.web_ban_giay_dep_be.dto;

import lombok.Data;

@Data
public class BinhLuanRequest {
    private String maSanPham;
    private String username;
    private String noiDung;
    private int diemDanhGia;
}