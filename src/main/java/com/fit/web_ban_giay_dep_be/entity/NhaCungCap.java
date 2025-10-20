package com.fit.web_ban_giay_dep_be.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaCungCap {
    @Id
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String sdt;
    private String email;
    private String diaChi;
}