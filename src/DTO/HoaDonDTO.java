package DTO;

import java.io.Serializable;

public class HoaDonDTO implements Serializable {
    private String maHoaDon;
    private String sdtNhanVien;
    private String sdtKhachHang;
    private String ngayLap;
    private int tongTien;
    private DanhSachChiTietHoaDonDTO dsChiTietHoaDon;
    private String tinhTrang;
    
    public HoaDonDTO() {
        this.maHoaDon = null;
        this.tongTien = 0;
        this.dsChiTietHoaDon = null;
    }

    public HoaDonDTO(String maHoaDon, String sdtNhanVien, String sdtKhachHang, 
                  String ngayLap, int tongTien, DanhSachChiTietHoaDonDTO dsChiTietHoaDon, 
                  String tinhTrang) {
        this.maHoaDon = maHoaDon;
        this.sdtNhanVien = sdtNhanVien;
        this.sdtKhachHang = sdtKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.dsChiTietHoaDon = dsChiTietHoaDon;
        this.tinhTrang = tinhTrang;
    }

    public void xuatThongTin() {
        String tinhTrangColor = null;
        if(getTinhTrang().equals("Dang cho xac nhan"))
            tinhTrangColor = Check.toBlueText(getTinhTrang());
        else if(getTinhTrang().equals("Da xac nhan") || getTinhTrang().equals("Da xuat kho"))
            tinhTrangColor = Check.toYellowText(getTinhTrang());
        else if(getTinhTrang().equals("Da nhan hang"))
            tinhTrangColor = Check.toGreenText(getTinhTrang());
        System.out.printf("│%-20s│%-20s│%-20s│%-10s│%-15s│%-25s \n", 
            maHoaDon, sdtNhanVien, sdtKhachHang, ngayLap, tongTien, tinhTrangColor);
    }

    // Getters and Setters
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getSDTNhanVien() {
        return sdtNhanVien;
    }

    public void setSDTNhanVien(String sdtNhanVien) {
        this.sdtNhanVien = sdtNhanVien;
    }

    public String getSDTKhachHang() {
        return sdtKhachHang;
    }

    public void setSDTKhachHang(String sdtKhachHang) {
        this.sdtKhachHang = sdtKhachHang;
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

    public DanhSachChiTietHoaDonDTO getDsChiTietHoaDon() {
        return dsChiTietHoaDon;
    }

    public void setDsChiTietHoaDon(DanhSachChiTietHoaDonDTO dsChiTietHoaDon) {
        this.dsChiTietHoaDon = dsChiTietHoaDon;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
} 