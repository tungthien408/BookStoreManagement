package DTO;

public class TaiKhoanNVDTO {
    private String maNV;
    private String pass;
    private int maQuyen;
    private int trangThaiXoa;

    // Constructor mặc định
    public TaiKhoanNVDTO() {
    }

    // Constructor đầy đủ
    public TaiKhoanNVDTO(String maNV, String pass,int maQuyen, int trangThaiXoa) {
        this.maNV = maNV;
        this.pass = pass;
        this.trangThaiXoa = trangThaiXoa;
        this.maQuyen = maQuyen;
    }

    // Getters và Setters
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }
    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }
    public int getMaQuyen() {
        return maQuyen;
    }
}