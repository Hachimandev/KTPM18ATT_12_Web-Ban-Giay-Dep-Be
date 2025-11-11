package com.fit.web_ban_giay_dep_be.repository;


import com.fit.web_ban_giay_dep_be.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SanPhamRepository extends JpaRepository<SanPham, String>, JpaSpecificationExecutor<SanPham> {
}
