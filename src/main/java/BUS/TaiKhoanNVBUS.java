package BUS;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import DAO.TaiKhoanNVDAO;
import DTO.TaiKhoanNVDTO;

public class TaiKhoanNVBUS {
    private TaiKhoanNVDAO taiKhoanDAO = new TaiKhoanNVDAO();

    public boolean checkValidate(TaiKhoanNVDTO taiKhoan) {
    if (taiKhoan.getMaNV() == null || taiKhoan.getMaNV().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Mã nhân viên không được để trống.");
        return false;
    }

    if (taiKhoan.getMatKhau() == null || taiKhoan.getMatKhau().length() < 8) {
        JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 8 ký tự.");
        return false;
    }

    if (taiKhoan.getMaQuyen() == null || taiKhoan.getMaQuyen().trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Mã quyền không được để trống.");
        return false;
    }

    return true;
}



    public ArrayList<TaiKhoanNVDTO> getAllTaiKhoan() {
        return taiKhoanDAO.getAll();
    }

public boolean updateTaiKhoan(TaiKhoanNVDTO taiKhoan) {
    if (!checkValidate(taiKhoan)) {
        return false;
    }
    return taiKhoanDAO.update(taiKhoan) > 0;
}



public boolean addTaiKhoan(TaiKhoanNVDTO taiKhoan) {
    if (!checkValidate(taiKhoan)) {
        return false;
    }

    if (isTaiKhoanExists(taiKhoan.getMaNV())) {
        JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại.");
        return false;
    }

    return taiKhoanDAO.add(taiKhoan) > 0;
}


    public boolean deleteTaiKhoan(String maNV) {
        return taiKhoanDAO.delete(maNV) > 0;
    }

    public TaiKhoanNVDTO getTaiKhoanById(String maNV) {
        return taiKhoanDAO.getById(maNV);
    }
    // Kiểm tra xem tài khoản có tồn tại hay không
    public boolean isTaiKhoanExists(String maNV) {
        return taiKhoanDAO.getById(maNV) != null;
    }

    // Kiểm tra đăng nhập
    public boolean checkLogin(String maNV, String pass, Integer trangThaiXoa) {
        if (maNV == null || maNV.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            return false;
        }

        TaiKhoanNVDTO taiKhoan = taiKhoanDAO.getById(maNV);
        if (taiKhoan == null) {
            return false;
        }

        return taiKhoan.getMatKhau().equals(pass) && taiKhoan.getTrangThaiXoa() == 0;
    }

    // public boolean hasPermission(String username, int maCN, String maHD){
    // return taiKhoanDAO.hasPermission(username, maCN, maHD);
    // }

}