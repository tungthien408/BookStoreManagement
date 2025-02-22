 package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class NhanVienThuKho extends NhanVien implements Serializable {
    public NhanVienThuKho(String id, String hoTen, String diaChi, String SDT,
            String ngaySinh, String gioiTinh, String CMND,
            String password, int mucLuong) {
        super(id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password, mucLuong);
    }

    @Override
    public void xuatThongTin() {
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│",
                id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password);
        System.out.printf("%-10s│", mucLuong);
        System.out.printf("%-16s│%n", "Thu kho");
    }

    // Các phương thức đặc thù cho nhân viên thủ kho

    public void nhapKho() {
        // Code xử lý nhập kho
    }

    public void xuatKho() {
        // Code xử lý xuất kho
    }
}