package DTO;

public class NhanVienDTO extends NguoiDTO {
    protected String ChucVu;
    protected int mucLuong;

    public NhanVienDTO(String id, String hoTen, String diaChi, String SDT, 
                    String ngaySinh, String gioiTinh, String CMND, 
                    String password, String ChucVu, int mucLuong) {
        super(id, hoTen, diaChi, SDT, ngaySinh, gioiTinh, CMND, password);
        this.ChucVu = ChucVu;
        this.mucLuong = mucLuong;
    }

    public int getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(int mucLuong) {
        this.mucLuong = mucLuong;
    }

    public String getChucVu() {
        return this.ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }
} 