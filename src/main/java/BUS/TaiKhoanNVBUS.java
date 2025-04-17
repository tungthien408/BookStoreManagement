package BUS;

import java.util.List;

import DAO.TaiKhoanNVDAO;
import DTO.TaiKhoanNVDTO;

public class TaiKhoanNVBUS {
    private TaiKhoanNVDAO taiKhoanDAO;

    public TaiKhoanNVBUS() {
        taiKhoanDAO = new TaiKhoanNVDAO();
    }

    // Thêm tài khoản nhân viên
    public boolean addTaiKhoan(TaiKhoanNVDTO taiKhoan) {
        // Kiểm tra dữ liệu trước khi thêm
        if (taiKhoan.getMaNV() == null || taiKhoan.getMaNV().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        if (taiKhoan.getPass() == null || taiKhoan.getPass().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống!");
        }

        // Kiểm tra xem MANV đã tồn tại chưa
        TaiKhoanNVDTO existing = taiKhoanDAO.getByMaNV(taiKhoan.getMaNV());
        if (existing != null) {
            throw new IllegalArgumentException("Mã nhân viên đã tồn tại!");
        }

        // Gọi DAO để thêm
        return taiKhoanDAO.create(taiKhoan);
    }

    // Lấy danh sách tài khoản
    public List<TaiKhoanNVDTO> getAllTaiKhoan() {
        return taiKhoanDAO.getAll();
    }

    // Lấy tài khoản theo MANV
    public TaiKhoanNVDTO getTaiKhoanByMaNV(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        return taiKhoanDAO.getByMaNV(maNV);
    }

    // Cập nhật tài khoản
    public boolean updateTaiKhoan(TaiKhoanNVDTO taiKhoan) {
        if (taiKhoan.getMaNV() == null || taiKhoan.getMaNV().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        if (taiKhoan.getPass() == null || taiKhoan.getPass().trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống!");
        }
        return taiKhoanDAO.update(taiKhoan);
    }

    // Xóa tài khoản (xóa mềm)
    public boolean deleteTaiKhoan(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        return taiKhoanDAO.delete(maNV);
    }

    // Kiểm tra đăng nhập
    public boolean checkLogin(String maNV, String pass, Integer trangThaiXoa) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }
        if (pass == null || pass.trim().isEmpty()) {
            return false;
        }

        TaiKhoanNVDTO taiKhoan = getTaiKhoanByMaNV(maNV);
        if (taiKhoan == null) {
            return false;
        }

        return taiKhoan.getPass().equals(pass) && taiKhoan.getTrangThaiXoa() == 0;
    }
}