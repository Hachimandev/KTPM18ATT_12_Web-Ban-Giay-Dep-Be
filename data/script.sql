
-- =========================================================
-- 1️⃣ TAIKHOAN
-- =========================================================
INSERT INTO tai_khoan (ma_tai_khoan, email, ten_dang_nhap, mat_khau ) VALUES
                                                                  ('TK002', 'quyen@gmai.com','user1', '123456');




INSERT INTO tai_khoan_role(ma_tai_khoan, role) VALUES
    ('TK002', 'ROLE_USER');
-- =========================================================
-- 2️⃣ KHACHHANG
-- =========================================================
INSERT INTO khach_hang (ma_khach_hang, ho_ten, email, sdt, dia_chi, diem_tich_luy, ma_tai_Khoan) VALUES
                                                                                            ('KH001', 'Nguyen Van A', 'vana@gmail.com', '0909123456', 'TP. HCM', 100, 'TK002'),
                                                                                            ('KH003', 'Le Van C', 'vanc@gmail.com', '0988777666', 'Da Nang', 20, NULL);

-- =========================================================
-- 3️⃣ NHACUNGCAP
-- =========================================================
INSERT INTO nha_cung_cap (ma_nha_cung_cap, ten_nha_cung_cap, sdt, email, dia_chi) VALUES
                                                                             ('NCC001', 'Nike VN', '0909090909', 'contact@nike.vn', 'HCM'),
                                                                             ('NCC002', 'Adidas VN', '0939393939', 'info@adidas.vn', 'Ha Noi'),
                                                                             ('NCC003', 'Puma VN', '0966666666', 'support@puma.vn', 'Da Nang');

-- =========================================================
-- 4️⃣ LOAISANPHAM
-- =========================================================
INSERT INTO loai_san_pham (ma_loai, ten_loai) VALUES
                                              ('L001', 'Giày Thể Thao'),
                                              ('L002', 'Dép Sandal'),
                                              ('L003', 'Giày Tây');

-- =========================================================
-- 5️⃣ NHANVIEN
-- =========================================================
INSERT INTO nhan_vien (ma_nhan_vien, ho_ten, sdt, cccd, ngay_sinh, trang_thai_lam_viec, gioi_tinh, ma_tai_khoan) VALUES
                                                                                                          ('NV001', 'Pham Van D', '0909555111', '123456789', '1995-05-05', 'DangLam', 'Nam', 'TK002');

-- =========================================================
-- 6️⃣ KHUYENMAI
-- =========================================================
INSERT INTO khuyen_mai (ma_khuyen_mai, ngay_bat_dau, ngay_ket_thuc, dieu_kien, chiet_khau, ma_nhan_vien) VALUES
                                                                                                  ('KM001', '2025-10-01', '2025-10-31', 'Đơn trên 500k', 0.10, 'NV001'),
                                                                                                  ('KM002', '2025-12-01', '2025-12-31', 'Đơn trên 2 triệu', 0.20, 'NV001');

-- =========================================================
-- 7️⃣ SANPHAM
-- =========================================================
INSERT INTO san_pham (ma_san_pham, ten_san_pham, nuoc_san_xuat, mo_ta, chat_lieu, thuong_hieu, thue, gia_ban, hinh_anh, ma_loai, ma_nha_cung_cap) VALUES
                                                                                                                                      ('SP001', 'Nike Air Zoom', 'Việt Nam', 'Giày chạy bộ cao cấp', 'Da tổng hợp', 'Nike', 0.05, 2500000, 'nike_air_zoom.jpg', 'L001', 'NCC001'),
                                                                                                                                      ('SP002', 'Adidas UltraBoost', 'Việt Nam', 'Giày thể thao đệm khí', 'Vải dệt', 'Adidas', 0.05, 3200000, 'ultraboost.jpg', 'L001', 'NCC002'),
                                                                                                                                      ('SP003', 'Puma Slide', 'Trung Quốc', 'Dép sandal thời trang', 'Cao su', 'Puma', 0.02, 500000, 'puma_slide.jpg', 'L002', 'NCC003'),
                                                                                                                                      ('SP004', 'Giày Tây Nam Da Bò', 'Việt Nam', 'Giày tây công sở', 'Da thật', 'Local Brand', 0.05, 1500000, 'giaytay.jpg', 'L003', 'NCC001');

