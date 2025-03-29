package src.BUS;

import src.DAO.PhieuNhapDAO;
import src.DTO.PhieuNhapDTO;
import java.sql.SQLException;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
    }

    // Lấy tất cả phiếu nhập
    public List<PhieuNhapDTO> getAllPhieuNhap() throws SQLException {
        return phieuNhapDAO.getAllPhieuNhap();
    }

    // Lấy phiếu nhập theo mã
    public PhieuNhapDTO getPhieuNhapByID(String maPN) throws SQLException {
        return phieuNhapDAO.getPhieuNhapByID(maPN);
    }

    // Thêm phiếu nhập mới
    public boolean addPhieuNhap(PhieuNhapDTO pn) throws SQLException {
        // Kiểm tra khóa ngoại trước khi thêm
        if (!phieuNhapDAO.isForeignKeyValid(pn.getMaNV(), pn.getMaNXB())) {
            System.out.println("Lỗi: Mã nhân viên hoặc mã nhà xuất bản không tồn tại!");
            return false;
        }

        return phieuNhapDAO.addPhieuNhap(pn);
    }

    // Xóa phiếu nhập
    public boolean deletePhieuNhap(String maPN) throws SQLException {
        // Kiểm tra phiếu nhập có tồn tại không
        if (phieuNhapDAO.getPhieuNhapByID(maPN) == null) {
            System.out.println("Lỗi: Phiếu nhập không tồn tại!");
            return false;
        }

        return phieuNhapDAO.deletePhieuNhap(maPN);
    }
}
