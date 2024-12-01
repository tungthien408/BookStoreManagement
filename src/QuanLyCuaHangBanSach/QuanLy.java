package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class QuanLy extends NhanVien implements Serializable {
    public QuanLy(String id, String hoTen, String diaChi, String SDT,
                  String ngaySinh, String gioiTinh, String CMND, 
                  String password, int mucLuong) {
        super(id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password, mucLuong);
    }

    @Override
    public void xuatThongTin() {
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│", 
            id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password);
        System.out.printf("%-10s│", mucLuong);
        System.out.printf("%-16s│%n", "Quan ly");
    }

    // Các phương thức đặc thù của quản lý
    public void quanLyNhanVien() {
        // Code xử lý quản lý nhân viên
    }
    
    public void quanLyDoanhThu() {
        // Code xử lý quản lý doanh thu
    }
    
    public void quanLyKho() {
        // Code xử lý quản lý kho sách
    }
    
    public void lapBaoCao() {
        // Code xử lý lập báo cáo
    }
} 