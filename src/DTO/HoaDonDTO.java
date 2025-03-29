package src.DTO;

import java.util.Date;

public class HoaDonDTO {
    private String maHD;
    private String maNV;
    private String sdt;
    private Date ngayBan;
    private int tongTien;

    public HoaDonDTO() {
    }

    public HoaDonDTO(String maHD, String maNV, String sdt, Date ngayBan, int tongTien) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.sdt = sdt;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

}
