package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.ChiTietPhieuNhapDTO;
import Service.Data;

public class ChiTietPhieuNhapDAO {

    public int add(ChiTietPhieuNhapDTO obj) {
        String sql = "INSERT INTO chitietphieunhap (MASACH, MAPN, SoLuong, GiaNhap) VALUES (?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaSach());
            ps.setString(2, obj.getMaPN());
            ps.setInt(3, obj.getSoLuong());
            ps.setInt(4, obj.getGiaNhap());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(ChiTietPhieuNhapDTO obj) {
        String sql = "UPDATE chitietphieunhap SET SoLuong = ?, GiaNhap = ? WHERE MASACH = ? AND MAPN = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, obj.getSoLuong());
            ps.setInt(2, obj.getGiaNhap());
            ps.setString(3, obj.getMaSach());
            ps.setString(4, obj.getMaPN());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String maSach, String maPN) {
        String sql = "DELETE FROM chitietphieunhap WHERE MASACH = ? AND MAPN = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            ps.setString(2, maPN);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<ChiTietPhieuNhapDTO> getAll() {
        ArrayList<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietphieunhap";
        try (Connection conn = Data.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ChiTietPhieuNhapDTO(
                        rs.getString("MASACH"),
                        rs.getString("MAPN"),
                        rs.getInt("SoLuong"),
                        rs.getInt("GiaNhap")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ChiTietPhieuNhapDTO getById(String maSach, String maPN) {
        String sql = "SELECT * FROM chitietphieunhap WHERE MASACH = ? AND MAPN = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            ps.setString(2, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ChiTietPhieuNhapDTO(
                            rs.getString("MASACH"),
                            rs.getString("MAPN"),
                            rs.getInt("SoLuong"),
                            rs.getInt("GiaNhap")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhapByMaPn(String maPN) {
        ArrayList<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM chitietphieunhap WHERE MAPN = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietPhieuNhapDTO(
                            rs.getString("MASACH"),
                            rs.getString("MAPN"),
                            rs.getInt("SoLuong"),
                            rs.getInt("GiaNhap")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTongSoLuongNhapTheoMaSP(String maSach) {
        String sql = "SELECT SUM(SoLuong) AS tongNhap FROM chitietphieunhap WHERE MASACH = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("tongNhap");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getAllByDateRange(String startDate, String endDate) {
        ArrayList<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT ct.* FROM chitietphieunhap ct " +
                     "JOIN phieunhap pn ON ct.MAPN = pn.MAPN " +
                     "WHERE pn.ngayNhap BETWEEN ? AND ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ChiTietPhieuNhapDTO(
                            rs.getString("MASACH"),
                            rs.getString("MAPN"),
                            rs.getInt("SoLuong"),
                            rs.getInt("GiaNhap")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tổng số lượng sách đã bán theo MASACH
    public int getSoLuongByMaSach(String maSach) {
        String sql = "SELECT SUM(SoLuong) AS Total FROM chitietphieunhap WHERE MASACH = ?";
        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSach);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}