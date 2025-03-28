package DTO;

import java.io.Serializable;

public class SachKyNangSong extends SachDTO implements Serializable {
    public SachKyNangSong() {
      
    }

    public SachKyNangSong(String maSach, String tenSach, String maTacGia, String luaTuoi,
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