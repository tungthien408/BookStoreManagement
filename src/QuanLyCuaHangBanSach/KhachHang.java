package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class KhachHang extends Nguoi implements Serializable {
    private int diemTichLuy; // Thêm điểm tích lũy cho khách hàng mua sách

    public KhachHang(String id, String hoTen, String diaChi, String SDT,
                     String ngaySinh, String gioiTinh, String CMND, String password) {
        super(id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password);
        this.diemTichLuy = 0;
    }

    @Override
    public void xuatThongTin() {
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│%-10s│", 
            id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password, diemTichLuy);
        System.out.println();
    }

    // Các phương thức đặc thù cho khách hàng mua sách
    public void SetTichDiem(int soTien) {
        // Cứ 100,000đ tích 1 điểm
        this.diemTichLuy += soTien/100000;
    }
    public int getTichDiem() {
        return diemTichLuy;
    }

} 