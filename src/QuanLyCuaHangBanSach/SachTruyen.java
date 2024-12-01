package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class SachTruyen extends Sach implements Serializable {
    public SachTruyen() {
        ngonNgu = "Tieng Viet";
    }

    public SachTruyen(String maSach, String tenSach, String maTacGia, String luaTuoi,
                      int giaBan, String theLoai, String maNhaXuatBan, int soLuong) {
        super(maSach, tenSach, maTacGia, luaTuoi, giaBan, theLoai, maNhaXuatBan, soLuong);
        ngonNgu = "Tieng Viet";
    }

    @Override
    public void nhapThongTin() {
        super.nhapThongTin();
    }

    @Override
    public void xuatThongTin() {
        super.xuatThongTin();
    }
} 