package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.KhachHangDTO;
import Service.Data;

public class KhachHangDAO {
    
    public KhachHangDTO getKhachHangBySDT(String khsdt) throws SQLException {
        String query = "SELECT SDT, HoTen, Diem FROM khachhang WHERE SDT = ?";
        
        try (Connection conn = Data.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, khsdt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getKhachHangInfo(rs);
                }
            }
        }
        return null;
    }

    private KhachHangDTO getKhachHangInfo(ResultSet rs) throws SQLException {
        KhachHangDTO kh = new KhachHangDTO();
        kh.setSdt(rs.getString("SDT"));
        kh.setHoTen(rs.getString("HoTen"));
        kh.setDiem(rs.getInt("Diem"));
        return kh;
    }

    public boolean isSDTExists(String khsdt) throws SQLException {
        String query = "SELECT 1 FROM khachhang WHERE SDT = ? LIMIT 1";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, khsdt);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean addKhachHang(KhachHangDTO kh) throws SQLException {
        String query = "INSERT INTO khachhang (SDT, HoTen, Diem) VALUES (?, ?, ?)";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kh.getSdt());
            stmt.setString(2, kh.getHoTen());
            stmt.setInt(3, kh.getDiem());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateKhachHang(KhachHangDTO kh) throws SQLException {
        String query = "UPDATE khachhang SET HoTen = ?, Diem = ? WHERE SDT = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kh.getHoTen());
            stmt.setInt(2, kh.getDiem());
            stmt.setString(3, kh.getSdt());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteKhachHang(String sdt) throws SQLException {
        String query = "DELETE FROM khachhang WHERE SDT = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sdt);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<KhachHangDTO> getAllKhachHang() throws SQLException {
        List<KhachHangDTO> dskh = new ArrayList<>();
        String query = "SELECT SDT, HoTen, Diem FROM khachhang";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dskh.add(getKhachHangInfo(rs));
            }
        }
        return dskh;
    }
}
