package DAO;

import DTO.TacGiaDTO;
import src.main.Service.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacGiaDAO {

    public TacGiaDTO getTacGiaBySDT(String sdt) throws SQLException {
        String query = "SELECT MaTG, TenTG, DiaChi, SDT FROM tacgia WHERE SDT = ?";
        return getTacGia(query, sdt);
    }

    public TacGiaDTO getTacGiaByID(String maTG) throws SQLException {
        String query = "SELECT MaTG, TenTG, DiaChi, SDT FROM tacgia WHERE MaTG = ?";
        return getTacGia(query, maTG);
    }

    // Hàm chung để tái sử dụng logic lấy tác giả
    private TacGiaDTO getTacGia(String query, String value) throws SQLException {
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? getTacGiaInfo(rs) : null;
            }
        }
    }

    private TacGiaDTO getTacGiaInfo(ResultSet rs) throws SQLException {
        return new TacGiaDTO(
            rs.getString("MaTG"),
            rs.getString("TenTG"),
            rs.getString("DiaChi"),
            rs.getString("SDT")
        );
    }

    public boolean isSDTvsIDExists(String sdt, String maTG) throws SQLException {
        String query = "SELECT 1 FROM tacgia WHERE SDT = ? AND MaTG = ? LIMIT 1";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, sdt);
            stmt.setString(2, maTG);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean addTacGia(TacGiaDTO tg) throws SQLException {
        String query = "INSERT INTO tacgia (MaTG, TenTG, DiaChi, SDT) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, tg.getMaTG());
            stmt.setString(2, tg.getTenTG());
            stmt.setString(3, tg.getDiaChi());
            stmt.setString(4, tg.getSdt());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTacGia(TacGiaDTO tg) throws SQLException {
        String query = "UPDATE tacgia SET TenTG = ?, DiaChi = ?, SDT = ? WHERE MaTG = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, tg.getTenTG());
            stmt.setString(2, tg.getDiaChi());
            stmt.setString(3, tg.getSdt());
            stmt.setString(4, tg.getMaTG());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteTacGia(String maTG) throws SQLException {
        String query = "DELETE FROM tacgia WHERE MaTG = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, maTG);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<TacGiaDTO> getAllTacGia() throws SQLException {
        List<TacGiaDTO> dsTG = new ArrayList<>();
        String query = "SELECT MaTG, TenTG, DiaChi, SDT FROM tacgia";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                dsTG.add(getTacGiaInfo(rs));
            }
        }
        
        return dsTG;
    }
}
