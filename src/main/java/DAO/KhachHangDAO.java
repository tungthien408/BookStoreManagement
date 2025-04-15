package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.KhachHangDTO;
import Service.Data;

public class KhachHangDAO {
    // Thêm một khách hàng mới
    public boolean create(KhachHangDTO khachHang) {
        String sql = "INSERT INTO khachhang (MAKH, SDT, HoTen, Diem, trangThaiXoa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, khachHang.getMaKH());
            stmt.setString(2, khachHang.getSdt());
            stmt.setString(3, khachHang.getHoTen());
            stmt.setInt(4, khachHang.getDiem());
            stmt.setInt(5, khachHang.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả khách hàng chưa bị xóa (trangThaiXoa = 0)
    public List<KhachHangDTO> getAll() {
        List<KhachHangDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                KhachHangDTO khachHang = new KhachHangDTO();
                khachHang.setMaKH(rs.getString("MAKH"));
                khachHang.setSdt(rs.getString("SDT"));
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHang.setDiem(rs.getInt("Diem"));
                khachHang.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy khách hàng theo MAKH
    public KhachHangDTO getByMaKH(String maKH) {
        String sql = "SELECT * FROM khachhang WHERE MAKH = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKH);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKH(rs.getString("MAKH"));
                    khachHang.setSdt(rs.getString("SDT"));
                    khachHang.setHoTen(rs.getString("HoTen"));
                    khachHang.setDiem(rs.getInt("Diem"));
                    khachHang.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return khachHang;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin khách hàng
    public boolean update(KhachHangDTO khachHang) {
        String sql = "UPDATE khachhang SET SDT = ?, HoTen = ?, Diem = ?, trangThaiXoa = ? WHERE MAKH = ?";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, khachHang.getSdt());
            stmt.setString(2, khachHang.getHoTen());
            stmt.setInt(3, khachHang.getDiem());
            stmt.setInt(4, khachHang.getTrangThaiXoa());
            stmt.setString(5, khachHang.getMaKH());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(KhachHangDTO kh) throws SQLException {
        String query = "UPDATE khachhang SET HoTen = ?, Diem = ? WHERE SDT = ?"; // Lỗi

        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kh.getHoTen());
            stmt.setInt(2, kh.getDiem());
            stmt.setString(3, kh.getSdt());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}