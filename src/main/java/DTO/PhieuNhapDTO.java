package DTO;

import java.sql.Date;

public class PhieuNhapDTO {
    private String maPN;
    private String maNV;
    private Date ngayNhap;
    private double tongTien;
    private String maNXB;
    private int trangThaiXoa;

    // Constructor mặc định
    public PhieuNhapDTO() {
    }

    // Constructor đầy đủ
    public PhieuNhapDTO(String maPN, String maNV, Date ngayNhap, double tongTien, String maNXB, int trangThaiXoa) {
        this.maPN = maPN;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.maNXB = maNXB;
        this.trangThaiXoa = trangThaiXoa;
    }

    // Getters và Setters
    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }


}