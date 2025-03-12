package DTO;

public class KhachHangDTO extends NguoiDTO {

    private int diemTichLuy; // Thêm điểm tích lũy cho khách hàng mua sách

    public KhachHangDTO(String hoTen, String SDT ,int diemTichLuy) {
        super(hoTen, SDT);
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

    @Override
    public void xuatThongTin() {
        System.out.printf("│%-16s│%-16s│%-16s│", hoTen,SDT,diemTichLuy
            );
    }

} 