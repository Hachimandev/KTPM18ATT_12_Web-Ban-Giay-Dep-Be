package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHuyTraHang {
    @Id
    private String maDonHuyTraHang;
    private LocalDateTime ngayHuyTraHang;
    private double tienHoan;

    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    private KhachHang khachHang;

    @OneToOne
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;

    @OneToMany(mappedBy = "donHuyTraHang", cascade = CascadeType.ALL)
    private List<ChiTietDonHuyTraHang> chiTietDonHuyTraHangList;
}