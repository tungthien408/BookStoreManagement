package DTO;

import java.io.Serializable;

public class ChiTietHoaDonDTO implements Serializable {
    private String maSach;
    private int soLuong;
    private int gia;

    public ChiTietHoaDonDTO() {
        this.maSach = null;
        this.soLuong = 0;
        this.gia = 0;
    }

    public ChiTietHoaDonDTO(String maSach, int soLuong, int gia) {
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.gia = gia;
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