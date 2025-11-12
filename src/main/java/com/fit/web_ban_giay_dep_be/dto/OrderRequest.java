package com.fit.web_ban_giay_dep_be.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private UserInfo userInfo;
    private Cart cart;
    private double thanhTien;
}
