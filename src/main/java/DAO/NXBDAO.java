package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.NXBDTO;
import Service.Data;

public class NXBDAO {
    // Thêm một nhà xuất bản mới
    public boolean create(NXBDTO nxb) {
        String sql = "INSERT INTO nhaxuatban (MANXB, TenNXB, DiaChi, SDT, trangThaiXoa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nxb.getMaNXB());
            stmt.setString(2, nxb.getTenNXB());
            stmt.setString(3, nxb.getDiaChi());
            stmt.setString(4, nxb.getSdt());
            stmt.setInt(5, nxb.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả nhà xuất bản chưa bị xóa (trangThaiXoa = 0)
    public List<NXBDTO> getAll() {
        List<NXBDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nhaxuatban WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NXBDTO nxb = new NXBDTO();
                nxb.setMaNXB(rs.getString("MANXB"));
                nxb.setTenNXB(rs.getString("TenNXB"));
                nxb.setDiaChi(rs.getString("DiaChi"));
                nxb.setSdt(rs.getString("SDT"));
                nxb.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(nxb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy nhà xuất bản theo MANXB
    public NXBDTO getByMaNXB(String maNXB) {
        String sql = "SELECT * FROM nhaxuatban WHERE MANXB = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNXB);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    NXBDTO nxb = new NXBDTO();
                    nxb.setMaNXB(rs.getString("MANXB"));
                    nxb.setTenNXB(rs.getString("TenNXB"));
                    nxb.setDiaChi(rs.getString("DiaChi"));
                    nxb.setSdt(rs.getString("SDT"));
                    nxb.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return nxb;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin nhà xuất bản
    public boolean update(NXBDTO nxb) {
        String sql = "UPDATE nhaxuatban SET TenNXB = ?, DiaChi = ?, SDT = ?, trangThaiXoa = ? WHERE MANXB = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nxb.getTenNXB());
            stmt.setString(2, nxb.getDiaChi());
            stmt.setString(3, nxb.getSdt());
            stmt.setInt(4, nxb.getTrangThaiXoa());
            stmt.setString(5, nxb.getMaNXB());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm (cập nhật trangThaiXoa = 1)
    public boolean delete(String maNXB) {
        String sql = "UPDATE nhaxuatban SET trangThaiXoa = 1 WHERE MANXB = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNXB);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}