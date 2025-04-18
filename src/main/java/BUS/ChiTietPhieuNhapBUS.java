package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhapBUS {
    private final ChiTietPhieuNhapDAO chiTietPhieuNhapDAO;

    public ChiTietPhieuNhapBUS() {
        chiTietPhieuNhapDAO = new ChiTietPhieuNhapDAO();
    }

    // Thêm chi tiết phiếu nhập
    public boolean addChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) {
        try {
            // Kiểm tra logic nghiệp vụ trước khi thêm
            if (ctpn.getSoLuong() <= 0 || ctpn.getGiaNhap() <= 0) {
                throw new IllegalArgumentException("Số lượng và giá nhập phải lớn hơn 0!");
            }
            if (ctpn.getMaSach() == null || ctpn.getMaPN() == null) {
                throw new IllegalArgumentException("Mã sách và mã phiếu nhập không được để trống!");
            }
            return chiTietPhieuNhapDAO.add(ctpn) > 0;
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw to let the caller handle validation errors
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả chi tiết phiếu nhập
    public List<ChiTietPhieuNhapDTO> getAllChiTietPhieuNhap() {
        try {
            return chiTietPhieuNhapDAO.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list instead of null for safety
        }
    }

    // Cập nhật chi tiết phiếu nhập
    public boolean updateChiTietPhieuNhap(ChiTietPhieuNhapDTO ctpn) {
        try {
            // Kiểm tra logic nghiệp vụ trước khi cập nhật
            if (ctpn.getSoLuong() <= 0 || ctpn.getGiaNhap() <= 0) {
                throw new IllegalArgumentException("Số lượng và giá nhập phải lớn hơn 0!");
            }
            if (ctpn.getMaSach() == null || ctpn.getMaPN() == null) {
                throw new IllegalArgumentException("Mã sách và mã phiếu nhập không được để trống!");
            }
            return chiTietPhieuNhapDAO.update(ctpn) > 0;
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw to let the caller handle validation errors
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa chi tiết phiếu nhập
    public boolean deleteChiTietPhieuNhap(String maSach, String maPN) {
        try {
            if (maSach == null || maPN == null) {
                throw new IllegalArgumentException("Mã sách và mã phiếu nhập không được để trống!");
            }
            return chiTietPhieuNhapDAO.delete(maSach, maPN) > 0;
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw to let the caller handle validation errors
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy chi tiết phiếu nhập theo mã phiếu nhập
    public List<ChiTietPhieuNhapDTO> getChiTietPhieuNhapByMaPN(String maPN) {
        try {
            if (maPN == null) {
                throw new IllegalArgumentException("Mã phiếu nhập không được để trống!");
            }
            return chiTietPhieuNhapDAO.getAllChiTietPhieuNhapByMaPn(maPN);
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw to let the caller handle validation errors
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list instead of null for safety
        }
    }

    // Lấy tổng số lượng nhập theo mã sách
    public int getTongSoLuongNhapTheoMaSach(String maSach) {
        try {
            if (maSach == null) {
                throw new IllegalArgumentException("Mã sách không được để trống!");
            }
            return chiTietPhieuNhapDAO.getTongSoLuongNhapTheoMaSP(maSach);
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw to let the caller handle validation errors
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Lấy danh sách chi tiết phiếu nhập theo khoảng ngày
    public List<ChiTietPhieuNhapDTO> getAllByDateRange(String startDate, String endDate) {
        try {
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được để trống!");
            }
            return chiTietPhieuNhapDAO.getAllByDateRange(startDate, endDate);
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw to let the caller handle validation errors
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list instead of null for safety
        }
    }
}