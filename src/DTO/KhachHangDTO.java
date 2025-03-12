package DTO;

public class KhachHangDTO {
    protected String hoTen;
    protected String khachHang_id;
    protected String SDT;
    private int diemTichLuy; // Thêm điểm tích lũy cho khách hàng mua sách

    public KhachHangDTO(String id, String hoTen, String diaChi, String SDT,
                     String ngaySinh, String gioiTinh, String CMND, String password) {
        this.diemTichLuy = 0;
    }

    // Các phương thức đặc thù cho khách hàng mua sách
    public void SetTichDiem(int soTien) {
        // Cứ 100,000đ tích 1 điểm
        this.diemTichLuy += soTien/100000;
    }
    public int getTichDiem() {
        return diemTichLuy;
    }
} 