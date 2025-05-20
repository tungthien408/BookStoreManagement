package DTO;

import java.sql.Date;

public class HoaDonDTO {
    private String maHD;
    private String maNV;
    private String maKH;
    private String maGG;
    private Date ngayBan;
    private int tongTien;
    private int trangThaiXoa;

    // Constructor mặc định
    public HoaDonDTO() {
    }

    // Constructor đầy đủ
    public HoaDonDTO(String maHD, String maNV, String maKH, Date ngayBan, int tongTien,String maGG) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.maGG = maGG;
        this.trangThaiXoa = 0;
    }

    // Getters và Setters
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }
    public String getMaGiamGia() {
        return maGG;
    }
    public void setMaGiamGia(String maGG) {
        this.maGG = maGG;
    }
}
