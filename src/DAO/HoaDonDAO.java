package DAO;

import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import Service.Data;
import Service.Lib;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private int count = 0;

    public String getID() {
		count++;
		Integer a = count;
		String str = a.toString();
		while (str.length() != 3)
			str = "0" + str;
		str = "HD" + str;
		return str;
	}

    // Lấy hóa đơn theo mã (bao gồm thông tin nhân viên & khách hàng)
    public HoaDonDTO getHoaDonByID(String maHD) throws SQLException {
        String query = "SELECT * FROM hoadon WHERE MAHD = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getHoaDonInfo(rs);
                }
            }
        }
        return null;
    }

        private HoaDonDTO getHoaDonInfo(ResultSet rs) throws SQLException {
            HoaDonDTO hd = new HoaDonDTO();
        hd.setSdt(rs.getString("SDT"));
        hd.setMaHD(rs.getString("MaHD"));
        hd.setMaNV(rs.getString("MaNV"));
        hd.setTongTien(rs.getInt("TongTien"));
        hd.setNgayBan(rs.getDate("NgayBan"));
        return hd;
    }

    // Thêm hóa đơn
    public boolean addHoaDon(HoaDonDTO hoaDon) throws SQLException {

        String query = "INSERT INTO hoadon (MaHD, MaNV, SDT, NgayBan, TongTien) VALUES (?, ?, ?, ?, ?)";
        if(!isChiTietHoaDonExists(hoaDon.getSdt(),hoaDon.getMaNV()))
            return false;
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, getID());
            stmt.setString(2, hoaDon.getMaNV());
            stmt.setString(3, hoaDon.getSdt());
            stmt.setDate(4, Lib.getDateNow()); // Lấy ngày hiện tại tự động
            stmt.setInt(5, 0);
    
            return stmt.executeUpdate() > 0;
        }
    }
public boolean themHoaDon(HoaDonDTO hoaDon, List<ChiTietHoaDonDTO> danhSachCTHD) throws SQLException {
    Connection conn = Data.getConnection();
    try {
        conn.setAutoCommit(false); // Bắt đầu transaction

        // 1. Thêm hóa đơn
        String queryHoaDon = "INSERT INTO hoadon (MAHD, MANV, SDT, NgayBan, TongTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(queryHoaDon)) {
            stmt.setString(1, getID());
            stmt.setString(2, hoaDon.getMaNV());
            stmt.setString(3, hoaDon.getSdt());
            stmt.setDate(4, Lib.getDateNow()); // Lấy ngày hiện tại tự động
            stmt.setInt(5, 0);

            stmt.executeUpdate();
        }

        // 2. Thêm chi tiết hóa đơn
        String queryCTHD = "INSERT INTO chitiethoadon (MAHD, MASACH, SoLuong, Gia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(queryCTHD)) {
            for (ChiTietHoaDonDTO cthd : danhSachCTHD) {
                // Kiểm tra tồn kho
                String checkStock = "SELECT SoLuong FROM sach WHERE MASACH = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkStock)) {
                    checkStmt.setString(1, cthd.getMaSach());
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt("SoLuong") < cthd.getSoLuong()) {
                        throw new SQLException("Sách không đủ tồn kho!");
                    }
                }

                // Thêm vào CTHD
                stmt.setString(1, hoaDon.getMaHD());
                stmt.setString(2, cthd.getMaSach());
                stmt.setInt(3, cthd.getSoLuong());
                stmt.setInt(4, cthd.getGia());
                stmt.executeUpdate();

                // Cập nhật số lượng sách trong kho
                String updateStock = "UPDATE sach SET SoLuong = SoLuong - ? WHERE MASACH = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateStock)) {
                    updateStmt.setInt(1, cthd.getSoLuong());
                    updateStmt.setString(2, cthd.getMaSach());
                    updateStmt.executeUpdate();
                }
            }
        }

        // 3. Cập nhật tổng tiền
        String updateTongTien = "UPDATE hoadon SET TongTien = (SELECT SUM(SoLuong * Gia) FROM chitiethoadon WHERE MAHD = ?) WHERE MAHD = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateTongTien)) {
            stmt.setString(1, hoaDon.getMaHD());
            stmt.setString(2, hoaDon.getMaHD());
            stmt.executeUpdate();
        }

        conn.commit(); // Xác nhận giao dịch
        return true;
    } catch (Exception e) {
        conn.rollback(); // Quay lui nếu có lỗi
        throw e;
    } finally {
        conn.setAutoCommit(true);
        conn.close();
    }
}



    // kiểm tra khách hàng và mã nhân viên có tồn tại không để thêm vào hóa đơn 
    public boolean isChiTietHoaDonExists(String maKH, String maNV) throws SQLException {
           return ((isSDTExists(maKH) || isMaNVExists(maNV)) && isMaNVExists(maNV));
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

    public boolean isMaNVExists(String manv) throws SQLException {
        String query = "SELECT 1 FROM nhanvien WHERE MANV = ? LIMIT 1";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, manv);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    


    // Xóa hóa đơn
    public boolean deleteHoaDon(String maHD) throws SQLException {
        String query = "DELETE FROM hoadon WHERE MaHD = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, maHD);
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy tất cả hóa đơn (kèm thông tin nhân viên & khách hàng)
    public List<HoaDonDTO> getAllHoaDon() throws SQLException {
        List<HoaDonDTO> dsHoaDon = new ArrayList<>();
        String query = "SELECT * FROM HoaDon";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                dsHoaDon.add(getHoaDonInfo(rs));
            }
        }
        
        return dsHoaDon;
    }


}
