package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.TaiKhoanNVDTO;
import Service.Data;

public class TaiKhoanNVDAO {

    public int add(TaiKhoanNVDTO obj) {
        String sql = "INSERT INTO taikhoannv (maNV, PASS, maQuyen, trangThaiXoa) VALUES (?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaNV());        // đúng thứ tự: maNV
            ps.setString(2, obj.getMatKhau());     // PASS
            ps.setString(3, obj.getMaQuyen());     // maQuyen
            ps.setInt(4, obj.getTrangThaiXoa());   // trangThaiXoa
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(TaiKhoanNVDTO obj) {
        String sql = "UPDATE taikhoannv SET PASS = ?, maQuyen = ? WHERE maNV = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMatKhau());     // PASS
            ps.setString(2, obj.getMaQuyen());     // maQuyen
            ps.setString(3, obj.getMaNV());        // maNV (điều kiện WHERE)
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String maNV) {
        String sql = "UPDATE taikhoannv SET trangThaiXoa = 1 WHERE maNV = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<TaiKhoanNVDTO> getAll() {
        ArrayList<TaiKhoanNVDTO> dsTaiKhoan = new ArrayList<>();
        String sql = "SELECT * FROM taikhoannv WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dsTaiKhoan.add(new TaiKhoanNVDTO(
                    rs.getString("maNV"),
                    rs.getString("PASS"),
                    rs.getInt("trangThaiXoa"),
                    rs.getString("maQuyen")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsTaiKhoan;
    }

    public TaiKhoanNVDTO getById(String maNV) {
        String sql = "SELECT * FROM taikhoannv WHERE maNV = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoanNVDTO(
                        rs.getString("maNV"),
                        rs.getString("PASS"),
                        rs.getInt("trangThaiXoa"),
                        rs.getString("maQuyen")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
