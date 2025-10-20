package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaiSanPham {
    @Id
    private String maLoai;
    private String tenLoai;

    @OneToMany(mappedBy = "loaiSanPham", cascade = CascadeType.ALL)
    private List<SanPham> sanPhams;
}