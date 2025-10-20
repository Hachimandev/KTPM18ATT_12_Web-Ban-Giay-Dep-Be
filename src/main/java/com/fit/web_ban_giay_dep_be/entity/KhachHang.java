package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {
    @Id
    private String maKhachHang;
    private String hoTen;
    private String email;
    private String sdt;
    private String diaChi;
    private int diemTichLuy;


    @OneToOne
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;


    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    private List<HoaDon> hoaDons;
}
