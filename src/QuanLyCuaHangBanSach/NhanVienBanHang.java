package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class NhanVienBanHang extends NhanVien implements Serializable {
    public NhanVienBanHang(String id, String hoTen, String diaChi, String SDT,
                          String ngaySinh, String gioiTinh, String CMND, 
                          String password, int mucLuong) {
        super(id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password, mucLuong);
    }

    @Override
    public void xuatThongTin() {
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│", 
            id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password);
        System.out.printf("%-10s│", mucLuong);
        System.out.printf("%-16s│%n", "Ban sach");
    }

    // Các phương thức đặc thù cho nhân viên bán sách
    public void lapHoaDonBanSach() {
        // Code xử lý lập hóa đơn bán sách
    }
    
    public void kiemTraTonKhoSach() {
        // Code xử lý kiểm tra tồn kho sách
    }
    
    public void tuVanSachChoKhachHang() {
        // Code xử lý tư vấn sách cho khách hàng
    }
} 