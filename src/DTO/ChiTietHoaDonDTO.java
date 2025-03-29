package DTO;

public class ChiTietHoaDonDTO {
    private String maSach;
    private String maHD;
    private int soLuong;
    private int gia;

    // Constructor mặc định
    public ChiTietHoaDonDTO() {}

    // Constructor có tham số
    public ChiTietHoaDonDTO(String maSach, String maHD, int soLuong, int gia) {
        this.maSach = maSach;
        this.maHD = maHD;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    // Getter và Setter
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

}
