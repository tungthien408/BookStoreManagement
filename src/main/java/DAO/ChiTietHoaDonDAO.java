package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.ChiTietHoaDonDTO;
import Service.Data;

public class ChiTietHoaDonDAO {
    // Thêm một chi tiết hóa đơn mới
    public boolean create(ChiTietHoaDonDTO chiTiet) {
        String sql = "INSERT INTO chitiethoadon (MASACH, MAHD, SoLuong, Gia) VALUES (?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chiTiet.getMaSach());
            stmt.setString(2, chiTiet.getMaHD());
            stmt.setInt(3, chiTiet.getSoLuong());
            stmt.setInt(4, chiTiet.getGia());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả chi tiết hóa đơn
    public List<ChiTietHoaDonDTO> getAll() {
        List<ChiTietHoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM chitiethoadon";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ChiTietHoaDonDTO chiTiet = new ChiTietHoaDonDTO();
                chiTiet.setMaSach(rs.getString("MASACH"));
                chiTiet.setMaHD(rs.getString("MAHD"));
                chiTiet.setSoLuong(rs.getInt("SoLuong"));
                chiTiet.setGia(rs.getInt("Gia"));
                list.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy chi tiết hóa đơn theo MAHD và MASACH
    public ChiTietHoaDonDTO getByMaHDAndMaSach(String maHD, String maSach) {
        String sql = "SELECT * FROM chitiethoadon WHERE MAHD = ? AND MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            stmt.setString(2, maSach);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ChiTietHoaDonDTO chiTiet = new ChiTietHoaDonDTO();
                    chiTiet.setMaSach(rs.getString("MASACH"));
                    chiTiet.setMaHD(rs.getString("MAHD"));
                    chiTiet.setSoLuong(rs.getInt("SoLuong"));
                    chiTiet.setGia(rs.getInt("Gia"));
                    return chiTiet;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách chi tiết hóa đơn theo MAHD
    public List<ChiTietHoaDonDTO> getByMaHD(String maHD) {
        List<ChiTietHoaDonDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM chitiethoadon WHERE MAHD = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDonDTO chiTiet = new ChiTietHoaDonDTO();
                    chiTiet.setMaSach(rs.getString("MASACH"));
                    chiTiet.setMaHD(rs.getString("MAHD"));
                    chiTiet.setSoLuong(rs.getInt("SoLuong"));
                    chiTiet.setGia(rs.getInt("Gia"));
                    list.add(chiTiet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật chi tiết hóa đơn
    public boolean update(ChiTietHoaDonDTO chiTiet) {
        String sql = "UPDATE chitiethoadon SET SoLuong = ?, Gia = ? WHERE MAHD = ? AND MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chiTiet.getSoLuong());
            stmt.setInt(2, chiTiet.getGia());
            stmt.setString(3, chiTiet.getMaHD());
            stmt.setString(4, chiTiet.getMaSach());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa chi tiết hóa đơn
    public boolean delete(String maHD, String maSach) {
        String sql = "DELETE FROM chitiethoadon WHERE MAHD = ? AND MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            stmt.setString(2, maSach);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa tất cả chi tiết hóa đơn theo MAHD
    public boolean deleteByMaHD(String maHD) {
        String sql = "DELETE FROM chitiethoadon WHERE MAHD = ?";
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