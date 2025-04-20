-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 14, 2025
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `book`
--
CREATE DATABASE IF NOT EXISTS `book` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `book`;

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

--
-- Dumping data for table `nhaxuatban`
--
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

--
-- Dumping data for table `tacgia`
--
INSERT INTO `tacgia` (`MATG`, `TenTG`, `DiaChi`, `SDT`, `trangThaiXoa`) VALUES
('TG01', 'Nguyễn Nhật Ánh', '123 Lê Lợi, TP.HCM', '0901234567', 0),
('TG02', 'Tô Hoài', '45 Hàng Bông, Hà Nội', '0912345678', 0),
('TG03', 'Nam Cao', '78 Trần Phú, Nam Định', '0923456789', 0),
('TG04', 'Ngô Tất Tố', '56 Nguyễn Du, Hà Nội', '0934567890', 0),
('TG05', 'Xuân Quỳnh', '12 Trần Hưng Đạo, Hà Nội', '0945678901', 0),
('TG06', 'Nguyễn Huy Tưởng', '34 Lý Thường Kiệt, Hà Nội', '0956789012', 0),
('TG07', 'Nguyễn Tuân', '56 Nguyễn Trãi, Hà Nội', '0967890123', 0),
('TG08', 'Vũ Trọng Phụng', '78 Hàng Đào, Hà Nội', '0978901234', 0),
('TG09', 'Nguyễn Công Hoan', '90 Nguyễn Huệ, TP.HCM', '0989012345', 0),
('TG10', 'Hồ Biểu Chánh', '12 Lê Lợi, Hà Nội', '0990123456', 0);


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
  PRIMARY KEY (`MASACH`),
  FOREIGN KEY (`MATG`) REFERENCES `tacgia` (`MATG`) ON DELETE SET NULL,
  FOREIGN KEY (`MANXB`) REFERENCES `nhaxuatban` (`MANXB`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sach`
--
INSERT INTO `sach` (`MASACH`, `TenSach`, `TheLoai`, `SoLuong`, `DonGia`, `MATG`, `MANXB`, `trangThaiXoa`) VALUES
('S001', 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'Truyện dài', 100, 85000, 'TG01', 'NXB01', 0),
('S002', 'Dế Mèn Phiêu Lưu Ký', 'Truyện thiếu nhi', 150, 65000, 'TG02', 'NXB01', 0),
('S003', 'Chí Phèo', 'Truyện ngắn', 80, 45000, 'TG03', 'NXB04', 0),
('S004', 'Tắt Đèn', 'Tiểu thuyết', 60, 75000, 'TG04', 'NXB04', 0),
('S005', 'Sóng', 'Thơ', 200, 55000, 'TG05', 'NXB05', 0),
('S006', 'Cô Gái Đến Từ Hôm Qua', 'Truyện dài', 120, 80000, 'TG01', 'NXB02', 0),
('S007', 'Mắt Biếc', 'Truyện dài', 90, 82000, 'TG01', 'NXB03', 0),
('S008', 'Tôi Là Bêtô', 'Truyện thiếu nhi', 110, 70000, 'TG01', 'NXB01', 0),
('S009', 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'Truyện dài', 95, 85000, 'TG01', 'NXB02', 0),
('S010', 'Nhà Chử', 'Tiểu thuyết', 70, 60000, 'TG02', 'NXB03', 0),
('S011', 'O Chuột', 'Truyện ngắn', 85, 55000, 'TG02', 'NXB04', 0),
('S012', 'Truyện Tây Bắc', 'Truyện ngắn', 100, 65000, 'TG02', 'NXB05', 0),
('S013', 'Kim Đồng', 'Truyện thiếu nhi', 130, 60000, 'TG02', 'NXB01', 0),
('S014', 'Chiều Chiều', 'Truyện ngắn', 75, 58000, 'TG02', 'NXB02', 0),
('S015', 'Giấc Mộng Ông Thợ Dìu', 'Truyện ngắn', 60, 62000, 'TG02', 'NXB03', 0),
('S016', 'Giữ Gìn 36 Phố Phường', 'Tùy bút', 90, 70000, 'TG02', 'NXB04', 0),
('S017', 'Lão Hạc', 'Truyện ngắn', 80, 50000, 'TG03', 'NXB05', 0),
('S018', 'Sống Mòn', 'Tiểu thuyết', 70, 75000, 'TG03', 'NXB01', 0),
('S019', 'Đêm Hội Long Trì', 'Tiểu thuyết lịch sử', 65, 80000, 'TG06', 'NXB02', 0),
('S020', 'An Tư', 'Tiểu thuyết lịch sử', 60, 78000, 'TG06', 'NXB03', 0),
('S021', 'Vũ Như Tô', 'Kịch', 50, 70000, 'TG06', 'NXB04', 0),
('S022', 'Sống Mãi Với Thủ Đô', 'Truyện ngắn', 85, 65000, 'TG06', 'NXB05', 0),
('S023', 'Vang Bóng Một Thời', 'Tùy bút', 90, 72000, 'TG07', 'NXB01', 0),
('S024', 'Chùa Đàn', 'Tiểu thuyết', 70, 68000, 'TG07', 'NXB02', 0),
('S025', 'Chiếc Lư Đồng Mắt Cua', 'Tùy bút', 80, 70000, 'TG07', 'NXB03', 0),
('S026', 'Giông Tố', 'Tiểu thuyết', 75, 75000, 'TG08', 'NXB04', 0),
('S027', 'Làm Đĩ', 'Tiểu thuyết', 65, 72000, 'TG08', 'NXB05', 0),
('S028', 'Số Đỏ', 'Tiểu thuyết', 90, 80000, 'TG08', 'NXB01', 0),
('S029', 'Bước Đường Cùng', 'Tiểu thuyết', 70, 70000, 'TG09', 'NXB02', 0),
('S030', 'Kép Tư Bền', 'Truyện ngắn', 85, 60000, 'TG09', 'NXB03', 0),
('S031', 'Người Ngựa Ngựa Người', 'Truyện ngắn', 80, 65000, 'TG09', 'NXB04', 0),
('S032', 'Con Nhà Nghèo', 'Tiểu thuyết', 90, 75000, 'TG10', 'NXB05', 0),
('S033', 'Lòng Dạ Đàn Bà', 'Tiểu thuyết', 70, 72000, 'TG10', 'NXB01', 0),
('S034', 'Tại Tôi', 'Tiểu thuyết', 65, 70000, 'TG10', 'NXB02', 0),
('S035', 'Hai Khối Tình', 'Tiểu thuyết', 80, 73000, 'TG10', 'NXB03', 0),
('S036', 'Nợ Đời', 'Tiểu thuyết', 75, 74000, 'TG10', 'NXB04', 0),
('S037', 'Chị Đào Chị Lý', 'Tiểu thuyết', 85, 71000, 'TG10', 'NXB05', 0),
('S038', 'Chút Phận Linh Đinh', 'Tiểu thuyết', 70, 72000, 'TG10', 'NXB01', 0);

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

--
-- Dumping data for table `khachhang`
--
INSERT INTO `khachhang` (`MAKH`, `SDT`, `HoTen`, `Diem`, `trangThaiXoa`) VALUES
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
('KH036', '0545678901', 'Phạm Văn Tâm', 120, 0),
('KH037', '0556789012', 'Hoàng Thị Mai', 110, 0),
('KH038', '0567890123', 'Nguyễn Văn Hùng', 100, 0);
-- ('KH039', '0578901234', 'Trần Thị Hương', 90, 0),
-- ('KH040', '0589012345', 'Lê Văn Đức', 80, 0);

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

--
-- Dumping data for table `nhanvien`
--
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
  `MANV` VARCHAR(10) NOT NULL,
  `PASS` VARCHAR(255) NOT NULL,
  `trangThaiXoa` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`MANV`),
  FOREIGN KEY (`MANV`) REFERENCES `nhanvien` (`MaNV`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoannv`
--
INSERT INTO `taikhoannv` (`MANV`, `PASS`, `trangThaiXoa`) VALUES
('NV01', 'admin', 0),
('NV02', 'hashed_password_2', 0),
('NV03', 'hashed_password_3', 0),
('NV04', 'hashed_password_4', 0),
('NV05', 'hashed_password_5', 0);

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

--
-- Dumping data for table `hoadon`
--
INSERT INTO `hoadon` (`MAHD`, `MANV`, `MAKH`, `NgayBan`, `TongTien`, `trangThaiXoa`) VALUES
('HD001', 'NV02', 'KH001', '2025-04-10', 153000, 0), -- 10% discount for KH001
('HD002', 'NV05', 'KH003', '2025-04-11', 58500, 0), -- 10% discount for KH003
('HD003', 'NV02', 'KH002', '2025-04-12', 110000, 0), -- No discount for KH002
('HD004', 'NV05', 'KH004', '2025-04-13', 150000, 0), -- No discount for KH004
('HD005', 'NV03', 'KH005', '2025-04-14', 40500, 0), -- 10% discount for KH005
('HD006', 'NV01', 'KH006', '2025-04-15', 180000, 0), -- 10% discount for KH006
('HD007', 'NV04', 'KH007', '2025-04-16', 85000, 0), -- No discount for KH007
('HD008', 'NV02', 'KH008', '2025-04-17', 108000, 0), -- 10% discount for KH008
('HD009', 'NV05', 'KH009', '2025-04-18', 95000, 0), -- No discount for KH009
-- Insert the remaining invoices
('HD010', 'NV03', 'KH010', '2025-04-19', 162000, 0), -- 10% discount for KH010
('HD011', 'NV01', 'KH011', '2025-04-20', 70000, 0), -- No discount for KH011
('HD012', 'NV02', 'KH012', '2025-04-21', 195000, 0), -- 10% discount for KH012
('HD013', 'NV03', 'KH013', '2025-04-22', 140000, 0), -- 10% discount for KH013
('HD014', 'NV04', 'KH014', '2025-04-23', 58000, 0), -- No discount for KH014
('HD015', 'NV05', 'KH015', '2025-04-24', 100000, 0), -- 10% discount for KH015
('HD016', 'NV01', 'KH016', '2025-04-25', 200000, 0), -- 10% discount for KH016
('HD017', 'NV02', 'KH017', '2025-04-26', 90000, 0), -- No discount for KH017
('HD018', 'NV03', 'KH018', '2025-04-27', 80000, 0), -- No discount for KH018
('HD019', 'NV04', 'KH019', '2025-04-28', 70000, 0), -- No discount for KH019
('HD020', 'NV05', 'KH020', '2025-04-29', 60000, 0), -- No discount for KH020
('HD021', 'NV01', 'KH021', '2025-04-30', 150000, 0), -- 10% discount for KH021
('HD022', 'NV02', 'KH022', '2025-05-01', 140000, 0), -- 10% discount for KH022
('HD023', 'NV03', 'KH023', '2025-05-02', 130000, 0), -- 10% discount for KH023
('HD024', 'NV04', 'KH024', '2025-05-03', 120000, 0), -- 10% discount for KH024
('HD025', 'NV05', 'KH025', '2025-05-04', 110000, 0), -- 10% discount for KH025
('HD026', 'NV01', 'KH026', '2025-05-05', 100000, 0), -- 10% discount for KH026
('HD027', 'NV02', 'KH027', '2025-05-06', 90000, 0), -- No discount for KH027
('HD028', 'NV03', 'KH028', '2025-05-07', 80000, 0), -- No discount for KH028
('HD029', 'NV04', 'KH029', '2025-05-08', 70000, 0), -- No discount for KH029
('HD030', 'NV05', 'KH030', '2025-05-09', 60000, 0), -- No discount for KH030
('HD031', 'NV01', 'KH031', '2025-05-10', 50000, 0), -- No discount for KH031
('HD032', 'NV02', 'KH032', '2025-05-11', 200000, 0), -- 10% discount for KH032
('HD033', 'NV03', 'KH033', '2025-05-12', 150000, 0), -- 10% discount for KH033
('HD034', 'NV04', 'KH034', '2025-05-13', 140000, 0), -- 10% discount for KH034
('HD035', 'NV05', 'KH035', '2025-05-14', 130000, 0), -- 10% discount for KH035
('HD036', 'NV01', 'KH036', '2025-05-15', 120000, 0), -- 10% discount for KH036
('HD037', 'NV02', 'KH037', '2025-05-16', 110000, 0), -- 10% discount for KH037
('HD038', 'NV03', 'KH038', '2025-05-17', 100000, 0); -- 10% discount for KH038
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

--
-- Dumping data for table `chitiethoadon`
--
INSERT INTO `chitiethoadon` (`MASACH`, `MAHD`, `SoLuong`, `Gia`) VALUES
('S01', 'HD01', 2, 85000),
('S02', 'HD02', 1, 65000),
('S05', 'HD03', 2, 55000),
('S04', 'HD04', 2, 75000),
('S03', 'HD03', 1, 45000),
('S05', 'HD05', 1, 55000),
('S06', 'HD06', 3, 80000),
('S07', 'HD07', 1, 82000),
('S08', 'HD08', 2, 70000),
('S09', 'HD09', 1, 85000),
('S10', 'HD10', 2, 60000),
('S11', 'HD11', 1, 55000),
('S12', 'HD12', 3, 65000),
('S13', 'HD13', 2, 60000),
('S14', 'HD14', 1, 58000),
('S15', 'HD15', 2, 62000),
('S16', 'HD16', 1, 70000),
('S17', 'HD17', 2, 50000),
('S18', 'HD18', 1, 75000),
('S19', 'HD19', 2, 80000),
('S20', 'HD20', 1, 78000),
('S21', 'HD21', 2, 70000),
('S22', 'HD22', 1, 65000),
('S23', 'HD23', 2, 72000),
('S24', 'HD24', 1, 68000),
('S25', 'HD25', 2, 70000),
('S26', 'HD26', 1, 75000),
('S27', 'HD27', 2, 72000),
('S28', 'HD28', 1, 80000),
('S29', 'HD29', 2, 70000),
('S30', 'HD30', 1, 60000),
('S31', 'HD31', 2, 65000),
('S32', 'HD32', 1, 75000),
('S33', 'HD33', 2, 72000),
('S34', 'HD34', 1, 70000),
('S35', 'HD35', 2, 73000),
('S36', 'HD36', 1, 74000),
('S37', 'HD37', 2, 71000),
('S38', 'HD38', 1, 72000);
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

--
-- Dumping data for table `phieunhap`
--
INSERT INTO `phieunhap` (`MAPN`, `MANV`, `NgayNhap`, `TongTien`, `MANXB`, `trangThaiXoa`) VALUES
('PN01', 'NV04', '2025-04-01', 4250000.00, 'NXB01', 0),
('PN02', 'NV04', '2025-04-02', 2700000.00, 'NXB04', 0),
('PN03', 'NV03', '2025-04-03', 5500000.00, 'NXB05', 0),
('PN04', 'NV04', '2025-04-04', 3900000.00, 'NXB01', 0),
('PN05', 'NV03', '2025-04-05', 1800000.00, 'NXB04', 0);

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

--
-- Dumping data for table `chitietphieunhap`
--
INSERT INTO `chitietphieunhap` (`MASACH`, `MAPN`, `SoLuong`, `GiaNhap`) VALUES
('S01', 'PN01', 50, 85000),
('S02', 'PN01', 50, 65000),
('S03', 'PN02', 60, 45000),
('S05', 'PN03', 100, 55000),
('S04', 'PN04', 50, 75000);

-- --------------------------------------------------------

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD INDEX `idx_mahd` (`MAHD`);

--
-- Indexes for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD INDEX `idx_mapn` (`MAPN`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD INDEX `idx_manv` (`MANV`),
  ADD INDEX `idx_makh` (`MAKH`);

--
-- Indexes for table `sach`
--
ALTER TABLE `sach`
  ADD INDEX `idx_matg` (`MATG`),
  ADD INDEX `idx_manxb` (`MANXB`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD INDEX `idx_manv` (`MANV`),
  ADD INDEX `idx_manxb` (`MANXB`);

COMMIT;