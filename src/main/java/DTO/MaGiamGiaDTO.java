package DTO;

import java.sql.Date;

public class MaGiamGiaDTO {
    private String maGiamGia;
    private String tenGiamGia;
    private int phanTramGiam;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int trangThaiXoa;
    private int loaiGiamGia;
    private int soTienGiam;

    public MaGiamGiaDTO() {}

    public MaGiamGiaDTO(String maGiamGia, String tenGiamGia, int phanTramGiam, Date ngayBatDau, Date ngayKetThuc,
                        int trangThaiXoa, int loaiGiamGia, int soTienGiam) {
        this.maGiamGia = maGiamGia;
        this.tenGiamGia = tenGiamGia;
        this.phanTramGiam = phanTramGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThaiXoa = trangThaiXoa;
        this.loaiGiamGia = loaiGiamGia;
        this.soTienGiam = soTienGiam;
    }

    // Getters and Setters
    public String getMaGiamGia() { return maGiamGia; }
    public void setMaGiamGia(String maGiamGia) { this.maGiamGia = maGiamGia; }
    public String getTenGiamGia() { return tenGiamGia; }
    public void setTenGiamGia(String tenGiamGia) { this.tenGiamGia = tenGiamGia; }
    public int getPhanTramGiam() { return phanTramGiam; }
    public void setPhanTramGiam(int phanTramGiam) { this.phanTramGiam = phanTramGiam; }
    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public int getTrangThaiXoa() { return trangThaiXoa; }
    public void setTrangThaiXoa(int trangThaiXoa) { this.trangThaiXoa = trangThaiXoa; }
    public int getLoaiGiamGia() { return loaiGiamGia; }
    public void setLoaiGiamGia(int loaiGiamGia) { this.loaiGiamGia = loaiGiamGia; }
    public int getSoTienGiam() { return soTienGiam; }
    public void setSoTienGiam(int soTienGiam) { this.soTienGiam = soTienGiam; }
}