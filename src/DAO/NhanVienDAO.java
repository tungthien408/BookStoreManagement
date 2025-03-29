package src.DAO;

import src.DTO.NhanVienDTO;
import Service.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    
    public NhanVienDTO getNhanVienBySDT(String sdt) throws SQLException {
        String query = "SELECT HoTen, MaNV, ChucVu, DiaChi, SDT, NgaySinh FROM nhanvien WHERE SDT = ?";
        Connection conn = Data.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        NhanVienDTO nv = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, sdt);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nv = getNhanVienInfo(rs);
            }
        } finally {
            closeResources(rs, stmt, conn);
        }
        return nv;
    }

    public NhanVienDTO getNhanVienByMaNV(String maNV) throws SQLException {
        String query = "SELECT HoTen, MaNV, ChucVu, DiaChi, SDT, NgaySinh FROM nhanvien WHERE MaNV = ?";
        Connection conn = Data.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        NhanVienDTO nv = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, maNV);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nv = getNhanVienInfo(rs);
            }
        } finally {
            closeResources(rs, stmt, conn);
        }
        return nv;
    }

    private NhanVienDTO getNhanVienInfo(ResultSet rs) throws SQLException {
        NhanVienDTO nv = new NhanVienDTO();
        nv.setMaNV(rs.getString("MaNV"));
        nv.setHoTen(rs.getString("HoTen"));
        nv.setChucVu(rs.getString("ChucVu"));
        nv.setDiaChi(rs.getString("DiaChi"));
        nv.setSdt(rs.getString("SDT"));
        nv.setNgaySinh(rs.getString("NgaySinh"));
        return nv;
    }

    public boolean isSDTExists(String nvsdt) throws SQLException {
        String query = "SELECT 1 FROM nhanvien WHERE SDT = ? LIMIT 1";
        return checkExists(query, nvsdt);
    }

    public boolean isMaNVExists(String nvma) throws SQLException {
        String query = "SELECT 1 FROM nhanvien WHERE MaNV = ? LIMIT 1";
        return checkExists(query, nvma);
    }

    private boolean checkExists(String query, String param) throws SQLException {
        Connection conn = Data.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, param);
            rs = stmt.executeQuery();
            exists = rs.next();
        } finally {
            closeResources(rs, stmt, conn);
        }
        return exists;
    }

    public boolean addNhanVien(NhanVienDTO nv) throws SQLException {
        String query = "INSERT INTO nhanvien (MaNV, HoTen, ChucVu, DiaChi, SDT, NgaySinh) VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(query, nv);
    }

    public boolean updateNhanVien(NhanVienDTO nv) throws SQLException {
        String query = "UPDATE nhanvien SET HoTen = ?, ChucVu = ?, DiaChi = ?, NgaySinh = ? WHERE MaNV = ?";
        return executeUpdate(query, nv);
    }

    public boolean deleteNhanVien(String nvma) throws SQLException {
        String query = "DELETE FROM nhanvien WHERE MaNV = ?";
        return executeDelete(query, nvma);
    }

    public List<NhanVienDTO> getAllNhanVien() throws SQLException {
        String query = "SELECT MaNV, HoTen, ChucVu, DiaChi, SDT, NgaySinh FROM nhanvien";
        List<NhanVienDTO> dsNV = new ArrayList<>();
        Connection conn = Data.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                dsNV.add(getNhanVienInfo(rs));
            }
        } finally {
            closeResources(rs, stmt, conn);
        }
        return dsNV;
    }

    private boolean executeUpdate(String query, NhanVienDTO nv) throws SQLException {
        Connection conn = Data.getConnection();
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getChucVu());
            stmt.setString(3, nv.getDiaChi());
            stmt.setString(4, nv.getNgaySinh());
            stmt.setString(5, nv.getMaNV());

            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(null, stmt, conn);
        }
        return success;
    }

    private boolean executeDelete(String query, String param) throws SQLException {
        Connection conn = Data.getConnection();
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, param);
            success = stmt.executeUpdate() > 0;
        } finally {
            closeResources(null, stmt, conn);
        }
        return success;
    }

    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
}
