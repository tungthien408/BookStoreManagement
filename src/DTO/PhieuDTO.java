package DTO;

import java.io.Serializable;

public class PhieuDTO implements Serializable {
    protected String maPhieu;
    protected String ngayLap;
    protected String SDTNhanVien;
    protected DSChiTietPhieuDTO dsChiTietPhieu;
    protected int tongTien;

    public PhieuDTO(String maPhieu, String ngayLap, DSChiTietPhieuDTO dsChiTietPhieu, 
                 int tongTien, String SDTNhanVien) {
        this.maPhieu = maPhieu;
        this.ngayLap = ngayLap;
        this.dsChiTietPhieu = dsChiTietPhieu;
        this.tongTien = tongTien;
        this.SDTNhanVien = SDTNhanVien;
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

    public DSChiTietPhieuDTO getDsChiTietPhieu() {
        return dsChiTietPhieu;
    }

    public void setDsChiTietPhieu(DSChiTietPhieuDTO dsChiTietPhieu) {
        this.dsChiTietPhieu = dsChiTietPhieu;
    }

    public String getSDTNhanVien() {
        return SDTNhanVien;
    }

    public void setSDTNhanVien(String SDTNhanVien) {
        this.SDTNhanVien = SDTNhanVien;
    }
} 