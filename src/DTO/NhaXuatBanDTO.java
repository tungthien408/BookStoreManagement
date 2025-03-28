package DTO;

import java.io.Serializable;

public class NhaXuatBanDTO implements Serializable {
    private String ten;
    private String diaChi;
    private String sdt;
    private String email;
    private String maNXB;

    public NhaXuatBanDTO() {
        this.ten = null;
        this.diaChi = null;
        this.sdt = null;
        this.email = null;
        this.maNXB = null;
    }

    public NhaXuatBanDTO(String ten, String diaChi, String sdt, String email, String maNXB) {
        this.ten = ten;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.maNXB = maNXB;
    }
    public void xuatThongTin() {
        System.out.printf("│%-20s│%-20s│%-20s│%-20s│%-20s│ \n", 
            maNXB, ten, diaChi, sdt, email);
    }


    // Getters and Setters
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }
} 