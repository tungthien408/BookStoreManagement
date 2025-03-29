package src.DTO;

import java.sql.Date;

public class PhieuNhapDTO {
    private String maPN;
    private String maNV;
    private Date ngayNhap;
    private double tongTien;
    private String maNXB;

    // Constructor không tham số
    public PhieuNhapDTO() {}

    // Constructor có tham số
    public PhieuNhapDTO(String maPN, String maNV, Date ngayNhap, double tongTien, String maNXB) {
        this.maPN = maPN;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.maNXB = maNXB;
    }

    // Getter và Setter
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
}
