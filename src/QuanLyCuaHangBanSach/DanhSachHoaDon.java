package QuanLyCuaHangBanSach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class DanhSachHoaDon implements Serializable, DanhSach {
    private ArrayList<HoaDon> hoaDonArrayList = new ArrayList<>();
    private int count = 0;

    public DanhSachHoaDon(DanhSachSach danhSachSach) {
        DanhSachChiTietHoaDon a = new DanhSachChiTietHoaDon();
        Sach s1 = danhSachSach.getListSach()[0];
        Sach s2 = danhSachSach.getListSach()[1];
        Sach s3 = danhSachSach.getListSach()[2];
        Sach s4 = danhSachSach.getListSach()[3];
        a.getCthd().add(new ChiTietHoaDon(s1.getMaSach(), 10, s1.getGiaBan()));
        a.getCthd().add(new ChiTietHoaDon(s2.getMaSach(), 3, s2.getGiaBan()));
        a.getCthd().add(new ChiTietHoaDon(s3.getMaSach(), 6, s3.getGiaBan()));
        s1.mua(10);
        s2.mua(3);
        s3.mua(6);
        hoaDonArrayList.add(new HoaDon(getID(), "nam", "minh", "12/12/2023", a.tinhTongTien(), a, "Da xac nhan"));

        DanhSachChiTietHoaDon b = new DanhSachChiTietHoaDon();
        b.getCthd().add(new ChiTietHoaDon(s2.getMaSach(), 6, s2.getGiaBan()));
        b.getCthd().add(new ChiTietHoaDon(s3.getMaSach(), 4, s3.getGiaBan()));
        s2.mua(6);
        s3.mua(4);
        hoaDonArrayList.add(new HoaDon(getID(), "nam", "minh", "1/2/2024", b.tinhTongTien(), b, "Da xac nhan"));
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
        for (HoaDon hoaDon : hoaDonArrayList) {
            hoaDon.xuatThongTin();
        }
    }

    public void xemChiTietHoaDon(Nguoi nguoi) {
        if (hoaDonArrayList.isEmpty())
            Check.printError("Danh sach dang rong");
        else {
            String id = Check.takeStringInput("Nhap ID hoa don can xem chi tiet: ");
            HoaDon obj = timKiemTheoID(id);
            if (obj == null) {
                Check.printError("Khong co ID do trong danh sach");
                return;
            }
            if (!obj.getMaKhachHang().equals(nguoi.getId()) && nguoi instanceof KhachHang) {
                Check.printError("Ban khong co quyen xem hoa don nay");
                return;
            }
            obj.getDsChiTietHoaDon().xuatChiTietHoaDon();
        }
    }

    public void suaHD(DanhSachSach danhSachSach, Nguoi nguoi, BookShop bookShop) {
        String id = Check.takeStringInput("Nhap ID hoa don can sua: ");
        HoaDon hoaDon = timKiemTheoID(id);
        if (hoaDon == null)
            Check.printError("Khong tim thay ID");
        else {
            if (!hoaDon.getMaKhachHang().equals(nguoi.getId()) && nguoi instanceof KhachHang) {
                Check.printError("Ban khong co quyen sua hoa don nay");
                return;
            }
            boolean out = false;
            do {
                xuatTieuDe();
                hoaDon.xuatThongTin();
                System.out.println("1. Sua ma khach hang");
                System.out.println("2. Sua chi tiet hoa don");
                System.out.println("0. Thoat");
                switch (Check.takeInputChoice(0, 2)) {
                    case 1 -> {
                        bookShop.xuatDanhSachKhachHang();
                        String maKH = Check.takeStringInput("Nhap ma khach hang moi: ");
                        if (bookShop.timKiemTheoID(maKH, "KhachHang") != null)
                            hoaDon.setMaKhachHang(maKH);
                        else
                            Check.printError("Khong tim thay ma khach hang");
                    }
                    case 2 -> {
                        DanhSachChiTietHoaDon dsct = hoaDon.getDsChiTietHoaDon();
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

    public void timHD(Nguoi nguoi) {
        String tuKhoa = Check.takeStringInput("Nhap tu khoa can tim: ");
        ArrayList<HoaDon> filter = new ArrayList<>();
        int tongTien = 0;

        for (HoaDon hoaDon : hoaDonArrayList) {
            if (nguoi instanceof KhachHang && !hoaDon.getMaKhachHang().equals(nguoi.getId()))
                continue;

            try {
                tongTien = Integer.parseInt(tuKhoa);
            } catch (NumberFormatException ignored) {
            }

            boolean check = Check.subStrInStrIgnoreCase(hoaDon.getMaHoaDon(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(hoaDon.getMaNhanVien(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(hoaDon.getMaKhachHang(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(hoaDon.getNgayLap(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(hoaDon.getTinhTrang(), tuKhoa) ||
                    hoaDon.getTongTien() == tongTien;
            if (check)
                filter.add(hoaDon);
        }

        System.out.println(Check.toBlueText("Ket qua tim kiem theo tu khoa: ") + Check.toGreenText(tuKhoa));
        if (filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
            xuatTieuDe();
            for (HoaDon hoaDon : filter)
                hoaDon.xuatThongTin();
        }
    }

    public void xoaHD(Nguoi nguoi) {
        String id = Check.takeStringInput("Nhap ID can xoa: ");
        HoaDon hoaDon = timKiemTheoID(id);
        if (hoaDon == null)
            Check.printError("Khong tim thay ID");
        else {
            if (!hoaDon.getMaKhachHang().equals(nguoi.getId()) && nguoi instanceof KhachHang) {
                Check.printError("Ban khong co quyen xoa hoa don nay");
                return;
            }
            hoaDonArrayList.remove(hoaDon);
            Check.printMessage("Xoa thanh cong");
        }
    }

    public void xacNhanHoaDon(Nguoi nguoi, DSPhieuXuat dsPhieuXuat) {
        String id = Check.takeStringInput("Nhap ID hoa don can xac nhan: ");
        HoaDon hoaDon = timKiemTheoID(id);
        if (hoaDon == null)
            Check.printError("Khong tim thay ID");
        else {
            if (!hoaDon.getTinhTrang().equals("Dang cho xac nhan")) {
                Check.printError("Hoa don da duoc xac nhan");
                return;
            }
            hoaDon.setTinhTrang("Da xac nhan");
            Check.printMessage("Xac nhan thanh cong");
        }
    }

    public void xacNhanDaNhanDuocHang(Nguoi nguoi) {
        String id = Check.takeStringInput("Nhap ID hoa don can xac nhan: ");
        HoaDon hoaDon = timKiemTheoID(id);
        if (hoaDon == null)
            Check.printError("Khong tim thay ID");
        else {
            if (!hoaDon.getMaKhachHang().equals(nguoi.getId())) {
                Check.printError("Ban khong co quyen xac nhan hoa don nay");
                return;
            }
            if (!hoaDon.getTinhTrang().equals("Da xuat kho")) {
                Check.printError("Don hang chua duoc xuat kho");
                return;
            }
            hoaDon.setTinhTrang("Da nhan hang");
            Check.printMessage("Xac nhan thanh cong");
        }
    }

    public HoaDon timKiemTheoID(String id) {
        return hoaDonArrayList.stream()
                .filter(x -> x.getMaHoaDon().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<HoaDon> getHoaDonArrayList() {
        return hoaDonArrayList;
    }

    public void setHoaDonArrayList(ArrayList<HoaDon> hoaDonArrayList) {
        this.hoaDonArrayList = hoaDonArrayList;
    }

    public void menuHD(DanhSachSach danhSachSach, Nguoi nguoi, BookShop bookShop, DSPhieuXuat dsPhieuXuat) {
        boolean out = false;
        // Menu của nhân viên bán hàng
        if (nguoi instanceof NhanVienBanHang) {
            do {
                xuatDS();
                System.out.println(Check.toBlueText("MENU HOA DON"));
                System.out.println("1. Xem chi tiet hoa don");
                System.out.println("2. Sua thong tin hoa don");
                System.out.println("3. Tim kiem trong bang");
                System.out.println("4. Xoa hoa don");
                System.out.println("5. Xac nhan hoa don");
                System.out.println("0. Thoat menu");
                switch (Check.takeInputChoice(0, 5)) {
                    case 1 -> xemChiTietHoaDon(nguoi);
                    case 2 -> suaHD(danhSachSach, nguoi, bookShop);
                    case 3 -> timHD(nguoi);
                    case 4 -> xoaHD(nguoi);
                    case 5 -> xacNhanHoaDon(nguoi, dsPhieuXuat);
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        } else // Menu của khách hàng
        {
            do {
                xuatDS();
                System.out.println(Check.toBlueText("MENU HOA DON"));
                System.out.println("1. Xem chi tiet hoa don");
                System.out.println("2. Mua sach");
                System.out.println("3. Tim kiem trong bang");
                System.out.println("4. Xac nhan da nhan duoc hang");
                System.out.println("0. Thoat menu");
                switch (Check.takeInputChoice(0, 4)) {
                    case 1 -> xemChiTietHoaDon(nguoi);
                    case 2 -> themHoaDon(danhSachSach, nguoi);
                    case 3 -> timHD(nguoi);
                    case 4 -> xacNhanDaNhanDuocHang(nguoi);
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        }
    }

    public void themHoaDon(DanhSachSach danhSachSach, Nguoi nguoi) {
        DanhSachChiTietHoaDon dsct = new DanhSachChiTietHoaDon();
        dsct.nhapChiTietHoaDon(danhSachSach);
        hoaDonArrayList.add(new HoaDon(getID(), "nv001", nguoi.getId(),
                Check.getDateNow(), dsct.tinhTongTien(), dsct, "Dang cho xac nhan"));
        Check.printMessage("Tao hoa don thanh cong");
    }
}