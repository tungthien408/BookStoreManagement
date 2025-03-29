package src.DTO;
import java.io.Serializable;

public class KhachHangDTO implements Serializable {
    private String sdt;
    private String hoTen;
    private int Diem;

    // ✅ Constructor không tham số (Fix lỗi)
    public KhachHangDTO() {}

    // ✅ Constructor có tham số
    public KhachHangDTO(String sdt, String hoTen, int Diem) {
        this.sdt = sdt;
        this.hoTen = hoTen;
        this.Diem = Diem;
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
        return Diem;
    }

    public void setDiem(int Diem) {
        this.Diem = Diem;
    }
}
