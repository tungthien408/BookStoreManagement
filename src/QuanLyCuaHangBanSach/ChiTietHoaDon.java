package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class ChiTietHoaDon implements Serializable {
    private String maSach;
    private int soLuong;
    private int gia;

    public ChiTietHoaDon() {
        this.maSach = null;
        this.soLuong = 0;
        this.gia = 0;
    }

    public ChiTietHoaDon(String maSach, int soLuong, int gia) {
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public void xuatThongTin(String maHoaDon) {
        System.out.printf("│%-20s│%-20s│%-20s│%-20s│\n", 
            maHoaDon, getMaSach(), getSoLuong(), getGia());
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
} 