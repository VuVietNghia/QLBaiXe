CREATE DATABASE QL_bai_xe_ktx
GO
USE QL_bai_xe_ktx
GO
CREATE TABLE nguoi_dung(
	id INT PRIMARY KEY IDENTITY(1,1),
	username VARCHAR(50),
	password VARCHAR(30),
	fullname NVARCHAR(100),
	email VARCHAR(50),
	vai_tro NVARCHAR(20),
	ngay_dang_ky DATETIME DEFAULT GETDATE(),
	mo_ta NVARCHAR(50)
)
GO
CREATE TABLE chuc_vu(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_chuc_vu NVARCHAR(30),
	ten_chuc_vu NVARCHAR(50),
	mo_ta NVARCHAR(50)
)
GO
CREATE TABLE ql_nhan_vien(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_nhan_vien NVARCHAR(20),
	ho_ten_nhan_vien NVARCHAR(50),
	ngay_sinh DATE,
	gioi_tinh NVARCHAR(5),
	sdt VARCHAR(20),
	email VARCHAR(50),
	id_chuc_vu INT,
	anh_nhan_vien NVARCHAR(MAX),
	thoi_gian_bat_dau_lam DATE DEFAULT GETDATE(),
	trang_thai NVARCHAR(30),
	FOREIGN KEY (id_chuc_vu) REFERENCES chuc_vu(id) ON DELETE CASCADE
)
GO
CREATE TABLE bai_xe(
	id INT  PRIMARY KEY IDENTITY(1,1),
	ma_khu_vuc VARCHAR(20),
	ten_khu_vuc NVARCHAR(50),
	suc_chua_toi_da INT,
	dia_chi NVARCHAR(250),
	trang_thai NVARCHAR(50)
)
GO
CREATE TABLE ql_bai_xe(
	id INT  PRIMARY KEY IDENTITY(1,1),
	id_bai_xe INT,
	id_nhan_vien INT, --ma_nv, ho_ten_nv--
	sl_xe_trong_bai INT,
	sl_cho_trong INT,
	trang_thai_bai NVARCHAR(20),
	FOREIGN KEY (id_nhan_vien) REFERENCES ql_nhan_vien(id) ON DELETE CASCADE,
	FOREIGN KEY (id_bai_xe) REFERENCES bai_xe(id) ON DELETE CASCADE
)
GO
CREATE TABLE loai_ve(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_loai_ve VARCHAR(20),
	ten_loai_ve NVARCHAR(50),
	trang_thai NVARCHAR(50)
)
GO
CREATE TABLE ql_khach_hang(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_khach_hang NVARCHAR(20),
	ho_ten_khach_hang NVARCHAR(50),
	so_cccd VARCHAR(20),
	ngay_sinh DATE,
	sdt_kh VARCHAR(20),
	email NVARCHAR(50),
	ngay_dang_ky DATETIME DEFAULT GETDATE(),
	trang_thai NVARCHAR(50),
)
GO
CREATE TABLE loai_phuong_tien(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_phuong_tien VARCHAR(20),
	ten_loai_phuong_tien NVARCHAR(50),
	mo_ta_pt NVARCHAR(100)
)
CREATE TABLE ql_ve_xe(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_ve VARCHAR(20),
	id_loai_ve INT, --Tên loại vé--
	ten_khach_hang NVARCHAR(100),
	bien_so_xe NVARCHAR(20),
	id_loai_pt INT,
	don_gia FLOAT,
	ngay_dang_ky DATETIME DEFAULT GETDATE(),
	ngay_het_han DATE,
	id_ql_bai INT, -- tên khu vực--
	trang_thai_ve NVARCHAR(20),
	FOREIGN KEY (id_loai_ve) REFERENCES loai_ve(id) ON DELETE CASCADE,
	FOREIGN KEY (id_ql_bai) REFERENCES ql_bai_xe(id) ON DELETE CASCADE,
	FOREIGN KEY (id_loai_pt) REFERENCES loai_phuong_tien(id) ON DELETE CASCADE
)
SELECT * FROM ql_ve_xe
GO
CREATE TABLE ql_xe_ra_vao(
	id INT PRIMARY KEY IDENTITY(1,1),
	id_ve_xe INT, --ma_ve, loai_ve, bien_so, loai_xe--
	thoi_gian_xe_vao DATETIME DEFAULT GETDATE(),
	thoi_gian_xe_ra DATETIME,
	trang_thai NVARCHAR(20),
	FOREIGN KEY (id_ve_xe) REFERENCES ql_ve_xe(id) ON DELETE CASCADE
)
GO
CREATE TABLE ql_phuong_tien(
	id INT PRIMARY KEY IDENTITY(1,1),
	id_loai_pt INT,
	id_ve_xe INT, --bien_so, loai_xe--
	hang_xe NVARCHAR(20),
	anh_phuong_tien NVARCHAR(MAX),
	FOREIGN KEY (id_ve_xe) REFERENCES ql_ve_xe(id) ON DELETE CASCADE,
	FOREIGN KEY (id_loai_pt) REFERENCES loai_phuong_tien(id)
)
GO
CREATE TABLE doanh_thu (
    id INT PRIMARY KEY IDENTITY,
    ma_doanh_thu VARCHAR(20),
    tien_doanh_thu DECIMAL(18,2),
    id_bai_xe INT,
    id_nhan_vien INT,
    thoi_gian_bat_dau_hoat_dong DATETIME,
    thoi_gian_ket_thuc_hoat_dong DATETIME,
    thoi_gian_luu DATE,
    FOREIGN KEY (id_nhan_vien) REFERENCES ql_nhan_vien(id),
    FOREIGN KEY (id_bai_xe) REFERENCES ql_bai_xe(id) ON DELETE CASCADE
);
GO
CREATE TABLE ho_tro(
	id INT PRIMARY KEY IDENTITY(1,1),
	ma_ho_tro VARCHAR(20),
	ho_ten_nguoi_can_ho_tro NVARCHAR(50),
	sdt VARCHAR(20),
	email VARCHAR(100),
	id_khach_hang INT,
	id_nhan_vien INT,
	trang_thai_ho_tro INT, -- 1. là đã được giả quyết, 2. là đang được giải quyết, 3. là chưa được giải quyết
	noi_dung NVARCHAR(MAX),
	FOREIGN KEY (id_khach_hang) REFERENCES ql_khach_hang(id) ON DELETE CASCADE,
	FOREIGN KEY (id_nhan_vien) REFERENCES ql_nhan_vien(id) ON DELETE CASCADE
)
GO
INSERT INTO nguoi_dung (username, password, fullname, email, vai_tro, ngay_dang_ky, mo_ta)
VALUES 
('LuuSonTruong', '123456', N'Lưu Sơn Trường', 'truongsivar@gmail.com', N'Quản lý', '2025-07-04 15:05:30',N'Quản lý'),

