package DTO;
import java.io.Serializable;

public class NhanVienBanHangDTO extends NhanVienDTO implements Serializable {
    public NhanVienBanHangDTO(String hoTen, String diaChi, String SDT, String ngaySinh, String gioiTinh, String CMND,String password, String ChucVu, int mucLuong)
    {
        super(hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password,ChucVu,mucLuong);
    }


    @Override
    public void xuatThongTin() {
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│", 
             hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password,mucLuong);
        System.out.printf("%-10s│", ChucVu);
    }

}