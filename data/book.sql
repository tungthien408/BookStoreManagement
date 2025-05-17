-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 15, 2025 at 10:16 PM +07
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+07:00";

--
-- Database: `book`
--
CREATE DATABASE IF NOT EXISTS `book` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `book`;

-- --------------------------------------------------------

--
-- Table structure for table `quyen`
--
CREATE TABLE `quyen` (
    `maQuyen` VARCHAR(255) NOT NULL PRIMARY KEY,
    `tenQuyen` NVARCHAR(50) NOT NULL,
    `trangThaiXoa` INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Updated quyen data with standardized maQuyen (0-9 permissions)
INSERT INTO `quyen` (`maQuyen`, `tenQuyen`) VALUES
('0123456789', N'Quản trị viên (admin)'), -- All permissions
('1245', N'Quản lý'),                    -- 1: Nhập sách, 2: Hóa đơn bán, 4: Sách, 5: Nhà xuất bản
('1246', N'Bán hàng'),                   -- 1: Nhập sách, 2: Hóa đơn bán, 4: Sách, 6: Tác giả
('1248', N'Kế toán');                    -- 1: Nhập sách, 2: Hóa đơn bán, 4: Sách, 8: Khách hàng

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--
CREATE TABLE `nhanvien` (
    `MaNV` VARCHAR(10) NOT NULL,
    `HoTen` VARCHAR(255) NOT NULL,
    `ChucVu` VARCHAR(255) NOT NULL,
    `DiaChi` VARCHAR(255) NOT NULL,
    `SDT` VARCHAR(10) NOT NULL,
    `Cccd` VARCHAR(12) NOT NULL,
    `NgaySinh` DATE NOT NULL,
    `trangThaiXoa` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `nhanvien` (`MaNV`, `HoTen`, `ChucVu`, `DiaChi`, `SDT`, `Cccd`, `NgaySinh`, `trangThaiXoa`) VALUES
('NV01', 'Nguyễn Thành Nam', 'Quản lý', '12 Nguyễn Trãi, Hà Nội', '0912345678', '010123456789', '1990-05-15', 0),
('NV02', 'Trần Thị Mai', 'Nhân viên bán hàng', '45 Lê Lợi, TP.HCM', '0987654321', '020234567890', '1995-08-20', 0),
('NV03', 'Lê Văn Hùng', 'Kế toán', '78 Hùng Vương, Đà Nẵng', '0935432109', '040345678901', '1992-03-10', 0),
('NV04', 'Phạm Thị Lan', 'Nhân viên kho', '56 Trần Phú, Nha Trang', '0901234567', '060456789012', '1998-11-25', 0),
('NV05', 'Hoàng Văn Long', 'Nhân viên bán hàng', '23 Bà Triệu, Hà Nội', '0923456789', '080567890123', '1993-07-30', 0);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoannv`
--
CREATE TABLE `taikhoannv` (
    `MANV` VARCHAR(50) NOT NULL PRIMARY KEY, -- Changed to primary key for unique login
    `PASS` VARCHAR(255) NOT NULL,
    `trangThaiXoa` INT NOT NULL DEFAULT 0,
    `maQuyen` VARCHAR(255) NOT NULL,
    FOREIGN KEY (`maQuyen`) REFERENCES `quyen`(`maQuyen`) ON DELETE RESTRICT,
    FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MaNV`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Updated taikhoannv data with consistent maQuyen
INSERT INTO `taikhoannv` (`MANV`, `PASS`, `maQuyen`, `trangThaiXoa`) VALUES
('NV01', 'admin', '0123456789', 0), -- Admin with all permissions
('NV02', '12345678', '1246', 0),   -- Bán hàng
( 'NV03', '12345678', '1245', 0),   -- Quản lý
('NV04', '12345678', '1246', 0),   -- Bán hàng
( 'NV05', '12345678', '1248', 0);   -- Kế toán

-- --------------------------------------------------------

--
-- Table structure for table `nhaxuatban`
--
CREATE TABLE `nhaxuatban` (
  `MANXB` VARCHAR(10) NOT NULL,
  `TenNXB` VARCHAR(100) NOT NULL,
  `DiaChi` VARCHAR(255) DEFAULT NULL,
  `SDT` VARCHAR(10) DEFAULT NULL,
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MANXB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `nhaxuatban` (`MANXB`, `TenNXB`, `DiaChi`, `SDT`, `trangThaiXoa`) VALUES
('NXB01', 'Nhà Xuất Bản Kim Đồng', '55 Quang Trung, Hà Nội', '0243822309', 0),
('NXB02', 'Nhà Xuất Bản Trẻ', '161B Lý Chính Thắng, TP.HCM', '0283931628', 0),
('NXB03', 'Nhà Xuất Bản Giáo Dục', '81 Trần Hưng Đạo, Hà Nội', '0243822080', 0),
('NXB04', 'Nhà Xuất Bản Văn Học', '18 Nguyễn Du, Hà Nội', NULL, 0),
('NXB05', 'Nhà Xuất Bản Hội Nhà Văn', '65 Nguyễn Du, Hà Nội', '0243822181', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tacgia`
--
CREATE TABLE `tacgia` (
  `MATG` VARCHAR(10) NOT NULL,
  `TenTG` VARCHAR(100) NOT NULL,
  `DiaChi` VARCHAR(255) DEFAULT NULL,
  `SDT` VARCHAR(10) DEFAULT NULL,
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MATG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `tacgia` (`MATG`, `TenTG`, `DiaChi`, `SDT`, `trangThaiXoa`) VALUES
('TG01', 'Nguyễn Nhật Ánh', '123 Lê Lợi, TP.HCM', '0901234567', 0),
('TG02', 'Tô Hoài', '45 Hàng Bông, Hà Nội', '0912345678', 0),
('TG03', 'Nam Cao', '78 Trần Phú, Nam Định', '0923456789', 0),
('TG04', 'Ngô Tất Tố', '56 Nguyễn Du, Hà Nội', '0934567890', 0),
('TG05', 'Nguyễn Huy Tưởng', '34 Lý Thường Kiệt, Hà Nội', '0956789012', 0),
('TG06', 'Nguyễn Tuân', '56 Nguyễn Trãi, Hà Nội', '0967890123', 0),
('TG07', 'Vũ Trọng Phụng', '78 Hàng Đào, Hà Nội', '0978901234', 0),
('TG08', 'Nguyễn Công Hoan', '90 Nguyễn Huệ, TP.HCM', '0989012345', 0),
('TG09', 'Hồ Biểu Chánh', '12 Lê Lợi, Hà Nội', '0990123456', 0);

-- --------------------------------------------------------

--
-- Table structure for table `sach`
--
CREATE TABLE `sach` (
  `MASACH` VARCHAR(10) NOT NULL,
  `TenSach` VARCHAR(255) NOT NULL,
  `TheLoai` VARCHAR(100) DEFAULT NULL,
  `SoLuong` INT(11) DEFAULT 0,
  `DonGia` INT(10) DEFAULT NULL CHECK (`DonGia` >= 0),
  `MATG` VARCHAR(10) DEFAULT NULL,
  `MANXB` VARCHAR(10) DEFAULT NULL,
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  `img` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`MASACH`),
  FOREIGN KEY (`MATG`) REFERENCES `tacgia` (`MATG`) ON DELETE SET NULL,
  FOREIGN KEY (`MANXB`) REFERENCES `nhaxuatban` (`MANXB`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `sach` (`MASACH`, `TenSach`, `TheLoai`, `SoLuong`, `DonGia`, `MATG`, `MANXB`, `trangThaiXoa`, `img`) VALUES
('S001', 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'Truyện dài', 100, 85000, 'TG01', 'NXB01', 0, 'cho_toi_xin_mot_ve_di_tuoi_tho.jpg'),
('S002', 'Dế Mèn Phiêu Lưu Ký', 'Truyện thiếu nhi', 150, 65000, 'TG02', 'NXB01', 0, 'de_men_phieu_luu_ky.jpg'),
('S003', 'Chí Phèo', 'Truyện ngắn', 80, 45000, 'TG03', 'NXB04', 0, 'chi_pheo.png'),
('S004', 'Tắt Đèn', 'Tiểu thuyết', 60, 75000, 'TG04', 'NXB04', 0, 'tat_den.jpg'),
('S005', 'Cô Gái Đến Từ Hôm Qua', 'Truyện dài', 120, 80000, 'TG01', 'NXB02', 0, 'co_gai_den_tu_hom_qua.jpg'),
('S006', 'Mắt Biếc', 'Truyện dài', 90, 82000, 'TG01', 'NXB03', 0, 'mat_biec.jpg'),
('S007', 'Tôi Là Bêtô', 'Truyện thiếu nhi', 110, 70000, 'TG01', 'NXB01', 0, 'toi_la_beto.jpg'),
('S008', 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'Truyện dài', 95, 85000, 'TG01', 'NXB02', 0, 'toi_thay_hoa_vang_tren_co_xanh.jpg'),
('S009', 'Nhà Chử', 'Tiểu thuyết', 70, 60000, 'TG02', 'NXB03', 0, 'nha_chu.jpg'),
('S010', 'Truyện Tây Bắc', 'Truyện ngắn', 100, 65000, 'TG02', 'NXB05', 0, 'truyen_tay_bac.png'),
('S011', 'Kim Đồng', 'Truyện thiếu nhi', 130, 60000, 'TG02', 'NXB01', 0, 'kim_dong.jpg'),
('S012', 'Chiều Chiều', 'Truyện ngắn', 75, 58000, 'TG02', 'NXB02', 0, 'chieu_chieu.jpg'),
('S013', 'Giấc Mộng Ông Thợ Dìu', 'Truyện ngắn', 60, 62000, 'TG02', 'NXB03', 0, 'giac_mong_ong_tho_diu.jpg'),
('S014', 'Giữ Gìn 36 Phố Phường', 'Tùy bút', 90, 70000, 'TG02', 'NXB04', 0, 'giu_gin_36_pho_phuong.jpg'),
('S015', 'Lão Hạc', 'Truyện ngắn', 80, 50000, 'TG03', 'NXB05', 0, 'lao_hac.jpg'),
('S016', 'Sống Mòn', 'Tiểu thuyết', 70, 75000, 'TG03', 'NXB01', 0, 'song_mon.jpg'),
('S017', 'Đêm Hội Long Trì', 'Tiểu thuyết lịch sử', 65, 80000, 'TG05', 'NXB02', 0, 'dem_hoi_long_tri.jpg'),
('S018', 'An Tư', 'Tiểu thuyết lịch sử', 60, 78000, 'TG05', 'NXB03', 0, 'an_tu.jpg'),
('S019', 'Vũ Như Tô', 'Kịch', 50, 70000, 'TG05', 'NXB04', 0, 'vu_nhu_to.jpg'),
('S020', 'Sống Mãi Với Thủ Đô', 'Truyện ngắn', 85, 65000, 'TG05', 'NXB05', 0, 'song_mai_voi_thu_do.jpg'),
('S021', 'Vang Bóng Một Thời', 'Tùy bút', 90, 72000, 'TG06', 'NXB01', 0, 'vang_bong_mot_thoi.jpg'),
('S022', 'Chùa Đàn', 'Tiểu thuyết', 70, 68000, 'TG06', 'NXB02', 0, 'chua_dan.jpg'),
('S023', 'Chiếc Lư Đồng Mắt Cua', 'Tùy bút', 80, 70000, 'TG06', 'NXB03', 0, 'chiec_lu_dong_mat_cua.jpg'),
('S024', 'Giông Tố', 'Tiểu thuyết', 75, 75000, 'TG07', 'NXB04', 0, 'giong_to.jpg'),
('S025', 'Làm Đĩ', 'Tiểu thuyết', 65, 72000, 'TG07', 'NXB05', 0, 'lam_di.jpg'),
('S026', 'Số Đỏ', 'Tiểu thuyết', 90, 80000, 'TG07', 'NXB01', 0, 'so_do.jpg'),
('S027', 'Bước Đường Cùng', 'Tiểu thuyết', 70, 70000, 'TG08', 'NXB02', 0, 'buoc_duong_cung.jpg'),
('S028', 'Kép Tư Bền', 'Truyện ngắn', 85, 60000, 'TG08', 'NXB03', 0, 'kep_tu_ben.jpg'),
('S029', 'Người Ngựa Ngựa Người', 'Truyện ngắn', 80, 65000, 'TG08', 'NXB04', 0, 'nguoi_ngua_ngua_nguoi.jpg'),
('S030', 'Con Nhà Nghèo', 'Tiểu thuyết', 90, 75000, 'TG09', 'NXB05', 0, 'con_nha_ngheo.jpg'),
('S031', 'Lòng Dạ Đàn Bà', 'Tiểu thuyết', 70, 72000, 'TG09', 'NXB01', 0, 'long_da_dan_ba.jpg'),
('S032', 'Tại Tôi', 'Tiểu thuyết', 65, 70000, 'TG09', 'NXB02', 0, 'tai_toi.jpg'),
('S033', 'Hai Khối Tình', 'Tiểu thuyết', 80, 73000, 'TG09', 'NXB03', 0, 'hai_khoi_tinh.jpg'),
('S034', 'Nợ Đời', 'Tiểu thuyết', 75, 74000, 'TG09', 'NXB04', 0, 'no_doi.jpg'),
('S035', 'Chị Đào Chị Lý', 'Tiểu thuyết', 85, 71000, 'TG09', 'NXB05', 0, 'chi_dao_chi_ly.jpg'),
('S036', 'Chút Phận Linh Đinh', 'Tiểu thuyết', 70, 72000, 'TG09', 'NXB01', 0, 'chut_phan_linh_dinh.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--
CREATE TABLE `khachhang` (
  `MAKH` VARCHAR(10) NOT NULL,
  `SDT` VARCHAR(10) NOT NULL,
  `HoTen` VARCHAR(255) NOT NULL,
  `Diem` INT(11) NOT NULL DEFAULT 0,
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MAKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `khachhang` (`MAKH`, `SDT`, `HoTen`, `Diem`, `trangThaiXoa`) VALUES
('KH000', '0123456789', 'Anonymous', 0, 0),
('KH001', '0912345678', 'Nguyễn Văn An', 100, 0),
('KH002', '0987654321', 'Trần Thị Bình', 50, 0),
('KH003', '0935432109', 'Lê Minh Châu', 200, 0),
('KH004', '0901234567', 'Phạm Quốc Dũng', 0, 0),
('KH005', '0923456789', 'Hoàng Thị E', 150, 0),
('KH006', '0812345678', 'Nguyễn Thị Hoa', 120, 0),
('KH007', '0823456789', 'Trần Văn Bảo', 80, 0),
('KH008', '0834567890', 'Lê Thị Hạnh', 90, 0),
('KH009', '0845678901', 'Phạm Minh Tâm', 60, 0),
('KH010', '0856789012', 'Hoàng Văn Phúc', 110, 0),
('KH011', '0867890123', 'Nguyễn Thị Lan', 70, 0),
('KH012', '0878901234', 'Trần Quốc Hùng', 130, 0),
('KH013', '0889012345', 'Lê Văn Hòa', 140, 0),
('KH014', '0890123456', 'Phạm Thị Mai', 50, 0),
('KH015', '0712345678', 'Nguyễn Văn Lộc', 100, 0),
('KH016', '0723456789', 'Trần Thị Ngọc', 200, 0),
('KH017', '0734567890', 'Lê Minh Tuấn', 90, 0),
('KH018', '0745678901', 'Phạm Văn Hùng', 80, 0),
('KH019', '0756789012', 'Hoàng Thị Hương', 70, 0),
('KH020', '0767890123', 'Nguyễn Văn Phong', 60, 0),
('KH021', '0778901234', 'Trần Thị Thanh', 150, 0),
('KH022', '0789012345', 'Lê Văn Hải', 140, 0),
('KH023', '0790123456', 'Phạm Minh Đức', 130, 0),
('KH024', '0312345678', 'Nguyễn Thị Hồng', 120, 0),
('KH025', '0323456789', 'Trần Văn Hậu', 110, 0),
('KH026', '0334567890', 'Lê Thị Thu', 100, 0),
('KH027', '0345678901', 'Phạm Văn Long', 90, 0),
('KH028', '0356789012', 'Hoàng Thị Vân', 80, 0),
('KH029', '0367890123', 'Nguyễn Văn Hòa', 70, 0),
('KH030', '0378901234', 'Trần Thị Hạnh', 60, 0),
('KH031', '0389012345', 'Lê Văn Tài', 50, 0),
('KH032', '0390123456', 'Phạm Thị Hương', 200, 0),
('KH033', '0512345678', 'Nguyễn Văn Bình', 150, 0),
('KH034', '0523456789', 'Trần Thị Hòa', 140, 0),
('KH035', '0534567890', 'Lê Minh Phúc', 130, 0),
('KH036', '0545678901', 'Phạm Văn Tâm', 120, 0);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--
CREATE TABLE `hoadon` (
  `MAHD` VARCHAR(10) NOT NULL,
  `MANV` VARCHAR(10) DEFAULT NULL,
  `MAKH` VARCHAR(10) DEFAULT NULL,
  `NgayBan` DATE NOT NULL,
  `TongTien` INT(10) NOT NULL CHECK (`TongTien` >= 0),
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MAHD`),
  FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MaNV`) ON DELETE SET NULL,
  FOREIGN KEY (`MAKH`) REFERENCES `khachhang` (`MAKH`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `hoadon` (`MAHD`, `MANV`, `MAKH`, `NgayBan`, `TongTien`, `trangThaiXoa`) VALUES
('HD001', 'NV02', 'KH001', '2025-04-10', 153000, 0), -- 2 * 85000 * 0.9 = 153000
('HD002', 'NV05', 'KH003', '2025-04-11', 58500, 0), -- 1 * 65000 * 0.9 = 58500
('HD003', 'NV02', 'KH002', '2025-04-12', 155000, 0), -- (2 * 55000) + (1 * 45000) = 155000
('HD004', 'NV05', 'KH004', '2025-04-13', 150000, 0), -- 2 * 75000 = 150000
('HD005', 'NV03', 'KH005', '2025-04-14', 72000, 0), -- 1 * 80000 * 0.9 = 72000
('HD006', 'NV01', 'KH006', '2025-04-15', 221400, 0), -- 3 * 82000 * 0.9 = 221400
('HD007', 'NV04', 'KH007', '2025-04-16', 70000, 0), -- 1 * 70000 = 70000
('HD008', 'NV02', 'KH008', '2025-04-17', 153000, 0), -- 2 * 85000 * 0.9 = 153000
('HD009', 'NV05', 'KH009', '2025-04-18', 60000, 0), -- 1 * 60000 = 60000
('HD010', 'NV03', 'KH010', '2025-04-19', 108000, 0), -- 2 * 60000 * 0.9 = 108000
('HD011', 'NV01', 'KH011', '2025-04-20', 60000, 0), -- 1 * 60000 = 60000
('HD012', 'NV02', 'KH012', '2025-04-21', 175500, 0), -- 3 * 65000 * 0.9 = 175500
('HD013', 'NV03', 'KH013', '2025-04-22', 111600, 0), -- 2 * 62000 * 0.9 = 111600
('HD014', 'NV04', 'KH014', '2025-04-23', 58000, 0), -- 1 * 58000 = 58000
('HD015', 'NV05', 'KH015', '2025-04-24', 90000, 0), -- 2 * 50000 * 0.9 = 90000
('HD016', 'NV01', 'KH016', '2025-04-25', 67500, 0), -- 1 * 75000 * 0.9 = 67500
('HD017', 'NV02', 'KH017', '2025-04-26', 160000, 0), -- 2 * 80000 = 160000
('HD018', 'NV03', 'KH018', '2025-04-27', 78000, 0), -- 1 * 78000 = 78000
('HD019', 'NV04', 'KH019', '2025-04-28', 140000, 0), -- 2 * 70000 = 140000
('HD020', 'NV05', 'KH020', '2025-04-29', 65000, 0), -- 1 * 65000 = 65000
('HD021', 'NV01', 'KH021', '2025-04-30', 126000, 0), -- 2 * 70000 * 0.9 = 126000
('HD022', 'NV02', 'KH022', '2025-05-01', 61200, 0), -- 1 * 68000 * 0.9 = 61200
('HD023', 'NV03', 'KH023', '2025-05-02', 129600, 0), -- 2 * 72000 * 0.9 = 129600
('HD024', 'NV04', 'KH024', '2025-05-03', 67500, 0), -- 1 * 75000 * 0.9 = 67500
('HD025', 'NV05', 'KH025', '2025-05-04', 126000, 0), -- 2 * 70000 * 0.9 = 126000
('HD026', 'NV01', 'KH026', '2025-05-05', 72000, 0), -- 1 * 80000 * 0.9 = 72000
('HD027', 'NV02', 'KH027', '2025-05-06', 144000, 0), -- 2 * 72000 = 144000
('HD028', 'NV03', 'KH028', '2025-05-07', 60000, 0), -- 1 * 60000 = 60000
('HD029', 'NV04', 'KH029', '2025-05-08', 130000, 0), -- 2 * 65000 = 130000
('HD030', 'NV05', 'KH030', '2025-05-09', 75000, 0), -- 1 * 75000 = 75000
('HD031', 'NV01', 'KH031', '2025-05-10', 72000, 0), -- 2 * 72000 * 0.5 = 72000
('HD032', 'NV02', 'KH032', '2025-05-11', 67500, 0), -- 1 * 75000 * 0.9 = 67500
('HD033', 'NV03', 'KH033', '2025-05-12', 131400, 0), -- 2 * 73000 * 0.9 = 131400
('HD034', 'NV04', 'KH034', '2025-05-13', 66600, 0), -- 1 * 74000 * 0.9 = 66600
('HD035', 'NV05', 'KH035', '2025-05-14', 131400, 0), -- 2 * 73000 * 0.9 = 131400
('HD036', 'NV01', 'KH036', '2025-05-15', 66600, 0); -- 1 * 74000 * 0.9 = 66600

-- --------------------------------------------------------

--
-- Table structure for table `chitiethoadon`
--
CREATE TABLE `chitiethoadon` (
  `MASACH` VARCHAR(10) NOT NULL,
  `MAHD` VARCHAR(10) NOT NULL,
  `SoLuong` INT(11) DEFAULT NULL CHECK (`SoLuong` > 0),
  `Gia` INT(10) DEFAULT NULL CHECK (`Gia` >= 0),
  PRIMARY KEY (`MASACH`, `MAHD`),
  FOREIGN KEY (`MASACH`) REFERENCES `sach` (`MASACH`) ON DELETE CASCADE,
  FOREIGN KEY (`MAHD`) REFERENCES `hoadon` (`MAHD`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `chitiethoadon` (`MASACH`, `MAHD`, `SoLuong`, `Gia`) VALUES
('S001', 'HD001', 2, 85000),
('S002', 'HD002', 1, 65000),
('S003', 'HD003', 1, 45000),
('S004', 'HD004', 2, 75000),
('S005', 'HD003', 2, 55000),
('S005', 'HD005', 1, 80000),
('S006', 'HD006', 3, 82000),
('S007', 'HD007', 1, 70000),
('S008', 'HD008', 2, 85000),
('S009', 'HD009', 1, 60000),
('S010', 'HD010', 2, 60000),
('S011', 'HD011', 1, 60000),
('S012', 'HD012', 3, 65000),
('S013', 'HD013', 2, 62000),
('S014', 'HD014', 1, 58000),
('S015', 'HD015', 2, 50000),
('S016', 'HD016', 1, 75000),
('S017', 'HD017', 2, 80000),
('S018', 'HD018', 1, 78000),
('S019', 'HD019', 2, 70000),
('S020', 'HD020', 1, 65000),
('S021', 'HD021', 2, 70000),
('S022', 'HD022', 1, 68000),
('S023', 'HD023', 2, 72000),
('S024', 'HD024', 1, 75000),
('S025', 'HD025', 2, 70000),
('S026', 'HD026', 1, 80000),
('S027', 'HD027', 2, 72000),
('S028', 'HD028', 1, 60000),
('S029', 'HD029', 2, 65000),
('S030', 'HD030', 1, 75000),
('S031', 'HD031', 2, 72000),
('S032', 'HD032', 1, 75000),
('S033', 'HD033', 2, 73000),
('S034', 'HD034', 1, 74000),
('S035', 'HD035', 2, 73000),
('S036', 'HD036', 1, 74000);

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--
CREATE TABLE `phieunhap` (
  `MAPN` VARCHAR(10) NOT NULL,
  `MANV` VARCHAR(10) DEFAULT NULL,
  `NgayNhap` DATE DEFAULT NULL,
  `TongTien` DECIMAL(10,2) DEFAULT NULL,
  `MANXB` VARCHAR(10) DEFAULT NULL,
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MAPN`),
  FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MaNV`) ON DELETE SET NULL,
  FOREIGN KEY (`MANXB`) REFERENCES `nhaxuatban` (`MANXB`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `phieunhap` (`MAPN`, `MANV`, `NgayNhap`, `TongTien`, `MANXB`, `trangThaiXoa`) VALUES
('PN01', 'NV04', '2025-04-01', 7500000.00, 'NXB01', 0),
('PN02', 'NV04', '2025-04-02', 2700000.00, 'NXB04', 0),
('PN03', 'NV03', '2025-04-03', 5500000.00, 'NXB05', 0),
('PN04', 'NV04', '2025-04-04', 3750000.00, 'NXB01', 0),
('PN05', 'NV03', '2025-04-05', 3600000.00, 'NXB04', 0);

-- --------------------------------------------------------

--
-- Table structure for table `chitietphieunhap`
--
CREATE TABLE `chitietphieunhap` (
  `MASACH` VARCHAR(10) NOT NULL,
  `MAPN` VARCHAR(10) NOT NULL,
  `SoLuong` INT(11) DEFAULT NULL CHECK (`SoLuong` > 0),
  `GiaNhap` INT(10) DEFAULT NULL CHECK (`GiaNhap` >= 0),
  PRIMARY KEY (`MASACH`, `MAPN`),
  FOREIGN KEY (`MASACH`) REFERENCES `sach` (`MASACH`) ON DELETE CASCADE,
  FOREIGN KEY (`MAPN`) REFERENCES `phieunhap` (`MAPN`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `chitietphieunhap` (`MASACH`, `MAPN`, `SoLuong`, `GiaNhap`) VALUES
('S001', 'PN01', 50, 85000),
('S002', 'PN01', 50, 65000),
('S003', 'PN02', 60, 45000),
('S004', 'PN04', 50, 75000),
('S005', 'PN03', 100, 55000),
('S005', 'PN05', 45, 80000);

-- --------------------------------------------------------

--
-- Indexes for dumped tables
--

ALTER TABLE `chitiethoadon`
  ADD INDEX `idx_mahd` (`MAHD`);

ALTER TABLE `chitietphieunhap`
  ADD INDEX `idx_mapn` (`MAPN`);

ALTER TABLE `hoadon`
  ADD INDEX `idx_manv` (`MANV`),
  ADD INDEX `idx_makh` (`MAKH`);

ALTER TABLE `sach`
  ADD INDEX `idx_matg` (`MATG`),
  ADD INDEX `idx_manxb` (`MANXB`);

ALTER TABLE `phieunhap`
  ADD INDEX `idx_manv` (`MANV`),
  ADD INDEX `idx_manxb` (`MANXB`);

COMMIT;