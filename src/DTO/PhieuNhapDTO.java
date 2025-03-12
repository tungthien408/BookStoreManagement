package DTO;

public class PhieuNhapDTO extends PhieuDTO {
    private String maNhaXuatBan;

    public PhieuNhapDTO(String maPhieu, String ngayLap, String maNhaXuatBan, 
                     String SDTNhanVien, DSChiTietPhieuDTO dsChiTietPhieu, int tongTien) {
        super(maPhieu, ngayLap, dsChiTietPhieu, tongTien, SDTNhanVien);
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public String getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(String maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }
    public void inPhieu() {
        System.out.printf("│%-16s│%-16s│%-16s", maPhieu, ngayLap, tongTien);
        System.out.printf("│%-16s│%-16s│\n", SDTNhanVien, maNhaXuatBan);
    }
} 