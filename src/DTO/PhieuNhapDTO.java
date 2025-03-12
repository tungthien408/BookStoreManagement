package DTO;

public class PhieuNhapDTO extends Phieu {
    private String maNhaXuatBan;

    public PhieuNhapDTO(String maPhieu, String ngayLap, String maNhaXuatBan, 
                     String maNhanVien, DSChiTietPhieu dsChiTietPhieu, int tongTien) {
        super(maPhieu, ngayLap, dsChiTietPhieu, tongTien, maNhanVien);
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public String getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(String maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }
} 