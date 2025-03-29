package BUS;

import java.sql.SQLException;
import java.util.List;
import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        this.khachHangDAO = new KhachHangDAO();
    }

    public KhachHangDTO getKhachHangBySDT(String khsdt) {
        try {
            return khachHangDAO.getKhachHangBySDT(khsdt);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addKhachHang(KhachHangDTO kh) {
        try {
            return khachHangDAO.addKhachHang(kh);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(KhachHangDTO kh) {
        try {
            return khachHangDAO.updateKhachHang(kh);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhachHang(String sdt) {
        try {
            return khachHangDAO.deleteKhachHang(sdt);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KhachHangDTO> getAllKhachHang() {
        try {
            return khachHangDAO.getAllKhachHang();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
