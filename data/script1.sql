-- *********************************************************************************
-- SETUP: PASSWORD HASH
-- *********************************************************************************

-- Mat khau '123' sau khi hash bang BCrypt
SET @HASHED_PASSWORD = '$2a$10$tMh4.lB67Yv8S8fR/7D2S.gM7N5jA5s4s9iG2H8o9y7r5F.0R6h9y';


-- *********************************************************************************
-- 1. TAI KHOAN VÀ PHÂN QUYỀN
-- *********************************************************************************

-- Bảng TaiKhoan -> tai_khoan
INSERT INTO tai_khoan (ma_tai_khoan, ten_dang_nhap, email, mat_khau) VALUES
('TK001', 'admin', 'admin@shopgiay.com', @HASHED_PASSWORD),
('TK002', 'khachhang', 'khachhang@gmail.com', @HASHED_PASSWORD),
('TK003', 'nhanvien', 'nhanvien@shopgiay.com', @HASHED_PASSWORD);

-- Bảng liên kết TaiKhoan_Role -> tai_khoan_role
INSERT INTO tai_khoan_role (ma_tai_khoan, role) VALUES
('TK001', 'ROLE_ADMIN'),
('TK002', 'ROLE_USER'),
('TK003', 'ROLE_USER');


-- *********************************************************************************
-- 2. LOAI SAN PHAM & NHA CUNG CAP
-- *********************************************************************************

-- Bảng LoaiSanPham -> loai_san_pham
INSERT INTO loai_san_pham (ma_loai, ten_loai) VALUES
('LSP01', 'Giày Sneaker'),
('LSP02', 'Giày Boot'),
('LSP03', 'Giày Cao Gót'),
('LSP04', 'Giày Lười'),
('LSP05', 'Giày Chạy Bộ'),
('LSP06', 'Giày Canvas'),
('LSP07', 'Giày Oxford'),
('LSP08', 'Dép Sandal');

-- Bảng NhaCungCap -> nha_cung_cap
INSERT INTO nha_cung_cap (ma_nha_cung_cap, ten_nha_cung_cap, sdt, email, dia_chi) VALUES
('NCC01', 'Nike Việt Nam', '0281112223', 'contact@nike.vn', 'KCN Long Hậu'),
('NCC02', 'Adidas Official', '0283334445', 'contact@adidas.vn', 'Quận 7, TP HCM');


-- *********************************************************************************
-- 3. SAN PHAM (Liên kết với LoaiSanPham và NhaCungCap)
-- *********************************************************************************

-- Bảng SanPham -> san_pham
INSERT INTO san_pham (ma_san_pham, ten_san_pham, nuoc_san_xuat, mo_ta, gioi_tinh, chat_lieu, thuong_hieu, thue, gia_ban, hinh_anh, ma_nha_cung_cap, ma_loai) VALUES
('SP001', 'Giày Sneaker Trắng XYZ', 'Việt Nam', 'Giày da thể thao phong cách tối giản', 'Nam', 'Da PU cao cấp', 'Nike', 0.10, 899000.00, 'giay-sneaker-trang.png', 'NCC01', 'LSP01'),
('SP002', 'Giày Boot Da Đen', 'Trung Quốc', 'Giày boot cao cổ da thật, phong cách cá tính', 'Nam', 'Da bò thật', 'Dr. Martens', 0.05, 1299000.00, 'giay-boot-da.png', 'NCC01', 'LSP02'),
('SP003', 'Giày Chạy Bộ AirMax', 'Việt Nam', 'Giày chạy bộ chuyên nghiệp, đế Air đàn hồi', 'Nu', 'Mesh & Cao su', 'Nike', 0.00, 1199000.00, 'giay-chay-bo.png', 'NCC02', 'LSP05'),
('SP004', 'Sandal Quai Hậu Nữ', 'Thái Lan', 'Dép sandal quai hậu mềm mại, thoải mái đi chơi', 'Nu', 'Cao su EVA', 'Dior', 0.10, 349000.00, 'sandalnu.jpg', 'NCC02', 'LSP08');
INSERT INTO san_pham (ma_san_pham, ten_san_pham, nuoc_san_xuat, mo_ta, gioi_tinh, chat_lieu, thuong_hieu, thue, gia_ban, hinh_anh, ma_nha_cung_cap, ma_loai) VALUES
('SP005', 'Giày Canvas Trẻ Trung', 'Việt Nam', 'Giày canvas phong cách học sinh, dễ phối đồ', 'Nam', 'Vải Canvas', 'Converse', 0.05, 499000.00, 'giay-canvas.png', 'NCC02', 'LSP06'),
('SP006', 'Giày Cao Gót Quyến Rũ', 'Việt Nam', 'Giày cao gót 7cm tôn dáng, thích hợp dự tiệc', 'Nu', 'Da tổng hợp', 'Gucci', 0.10, 799000.00, 'giay-caogot.png', 'NCC02', 'LSP03'),
('SP007', 'Giày Lười Công Sở', 'Việt Nam', 'Giày lười da bò, tiện lợi và thanh lịch cho dân văn phòng', 'Nam', 'Da bò thật', 'Pedro', 0.05, 899000.00, 'giay-luoi.png', 'NCC01', 'LSP04'),
('SP008', 'Giày Oxford Cổ Điển', 'Anh', 'Giày Oxford phong cách châu Âu, dành cho quý ông lịch lãm', 'Nam', 'Da bò thật', 'Clarks', 0.10, 1099000.00, 'giay-oxford.png', 'NCC01', 'LSP07'),
('SP009', 'Sandal Ngang Nam Thể Thao', 'Thái Lan', 'Dép sandal quai ngang thể thao, bền và thoáng mát', 'Nam', 'Cao su tổng hợp', 'Adidas', 0.00, 299000.00, 'sandalngang.jpg', 'NCC02', 'LSP08'),
('SP010', 'Sandal Nữ Đi Biển', 'Thái Lan', 'Sandal nữ đế thấp chống trơn, phù hợp đi biển mùa hè', 'Nu', 'Nhựa dẻo', 'Havaianas', 0.00, 279000.00, 'sandal3.jpg', 'NCC02', 'LSP08');

