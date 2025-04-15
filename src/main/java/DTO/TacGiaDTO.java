package DTO;

public class TacGiaDTO {
    private String maTG;
    private String tenTG;
    private String diaChi;
    private String sdt;
    private int trangThaiXoa;

    // Constructor mặc định
    public TacGiaDTO() {
    }

    // Constructor đầy đủ
    public TacGiaDTO(String maTG, String tenTG, String diaChi, String sdt, int trangThaiXoa) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThaiXoa = trangThaiXoa;
    }

    // Getters và Setters
    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
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