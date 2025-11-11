package com.fit.web_ban_giay_dep_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {
    @Id
    private String maHoaDon;
    private LocalDateTime ngayDat;
    private int diemSuDung;
    private double thanhTien;

    @Enumerated(EnumType.STRING)
    private TrangThaiHoaDon trangThaiHoaDon;

    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    @JsonIgnore
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "maKhuyenMai")
    @JsonIgnore
    private KhuyenMai khuyenMai;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    @JsonIgnore
    private NhanVien nhanVien;

    @OneToOne(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    @JsonIgnore
    private DonHuyTraHang donHuyTraHang;
}