('haidang', 'abcdef', N'Nguyễn Hải Đăng', 'haidang@gmail.com', N'Nhân viên', '2025-08-04 13:05:30', N'Người dùng'),

('phamvanC', 'qwerty', N'Phạm Văn C', 'phamvanc@gmail.com', N'Khách hàng', '2025-03-04 05:05:30', N'Người dùng'),

('huynhminhd', 'xyz789', N'Huỳnh Minh D', 'huynhminhd@gmail.com', N'Nhân viên', '2025-09-14 17:05:30', N'Người dùng'),

('trandoe', 'password1', N'Trần Do E', 'trandoe@gmail.com', N'Khách hàng', '2025-04-01 08:05:30', N'Người dùng');

GO
-- Bảng chuc_vu
INSERT INTO chuc_vu (ma_chuc_vu, ten_chuc_vu, mo_ta) VALUES
(N'QL', N'Quản lý', N'Quản lý chung'),
(N'NV', N'Nhân viên', N'Nhân viên vận hành'),
(N'BV', N'Bảo vệ', N'Bảo vệ');

GO
-- Bảng ql_nhan_vien
INSERT INTO ql_nhan_vien (ma_nhan_vien, ho_ten_nhan_vien, ngay_sinh, gioi_tinh, sdt, email, id_chuc_vu, anh_nhan_vien, thoi_gian_bat_dau_lam, trang_thai)
VALUES 
('NS001', N'Lưu Sơn Trường','2005-01-04', N'Nam', '0123456789', 'truongadmin@gmail.com', 1, '/image/admin.jpg', GETDATE(), N'Đang làm việc'),
('NS002', N'Trần Thị B','2000-06-03', N'Nữ', '0987654321', 'nvb@gmail.com', 2,'/image/nv02.png', GETDATE(), N'Đang làm việc'),
('NS003', N'Lê Văn C','2003-09-03', N'Nam', '0369123456', 'nvc@gmail.com', 2, '/image/nv03.png',GETDATE(), N'Nghỉ việc'),
('NS004', N'Hoàng Minh D','2006-04-03', N'Nam', '0934567890', 'nvd@gmail.com', 2, '/image/nv04.png',GETDATE(), N'Đang làm việc'),
('NS005', N'Phạm Thị E','1999-02-03', N'Nữ', '0901234567', 'nve@gmail.com', 2, '/image/nv05.png',GETDATE(), N'Đang làm việc');


