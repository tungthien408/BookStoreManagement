package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    public HoaDonDTO getHoaDonByID(String maHD) throws SQLException {
        return hoaDonDAO.getHoaDonByID(maHD);
    }

    // kiểm tra khách hàng và mã nhân viên có tồn tại không để thêm vào hóa đơn 
    public boolean addHoaDon(HoaDonDTO hoaDon) throws SQLException {

        return hoaDonDAO.addHoaDon(hoaDon);
    }

    public boolean deleteHoaDon(String maHD) throws SQLException {
        return hoaDonDAO.deleteHoaDon(maHD);
    }

    public List<HoaDonDTO> getAllHoaDon() throws SQLException {
        return hoaDonDAO.getAllHoaDon();
    }

}
