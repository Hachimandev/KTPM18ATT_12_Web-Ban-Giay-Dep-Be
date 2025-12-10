package com.fit.web_ban_giay_dep_be.dto;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String email;
    private String hoTen;
    private String sdt;
    private String diaChi;
}