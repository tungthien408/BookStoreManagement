package DTO;

public class TaiKhoanNVDTO {
    private String maNV;
<<<<<<< HEAD
    private String matKhau;
=======
    private String pass;
    private int maQuyen;
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
    private int trangThaiXoa;
    private String maQuyen;

    public TaiKhoanNVDTO() {}

<<<<<<< HEAD
    public TaiKhoanNVDTO( String maNV, String matKhau, int trangThaiXoa, String maQuyen) {
=======
    // Constructor đầy đủ
    public TaiKhoanNVDTO(String maNV, String pass,int maQuyen, int trangThaiXoa) {
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.trangThaiXoa = trangThaiXoa;
        this.maQuyen = maQuyen;
    }


    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }
<<<<<<< HEAD

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }
}
=======
    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }
    public int getMaQuyen() {
        return maQuyen;
    }
}
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
