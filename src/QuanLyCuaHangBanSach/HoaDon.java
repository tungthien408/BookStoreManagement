package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private String maHoaDon;
    private String maNhanVien;
    private String maKhachHang;
    private String ngayLap;
    private int tongTien;
    private DanhSachChiTietHoaDon dsChiTietHoaDon;
    private String tinhTrang;
    
    public HoaDon() {
        this.maHoaDon = null;
        this.tongTien = 0;
        this.dsChiTietHoaDon = null;
    }

    public HoaDon(String maHoaDon, String maNhanVien, String maKhachHang, 
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

    public void xuatThongTin() {
        String tinhTrangColor = null;
        if(getTinhTrang().equals("Dang cho xac nhan"))
            tinhTrangColor = Check.toBlueText(getTinhTrang());
        else if(getTinhTrang().equals("Da xac nhan") || getTinhTrang().equals("Da xuat kho"))
            tinhTrangColor = Check.toYellowText(getTinhTrang());
        else if(getTinhTrang().equals("Da nhan hang"))
            tinhTrangColor = Check.toGreenText(getTinhTrang());
        System.out.printf("│%-20s│%-20s│%-20s│%-10s│%-15s│%-25s \n", 
            maHoaDon, maNhanVien, maKhachHang, ngayLap, tongTien, tinhTrangColor);
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