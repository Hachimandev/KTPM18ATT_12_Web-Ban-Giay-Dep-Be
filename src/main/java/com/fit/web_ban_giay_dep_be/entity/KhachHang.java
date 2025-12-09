package com.fit.web_ban_giay_dep_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime ngayThamGia;
    private double tongChiTieu;


    @OneToOne
    @JoinColumn(name = "maTaiKhoan")
    @JsonIgnore
    private TaiKhoan taiKhoan;


    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<HoaDon> hoaDons;
}
