// Getter, setters of human (customer, staff,...)

package DTO;

import java.io.Serializable;

public abstract class NguoiDTO implements Serializable {
    protected String hoTen;
    protected String id;
    protected String diaChi;
    protected String SDT;
    protected String ngaySinh;
    protected String gioiTinh;
    protected String CMND;
    protected String password;

    public NguoiDTO(String id, String hoTen, String diaChi, String SDT,
                 String ngaySinh, String gioiTinh, String CMND, String password) {
        this.hoTen = hoTen;
        this.id = id;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.CMND = CMND;
        this.password = password;
    }

    // Getters and Setters
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} 