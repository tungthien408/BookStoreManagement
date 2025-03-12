package DTO;

import java.io.Serializable;

public class HoaDonDTO implements Serializable {
    private String maHoaDon;
    private String maNhanVien;
    private String maKhachHang;
    private String ngayLap;
    private int tongTien;
    private DanhSachChiTietHoaDon dsChiTietHoaDon;
    private String tinhTrang;
    
    public HoaDonDTO() {
        this.maHoaDon = null;
        this.tongTien = 0;
        this.dsChiTietHoaDon = null;
    }

    public HoaDonDTO(String maHoaDon, String maNhanVien, String maKhachHang, 
                  String ngayLap, int tongTien, DanhSachChiTietHoaDon dsChiTietHoaDon, 
                  String tinhTrang) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.dsChiTietHoaDon = dsChiTietHoaDon;
        this.tinhTrang = tinhTrang;
    }

    // Getters and Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
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

    public DanhSachChiTietHoaDon getDsChiTietHoaDon() {
        return dsChiTietHoaDon;
    }

    public void setDsChiTietHoaDon(DanhSachChiTietHoaDon dsChiTietHoaDon) {
        this.dsChiTietHoaDon = dsChiTietHoaDon;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
} 