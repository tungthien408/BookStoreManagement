package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.NhanVienDTO;
import Service.Data;

public class NhanVienDAO {
    // Thêm một nhân viên mới
    public boolean create(NhanVienDTO nhanVien) {
        String sql = "INSERT INTO nhanvien (MaNV, HoTen, ChucVu, DiaChi, SDT, Cccd, NgaySinh, trangThaiXoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nhanVien.getMaNV());
            stmt.setString(2, nhanVien.getHoTen());
            stmt.setString(3, nhanVien.getChucVu());
            stmt.setString(4, nhanVien.getDiaChi());
            stmt.setString(5, nhanVien.getSdt());
            stmt.setString(6, nhanVien.getCccd());
            stmt.setDate(7, nhanVien.getNgaySinh());
            stmt.setInt(8, nhanVien.getTrangThaiXoa());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả nhân viên chưa bị xóa (trangThaiXoa = 0)
    public List<NhanVienDTO> getAll() {
        List<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NhanVienDTO nhanVien = new NhanVienDTO();
                nhanVien.setMaNV(rs.getString("MaNV"));
                nhanVien.setHoTen(rs.getString("HoTen"));
                nhanVien.setChucVu(rs.getString("ChucVu"));
                nhanVien.setDiaChi(rs.getString("DiaChi"));
                nhanVien.setSdt(rs.getString("SDT"));
                nhanVien.setCccd(rs.getString("Cccd"));
                nhanVien.setNgaySinh(rs.getDate("NgaySinh"));
                nhanVien.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                list.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy nhân viên theo MaNV
    public NhanVienDTO getByMaNV(String maNV) {
        String sql = "SELECT * FROM nhanvien WHERE MaNV = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien.setMaNV(rs.getString("MaNV"));
                    nhanVien.setHoTen(rs.getString("HoTen"));
                    nhanVien.setChucVu(rs.getString("ChucVu"));
                    nhanVien.setDiaChi(rs.getString("DiaChi"));
                    nhanVien.setSdt(rs.getString("SDT"));
                    nhanVien.setCccd(rs.getString("Cccd"));
                    nhanVien.setNgaySinh(rs.getDate("NgaySinh"));
                    nhanVien.setTrangThaiXoa(rs.getInt("trangThaiXoa"));
                    return nhanVien;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin nhân viên
    public boolean update(NhanVienDTO nhanVien) {
        String sql = "UPDATE nhanvien SET HoTen = ?, ChucVu = ?, DiaChi = ?, SDT = ?, Cccd = ?, NgaySinh = ?, trangThaiXoa = ? WHERE MaNV = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nhanVien.getHoTen());
            stmt.setString(2, nhanVien.getChucVu());
            stmt.setString(3, nhanVien.getDiaChi());
            stmt.setString(4, nhanVien.getSdt());
            stmt.setString(5, nhanVien.getCccd());
            stmt.setDate(6, nhanVien.getNgaySinh());
            stmt.setInt(7, nhanVien.getTrangThaiXoa());
            stmt.setString(8, nhanVien.getMaNV());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm (cập nhật trangThaiXoa = 1)
    public boolean delete(String maNV) {
        String sql = "UPDATE nhanvien SET trangThaiXoa = 1 WHERE MaNV = ?";
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