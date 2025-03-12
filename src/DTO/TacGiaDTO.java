package DTO;

import java.io.Serializable;

public class TacGiaDTO implements Serializable {
    private String maTacGia;
    private String ten;
    private String sdt;
    private String diaChi;
    private String email;
    
    public TacGiaDTO() {
        this.maTacGia = null;
        this.ten = null;
        this.sdt = null;
        this.diaChi = null;
        this.email = null;
    }
    
    public TacGiaDTO(String maTacGia, String ten, String sdt, String diaChi, String email) {
        this.maTacGia = maTacGia;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.email = email;
    }

    // Getters and Setters
    public String getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(String maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSDT() {
        return sdt;
    }

    public void setSDT(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
} 