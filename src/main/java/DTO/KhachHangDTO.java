package DTO;
public class KhachHangDTO {
    private String maKH;
    private String sdt;
    private String hoTen;
    private int diem;
    private int trangThaiXoa;

    // Constructor mặc định
    public KhachHangDTO() {
    }

    // Constructor đầy đủ
    public KhachHangDTO(String maKH, String sdt, String hoTen, int diem, int trangThaiXoa) {
        this.maKH = maKH;
        this.sdt = sdt;
        this.hoTen = hoTen;
        this.diem = diem;
        this.trangThaiXoa = trangThaiXoa;
    }

    // Getters và Setters
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

}