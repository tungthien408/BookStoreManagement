 package DTO;

import java.io.Serializable;

public abstract class NguoiDTO implements Serializable {
    protected String hoTen;
    protected String SDT;

    public NguoiDTO(String hoTen, String SDT ) {
        this.hoTen = hoTen;
        this.SDT = SDT;
    }

    public abstract void xuatThongTin();
    // Getters and Setters
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

   
} 