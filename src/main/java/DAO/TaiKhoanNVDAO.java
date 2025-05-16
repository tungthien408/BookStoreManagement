package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.TaiKhoanNVDTO;
import Service.Data;

public class TaiKhoanNVDAO {
<<<<<<< HEAD

    public int add(TaiKhoanNVDTO obj) {
        String sql = "INSERT INTO taikhoannv ( maNV, PASS, maQuyen) VALUES (?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(2, obj.getMaNV());
            ps.setString(3, obj.getMatKhau());
            ps.setString(4, obj.getMaQuyen());
            return ps.executeUpdate();
=======
    // Thêm một tài khoản nhân viên
    public boolean create(TaiKhoanNVDTO taiKhoan) {
        String sql = "INSERT INTO taikhoannv (MANV, PASS,maQuyen, trangThaiXoa) VALUES (?, ?, ?)";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taiKhoan.getMaNV());
            stmt.setString(2, taiKhoan.getPass());
            stmt.setInt(3,taiKhoan.getMaQuyen());
            stmt.setInt(4, taiKhoan.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(TaiKhoanNVDTO obj) {
        String sql = "UPDATE taikhoannv SET  PASS = ?, maQuyen = ? WHERE maNV = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(2, obj.getMatKhau());
            ps.setString(3, obj.getMaQuyen());
            ps.setString(4, obj.getMaNV());
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
<<<<<<< HEAD
                dsTaiKhoan.add(new TaiKhoanNVDTO(
                    rs.getString("maNV"),
                    rs.getString("PASS"),
                    rs.getInt("trangThaiXoa"),
                    rs.getString("maQuyen")
                ));
=======
                TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                taiKhoan.setMaNV(rs.getString("MANV"));
                taiKhoan.setPass(rs.getString("PASS"));
                taiKhoan.setMaQuyen(rs.getInt("maQuyen"));
                taiKhoan.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(taiKhoan);
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
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
<<<<<<< HEAD
                    return new TaiKhoanNVDTO(
                        rs.getString("maNV"),
                        rs.getString("PASS"),
                        rs.getInt("trangThaiXoa"),
                        rs.getString("maQuyen")
                    );
=======
                    TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                    taiKhoan.setMaNV(rs.getString("MANV"));
                    taiKhoan.setPass(rs.getString("PASS"));
                    taiKhoan.setMaQuyen(rs.getInt("maQuyen"));
                    taiKhoan.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return taiKhoan;
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
<<<<<<< HEAD
}
=======

    // Cập nhật tài khoản nhân viên
    public boolean update(TaiKhoanNVDTO taiKhoan) {
        String sql = "UPDATE taikhoannv SET PASS = ?, trangThaiXoa = ? WHERE MANV = ?";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, taiKhoan.getPass());
            stmt.setInt(2, taiKhoan.getTrangThaiXoa());
            stmt.setString(3, taiKhoan.getMaNV());
            stmt.setInt(4, taiKhoan.getMaQuyen());
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

    public boolean hasPermission(String username, int maCN, String maHD) {
        String sql = """
            SELECT 1
            FROM taikhoan tk
            JOIN quyen q ON tk.maQuyen = q.maQuyen
            JOIN chitietchucnang ctcn ON q.maQuyen = ctcn.maQuyen
            WHERE ctcn.trangThaiXoa = 0
                AND ctcn.maCN = ?
                AND ctcn.maHD = ?
                AND tk.tenDangNhap = ?
            LIMIT 1
        """;

        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maCN);
            ps.setString(2, maHD);
            ps.setString(3, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Có kết quả -> có quyền
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }
}
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
