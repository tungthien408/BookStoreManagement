package DTO;

public class TacGiaDTO {
    private String maTG;
    private String tenTG;
    private String diaChi;
    private String sdt;

    public TacGiaDTO() {
    }

    public TacGiaDTO(String maTG, String tenTG, String diaChi, String sdt) {
        this.maTG = maTG;
        this.tenTG = tenTG;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

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

}
