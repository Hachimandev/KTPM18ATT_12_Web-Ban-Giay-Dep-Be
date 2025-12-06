package com.fit.web_ban_giay_dep_be.repository;


import com.fit.web_ban_giay_dep_be.entity.ChiTietHoaDon;
import com.fit.web_ban_giay_dep_be.entity.HoaDon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, String> {
    @Query("SELECT c FROM ChiTietHoaDon c ORDER BY c.maChiTietHoaDon DESC")
    List<ChiTietHoaDon> findAllOrderByMaChiTietHoaDonDesc(Pageable pageable);

    List<ChiTietHoaDon> findByHoaDon_MaHoaDon(String maHoaDon);
}
