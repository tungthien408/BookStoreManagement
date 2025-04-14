package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;

public class ChiTietPhieuNhapBUS {
    private ChiTietPhieuNhapDAO chiTietPhieuNhapDAO;

    public ChiTietPhieuNhapBUS() {
        chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
    }

    // Thêm chi tiết phiếu nhập
    public void addChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) {
        try {
            // Kiểm tra logic nghiệp vụ trước khi thêm
            if (ctpn.getSoLuong() <= 0 || ctpn.getGiaNhap() <= 0) {
                throw new IllegalArgumentException("Số lượng và giá nhập phải lớn hơn 0!");
            }
            chiTietPhieuNhapDAO.addChiTietPhieuNhap(ctpn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách chi tiết phiếu nhập
    public List<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() {
        try {
            return chiTietPhieuNhapDAO.getAllChiTietPhieuNhap();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Cập nhật chi tiết phiếu nhập
    public void updateChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) {
        try {
            if (ctpn.getSoLuong() <= 0 || ctpn.getGiaNhap() <= 0) {
                throw new IllegalArgumentException("Số lượng và giá nhập phải lớn hơn 0!");
            }
            chiTietPhieuNhapDAO.updateChiTietPhieuNhap(ctpn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa chi tiết phiếu nhập
    public void deleteChiTietPhieuNhap(String maSach, String maPN) {
        try {
            chiTietPhieuNhapDAO.deleteChiTietPhieuNhap(maSach, maPN);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}