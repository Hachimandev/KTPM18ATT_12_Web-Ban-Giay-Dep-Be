package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPham {
    @Id
    private String maChiTiet;
    private String mau;
    private int size;
    private int soLuongTonKho;

    @ManyToOne
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;
}