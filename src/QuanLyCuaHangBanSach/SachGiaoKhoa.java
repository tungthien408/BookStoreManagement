package QuanLyCuaHangBanSach;

import java.io.Serializable;

public class SachGiaoKhoa extends Sach implements Serializable {
    public SachGiaoKhoa() {
        ngonNgu = "Tieng Viet";
    }
    
    public SachGiaoKhoa(String maSach, String tenSach, String maTacGia, String luaTuoi,
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