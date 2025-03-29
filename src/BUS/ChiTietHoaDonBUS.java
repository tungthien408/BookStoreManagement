package BUS;

import DAO.ChiTietHoaDonDAO;
import DTO.ChiTietHoaDonDTO;
import java.sql.SQLException;
import java.util.List;

public class ChiTietHoaDonBUS {
    private final ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ChiTietHoaDonBUS() {
        this.chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    // Thêm chi tiết hóa đơn (kiểm tra MaHD & MaSach hợp lệ trước khi thêm)
    public boolean addChiTietHoaDon(ChiTietHoaDonDTO cthd) throws SQLException {
        if (!chiTietHoaDonDAO.isChiTietHoaDonExists(cthd.getMaHD(), cthd.getMaSach())) {
            System.out.println("Lỗi: MaHD hoặc MaSach không tồn tại!");
            return false;
        }
        return chiTietHoaDonDAO.addChiTietHoaDon(cthd);
    }

    // Xóa chi tiết hóa đơn
    public boolean deleteChiTietHoaDon(String maHD, String maSach) throws SQLException {
        return chiTietHoaDonDAO.deleteChiTietHoaDon(maHD, maSach);
    }

    // Lấy danh sách chi tiết hóa đơn theo MaHD
    public List<ChiTietHoaDonDTO> getChiTietHoaDonByMaHD(String maHD) throws SQLException {
        return chiTietHoaDonDAO.getChiTietHoaDonByMaHD(maHD);
    }

}
