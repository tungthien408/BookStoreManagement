package DTO;

import java.io.Serializable;

public class SachTruyen extends SachDTO implements Serializable {
    public SachTruyen() {
    }

    public SachTruyen(String maSach, String tenSach, String maTacGia, String luaTuoi,
                      int giaBan, String theLoai, String maNhaXuatBan, int soLuong) {
        super(maSach, tenSach, maTacGia, luaTuoi, giaBan, theLoai, maNhaXuatBan, soLuong);
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