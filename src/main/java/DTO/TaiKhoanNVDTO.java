package DTO;

public class TaiKhoanNVDTO {
    private String maNV;
    private String pass;
    private int trangThaiXoa;

    // Constructor mặc định
    public TaiKhoanNVDTO() {
    }

    // Constructor đầy đủ
    public TaiKhoanNVDTO(String maNV, String pass, int trangThaiXoa) {
        this.maNV = maNV;
        this.pass = pass;
        this.trangThaiXoa = trangThaiXoa;
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
}