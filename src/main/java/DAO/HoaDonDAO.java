package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.HoaDonDTO;
import Service.Data;

public class HoaDonDAO {
    // Thêm một hóa đơn mới
    public boolean create(HoaDonDTO hoaDon) {
        String sql = "INSERT INTO hoadon (MAHD, MANV, MAKH, NgayBan, TongTien, maGiamGia, trangThaiXoa) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoaDon.getMaHD());
            stmt.setString(2, hoaDon.getMaNV());
            stmt.setString(3, hoaDon.getMaKH());
            stmt.setDate(4, hoaDon.getNgayBan());
            stmt.setInt(5, hoaDon.getTongTien());
            stmt.setString(6, hoaDon.getMaGiamGia());    // thêm maGiamGia
            stmt.setInt(7, hoaDon.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả hóa đơn chưa bị xóa (trangThaiXoa = 0)
    public List<HoaDonDTO> getAll() {
        List<HoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HoaDonDTO hoaDon = new HoaDonDTO();
                hoaDon.setMaHD(rs.getString("MAHD"));
                hoaDon.setMaNV(rs.getString("MANV"));
                hoaDon.setMaKH(rs.getString("MAKH"));
                hoaDon.setNgayBan(rs.getDate("NgayBan"));
                hoaDon.setTongTien(rs.getInt("TongTien"));
                hoaDon.setMaGiamGia(rs.getString("maGiamGia"));  // lấy maGiamGia
                hoaDon.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy hóa đơn theo MAHD
    public HoaDonDTO getByMaHD(String maHD) {
        String sql = "SELECT * FROM hoadon WHERE MAHD = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HoaDonDTO hoaDon = new HoaDonDTO();
                    hoaDon.setMaHD(rs.getString("MAHD"));
                    hoaDon.setMaNV(rs.getString("MANV"));
                    hoaDon.setMaKH(rs.getString("MAKH"));
                    hoaDon.setNgayBan(rs.getDate("NgayBan"));
                    hoaDon.setTongTien(rs.getInt("TongTien"));
                    hoaDon.setMaGiamGia(rs.getString("maGiamGia"));  // lấy maGiamGia
                    hoaDon.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return hoaDon;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin hóa đơn
    public boolean update(HoaDonDTO hoaDon) {
        String sql = "UPDATE hoadon SET MANV = ?, MAKH = ?, NgayBan = ?, TongTien = ?, maGiamGia = ?, trangThaiXoa = ? "
                   + "WHERE MAHD = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoaDon.getMaNV());
            stmt.setString(2, hoaDon.getMaKH());
            stmt.setDate(3, hoaDon.getNgayBan());
            stmt.setInt(4, hoaDon.getTongTien());
            stmt.setString(5, hoaDon.getMaGiamGia());    // cập nhật maGiamGia
            stmt.setInt(6, hoaDon.getTrangThaiXoa());
            stmt.setString(7, hoaDon.getMaHD());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm (cập nhật trangThaiXoa = 1)
    public boolean delete(String maHD) {
        String sql = "UPDATE hoadon SET trangThaiXoa = 1 WHERE MAHD = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
