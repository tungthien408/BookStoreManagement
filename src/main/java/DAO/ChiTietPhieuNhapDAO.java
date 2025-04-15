package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DTO.ChiTietPhieuNhapDTO;

public class ChiTietPhieuNhapDAO {
    private Connection conn;

    // Constructor giả định bạn đã có kết nối cơ sở dữ liệu
    public ChiTietPhieuNhapDAO() {
        // Kết nối cơ sở dữ liệu (thay đổi thông tin kết nối theo hệ thống của bạn)
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ten_database", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Thêm một chi tiết phiếu nhập
    public void addChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) throws SQLException {
        String query = "INSERT INTO CHITIETPHIEUNHAP (MASACH, MAPN, SoLuong, GiaNhap) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, ctpn.getMaSach());
        stmt.setString(2, ctpn.getMaPN());
        stmt.setInt(3, ctpn.getSoLuong());
        stmt.setInt(4, ctpn.getGiaNhap());
        stmt.executeUpdate();
        stmt.close();
    }

    // Lấy danh sách tất cả chi tiết phiếu nhập
    public List<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() throws SQLException {
        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String query = "SELECT * FROM CHITIETPHIEUNHAP";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(
                rs.getString("MASACH"),
                rs.getString("MAPN"),
                rs.getInt("SoLuong"),
                rs.getInt("GiaNhap")
            );
            list.add(ctpn);
        }
        rs.close();
        stmt.close();
        return list;
    }

    // Cập nhật chi tiết phiếu nhập
    public void updateChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) throws SQLException {
        String query = "UPDATE CHITIETPHIEUNHAP SET SoLuong = ?, GiaNhap = ? WHERE MASACH = ? AND MAPN = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, ctpn.getSoLuong());
        stmt.setInt(2, ctpn.getGiaNhap());
        stmt.setString(3, ctpn.getMaSach());
        stmt.setString(4, ctpn.getMaPN());
        stmt.executeUpdate();
        stmt.close();
    }

    // Xóa chi tiết phiếu nhập
    public void deleteChiTietPhieuNhap(String maSach, String maPN) throws SQLException {
        String query = "DELETE FROM CHITIETPHIEUNHAP WHERE MASACH = ? AND MAPN = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, maSach);
        stmt.setString(2, maPN);
        stmt.executeUpdate();
        stmt.close();
    }

    // Lấy danh sách chi tiết phiếu nhập theo MAPN
    public List<ChiTietPhieuNhapDTO> getChiTietPhieuNhapByMaPN(String maPN) throws SQLException {
        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String query = "SELECT * FROM CHITIETPHIEUNHAP WHERE MAPN = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maPN);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhapDTO ctpn = new ChiTietPhieuNhapDTO(
                        rs.getString("MASACH"),
                        rs.getString("MAPN"),
                        rs.getInt("SoLuong"),
                        rs.getInt("GiaNhap")
                    );
                    list.add(ctpn);
                }
            }
        }
        return list;
    }
}