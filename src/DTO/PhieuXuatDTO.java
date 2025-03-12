package DTO;

public class PhieuXuatDTO extends PhieuDTO {
    private String maNhanVienThuKho;
    private String maHoaDon;
    private String tinhTrang = "Cho xuat kho";

    public PhieuXuatDTO(String maPhieu, String ngayLap, String maNhanVienBanHang,
            String maNhanVienThuKho, DSChiTietPhieu dsChiTietPhieu,
            int tongTien, String maHoaDon) {
        super(maPhieu, ngayLap, dsChiTietPhieu, tongTien, maNhanVienBanHang);
        this.maNhanVienThuKho = maNhanVienThuKho;
        this.maHoaDon = maHoaDon;
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