package DAO;

import DTO.ChiTietHoaDonDTO;
import Service.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {

    // Thêm chi tiết hóa đơn
    public boolean addChiTietHoaDon(ChiTietHoaDonDTO cthd) throws SQLException {
        String query = "INSERT INTO chitiethoadon (MASACH, MAHD, SoLuong, Gia) VALUES (?, ?, ?, ?)";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cthd.getMaSach());
            stmt.setString(2, cthd.getMaHD());
            stmt.setInt(3, cthd.getSoLuong());
            stmt.setInt(4, cthd.getGia());

            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public List<ChiTietHoaDonDTO> getChiTietHoaDonByMaHD(String maHD) throws SQLException {
        List<ChiTietHoaDonDTO> dsCTHD = new ArrayList<>();
        String query = """
            SELECT MASACH, MAHD, SoLuong, Gia 
            FROM chitiethoadon 
            WHERE MAHD = ?
        """;

        try (Connection conn = Data.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDonDTO cthd = new ChiTietHoaDonDTO(
                        rs.getString("MASACH"),
                        rs.getString("MAHD"),
                        rs.getInt("SoLuong"),
                        rs.getInt("Gia")
                    );
                    dsCTHD.add(cthd);
                }
            }
        }
        return dsCTHD;
    }
    

    // Lấy danh sách tất cả chi tiết hóa đơn
    public List<ChiTietHoaDonDTO> getAllChiTietHoaDon() throws SQLException {
        List<ChiTietHoaDonDTO> dsCTHD = new ArrayList<>();
        String query = "SELECT * FROM chitiethoadon";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                dsCTHD.add(getChiTietHoaDonInfo(rs));
            }
        }
        return dsCTHD;
    }

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public List<ChiTietHoaDonDTO> getChiTietHoaDonByMaHD(String maHD) throws SQLException {
        List<ChiTietHoaDonDTO> dsCTHD = new ArrayList<>();
        String query = "SELECT * FROM chitiethoadon WHERE MAHD = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dsCTHD.add(getChiTietHoaDonInfo(rs));
                }
            }
        }
        return dsCTHD;
    }

    // Xóa chi tiết hóa đơn theo mã hóa đơn và mã sách
    public boolean deleteChiTietHoaDon(String maHD, String maSach) throws SQLException {
        String query = "DELETE FROM chitiethoadon WHERE MAHD = ? AND MASACH = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maSach);
            return stmt.executeUpdate() > 0;
        }
    }

    // Kiểm tra tồn tại chi tiết hóa đơn
    public boolean isChiTietHoaDonExists(String maHD, String maSach) throws SQLException {
        String query = "SELECT 1 FROM chitiethoadon WHERE MAHD = ? AND MASACH = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maHD);
            stmt.setString(2, maSach);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // Hàm hỗ trợ chuyển ResultSet thành ChiTietHoaDonDTO
    private ChiTietHoaDonDTO getChiTietHoaDonInfo(ResultSet rs) throws SQLException {
        return new ChiTietHoaDonDTO(
            rs.getString("MASACH"),
            rs.getString("MAHD"),
            rs.getInt("SoLuong"),
            rs.getInt("Gia")
        );
    }
}
