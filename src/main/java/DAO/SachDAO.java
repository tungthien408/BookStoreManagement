package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.SachDTO;
import Service.Data;

public class SachDAO {
    // Thêm một sách mới
    public boolean create(SachDTO sach) {
        String sql = "INSERT INTO sach (MASACH, TenSach, TheLoai, SoLuong, DonGia, MATG, MANXB, trangThaiXoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sach.getMaSach());
            stmt.setString(2, sach.getTenSach());
            stmt.setString(3, sach.getTheLoai());
            stmt.setInt(4, sach.getSoLuong());
            stmt.setInt(5, sach.getDonGia());
            stmt.setString(6, sach.getMaTG());
            stmt.setString(7, sach.getMaNXB());
            stmt.setInt(8, sach.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả sách chưa bị xóa (trangThaiXoa = 0)
    public List<SachDTO> getAll() {
        List<SachDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM sach WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SachDTO sach = new SachDTO();
                sach.setMaSach(rs.getString("MASACH"));
                sach.setTenSach(rs.getString("TenSach"));
                sach.setTheLoai(rs.getString("TheLoai"));
                sach.setSoLuong(rs.getInt("SoLuong"));
                sach.setDonGia(rs.getInt("DonGia"));
                sach.setMaTG(rs.getString("MATG"));
                sach.setMaNXB(rs.getString("MANXB"));
                sach.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(sach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sách theo MASACH
    public SachDTO getByMaSach(String maSach) {
        String sql = "SELECT * FROM sach WHERE MASACH = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSach);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SachDTO sach = new SachDTO();
                    sach.setMaSach(rs.getString("MASACH"));
                    sach.setTenSach(rs.getString("TenSach"));
                    sach.setTheLoai(rs.getString("TheLoai"));
                    sach.setSoLuong(rs.getInt("SoLuong"));
                    sach.setDonGia(rs.getInt("DonGia"));
                    sach.setMaTG(rs.getString("MATG"));
                    sach.setMaNXB(rs.getString("MANXB"));
                    sach.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return sach;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin sách
    public boolean update(SachDTO sach) {
        String sql = "UPDATE sach SET TenSach = ?, TheLoai = ?, SoLuong = ?, DonGia = ?, MATG = ?, MANXB = ?, trangThaiXoa = ? WHERE MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sach.getTenSach());
            stmt.setString(2, sach.getTheLoai());
            stmt.setInt(3, sach.getSoLuong());
            stmt.setInt(4, sach.getDonGia());
            stmt.setString(5, sach.getMaTG());
            stmt.setString(6, sach.getMaNXB());
            stmt.setInt(7, sach.getTrangThaiXoa());
            stmt.setString(8, sach.getMaSach());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm (cập nhật trangThaiXoa = 1)
    public boolean delete(String maSach) {
        String sql = "UPDATE sach SET trangThaiXoa = 1 WHERE MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSach);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getSoLuongTonSanPham(String maSach){
        String sql = "SELECT SoLuong FROM sach WHERE MASACH=?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("SoLuong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int updateSoLuongTonSanPham(String maSach, int soLuongTon) {
        String sql = "UPDATE sach SET SoLuong = ? WHERE MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongTon);
            ps.setString(2, maSach);
            return ps.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean existsByMaSach(String maSach) {
        String sql = "SELECT 1 FROM sach WHERE MASACH = ? AND trangThaiXoa = 0 LIMIT 1";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}