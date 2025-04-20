package BUS;
import java.util.ArrayList;
import java.util.List;

import DAO.SachDAO;
import DTO.SachDTO;

public class SachBUS {
    private SachDAO sachDAO;

    public SachBUS() {
        sachDAO = new SachDAO();
    }

    // Thêm sách mới
    public boolean addSach(SachDTO sach) {
        // Kiểm tra dữ liệu đầu vào
        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống!");
        }
        if (sach.getSoLuong() < 0) {
            throw new IllegalArgumentException("Số lượng không được nhỏ hơn 0!");
        }
        if (sach.getDonGia() < 0) {
            throw new IllegalArgumentException("Đơn giá không được nhỏ hơn 0!");
        }

        // Kiểm tra xem MASACH đã tồn tại chưa
        SachDTO existing = sachDAO.getByMaSach(sach.getMaSach());
        if (existing != null) {
            throw new IllegalArgumentException("Mã sách đã tồn tại!");
        }

        // Gọi DAO để thêm
        return sachDAO.create(sach);
    }

    // Lấy danh sách sách
    public List<SachDTO> getAllSach() {
        return sachDAO.getAll();
    }

    // Lấy sách theo MASACH
    public SachDTO getSachByMaSach(String maSach) {
        if (maSach == null || maSach.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        return sachDAO.getByMaSach(maSach);
    }

    // Cập nhật sách
    public boolean updateSach(SachDTO sach) {
        if (sach.getMaSach() == null || sach.getMaSach().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        if (sach.getTenSach() == null || sach.getTenSach().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống!");
        }
        if (sach.getSoLuong() < 0) {
            throw new IllegalArgumentException("Số lượng không được nhỏ hơn 0!");
        }
        if (sach.getDonGia() < 0) {
            throw new IllegalArgumentException("Đơn giá không được nhỏ hơn 0!");
        }
        return sachDAO.update(sach);
    }

    // Xóa sách (xóa mềm)
    public boolean deleteSach(String maSach) {
        if (maSach == null || maSach.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        return sachDAO.delete(maSach);
    }

    // Tìm kiếm sách theo tên (gần đúng)
    public List<SachDTO> searchByTenSach(String tenSach) {
        List<SachDTO> result = new ArrayList<>();
        for (SachDTO sach : sachDAO.getAll()) {
            if (sach.getTenSach().toLowerCase().contains(tenSach.toLowerCase())) {
                result.add(sach);
            }
        }
        return result;
    }

    public int getSoLuongTonSanPham(String maSp){
        return sachDAO.getSoLuongTonSanPham(maSp);
    }
    
    public boolean updateSoLuongTonSanPham(String maSp, int soLuongTon){
        return sachDAO.updateSoLuongTonSanPham(maSp, soLuongTon) > 0;
    }
}