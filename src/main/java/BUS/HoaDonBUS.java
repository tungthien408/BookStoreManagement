package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO;
    private NhanVienBUS nhanVienBUS; // Để kiểm tra MANV
    private KhachHangBUS khachHangBUS; // Để kiểm tra MAKH

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
        nhanVienBUS = new NhanVienBUS();
        khachHangBUS = new KhachHangBUS();
    }

    // Thêm hóa đơn mới
    public boolean addHoaDon(HoaDonDTO hoaDon) {
        // Kiểm tra dữ liệu đầu vào
        if (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        if (hoaDon.getTongTien() < 0) {
            throw new IllegalArgumentException("Tổng tiền không được nhỏ hơn 0!");
        }

        // Kiểm tra khóa ngoại MANV (nếu có)
        if (hoaDon.getMaNV() != null && !hoaDon.getMaNV().trim().isEmpty()) {
            if (nhanVienBUS.getNhanVienByMaNV(hoaDon.getMaNV()) == null) {
                throw new IllegalArgumentException("Mã nhân viên không tồn tại!");
            }
        }

        // Kiểm tra khóa ngoại MAKH (nếu có)
        if (hoaDon.getMaKH() != null && !hoaDon.getMaKH().trim().isEmpty()) {
            if (khachHangBUS.getKhachHangByMaKH(hoaDon.getMaKH()) == null) {
                throw new IllegalArgumentException("Mã khách hàng không tồn tại!");
            }
        }

        // Kiểm tra xem MAHD đã tồn tại chưa
        HoaDonDTO existing = hoaDonDAO.getByMaHD(hoaDon.getMaHD());
        if (existing != null) {
            throw new IllegalArgumentException("Mã hóa đơn đã tồn tại!");
        }

        // Gọi DAO để thêm
        return hoaDonDAO.create(hoaDon);
    }

    // Lấy danh sách hóa đơn
    public List<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAll();
    }

    // Lấy hóa đơn theo MAHD
    public HoaDonDTO getHoaDonByMaHD(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        return hoaDonDAO.getByMaHD(maHD);
    }

    // Cập nhật hóa đơn
    public boolean updateHoaDon(HoaDonDTO hoaDon) {
        if (hoaDon.getMaHD() == null || hoaDon.getMaHD().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        if (hoaDon.getTongTien() < 0) {
            throw new IllegalArgumentException("Tổng tiền không được nhỏ hơn 0!");
        }

        // Kiểm tra khóa ngoại MANV (nếu có)
        if (hoaDon.getMaNV() != null && !hoaDon.getMaNV().trim().isEmpty()) {
            if (nhanVienBUS.getNhanVienByMaNV(hoaDon.getMaNV()) == null) {
                throw new IllegalArgumentException("Mã nhân viên không tồn tại!");
            }
        }

        // Kiểm tra khóa ngoại MAKH (nếu có)
        if (hoaDon.getMaKH() != null && !hoaDon.getMaKH().trim().isEmpty()) {
            if (khachHangBUS.getKhachHangByMaKH(hoaDon.getMaKH()) == null) {
                throw new IllegalArgumentException("Mã khách hàng không tồn tại!");
            }
        }

        return hoaDonDAO.update(hoaDon);
    }

    // Xóa hóa đơn (xóa mềm)
    public boolean deleteHoaDon(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        return hoaDonDAO.delete(maHD);
    }

    // Tìm kiếm hóa đơn theo mã nhân viên
    public List<HoaDonDTO> searchByMaNV(String maNV) {
        List<HoaDonDTO> result = new ArrayList<>();
        for (HoaDonDTO hd : hoaDonDAO.getAll()) {
            if (hd.getMaNV() != null && hd.getMaNV().equals(maNV)) {
                result.add(hd);
            }
        }
        return result;
    }

    // Tìm kiếm hóa đơn theo mã khách hàng
    public List<HoaDonDTO> searchByMaKH(String maKH) {
        List<HoaDonDTO> result = new ArrayList<>();
        for (HoaDonDTO hd : hoaDonDAO.getAll()) {
            if (hd.getMaKH() != null && hd.getMaKH().equals(maKH)) {
                result.add(hd);
            }
        }
        return result;
    }
}
