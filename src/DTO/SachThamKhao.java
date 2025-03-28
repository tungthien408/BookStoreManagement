package DTO;

import java.io.Serializable;

public class SachThamKhao extends SachDTO implements Serializable {
    public SachThamKhao() {
      
    }

    public SachThamKhao(String maSach, String tenSach, String maTacGia, String luaTuoi,
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