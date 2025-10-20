package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {
    @Id
    private String maNhanVien;
    private String hoTen;
    private String sdt;
    private String cccd;
    private Date ngaySinh;
    @Enumerated(EnumType.STRING)
    private TrangThaiLamViec trangThaiLamViec;

    @Enumerated(EnumType.STRING)
    private GioiTinh gioiTinh;

    @OneToOne
    @JoinColumn(name = "maTaiKhoan")
    private TaiKhoan taiKhoan;


    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private List<KhuyenMai> khuyenMais;


    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private List<HoaDon> hoaDons;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private List<DonNhapHang> donNhapHangs;
}
