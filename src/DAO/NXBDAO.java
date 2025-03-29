package DAO;

import DTO.NXBDTO;
import Service.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NXBDAO {

    public NXBDTO getNXBBySDT(String sdt) throws SQLException {
        String query = "SELECT MaNXB, TenNXB, DiaChi, SDT FROM nhaxuatban WHERE SDT = ?";
        return getNXB(query, sdt);
    }

    public NXBDTO getNXBByID(String maNXB) throws SQLException {
        String query = "SELECT MaNXB, TenNXB, DiaChi, SDT FROM nhaxuatban WHERE MaNXB = ?";
        return getNXB(query, maNXB);
    }

    // Hàm chung để giảm trùng lặp
    private NXBDTO getNXB(String query, String value) throws SQLException {
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? getNXBInfo(rs) : null;
            }
        }
    }

    private NXBDTO getNXBInfo(ResultSet rs) throws SQLException {
        return new NXBDTO(
            rs.getString("MaNXB"),
            rs.getString("TenNXB"),
            rs.getString("DiaChi"),
            rs.getString("SDT")
        );
    }

    public boolean isSDTvsIDExists(String sdt, String maNXB) throws SQLException {
        String query = "SELECT 1 FROM nhaxuatban WHERE SDT = ? AND MaNXB = ? LIMIT 1";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, sdt);
            stmt.setString(2, maNXB);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean addNXB(NXBDTO nxb) throws SQLException {
        String query = "INSERT INTO nhaxuatban (MaNXB, TenNXB, DiaChi, SDT) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, nxb.getMaNXB());
            stmt.setString(2, nxb.getTenNXB());
            stmt.setString(3, nxb.getDiaChi());
            stmt.setString(4, nxb.getSdt());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateNXB(NXBDTO nxb) throws SQLException {
        String query = "UPDATE nhaxuatban SET TenNXB = ?, DiaChi = ?, SDT = ? WHERE MaNXB = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, nxb.getTenNXB());
            stmt.setString(2, nxb.getDiaChi());
            stmt.setString(3, nxb.getSdt());
            stmt.setString(4, nxb.getMaNXB());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteNXB(String maNXB) throws SQLException {
        String query = "DELETE FROM nhaxuatban WHERE MaNXB = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, maNXB);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<NXBDTO> getAllNXB() throws SQLException {
        List<NXBDTO> dsNXB = new ArrayList<>();
        String query = "SELECT MaNXB, TenNXB, DiaChi, SDT FROM nhaxuatban";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                dsNXB.add(getNXBInfo(rs));
            }
        }
        
        return dsNXB;
    }
}
