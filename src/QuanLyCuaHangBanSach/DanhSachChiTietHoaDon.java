package QuanLyCuaHangBanSach;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachChiTietHoaDon implements Serializable {
    private ArrayList<ChiTietHoaDon> cthd = new ArrayList<>();

    public ChiTietHoaDon timKiemTheoMaHoaDon(String id) {
        return cthd.stream()
            .filter(x -> x.getMaSach().equals(id))
            .findFirst()
            .orElse(null);
    }

    public String nhapMaSach(DanhSachSach danhSachSach) {
        do {
            String maSach = Check.takeStringInput("Nhap ma sach:");
            if(danhSachSach.timKiemTheoMa(maSach) == -1) {
                Check.printError("Khong co ma sach nay");
            } else
                return maSach;
        } while (true);
    }

    public void nhapChiTietHoaDon(DanhSachSach danhSachSach) {
        danhSachSach.xuatDS();
        boolean stop = false;
        do {
            String maSach = nhapMaSach(danhSachSach);
            Sach sach = danhSachSach.getListSach()[danhSachSach.timKiemTheoMa(maSach)];
            int soLuong = Check.takeIntegerInput("Nhap so luong:");
            int gia = sach.getGiaBan();
            if(!sach.mua(soLuong)) {
                Check.printError("Khong the mua");
                System.out.println("Trong kho chi con " + sach.getSoLuong());
                continue;
            }

            boolean daCo = false;
            for(ChiTietHoaDon chiTietHoaDon : cthd) {
                if(chiTietHoaDon.getMaSach().equals(maSach)) {
                    chiTietHoaDon.setSoLuong(chiTietHoaDon.getSoLuong() + soLuong);
                    daCo = true;
                }
            }

            if(!daCo)
                cthd.add(new ChiTietHoaDon(maSach, soLuong, gia));

            System.out.println("1. Mua tiep");
            System.out.println("0. Dat hang");
            if(Check.takeInputChoice(0, 1) == 0)
                stop = true;

        } while(!stop);
    }

    public void xuatChiTietHoaDon() {
        int length = 60 - "Chi tiet hoa don".length();
        System.out.printf("│%-50s%-65s│ \n", Check.repeatStr(" ", 50), 
            "Chi tiet hoa don" + Check.repeatStr(" ", length));
        System.out.printf("│%-20s┌%-20s┬%-20s┬%-25s─┐%-25s│ \n", 
            Check.repeatStr(" ", 20), Check.repeatStr("─", 20), 
            Check.repeatStr("─", 20), Check.repeatStr("─", 25), 
            Check.repeatStr(" ", 25));
        System.out.printf("│%-20s│%-20s│%-20s│%-25s │%-25s│ \n", 
            Check.repeatStr(" ", 20), "Ma sach", "So luong", "Gia", 
            Check.repeatStr(" ", 25));
        System.out.printf("│%-20s├%-20s┼%-20s┼%-25s─┤%-25s│ \n", 
            Check.repeatStr(" ", 20), Check.repeatStr("─", 20), 
            Check.repeatStr("─", 20), Check.repeatStr("─", 25), 
            Check.repeatStr(" ", 25));
        for (ChiTietHoaDon chiTietHoaDon : cthd) {
            System.out.printf("│%-20s│%-20s│%-20s│%-25s │%-25s│ \n", 
                Check.repeatStr(" ", 20), chiTietHoaDon.getMaSach(),
                chiTietHoaDon.getSoLuong(), chiTietHoaDon.getGia(), 
                Check.repeatStr(" ", 25));
        }
    }

    public void delete(ChiTietHoaDon chiTietHoaDon) {
        cthd.remove(chiTietHoaDon);
    }

    public int tinhTongTien() {
        int sumMoney = 0;
        for (ChiTietHoaDon chiTietHoaDon : cthd) {
            sumMoney += chiTietHoaDon.getGia() * chiTietHoaDon.getSoLuong();
        }
        return sumMoney;
    }

    public ArrayList<ChiTietHoaDon> getCthd() {
        return cthd;
    }

    public void setCthd(ArrayList<ChiTietHoaDon> cthd) {
        this.cthd = cthd;
    }
} 