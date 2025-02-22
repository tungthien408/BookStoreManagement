package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class PhieuNhap extends Phieu implements Serializable {
    private String maNhaXuatBan;

    public PhieuNhap(String maPhieu, String ngayLap, String maNhaXuatBan, 
                     String maNhanVien, DSChiTietPhieu dsChiTietPhieu, int tongTien) {
        super(maPhieu, ngayLap, dsChiTietPhieu, tongTien, maNhanVien);
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public void inPhieu() {
        System.out.printf("│%-16s│%-16s│%-16s", maPhieu, ngayLap, tongTien);
        System.out.printf("│%-16s│%-16s│\n", maNhanVien, maNhaXuatBan);
    }

    public void xemChiTietPhieu() {
        System.out.printf("┌%-50s┐\n", Check.repeatStr("─", 50));
        System.out.printf("│%-10s%-30s%-10s│\n", Check.repeatStr(" ", 10), 
            "Chi tiet phieu nhap", Check.repeatStr(" ", 10));
        System.out.printf("│%-50s│\n", ("Ma phieu: " + getMaPhieu()));
        System.out.printf("│%-50s│\n", ("Ngay lap: " + getNgayLap()));
        System.out.printf("│%-50s│\n", ("Tong tien: " + getTongTien()));
        System.out.printf("│%-50s│\n", ("Ma nhan vien: " + getMaNhanVien()));
        System.out.printf("│%-50s│\n", ("Ma NXB: " + getMaNhaXuatBan()));
        dsChiTietPhieu.xuatDS();
        System.out.printf("└%-16s┴%-16s┴%-16s┘\n", Check.repeatStr("─", 16), 
            Check.repeatStr("─", 16), Check.repeatStr("─", 16));
    }

    public String getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(String maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }
} 