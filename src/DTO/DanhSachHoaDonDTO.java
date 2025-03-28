package DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachHoaDonDTO implements Serializable, DanhSach {
    private ArrayList<HoaDonDTO> hoaDonArrayList = new ArrayList<>();
    private int count = 0;

    public DanhSachHoaDonDTO(DanhSachSachDTO danhSachSach) {
        DanhSachChiTietHoaDonDTO a = new DanhSachChiTietHoaDonDTO();
        SachDTO s1 = danhSachSach.getListSach()[0];
        SachDTO s2 = danhSachSach.getListSach()[1];
        SachDTO s3 = danhSachSach.getListSach()[2];
        a.getCthd().add(new ChiTietHoaDonDTO(s1.getMaSach(), 10, s1.getGiaBan()));
        a.getCthd().add(new ChiTietHoaDonDTO(s2.getMaSach(), 3, s2.getGiaBan()));
        a.getCthd().add(new ChiTietHoaDonDTO(s3.getMaSach(), 6, s3.getGiaBan()));
        s1.mua(10);
        s2.mua(3);
        s3.mua(6);
        hoaDonArrayList.add(
                new HoaDonDTO(getID(), "Doraemon", "Honekawa Suneo", "12/12/2024", a.tinhTongTien(), a, "Da xac nhan"));

        DanhSachChiTietHoaDonDTO b = new DanhSachChiTietHoaDonDTO();
        b.getCthd().add(new ChiTietHoaDonDTO(s2.getMaSach(), 6, s2.getGiaBan()));
        b.getCthd().add(new ChiTietHoaDonDTO(s3.getMaSach(), 4, s3.getGiaBan()));
        s2.mua(6);
        s3.mua(4);
        hoaDonArrayList.add(
                new HoaDonDTO(getID(), "Doraemon", "Nobi Nobita", "1/2/2024", b.tinhTongTien(), b, "Da xac nhan"));
        
                DanhSachChiTietHoaDonDTO c = new DanhSachChiTietHoaDonDTO();
                c.getCthd().add(new ChiTietHoaDonDTO(s2.getMaSach(), 12, s2.getGiaBan()));
                c.getCthd().add(new ChiTietHoaDonDTO(s1.getMaSach(), 4, s1.getGiaBan()));
                s2.mua(12);
                s1.mua(4);
                hoaDonArrayList.add(
                        new HoaDonDTO(getID(), "Doraemon", "Chaien", "4/7/2024", b.tinhTongTien(), b, "Da xac nhan"));
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "HD" + str;
    }

    public void xuatTieuDe() {
        System.out.printf("┌%-20s┬%-20s┬%-20s┬%-10s┬%-15s┬%-25s┐ \n",
                Check.repeatStr("─", 20), Check.repeatStr("─", 20), Check.repeatStr("─", 20),
                Check.repeatStr("─", 10), Check.repeatStr("─", 15), Check.repeatStr("─", 25));
        System.out.printf("|%-20s│%-20s│%-20s│%-10s│%-15s│%-25s| \n",
                "Ma hoa don", "Ma nhan vien", "Ma khach hang", "Thoi gian", "Tong tien", "Tinh trang");
        System.out.printf("├%-20s┼%-20s┼%-20s┼%-10s┼%-15s┼%-25s┤ \n",
                Check.repeatStr("─", 20), Check.repeatStr("─", 20), Check.repeatStr("─", 20),
                Check.repeatStr("─", 10), Check.repeatStr("─", 15), Check.repeatStr("─", 25));
    }

    public void xuatDS() {
        xuatTieuDe();
        for (HoaDonDTO hoaDon : hoaDonArrayList) {
            hoaDon.xuatThongTin();
        }
    }

    public void xemChiTietHoaDon(NguoiDTO nguoi) {
        if (hoaDonArrayList.isEmpty())
            Check.printError("Danh sach dang rong");
        else {
            String ID = Check.takeStringInput("Nhap ID hoa don can xem chi tiet: ");
            HoaDonDTO obj = timKiemTheoID(ID);
            if (obj == null) {
                Check.printError("Khong co ID do trong danh sach");
                return;
            }
            obj.getDsChiTietHoaDon().xuatChiTietHoaDon();
        }
    }

    public void suaHD(DanhSachSachDTO danhSachSach, NguoiDTO nguoi, BookShopDTO bookShop) {
        String SDT = Check.takeStringInput("Nhap ID hoa don can sua: ");
        HoaDonDTO hoaDon = timKiemTheoID(SDT);
        if (hoaDon == null)
            Check.printError("Khong tim thay SDT");
        else {
            if (!hoaDon.getSDTKhachHang().equals(nguoi.getSDT()) && nguoi instanceof KhachHangDTO) {
                Check.printError("Ban khong co quyen sua hoa don nay");
                return;
            }
            boolean out = false;
            do {
                xuatTieuDe();
                hoaDon.xuatThongTin();
                System.out.println("1. Sua SDT khach hang");
                System.out.println("2. Sua chi tiet hoa don");
                System.out.println("0. Thoat");
                switch (Check.takeInputChoice(0, 2)) {
                    case 1 -> {
                        bookShop.xuatDanhSachKhachHang();
                        String sdt = Check.takeStringInput("Nhap SDT khach hang moi: ");
                        if (bookShop.timKiemTheoSDT(sdt, "KhachHang") != null)
                            hoaDon.setSDTKhachHang(sdt);
                        else
                            Check.printError("Khong tim thay SDT khach hang");
                    }
                    case 2 -> {
                        DanhSachChiTietHoaDonDTO dsct = hoaDon.getDsChiTietHoaDon();
                        dsct.nhapChiTietHoaDon(danhSachSach);
                        hoaDon.setTongTien(dsct.tinhTongTien());
                    }
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        }
    }

    public void timHD(NguoiDTO nguoi) {
        String tuKhoa = Check.takeStringInput("Nhap tuKhoa can tim: ");
        ArrayList<HoaDonDTO> filter = new ArrayList<>();

        for (HoaDonDTO hoaDon : hoaDonArrayList) {
            if (nguoi instanceof KhachHangDTO && !hoaDon.getSDTKhachHang().equals(nguoi.getSDT()))
                continue;
            boolean check = Check.subStrInStrIgnoreCase(hoaDon.getMaHoaDon(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(hoaDon.getSDTNhanVien(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(hoaDon.getSDTKhachHang(), tuKhoa);

            if (check)
                filter.add(hoaDon);
        }

        System.out.println(Check.toBlueText("Ket qua tim kiem theo tu khoa: ") + Check.toGreenText(tuKhoa));
        if (filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
             xuatTieuDe();
            for (HoaDonDTO hoaDon : filter)
                hoaDon.xuatThongTin();
        }
    }

    public void xoaHD(NguoiDTO nguoi) {
        String SDT = Check.takeStringInput("Nhap SDT can xoa: ");
        HoaDonDTO hoaDon = timKiemTheoID(SDT);
        if (hoaDon == null)
            Check.printError("Khong tim thay SDT");
        else {
            if (!hoaDon.getSDTKhachHang().equals(nguoi.getSDT()) && nguoi instanceof KhachHangDTO) {
                Check.printError("Ban khong co quyen xoa hoa don nay");
                return;
            }
            hoaDonArrayList.remove(hoaDon);
            Check.printMessage("Xoa thanh cong");
        }
    }

    // public void xacNhanHoaDon(NguoiDTO nguoi, DSPhieuXuatDTO dsPhieuXuat) {
    //     String id = Check.takeStringInput("Nhap ID hoa don can xac nhan: ");
    //     HoaDonDTO hoaDon = timKiemTheoID(id);
    //     if (hoaDon == null)
    //         Check.printError("Khong tim thay ID");
    //     else {
    //         if (!hoaDon.getTinhTrang().equals("Dang cho xac nhan")) {
    //             Check.printError("Hoa don da duoc xac nhan");
    //             return;
    //         }
    //         else{
    //             hoaDon.setTinhTrang("Da xac nhan");
    //         Check.printMessage("Xac nhan thanh cong");
    //         }
            
    //     }
    // }

    // public void xacNhanDaNhanDuocHang(NguoiDTO nguoi) {
    //     String id = Check.takeStringInput("Nhap ID hoa don can xac nhan: ");
    //     HoaDonDTO hoaDon = timKiemTheoID(id);
    //     if (hoaDon == null)
    //         Check.printError("Khong tim thay ID");
    //     else {
    //         if (!hoaDon.getMaKhachHang().equals(nguoi.getId())) {
    //             Check.printError("Ban khong co quyen xac nhan hoa don nay");
    //             return;
    //         }
    //         if (!hoaDon.getTinhTrang().equals("Da xuat kho")) {
    //             Check.printError("Don hang chua duoc xuat kho");
    //             return;
    //         }
           
    //           if (nguoi instanceof KhachHangDTO) {
    //             hoaDon.setTinhTrang("Da nhan hang");
    //             Check.printMessage("Xac nhan thanh cong");
    //             ((KhachHangDTO nguoi).SetTichDiem(hoaDon.getTongTien());
    //         }
    //     }
    // }

    public HoaDonDTO timKiemTheoID(String id) {
        return hoaDonArrayList.stream()
                .filter(x -> x.getMaHoaDon().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<HoaDonDTO> getHoaDonArrayList() {
        return hoaDonArrayList;
    }

    public void setHoaDonArrayList(ArrayList<HoaDonDTO> hoaDonArrayList) {
        this.hoaDonArrayList = hoaDonArrayList;
    }

    public void menuHD(DanhSachSachDTO danhSachSach, NguoiDTO nguoi, BookShopDTO bookShop) {
        boolean out = false;
        // Menu của nhân viên bán hàng
        // if (nguoi instanceof NhanVienBanHangDTO) {
        if (nguoi instanceof QuanLyDTO) {    
            do {
                xuatDS();
                System.out.println(Check.toBlueText("MENU HOA DON CHO NHAN VIEN BAN HANG"));
                System.out.println("1. Xem chi tiet hoa don");
                System.out.println("2. Sua thong tin hoa don");
                System.out.println("3. Tim kiem trong bang");
                System.out.println("4. Xoa hoa don");
                System.out.println("5. them Hoa Don");

                System.out.println("0. Thoat menu");
                switch (Check.takeInputChoice(0, 5)) {
                    case 1 -> xemChiTietHoaDon(nguoi);
                    case 2 -> suaHD(danhSachSach, nguoi, bookShop);
                    case 3 -> timHD(nguoi);
                    case 4 -> xoaHD(nguoi);
                    case 5 -> themHoaDon(danhSachSach, nguoi);
                    // case 5 -> xacNhanHoaDon(nguoi, dsPhieuXuat);
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        }
        //  else // Menu của khách hàng
        // {
        //     do {
        //         xuatDS();
        //         System.out.println(Check.toBlueText("MENU HOA DON CHO KHACH HANG"));
        //         System.out.println("1. Xem chi tiet hoa don");
        //         System.out.println("2. Mua sach");
        //         System.out.println("3. Tim kiem trong bang");
        //         // System.out.println("4. Xac nhan da nhan duoc hang");
        //         System.out.println("0. Thoat menu");
        //         switch (Check.takeInputChoice(0, 4)) {
        //             case 1 -> xemChiTietHoaDon(nguoi);
        //             case 2 -> themHoaDon(danhSachSach, nguoi);
        //             case 3 -> timHD(nguoi);
        //             // case 4 -> xacNhanDaNhanDuocHang(nguoi);
        //             case 0 -> out = true;
        //         }
        //         if (!out)
        //             Check.clearScreen();
        //     } while (!out);
    }

    public void themHoaDon(DanhSachSachDTO danhSachSach, NguoiDTO nguoi) {
        // xuatDS();
        String Number = Check.takeStringInput("nhap sdt:");
        DanhSachChiTietHoaDonDTO dsct = new DanhSachChiTietHoaDonDTO();
        dsct.nhapChiTietHoaDon(danhSachSach);
        hoaDonArrayList.add(new HoaDonDTO(getID(), nguoi.getSDT(),Number,
                Check.getDateNow(), dsct.tinhTongTien(), dsct, "Da Thanh Toan"));
        Check.printMessage("Tao hoa don thanh cong");
    }
}