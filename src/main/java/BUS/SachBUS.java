package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.SachDAO;
import DTO.SachDTO;

public class SachBUS {
    private SachDAO sachDAO;

    public SachBUS() {
        sachDAO = new SachDAO();
    }

    // Lấy sách theo mã sách
    public SachDTO getSachByID(String maSach) {
        try {
            if (maSach == null || maSach.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã sách không được để trống!");
            }
            return sachDAO.getSachByID(maSach);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lấy danh sách tất cả sách
    public List<SachDTO> getAllSach() {
        try {
            return sachDAO.getAllSach();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Thêm sách mới
    public boolean addSach(SachDTO sach) {
        try {
            // Kiểm tra logic nghiệp vụ trước khi thêm
            if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
                throw new IllegalArgumentException("Mã sách không được để trống!");
            }
            if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên sách không được để trống!");
            }
            if (sach.getSoLuong() < 0) {
                throw new IllegalArgumentException("Số lượng sách không được nhỏ hơn 0!");
            }
            if (sach.getDonGia() <= 0) {
                throw new IllegalArgumentException("Đơn giá phải lớn hơn 0!");
            }
            if (getSachByID(sach.getMaSach()) != null) {
                throw new IllegalArgumentException("Mã sách đã tồn tại!");
            }

            return sachDAO.addSach(sach);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin sách
    public boolean updateSach(SachDTO sach) {
        try {
            // Kiểm tra logic nghiệp vụ trước khi cập nhật
            if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
                throw new IllegalArgumentException("Mã sách không được để trống!");
            }
            if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên sách không được để trống!");
            }
            if (sach.getSoLuong() < 0) {
                throw new IllegalArgumentException("Số lượng sách không được nhỏ hơn 0!");
            }
            if (sach.getDonGia() <= 0) {
                throw new IllegalArgumentException("Đơn giá phải lớn hơn 0!");
            }
            if (getSachByID(sach.getMaSach()) == null) {
                throw new IllegalArgumentException("Mã sách không tồn tại!");
            }

            return sachDAO.updateSach(sach);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sách theo mã
    public boolean deleteSach(String maSach) {
        try {
            if (maSach == null || maSach.trim().isEmpty()) {
                throw new IllegalArgumentException("Mã sách không được để trống!");
            }
            if (getSachByID(maSach) == null) {
                throw new IllegalArgumentException("Mã sách không tồn tại!");
            }

            return sachDAO.deleteSach(maSach);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}