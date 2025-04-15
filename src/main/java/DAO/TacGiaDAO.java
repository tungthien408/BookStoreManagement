package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.TacGiaDTO;
import Service.Data;

public class TacGiaDAO {
    // Thêm một tác giả mới
    public boolean create(TacGiaDTO tacGia) {
        String sql = "INSERT INTO tacgia (MATG, TenTG, DiaChi, SDT, trangThaiXoa) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tacGia.getMaTG());
            stmt.setString(2, tacGia.getTenTG());
            stmt.setString(3, tacGia.getDiaChi());
            stmt.setString(4, tacGia.getSdt());
            stmt.setInt(5, tacGia.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả tác giả chưa bị xóa (trangThaiXoa = 0)
    public List<TacGiaDTO> getAll() {
        List<TacGiaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tacgia WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TacGiaDTO tacGia = new TacGiaDTO();
                tacGia.setMaTG(rs.getString("MATG"));
                tacGia.setTenTG(rs.getString("TenTG"));
                tacGia.setDiaChi(rs.getString("DiaChi"));
                tacGia.setSdt(rs.getString("SDT"));
                tacGia.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(tacGia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tác giả theo MATG
    public TacGiaDTO getByMaTG(String maTG) {
        String sql = "SELECT * FROM tacgia WHERE MATG = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maTG);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TacGiaDTO tacGia = new TacGiaDTO();
                    tacGia.setMaTG(rs.getString("MATG"));
                    tacGia.setTenTG(rs.getString("TenTG"));
                    tacGia.setDiaChi(rs.getString("DiaChi"));
                    tacGia.setSdt(rs.getString("SDT"));
                    tacGia.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return tacGia;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin tác giả
    public boolean update(TacGiaDTO tacGia) {
        String sql = "UPDATE tacgia SET TenTG = ?, DiaChi = ?, SDT = ?, trangThaiXoa = ? WHERE MATG = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tacGia.getTenTG());
            stmt.setString(2, tacGia.getDiaChi());
            stmt.setString(3, tacGia.getSdt());
            stmt.setInt(4, tacGia.getTrangThaiXoa());
            stmt.setString(5, tacGia.getMaTG());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm (cập nhật trangThaiXoa = 1)
    public boolean delete(String maTG) {
        String sql = "UPDATE tacgia SET trangThaiXoa = 1 WHERE MATG = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maTG);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}