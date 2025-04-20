package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.PhieuNhapDTO;
import Service.Data;

public class PhieuNhapDAO {
    // Thêm một phiếu nhập mới
    public boolean create(PhieuNhapDTO phieuNhap) {
        String sql = "INSERT INTO phieunhap (MAPN, MANV, NgayNhap, TongTien, MANXB, trangThaiXoa) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phieuNhap.getMaPN());
            stmt.setString(2, phieuNhap.getMaNV());
            stmt.setDate(3, phieuNhap.getNgayNhap());
            stmt.setDouble(4, phieuNhap.getTongTien());
            stmt.setString(5, phieuNhap.getMaNXB());
            stmt.setInt(6, phieuNhap.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả phiếu nhập chưa bị xóa (trangThaiXoa = 0)
    public List<PhieuNhapDTO> getAll() {
        List<PhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
                phieuNhap.setMaPN(rs.getString("MAPN"));
                phieuNhap.setMaNV(rs.getString("MANV"));
                phieuNhap.setNgayNhap(rs.getDate("NgayNhap"));
                phieuNhap.setTongTien(rs.getDouble("TongTien"));
                phieuNhap.setMaNXB(rs.getString("MANXB"));
                phieuNhap.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(phieuNhap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy phiếu nhập theo MAPN
    public PhieuNhapDTO getByMaPN(String maPN) {
        String sql = "SELECT * FROM phieunhap WHERE MAPN = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
                    phieuNhap.setMaPN(rs.getString("MAPN"));
                    phieuNhap.setMaNV(rs.getString("MANV"));
                    phieuNhap.setNgayNhap(rs.getDate("NgayNhap"));
                    phieuNhap.setTongTien(rs.getDouble("TongTien"));
                    phieuNhap.setMaNXB(rs.getString("MANXB"));
                    phieuNhap.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return phieuNhap;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin phiếu nhập
    public boolean delete(String maPN) {
        String sql = "UPDATE phieunhap SET trangThaiXoa = 1 WHERE MAPN = ?";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPN);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsByMaPN(String maPN) {
        String sql = "SELECT 1 FROM phieunhap WHERE MAPN = ? LIMIT 1";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}