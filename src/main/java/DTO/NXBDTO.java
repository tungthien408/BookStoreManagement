package DTO;
public class NXBDTO {
    private String maNXB;
    private String tenNXB;
    private String diaChi;
    private String sdt;
    private int trangThaiXoa;

    // Constructor mặc định
    public NXBDTO() {
    }

    // Constructor đầy đủ
    public NXBDTO(String maNXB, String tenNXB, String diaChi, String sdt, int trangThaiXoa) {
        this.maNXB = maNXB;
        this.tenNXB = tenNXB;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThaiXoa = trangThaiXoa;
    }

    // Getters và Setters
    public String getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
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

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

}