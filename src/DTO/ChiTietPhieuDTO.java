package DTO;

import java.io.Serializable;

public class ChiTietPhieuDTO implements Serializable {
    private String maSach;
    private int soLuong;
    private int donGia;

    public ChiTietPhieuDTO(String maSach, int soLuong, int donGia) {
        setMaSach(maSach);
        setSoLuong(soLuong);
        setDonGia(donGia);
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

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
} 