package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.QuyenDTO;
import Service.Data;

public class QuyenDAO {

    public int add(QuyenDTO obj) {
        String sql = "INSERT INTO quyen (maQuyen, tenQuyen) VALUES (?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaQuyen());
            ps.setString(2, obj.getTenQuyen());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(QuyenDTO obj, String originalMaQuyen) { // Added originalMaQuyen parameter
        String sql = "UPDATE quyen SET maQuyen = ?, tenQuyen = ? WHERE maQuyen = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaQuyen()); // New maQuyen
            ps.setString(2, obj.getTenQuyen()); // New tenQuyen
            ps.setString(3, originalMaQuyen);   // Original maQuyen for WHERE clause
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String maQuyen) {
        String sql = "UPDATE quyen SET trangThaiXoa = 1 WHERE maQuyen = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maQuyen);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<QuyenDTO> getAll() {
        ArrayList<QuyenDTO> dsQuyen = new ArrayList<>();
        String sql = "SELECT * FROM quyen WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dsQuyen.add(new QuyenDTO(
                    rs.getString("maQuyen"),
                    rs.getString("tenQuyen"),
                    rs.getInt("trangThaiXoa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsQuyen;
    }

    public QuyenDTO getById(String maQuyen) {
        String sql = "SELECT * FROM quyen WHERE maQuyen = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maQuyen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new QuyenDTO(
                        rs.getString("maQuyen"),
                        rs.getString("tenQuyen"),
                        rs.getInt("trangThaiXoa")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
