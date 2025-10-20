package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonNhap {
    @Id
    private String maChiTietDonNhap;
    private int soLuong;
    private double giaNhap;

    @ManyToOne
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "maDonNhap")
    private DonNhapHang donNhapHang;
}