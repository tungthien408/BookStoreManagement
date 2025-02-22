package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class PhieuXuat extends Phieu implements Serializable {
    private String maNhanVienThuKho;
    private String maHoaDon;
    private String tinhTrang = "Cho xuat kho";

    public PhieuXuat(String maPhieu, String ngayLap, String maNhanVienBanHang,
            String maNhanVienThuKho, DSChiTietPhieu dsChiTietPhieu,
            int tongTien, String maHoaDon) {
        super(maPhieu, ngayLap, dsChiTietPhieu, tongTien, maNhanVienBanHang);
        this.maNhanVienThuKho = maNhanVienThuKho;
        this.maHoaDon = maHoaDon;
    }

    public void inPhieu() {
        System.out.printf("│%-8s│%-8s│%-12s│%-16s", maPhieu, maHoaDon, ngayLap, tongTien);
        String colorTinhTrang = tinhTrang;
        if (tinhTrang.equals("Cho xuat kho"))
            colorTinhTrang = Check.toYellowText(tinhTrang);
        else
            colorTinhTrang = Check.toGreenText(tinhTrang);
        System.out.printf("│%-25s│%-25s│%-16s\n", maNhanVien, maNhanVienThuKho, colorTinhTrang);
    }

    public void xemChiTietPhieu() {
        System.out.printf("┌%-50s┐\n", Check.repeatStr("─", 50));
        System.out.printf("│%-10s%-30s%-10s│\n", Check.repeatStr(" ", 10),
                "Chi tiet phieu xuat", Check.repeatStr(" ", 10));
        System.out.printf("│%-50s│\n", ("Ma phieu: " + getMaPhieu()));
        System.out.printf("│%-50s│\n", ("Ngay lap: " + getNgayLap()));
        System.out.printf("│%-50s│\n", ("Tong tien: " + getTongTien()));
        System.out.printf("│%-50s│\n", ("Ma nhan vien ban hang: " + getMaNhanVien()));
        System.out.printf("│%-50s│\n", ("Ma nhan vien thu kho: " + getMaNhanVienThuKho()));
        System.out.printf("│%-50s│\n", ("Tinh trang: " + getTinhTrang()));
        dsChiTietPhieu.xuatDS();
        System.out.printf("└%-16s┴%-16s┴%-16s┘\n", Check.repeatStr("─", 16),
                Check.repeatStr("─", 16), Check.repeatStr("─", 16));
    }

    public void xuatKho(Nguoi nguoi, DanhSachHoaDon danhSachHoaDon) {
        if (tinhTrang.equals("Da xuat kho"))
            return;
        maNhanVienThuKho = nguoi.getId();
        tinhTrang = "Da xuat kho";
        for (HoaDon hoaDon : danhSachHoaDon.getHoaDonArrayList())
            if (hoaDon.getMaHoaDon().equals(maHoaDon))
                hoaDon.setTinhTrang("Da xuat kho");
    }

    // Getters and Setters
    public String getMaNhanVienThuKho() {
        return maNhanVienThuKho;
    }

    public void setMaNhanVienThuKho(String maNhanVienThuKho) {
        this.maNhanVienThuKho = maNhanVienThuKho;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}