package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.TaiKhoanNVDTO;
import Service.Data;

public class TaiKhoanNVDAO {
    // Thêm một tài khoản nhân viên
    public boolean create(TaiKhoanNVDTO taiKhoan) {
        String sql = "INSERT INTO taikhoannv (MANV, PASS, trangThaiXoa) VALUES (?, ?, ?)";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taiKhoan.getMaNV());
            stmt.setString(2, taiKhoan.getPass());
            stmt.setInt(3, taiKhoan.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả tài khoản nhân viên chưa bị xóa (trangThaiXoa = 0)
    public List<TaiKhoanNVDTO> getAll() {
        List<TaiKhoanNVDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM taikhoannv WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                taiKhoan.setMaNV(rs.getString("MANV"));
                taiKhoan.setPass(rs.getString("PASS"));
                taiKhoan.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(taiKhoan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tài khoản nhân viên theo MANV
    public TaiKhoanNVDTO getByMaNV(String maNV) {
        String sql = "SELECT * FROM taikhoannv WHERE MANV = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                    taiKhoan.setMaNV(rs.getString("MANV"));
                    taiKhoan.setPass(rs.getString("PASS"));
                    taiKhoan.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return taiKhoan;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật tài khoản nhân viên
    public boolean update(TaiKhoanNVDTO taiKhoan) {
        String sql = "UPDATE taikhoannv SET PASS = ?, trangThaiXoa = ? WHERE MANV = ?";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taiKhoan.getPass());
            stmt.setInt(2, taiKhoan.getTrangThaiXoa());
            stmt.setString(3, taiKhoan.getMaNV());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm (cập nhật trangThaiXoa = 1)
    public boolean delete(String maNV) {
        String sql = "UPDATE taikhoannv SET trangThaiXoa = 1 WHERE MANV = ?";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}