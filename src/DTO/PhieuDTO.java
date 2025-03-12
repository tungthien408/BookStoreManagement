package DTO;

import java.io.Serializable;

public class PhieuDTO implements Serializable {
    protected String maPhieu;
    protected String ngayLap;
    protected String maNhanVien;
    protected DSChiTietPhieu dsChiTietPhieu;
    protected int tongTien;

    public PhieuDTO(String maPhieu, String ngayLap, DSChiTietPhieu dsChiTietPhieu, 
                 int tongTien, String maNhanVien) {
        this.maPhieu = maPhieu;
        this.ngayLap = ngayLap;
        this.dsChiTietPhieu = dsChiTietPhieu;
        this.tongTien = tongTien;
        this.maNhanVien = maNhanVien;
    }

    // Getters and Setters
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public DSChiTietPhieu getDsChiTietPhieu() {
        return dsChiTietPhieu;
    }

    public void setDsChiTietPhieu(DSChiTietPhieu dsChiTietPhieu) {
        this.dsChiTietPhieu = dsChiTietPhieu;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
} 