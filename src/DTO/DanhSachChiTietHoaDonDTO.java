package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachChiTietHoaDonDTO implements Serializable {
    private ArrayList<ChiTietHoaDonDTO> cthd = new ArrayList<>();

    public ChiTietHoaDonDTO timKiemTheoMaHoaDon(String id) {
        return cthd.stream()
            .filter(x -> x.getMaSach().equals(id))
            .findFirst()
            .orElse(null);
    }
    public ChiTietHoaDonDTO timKiemTheoMaHoaDon1(String id){
        return cthd.stream().filter(x -> x.getMaSach().equals(id)).findFirst().orElse(null);

    }

    public String nhapMaSach(DanhSachSachDTO danhSachSach) {
        do {
            String maSach = Check.takeStringInput("Nhap ma sach:");
            if(danhSachSach.timKiemTheoMa(maSach) == -1) {
                Check.printError("Khong co ma sach nay");
            } else
                return maSach;
        } while (true);
    }

    public void nhapChiTietHoaDon(DanhSachSachDTO danhSachSach) {
        danhSachSach.xuatDS();
        boolean stop = false;
        do {
            String maSach = nhapMaSach(danhSachSach);
            SachDTO sach = danhSachSach.getListSach()[danhSachSach.timKiemTheoMa(maSach)];
            int soLuong = Check.takeIntegerInput("Nhap so luong:");
            int gia = sach.getGiaBan();
            if(!sach.mua(soLuong)) {
                Check.printError("Khong the mua");
                System.out.println("Trong kho chi con " + sach.getSoLuong());
                continue;
            }

            boolean daCo = false;
            for(ChiTietHoaDonDTO chiTietHoaDon : cthd) {
                if(chiTietHoaDon.getMaSach().equals(maSach)) {
                    chiTietHoaDon.setSoLuong(chiTietHoaDon.getSoLuong() + soLuong);
                    daCo = true;
                }
            }

            if(!daCo)
                cthd.add(new ChiTietHoaDonDTO(maSach, soLuong, gia));

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
        for (ChiTietHoaDonDTO chiTietHoaDon : cthd) {
            System.out.printf("│%-20s│%-20s│%-20s│%-25s │%-25s│ \n", 
                Check.repeatStr(" ", 20), chiTietHoaDon.getMaSach(),
                chiTietHoaDon.getSoLuong(), chiTietHoaDon.getGia(), 
                Check.repeatStr(" ", 25));
        }
    }

    public void delete(ChiTietHoaDonDTO chiTietHoaDon) {
        cthd.remove(chiTietHoaDon);
    }

    public int tinhTongTien() {
        int sumMoney = 0;
        for (ChiTietHoaDonDTO chiTietHoaDon : cthd) {
            sumMoney += chiTietHoaDon.getGia() * chiTietHoaDon.getSoLuong();
        }
        return sumMoney;
    }

    public ArrayList<ChiTietHoaDonDTO> getCthd() {
        return cthd;
    }

    public void setCthd(ArrayList<ChiTietHoaDonDTO> cthd) {
        this.cthd = cthd;
    }
} 