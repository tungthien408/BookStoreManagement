package src.DTO;

public class SachDTO {
    private String maSach;
    private String tenSach;
    private String theLoai;
    private int soLuong;
    private int donGia;
    private String maTG;
    private String maNXB;

    // Constructor
    public SachDTO(String maSach, String tenSach, String theLoai, int soLuong, int donGia, String maTG, String maNXB) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maTG = maTG;
        this.maNXB = maNXB;
    }

    // Getters
    public String getMaSach() {
        return maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public String getMaTG() {
        return maTG;
    }

    public String getMaNXB() {
        return maNXB;
    }

    // Setters
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

}
