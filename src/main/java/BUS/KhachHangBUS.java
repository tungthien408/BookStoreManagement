package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        khachHangDAO = new KhachHangDAO();
    }

    // Thêm khách hàng mới
    public boolean addKhachHang(KhachHangDTO khachHang) {
        // Kiểm tra dữ liệu đầu vào
        if (khachHang.getMaKH() == null || khachHang.getMaKH().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống!");
        }
        if (khachHang.getHoTen() == null || khachHang.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống!");
        }
        if (khachHang.getSdt() == null || !khachHang.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        if (khachHang.getDiem() < 0) {
            throw new IllegalArgumentException("Điểm không được nhỏ hơn 0!");
        }

        // Kiểm tra xem MAKH đã tồn tại chưa
        KhachHangDTO existing = khachHangDAO.getByMaKH(khachHang.getMaKH());
        if (existing != null) {
            throw new IllegalArgumentException("Mã khách hàng đã tồn tại!");
        }

        // Gọi DAO để thêm
        return khachHangDAO.create(khachHang);
    }

    // Lấy danh sách khách hàng
    public List<KhachHangDTO> getAllKhachHang() {
        return khachHangDAO.getAll();
    }

    // Lấy khách hàng theo MAKH
    public KhachHangDTO getKhachHangByMaKH(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống!");
        }
        return khachHangDAO.getByMaKH(maKH);
    }

    // Cập nhật khách hàng
    public boolean updateKhachHang(KhachHangDTO khachHang) {
        if (khachHang.getMaKH() == null || khachHang.getMaKH().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống!");
        }
        if (khachHang.getHoTen() == null || khachHang.getHoTen().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống!");
        }
        if (khachHang.getSdt() == null || !khachHang.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        if (khachHang.getDiem() < 0) {
            throw new IllegalArgumentException("Điểm không được nhỏ hơn 0!");
        }
        return khachHangDAO.update(khachHang);
    }

    // Xóa khách hàng (xóa mềm)
    // public boolean deleteKhachHang(String maKH) {
    //     if (maKH == null || maKH.trim().isEmpty()) {
    //         throw new IllegalArgumentException("Mã khách hàng không được để trống!");
    //     }
    //     return khachHangDAO.delete(maKH);
    // }

    // Tìm kiếm khách hàng theo họ tên (gần đúng)
    public List<KhachHangDTO> searchByHoTen(String hoTen) {
        List<KhachHangDTO> result = new ArrayList<>();
        for (KhachHangDTO kh : khachHangDAO.getAll()) {
            if (kh.getHoTen().toLowerCase().contains(hoTen.toLowerCase())) {
                result.add(kh);
            }
        }
        return result;
    }

    // Tìm kiếm khách hàng theo số điện thoại
    public KhachHangDTO searchBySDT(String sdt) {
        if (sdt == null || !sdt.matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        for (KhachHangDTO kh : khachHangDAO.getAll()) {
            if (kh.getSdt().equals(sdt)) {
                return kh;
            }
        }
        return null;
    }
    
    // Lấy khách hàng theo MAKH
    public KhachHangDTO getMaKhachHangBySdt(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            throw new IllegalArgumentException("sdt khách hàng không được để trống!");
        }
        return khachHangDAO.getmaKHBysdt(maKH);
    }

    public int getCountKhachHang() {
        return khachHangDAO.getCountKhachHang();
    }
}