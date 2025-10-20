package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {
    @Id
    private String maSanPham;
    private String tenSanPham;
    private String nuocSanXuat;
    private String moTa;
    private String chatLieu;
    private String thuongHieu;
    private double thue;
    private double giaBan;
    private String hinhAnh;

    @ManyToOne
    @JoinColumn(name = "maNhaCungCap")
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "maLoai")
    private LoaiSanPham loaiSanPham;


    @ManyToMany
    @JoinTable(
            name = "SanPham_NhaCungCap",
            joinColumns = @JoinColumn(name = "maSanPham"),
            inverseJoinColumns = @JoinColumn(name = "maNhaCungCap")
    )
    private List<NhaCungCap> nhaCungCaps;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<ChiTietSanPham> chiTietSanPhams;
}