GO
INSERT INTO bai_xe (ma_khu_vuc, ten_khu_vuc, suc_chua_toi_da, dia_chi, trang_thai)
VALUES 
('BX1', N'Bãi xe KTX A', 100,N'HÀ NỘI', N'Hoạt động'),
('BX2', N'Bãi xe KTX B', 150,N'HÀ NỘI', N'Hoạt động'),
('BX3', N'Bãi xe KTX C', 200,N'HÀ NỘI', N'Bảo trì'),
('BX4', N'Bãi xe KTX D', 250,N'HÀ NỘI', N'Hoạt động'),
('BX5', N'Bãi xe KTX E', 300,N'HÀ NỘI', N'Đóng cửa');
GO
INSERT INTO ql_bai_xe (id_bai_xe, id_nhan_vien, sl_xe_trong_bai, sl_cho_trong, trang_thai_bai)
VALUES 
(1, 1, 80, 20, N'Hoạt động'),
(2, 2, 120, 30, N'Hoạt động'),
(3, 3, 180, 20, N'Bảo trì'),
(4, 4, 200, 50, N'Hoạt động'),
(5, 5, 250, 50, N'Đóng cửa');

GO

INSERT INTO ql_khach_hang (ma_khach_hang, ho_ten_khach_hang, so_cccd, ngay_sinh, sdt_kh, email,ngay_dang_ky, trang_thai)
VALUES 
('KH001', N'Nguyễn Văn A', '123456789012', '2000-05-15', '0987654321', 'kh.a@example.com', GETDATE(), N'Đang hoạt động'),
('KH002', N'Trần Thị B', '234567890123', '1999-08-22', '0976543210', 'kh.b@example.com',GETDATE(), N'Đang hoạt động'),
('KH003', N'Lê Văn C', '345678901234', '2001-11-10', '0965432109', 'kh.c@example.com',GETDATE(), N'Hết hạn vé'),
('KH004', N'Hoàng Minh D', '456789012345', '2002-02-28', '0954321098', 'kh.d@example.com',GETDATE(), N'Đang hoạt động'),
('KH005', N'Phạm Thị E', '567890123456', '1998-07-05', '0943210987', 'kh.e@example.com',GETDATE(), N'Hết hạn vé');

GO
INSERT INTO loai_ve (ma_loai_ve, ten_loai_ve, trang_thai)
VALUES 
('VN01', N'Vé ngày', N'Còn bán'),
('VT02', N'Vé tháng', N'Còn bán');
GO
INSERT INTO loai_phuong_tien (ma_phuong_tien, ten_loai_phuong_tien, mo_ta_pt)
VALUES 
('PT001', N'Xe máy', N'Xe hai bánh có động cơ'),
('PT002', N'Xe đạp', N'Xe hai bánh không động cơ'),
('PT003', N'Xe điện', N'Xe hai bánh chạy điện');
GO

