package DTO;

public class QuyenDTO {
    private String maQuyen; // Changed to String
    private String tenQuyen;
    private int trangThaiXoa;

    public QuyenDTO() {}

    public QuyenDTO(String maQuyen, String tenQuyen, int trangThaiXoa) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.trangThaiXoa = trangThaiXoa;
    }

    // Getters and Setters
    public String getMaQuyen() { return maQuyen; }
    public void setMaQuyen(String maQuyen) { this.maQuyen = maQuyen; }

    public String getTenQuyen() { return tenQuyen; }
    public void setTenQuyen(String tenQuyen) { this.tenQuyen = tenQuyen; }

    public int getTrangThaiXoa() { return trangThaiXoa; }
    public void setTrangThaiXoa(int trangThaiXoa) { this.trangThaiXoa = trangThaiXoa; }
}