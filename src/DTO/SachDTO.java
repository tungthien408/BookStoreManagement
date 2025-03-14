package DTO;

import java.io.Serializable;

public abstract class SachDTO implements Serializable {
    protected String maSach;
    protected String tenSach;
    protected String maTacGia;
    protected String luaTuoi;
    protected int giaBan;
    protected String theLoai;
    protected String maNhaXuatBan;
    protected String ngonNgu;
    protected int soLuong;
    
    public SachDTO() {
        maSach = null;
        tenSach = null;
        maTacGia = null;
        luaTuoi = null;
        giaBan = 0;
        theLoai = null;
        maNhaXuatBan = null;
        soLuong = 0;
    }
    
    public SachDTO(String maSach, String tenSach, String maTacGia, String luaTuoi,
                int giaBan, String theLoai, String maNhaXuatBan, int soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTacGia = maTacGia;
        this.luaTuoi = luaTuoi;
        this.giaBan = giaBan;
        this.theLoai = theLoai;
        this.maNhaXuatBan = maNhaXuatBan;
        this.soLuong = soLuong;
    }

    public void nhapThongTin() {
        this.tenSach = Check.takeStringInput("Nhap ten sach: ");
        this.luaTuoi = Check.takeStringInput("Nhap lua tuoi phu hop: ");
        this.giaBan = Check.takeIntegerInput("Nhap gia ban: ");
        this.theLoai = Check.takeStringInput("Nhap the loai: ");
        this.soLuong = Check.takeIntegerInput("Nhap so luong: ");
    }
    
    public void xuatThongTin() {
        System.out.format("│%-15s│%-18s│%-15s│%-10s│%-15s│%-15s│%-15s│%-15s│%n",
            maSach, tenSach, maTacGia, luaTuoi, giaBan, maNhaXuatBan, soLuong);
    }

    public boolean mua(int soLuongMua) {
        if(soLuongMua > soLuong)
            return false;
        soLuong -= soLuongMua;
        return true;
    }

    // Getters and Setters
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getLuaTuoi() {
        return luaTuoi;
    }

    public void setLuaTuoi(String luaTuoi) {
        this.luaTuoi = luaTuoi;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(String maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
} 