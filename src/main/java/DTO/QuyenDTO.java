package DTO;

public class QuyenDTO {
<<<<<<< HEAD
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
=======
    private int maQuyen;
    private String tenQuyen;
    private int trangThaiXoa;

    public QuyenDTO() {
    }

    public QuyenDTO(int maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.trangThaiXoa = 0;
    }

    public int getTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(int trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
}
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
