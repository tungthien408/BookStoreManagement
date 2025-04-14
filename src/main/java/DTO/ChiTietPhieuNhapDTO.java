package DTO;

public class ChiTietPhieuNhapDTO {
    private String maSach;
    private String maPN;
    private int soLuong;
    private int giaNhap;

    // Constructor
    public ChiTietPhieuNhapDTO(String maSach, String maPN, int soLuong, int giaNhap) {
        this.maSach = maSach;
        this.maPN = maPN;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
    }

    // Getters và Setters
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }
}