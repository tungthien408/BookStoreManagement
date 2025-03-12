package DTO;

public abstract class NhanVienDTO extends NguoiDTO {
    protected int mucLuong;
    protected String ChucVu;
    protected String diaChi;
    protected String ngaySinh;
    protected String gioiTinh;
    protected String CMND;
    protected String password;


    public NhanVienDTO( String hoTen, String diaChi, String SDT,
            String ngaySinh, String gioiTinh, String CMND,
            String password, String ChucVu, int mucLuong) {
        super(hoTen,SDT);
        this.mucLuong = mucLuong;
        this.ChucVu = ChucVu;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.CMND = CMND;
        this.password = password;
    }
    public abstract void xuatThongTin();

    public String getChucVu(){
        return ChucVu;
    }
    public void setChucVu(String ChucVu){
        this.ChucVu = ChucVu;
    }

    public int getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(int mucLuong) {
        this.mucLuong = mucLuong;
    }


    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}