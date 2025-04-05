package DAO;

import DTO.SachDTO;
import src.main.Service.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    // Lấy sách theo mã sách
    public SachDTO getSachByID(String maSach) throws SQLException {
        String query = "SELECT * FROM sach WHERE MASACH = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maSach);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getSachInfo(rs);
                }
            }
        }
        return null;
    }

    // Lấy danh sách tất cả sách
    public List<SachDTO> getAllSach() throws SQLException {
        List<SachDTO> dsSach = new ArrayList<>();
        String query = "SELECT * FROM sach";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                dsSach.add(getSachInfo(rs));
            }
        }
        return dsSach;
    }

    // Kiểm tra mã tác giả & mã nhà xuất bản có tồn tại trước khi thêm sách
    public boolean isForeignKeyValid(String maTG, String maNXB) throws SQLException {
        String queryTG = "SELECT 1 FROM tacgia WHERE MATG = ?";
        String queryNXB = "SELECT 1 FROM nhaxuatban WHERE MANXB = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmtTG = conn.prepareStatement(queryTG);
             PreparedStatement stmtNXB = conn.prepareStatement(queryNXB)) {

            stmtTG.setString(1, maTG);
            ResultSet rsTG = stmtTG.executeQuery();
            boolean tgExists = rsTG.next();
            rsTG.close();

            stmtNXB.setString(1, maNXB);
            ResultSet rsNXB = stmtNXB.executeQuery();
            boolean nxbExists = rsNXB.next();
            rsNXB.close();

            return tgExists && nxbExists; // Chỉ hợp lệ khi cả hai tồn tại
        }
    }

    // Thêm sách mới
    public boolean addSach(SachDTO sach) throws SQLException {
        if (!isForeignKeyValid(sach.getMaTG(), sach.getMaNXB())) {
            return false; // Không thêm được nếu khóa ngoại không hợp lệ
        }

        String query = "INSERT INTO sach (MASACH, TenSach, TheLoai, SoLuong, DonGia, MATG, MANXB) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sach.getMaSach());
            stmt.setString(2, sach.getTenSach());
            stmt.setString(3, sach.getTheLoai());
            stmt.setInt(4, sach.getSoLuong());
            stmt.setInt(5, sach.getDonGia());
            stmt.setString(6, sach.getMaTG());
            stmt.setString(7, sach.getMaNXB());

            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật thông tin sách
    public boolean updateSach(SachDTO sach) throws SQLException {
        if (!isForeignKeyValid(sach.getMaTG(), sach.getMaNXB())) {
            return false;
        }

        String query = "UPDATE sach SET TenSach = ?, TheLoai = ?, SoLuong = ?, DonGia = ?, MATG = ?, MANXB = ? WHERE MASACH = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sach.getTenSach());
            stmt.setString(2, sach.getTheLoai());
            stmt.setInt(3, sach.getSoLuong());
            stmt.setInt(4, sach.getDonGia());
            stmt.setString(5, sach.getMaTG());
            stmt.setString(6, sach.getMaNXB());
            stmt.setString(7, sach.getMaSach());

            return stmt.executeUpdate() > 0;
        }
    }

    // Kiểm tra sách có đang được sử dụng trong các bảng khác không (chitiethoadon, phieunhap)
    public boolean isSachUsedInOtherTables(String maSach) throws SQLException {
        String queryChiTietHD = "SELECT 1 FROM chitiethoadon WHERE MASACH = ?";
        String queryPhieuNhap = "SELECT 1 FROM phieunhap WHERE MAPN IN (SELECT MAPN FROM chitietphieunhap WHERE MASACH = ?)";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmtHD = conn.prepareStatement(queryChiTietHD);
             PreparedStatement stmtPN = conn.prepareStatement(queryPhieuNhap)) {

            stmtHD.setString(1, maSach);
            ResultSet rsHD = stmtHD.executeQuery();
            boolean existsInHD = rsHD.next();
            rsHD.close();

            stmtPN.setString(1, maSach);
            ResultSet rsPN = stmtPN.executeQuery();
            boolean existsInPN = rsPN.next();
            rsPN.close();

            return existsInHD || existsInPN;
        }
    }

    // Xóa sách theo mã (kiểm tra trước khi xóa)
    public boolean deleteSach(String maSach) throws SQLException {
        if (isSachUsedInOtherTables(maSach)) {
            return false; // Không thể xóa nếu sách đang được sử dụng
        }

        String query = "DELETE FROM sach WHERE MASACH = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maSach);
            return stmt.executeUpdate() > 0;
        }
    }

    // Hàm hỗ trợ chuyển ResultSet thành SachDTO
    private SachDTO getSachInfo(ResultSet rs) throws SQLException {
        return new SachDTO(
            rs.getString("MASACH"),
            rs.getString("TenSach"),
            rs.getString("TheLoai"),
            rs.getInt("SoLuong"),
            rs.getInt("DonGia"),
            rs.getString("MATG"),
            rs.getString("MANXB")
        );
    }
}