INSERT INTO ql_ve_xe (ma_ve, id_loai_ve, ten_khach_hang, bien_so_xe, id_loai_pt,don_gia,ngay_dang_ky, ngay_het_han, id_ql_bai, trang_thai_ve)
VALUES 
('V001', 1, N'Nguyễn văn a', N'29A-12345',1,3000,GETDATE(), '2025-04-30', 1, N'Còn hạn'),
('V002', 2, N'Nguyễn văn b', N'30B-67890',1,80000, GETDATE(),'2025-04-30', 2, N'Còn hạn'),
('V003', 2, N'Nguyễn văn c', N'Không biển sô',2,60000, GETDATE(),'2024-04-30', 3, N'Hết hạn'),
('V004', 1, N'Nguyễn văn d', N'32D-98765',3,3000, GETDATE(),'2025-04-30', 4, N'Còn hạn'),
('V005', 2, N'Nguyễn văn e', N'33E-45678',3,120000, GETDATE(),'2025-04-30', 5, N'Còn hạn');


-- Bảng ql_xe_ra_vao
INSERT INTO ql_xe_ra_vao (id_ve_xe, thoi_gian_xe_vao, thoi_gian_xe_ra, trang_thai) VALUES
(1, GETDATE(), '2024-03-24 08:30:00', N'Đang gửi'),
(2, GETDATE(), '2024-03-24 08:30:00', N'Đang gửi'),
(3, GETDATE(), '2024-03-24 08:30:00', N'Đã ra'),
(4, GETDATE(), '2024-03-24 08:30:00', N'Đang gửi'),
(5, GETDATE(), '2024-03-24 08:30:00', N'Đã ra');
GO
-- Bảng ql_phuong_tien
INSERT INTO ql_phuong_tien (id_loai_pt, id_ve_xe, hang_xe, anh_phuong_tien)
VALUES 
(1, 1, N'Honda', '/image/XM01.png'),
(2, 3, N'Giant', '/image/XDAP01.png'),
(3, 4, N'VinFast', '/image/XD01.png'),
(1, 2, N'Honda', '/image/XM02.png'),
(3, 5, N'Anbico', '/image/XD02.png');

GO
INSERT INTO doanh_thu (ma_doanh_thu, tien_doanh_thu, id_bai_xe, id_nhan_vien, thoi_gian_bat_dau_hoat_dong, thoi_gian_ket_thuc_hoat_dong, thoi_gian_luu)
VALUES 
('DT001', 5000000, 1, 1, '2025-03-01 08:00:00', '2025-03-01 18:00:00', '2024-03-01'),
('DT002', 7500000, 2, 2, '2025-04-02 08:00:00', '2025-04-02 18:00:00', '2024-03-02'),
('DT003', 3000000, 3, 3, '2025-05-03 08:00:00', '2025-05-03 18:00:00', '2024-03-03'),
('DT004', 9000000, 4, 4, '2025-06-04 08:00:00', '2025-06-04 18:00:00', '2024-03-04'),
('DT005', 4500000, 5, 5, '2025-03-05 08:00:00', '2025-03-05 18:00:00', '2024-03-05');

-- Bảng ho_tro
INSERT INTO ho_tro (ma_ho_tro, ho_ten_nguoi_can_ho_tro, sdt, email, id_khach_hang, id_nhan_vien, trang_thai_ho_tro,noi_dung) VALUES
(N'HT001', N'Nguyễn Văn P', '0966666666', 'nguyenvanp@email.com',1,1,1, N'Lỗi khi đăng ký vé'),
(N'HT002', N'Trần Thị Q', '0977777777', 'tranthiq@email.com',2,2, 2,N'Không tìm thấy thông tin xe'),
(N'HT003', N'Lê Văn R', '0988888888', 'levanr@email.com',3,3, 3,N'Cần hỗ trợ đổi vé tháng'),
(N'HT004', N'Hoàng Thị S', '0999999999', 'hoangthis@email.com',4,4,2, N'Làm mất vé xe'),
(N'HT005', N'Phạm Văn T', '0900000000', 'phamvant@email.com',5,5, 1,N'Hỏi về giá vé năm');

select * from nguoi_dung
select * from ql_nhan_vien
