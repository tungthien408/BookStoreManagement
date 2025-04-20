package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
    private NhanVienDAO nhanVienDAO;

    public NhanVienBUS() {
        nhanVienDAO = new NhanVienDAO();
    }

    public boolean isNhanVienExists(String maNV) {
        return nhanVienDAO.existsByMaNV(maNV);
    }

    // Thêm nhân viên mới
    public boolean addNhanVien(NhanVienDTO nhanVien) {
        // Kiểm tra dữ liệu đầu vào
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống!");
        }
        if (nhanVien.getChucVu() == null || nhanVien.getChucVu().trim().isEmpty()) {
            throw new IllegalArgumentException("Chức vụ không được để trống!");
        }
        if (nhanVien.getDiaChi() == null || nhanVien.getDiaChi().trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống!");
        }
        if (nhanVien.getSdt() == null || !nhanVien.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        if (nhanVien.getCccd() == null || !nhanVien.getCccd().matches("\\d{12}")) {
            throw new IllegalArgumentException("CCCD phải có đúng 12 chữ số!");
        }
        if (nhanVien.getNgaySinh() == null) {
            throw new IllegalArgumentException("Ngày sinh không được để trống!");
        }

        // Kiểm tra xem MaNV đã tồn tại chưa
        NhanVienDTO existing = nhanVienDAO.getByMaNV(nhanVien.getMaNV());
        if (existing != null) {
            throw new IllegalArgumentException("Mã nhân viên đã tồn tại!");
        }

        // Gọi DAO để thêm
        return nhanVienDAO.create(nhanVien);
    }

    // Lấy danh sách nhân viên
    public List<NhanVienDTO> getAllNhanVien() {
        return nhanVienDAO.getAll();
    }

    // Lấy nhân viên theo MaNV
    public NhanVienDTO getNhanVienByMaNV(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        return nhanVienDAO.getByMaNV(maNV);
    }

    // Cập nhật nhân viên
    public boolean updateNhanVien(NhanVienDTO nhanVien) {
        if (nhanVien.getMaNV() == null || nhanVien.getMaNV().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        if (nhanVien.getHoTen() == null || nhanVien.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống!");
        }
        if (nhanVien.getChucVu() == null || nhanVien.getChucVu().trim().isEmpty()) {
            throw new IllegalArgumentException("Chức vụ không được để trống!");
        }
        if (nhanVien.getDiaChi() == null || nhanVien.getDiaChi().trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống!");
        }
        if (nhanVien.getSdt() == null || !nhanVien.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        if (nhanVien.getCccd() == null || !nhanVien.getCccd().matches("\\d{12}")) {
            throw new IllegalArgumentException("CCCD phải có đúng 12 chữ số!");
        }
        if (nhanVien.getNgaySinh() == null) {
            throw new IllegalArgumentException("Ngày sinh không được để trống!");
        }
        return nhanVienDAO.update(nhanVien);
    }

    // Xóa nhân viên (xóa mềm)
    public boolean deleteNhanVien(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống!");
        }
        return nhanVienDAO.delete(maNV);
    }

    // Tìm kiếm nhân viên theo họ tên (gần đúng)
    public List<NhanVienDTO> searchByHoTen(String hoTen) {
        List<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : nhanVienDAO.getAll()) {
            if (nv.getHoTen().toLowerCase().contains(hoTen.toLowerCase())) {
                result.add(nv);
            }
        }
        return result;
    }
}