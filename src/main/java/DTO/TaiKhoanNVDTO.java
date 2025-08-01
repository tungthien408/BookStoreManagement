package DTO;

public class TaiKhoanNVDTO {
    private String maNV;
    private String matKhau;
    private int trangThaiXoa;
    private String maQuyen;

    public TaiKhoanNVDTO() {}

    public TaiKhoanNVDTO( String maNV, String matKhau, int trangThaiXoa, String maQuyen) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.trangThaiXoa = trangThaiXoa;
        this.maQuyen = maQuyen;
    }


    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }
}