-- Bảng liên kết SanPham_NhaCungCap -> san_pham_nha_cung_cap
INSERT INTO san_pham_nha_cung_cap (ma_san_pham, ma_nha_cung_cap) VALUES
('SP001', 'NCC01'),
('SP003', 'NCC02');


-- *********************************************************************************
-- 4. CHI TIET SAN PHAM (Biến thể của SP)
-- *********************************************************************************

-- Bảng ChiTietSanPham -> chi_tiet_san_pham
INSERT INTO chi_tiet_san_pham (ma_chi_tiet, mau, size, so_luong_ton_kho, ma_san_pham) VALUES
('CT001', '#FFFFFF', 39, 10, 'SP001'),
('CT002', '#FFFFFF', 40, 15, 'SP001'),
('CT003', '#000000', 41, 5, 'SP002'),
('CT004', '#000000', 42, 8, 'SP002'),
('CT005', '#000000', 38, 20, 'SP003'),
('CT006', '#000000', 39, 10, 'SP003');
INSERT INTO chi_tiet_san_pham (ma_chi_tiet, mau, size, so_luong_ton_kho, ma_san_pham) VALUES
('CT007', '#000000', 40, 12, 'SP005'),
('CT008', '#FFFFFF', 41, 8, 'SP005'),

('CT009', '#FF69B4', 36, 10, 'SP006'),
('CT010', '#FF69B4', 37, 12, 'SP006'),

('CT011', '#8B4513', 40, 15, 'SP007'),
('CT012', '#000000', 41, 10, 'SP007'),

('CT013', '#654321', 41, 8, 'SP008'),
('CT014', '#000000', 42, 6, 'SP008'),

('CT015', '#000000', 40, 20, 'SP009'),
('CT016', '#1E90FF', 41, 18, 'SP009'),

('CT017', '#FFB6C1', 36, 10, 'SP010'),
('CT018', '#FFD700', 37, 12, 'SP010');


-- *********************************************************************************
-- 5. NHAN VIEN & KHACH HANG
-- *********************************************************************************

-- Bảng NhanVien -> nhan_vien
-- Sử dụng giá trị Enum (DangLam, Nam)
INSERT INTO nhan_vien (ma_nhan_vien, ho_ten, sdt, cccd, ngay_sinh, trang_thai_lam_viec, gioi_tinh, ma_tai_khoan) VALUES
('NV001', 'Lưu Ngọc Cao Sơn', '0901234567', '123456789', '1995-05-10', 'DangLam', 'Nam', 'TK001');

-- Bảng KhachHang -> khach_hang
INSERT INTO khach_hang (ma_khach_hang, ho_ten, email, sdt, dia_chi, diem_tich_luy, ma_tai_khoan) VALUES
('KH001', 'Nguyễn Thị B', 'khachhang@gmail.com', '0987654321', '123 Đường Nguyễn Huệ, Q1', 50, 'TK002');


-- *********************************************************************************
-- 6. HOA DON (Hóa đơn mẫu)
-- *********************************************************************************

-- Bảng HoaDon -> hoa_don
-- Sử dụng giá trị Enum (DA_GIAO)
INSERT INTO hoa_don (ma_hoa_don, ngay_dat, diem_su_dung, thanh_tien, trang_thai_hoa_don, ma_khach_hang, ma_nhan_vien) VALUES
('HD001', NOW(), 0, 2098000.00, 'DA_GIAO', 'KH001', 'NV001');

-- Bảng ChiTietHoaDon -> chi_tiet_hoa_don
INSERT INTO chi_tiet_hoa_don (ma_chi_tiet_hoa_don, so_luong, tong_tien, ma_hoa_don, ma_san_pham, ma_chi_tiet_san_pham) VALUES
('CTHD001', 1, 899000.00, 'HD001', 'SP001', 'CT001'),  -- SP001 size 39
('CTHD002', 1, 1199000.00, 'HD001', 'SP003', 'CT005'); -- SP003 size 38