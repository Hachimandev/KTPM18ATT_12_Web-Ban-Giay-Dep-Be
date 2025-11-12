package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.KhuyenMai;
import java.util.List;

public interface KhuyenMaiService {
    List<KhuyenMai> layTatCaKhuyenMai();
    KhuyenMai LayKhuyenMaiTheoMa(String maKhuyenMai);
}
