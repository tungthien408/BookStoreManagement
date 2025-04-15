package DTO;

import java.sql.Date;

public class NhanVienDTO {
    private String maNV;
    private String hoTen;
    private String chucVu;
    private String diaChi;
    private String sdt;
    private String cccd;
    private Date ngaySinh;
    private int trangThaiXoa;

    // Constructor mặc định
    public NhanVienDTO() {
    }

    // Constructor đầy đủ
    public NhanVienDTO(String maNV, String hoTen, String chucVu, String diaChi, String sdt, String cccd, Date ngaySinh, int trangThaiXoa) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.cccd = cccd;
        this.ngaySinh = ngaySinh;
        this.trangThaiXoa = trangThaiXoa;
    }

    // Getters và Setters
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
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

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

}