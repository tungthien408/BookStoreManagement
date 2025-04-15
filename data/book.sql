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
('TG02', 'Tô Hoài', '45 Hàng Bông, Hà Nội', NULL, 0),
('TG03', 'Nam Cao', '78 Trần Phú, Nam Định', '0912345678', 0),
('TG04', 'Nguyễn Đình Thi', '56 Nguyễn Du, Hà Nội', '0987654321', 0),
('TG05', 'Xuân Quỳnh', NULL, '0935432109', 0);

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
('S01', 'Cho Tôi Xin Một Vé Đi Tuổi Thơ', 'Truyện dài', 100, 85000, 'TG01', 'NXB01', 0),
('S02', 'Dế Mèn Phiêu Lưu Ký', 'Truyện thiếu nhi', 150, 65000, 'TG02', 'NXB01', 0),
('S03', 'Chí Phèo', 'Truyện ngắn', 80, 45000, 'TG03', 'NXB04', 0),
('S04', 'Tắt Đèn', 'Tiểu thuyết', 60, 75000, 'TG03', 'NXB04', 0),
('S05', 'Sóng', 'Thơ', 200, 55000, 'TG05', 'NXB05', 0);

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
('KH01', '0912345678', 'Nguyễn Văn An', 100, 0),
('KH02', '0987654321', 'Trần Thị Bình', 50, 0),
('KH03', '0935432109', 'Lê Minh Châu', 200, 0),
('KH04', '0901234567', 'Phạm Quốc Dũng', 0, 0),
('KH05', '0923456789', 'Hoàng Thị E', 150, 0);

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
('NV01', 'Nguyễn Thành Nam', 'Quản lý', '12 Nguyễn Trãi, Hà Nội', '0912345678', '123456789012', '1990-05-15', 0),
('NV02', 'Trần Thị Mai', 'Nhân viên bán hàng', '45 Lê Lợi, TP.HCM', '0987654321', '234567890123', '1995-08-20', 0),
('NV03', 'Lê Văn Hùng', 'Kế toán', '78 Hùng Vương, Đà Nẵng', '0935432109', '345678901234', '1992-03-10', 0),
('NV04', 'Phạm Thị Lan', 'Nhân viên kho', '56 Trần Phú, Nha Trang', '0901234567', '456789012345', '1998-11-25', 0),
('NV05', 'Hoàng Văn Long', 'Nhân viên bán hàng', '23 Bà Triệu, Hà Nội', '0923456789', '567890123456', '1993-07-30', 0);

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
('NV01', 'hashed_password_1', 0),
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
('HD01', 'NV02', 'KH01', '2025-04-10', 170000, 0),
('HD02', 'NV05', 'KH03', '2025-04-11', 65000, 0),
('HD03', 'NV02', 'KH02', '2025-04-12', 110000, 0),
('HD04', 'NV05', 'KH04', '2025-04-13', 150000, 0),
('HD05', 'NV03', 'KH05', '2025-04-14', 45000, 0);

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
('S03', 'HD05', 1, 45000);

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