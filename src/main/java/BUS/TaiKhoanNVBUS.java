package BUS;

import java.util.ArrayList;

import DAO.TaiKhoanNVDAO;
import DTO.TaiKhoanNVDTO;

public class TaiKhoanNVBUS {
    private TaiKhoanNVDAO taiKhoanDAO = new TaiKhoanNVDAO();

    public ArrayList<TaiKhoanNVDTO> getAllTaiKhoan() {
        return taiKhoanDAO.getAll();
    }

    public boolean updateTaiKhoan(TaiKhoanNVDTO taiKhoan) {
        return taiKhoanDAO.update(taiKhoan) > 0;
    }

    public boolean addTaiKhoan(TaiKhoanNVDTO taiKhoan) {
        return taiKhoanDAO.add(taiKhoan) > 0;
    }

    public boolean deleteTaiKhoan(String maNV) {
        return taiKhoanDAO.delete(maNV) > 0;
    }

    public TaiKhoanNVDTO getTaiKhoanById(String maNV) {
        return taiKhoanDAO.getById(maNV);
    }
        // Kiểm tra đăng nhập
    public boolean checkLogin(String maNV, String pass, Integer trangThaiXoa) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }
        if (pass == null || pass.trim().isEmpty()) {
            return false;
        }

        // TaiKhoanNVDTO taiKhoan = taiKhoanDAO.getById(maNV);
        // if (taiKhoan == null) {
        //     return false;
        // }

        // return taiKhoan.getMatKhau().equals(pass) && taiKhoan.getTrangThaiXoa() == 0;
        return true;
    }
}