-- =========================================================
-- 8️⃣ SANPHAM_NHACUNGCAP
-- =========================================================
INSERT INTO san_pham_nha_cung_cap (ma_san_pham, ma_nha_cung_cap) VALUES
                                                             ('SP001', 'NCC001'),
                                                             ('SP002', 'NCC002'),
                                                             ('SP003', 'NCC003'),
                                                             ('SP004', 'NCC001'),
                                                             ('SP002', 'NCC001');

-- =========================================================
-- 9️⃣ CHITIETSANPHAM
-- =========================================================
INSERT INTO chi_tiet_san_pham (ma_chi_tiet, mau, size, so_luong_ton_kho, ma_san_pham) VALUES
                                                                                ('CT001', 'Đen', 41, 20, 'SP001'),
                                                                                ('CT002', 'Trắng', 42, 15, 'SP002'),
                                                                                ('CT003', 'Xanh', 40, 10, 'SP003'),
                                                                                ('CT004', 'Nâu', 41, 5, 'SP004');

-- =========================================================
-- 🔟 HOADON
-- =========================================================
INSERT INTO hoa_don (ma_hoa_don, ngay_dat, diem_su_dung, thanh_tien, trang_thai_hoa_don, ma_khach_hang, ma_khuyen_mai, ma_nhan_vien) VALUES
                                                                                                                         ('HD001', '2025-10-10 00:00:00', 10, 2700000, 'DA_GIAO', 'KH001', 'KM001', 'NV001'),
                                                                                                                         ('HD002', '2025-10-15 00:00:00', 0, 3500000, 'CHO_XAC_NHAN', 'KH003', NULL, 'NV001'),
                                                                                                                         ('HD003', '2025-10-20 00:00:00', 5, 500000, 'DA_GIAO', 'KH001', 'KM002', 'NV001');

-- =========================================================
-- 11️⃣ CHITIETHOADON
-- =========================================================
INSERT INTO chi_tiet_hoa_don (ma_chi_tiet_hoa_don, so_luong, tong_tien, ma_hoa_don, ma_san_pham, ma_chi_tiet_san_pham) VALUES
                                                                                                          ('CTHD001', 1, 2500000, 'HD001', 'SP001', 'CT001'),
                                                                                                          ('CTHD002', 1, 500000, 'HD003', 'SP003', 'CT003'),
                                                                                                          ('CTHD003', 1, 3200000, 'HD002', 'SP002', 'CT002');

-- =========================================================
-- 12️⃣ DONHUYTRAHANG
-- =========================================================
INSERT INTO don_huy_tra_hang (ma_don_huy_tra_hang, ngay_huy_tra_hang, tien_hoan, ma_khach_hang, ma_hoa_don) VALUES
                                                                                                 ('DHT001', '2025-10-18 00:00:00', 2500000, 'KH001', 'HD001'),
                                                                                                 ('DHT002', '2025-10-21 00:00:00', 500000, 'KH001', 'HD003');

-- =========================================================
-- 13️⃣ CHITIETDONHUYTRAHANG
-- =========================================================
INSERT INTO chi_tiet_don_huy_tra_hang (ma_chi_tiet_don_huy_tra_hang, so_luong, tong_tien, ma_don_huy_tra_hang, ma_san_pham, ma_chi_tiet_san_pham) VALUES
                                                                                                                               ('CTDHT001', 1, 2500000, 'DHT001', 'SP001', 'CT001'),
                                                                                                                               ('CTDHT002', 1, 500000, 'DHT002', 'SP003', 'CT003');

-- =========================================================
-- 14️⃣ DONNHAPHANG
-- =========================================================
INSERT INTO don_nhap_hang (ma_don_nhap, ngay_nhap, ma_nhan_vien) VALUES
                                                              ('DN001', '2025-09-01', 'NV001'),
                                                              ('DN002', '2025-09-10', 'NV001');

-- =========================================================
-- 15️⃣ CHITIETDONNHAP
-- =========================================================
INSERT INTO chi_tiet_don_nhap (ma_chi_tiet_don_nhap, so_luong, gia_nhap, ma_san_pham, ma_don_nhap) VALUES
                                                                                          ('CTDN001', 50, 1800000, 'SP001', 'DN001'),
                                                                                          ('CTDN002', 40, 2500000, 'SP002', 'DN002'),
                                                                                          ('CTDN003', 30, 300000, 'SP003', 'DN001'),
                                                                                          ('CTDN004', 20, 1100000, 'SP004', 'DN002');
