package src.DAO;

import src.DTO.PhieuNhapDTO;
import Service.Data;
import Service.Lib;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {
    private int count = 0;

    public String getID() {
		count++;
		Integer a = count;
		String str = a.toString();
		while (str.length() != 3)
			str = "0" + str;
		str = "NP" + str;
		return str;
	}


    // Lấy phiếu nhập theo mã
    public PhieuNhapDTO getPhieuNhapByID(String maPN) throws SQLException {
        String query = "SELECT * FROM phieunhap WHERE MAPN = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPN);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getPhieuNhapInfo(rs);
                }
            }
        }
        return null;
    }

    // Lấy danh sách tất cả phiếu nhập
    public List<PhieuNhapDTO> getAllPhieuNhap() throws SQLException {
        List<PhieuNhapDTO> dsPhieuNhap = new ArrayList<>();
        String query = "SELECT * FROM phieunhap";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                dsPhieuNhap.add(getPhieuNhapInfo(rs));
            }
        }
        return dsPhieuNhap;
    }

    // Thêm phiếu nhập mới
    public boolean addPhieuNhap(PhieuNhapDTO pn) throws SQLException {
        String query = "INSERT INTO phieunhap (MAPN, MANV, NgayNhap, TongTien, MANXB) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, getID());
            stmt.setString(2, pn.getMaNV());
            stmt.setDate(3, Lib.getDateNow()); // Ngày nhập tự động lấy thời gian hiện tại
            stmt.setDouble(4, pn.getTongTien());
            stmt.setString(5, pn.getMaNXB());

            return stmt.executeUpdate() > 0;
        }
    }


    // Xóa phiếu nhập
    public boolean deletePhieuNhap(String maPN) throws SQLException {
        String query = "DELETE FROM phieunhap WHERE MAPN = ?";
        
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, maPN);
            return stmt.executeUpdate() > 0;
        }
    }

    // Kiểm tra mã nhân viên & mã nhà xuất bản có tồn tại trước khi thêm phiếu nhập
    public boolean isForeignKeyValid(String maNV, String maNXB) throws SQLException {
        String queryNV = "SELECT 1 FROM nhanvien WHERE MANV = ?";
        String queryNXB = "SELECT 1 FROM nhaxuatban WHERE MANXB = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmtNV = conn.prepareStatement(queryNV);
             PreparedStatement stmtNXB = conn.prepareStatement(queryNXB)) {

            stmtNV.setString(1, maNV);
            ResultSet rsNV = stmtNV.executeQuery();
            boolean nvExists = rsNV.next();
            rsNV.close();

            stmtNXB.setString(1, maNXB);
            ResultSet rsNXB = stmtNXB.executeQuery();
            boolean nxbExists = rsNXB.next();
            rsNXB.close();

            return nvExists && nxbExists; // Chỉ hợp lệ khi cả hai đều tồn tại
        }
    }

    // Hàm hỗ trợ chuyển ResultSet thành PhieuNhapDTO
    private PhieuNhapDTO getPhieuNhapInfo(ResultSet rs) throws SQLException {
        return new PhieuNhapDTO(
            rs.getString("MAPN"),
            rs.getString("MANV"),
            rs.getDate("NgayNhap"),
            rs.getDouble("TongTien"),
            rs.getString("MANXB")
        );
    }
}
