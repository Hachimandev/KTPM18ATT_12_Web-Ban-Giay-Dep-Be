package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {
    @Id
    private String maHoaDon;
    private Date ngayDat;
    private int diemSuDung;
    private double thanhTien;

    @Enumerated(EnumType.STRING)
    private TrangThaiHoaDon trangThaiHoaDon;

    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "maKhuyenMai")
    private KhuyenMai khuyenMai;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;

    @OneToOne(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private DonHuyTraHang donHuyTraHang;
}