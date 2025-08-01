package DTO;

public class SachDTO {
    private String maSach;
    private String tenSach;
    private String theLoai;
    private int soLuong;
    private int donGia;
    private String maTG;
    private String maNXB;
    private int trangThaiXoa;
    private String img;
    private int soLuongDaBan;

    // Constructor mặc định
    public SachDTO() {
    }

    // Constructor đầy đủ
    public SachDTO(String maSach, String tenSach, String theLoai, int soLuong, int donGia, String maTG, String maNXB,
            String img, int soLuongDaBan) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maTG = maTG;
        this.maNXB = maNXB;
        this.trangThaiXoa = 0;
        this.img = img;
        this.soLuongDaBan = 0;
    }

    // Getters và Setters
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSoLuongDaBan() {
        return soLuongDaBan;
    }

    public void setSoLuongDaBan(int soLuongDaBan) {
        this.soLuongDaBan = soLuongDaBan;
    }
}
