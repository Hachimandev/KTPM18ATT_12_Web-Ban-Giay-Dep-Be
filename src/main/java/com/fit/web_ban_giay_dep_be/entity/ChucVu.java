package com.fit.web_ban_giay_dep_be.entity;

public enum ChucVu {
    NhanVien("Nhân viên"),
    ChuyenVien("Chuyên viên"),
    GiamDoc("Giám Đốc"),
    GiamDocDieuHanh("Giám Đốc Điều Hành"),
    PhoPhong("Phó Phòng"),
    TruongPhong("Trưởng phòng");


    private final String displayName;

    ChucVu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
