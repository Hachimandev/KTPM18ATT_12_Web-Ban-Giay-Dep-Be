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
public class KhuyenMai {
    @Id
    private String maKhuyenMai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String dieuKien;
    private double chietKhau;

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;


    @OneToMany(mappedBy = "khuyenMai")
    private List<HoaDon> hoaDons;
}
