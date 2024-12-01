package QuanLyCuaHangBanSach;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        BookShop bookShop = new BookShop();
        DanhSachSach danhSachSach = new DanhSachSach();
        DanhSachTacGia danhSachTacGia = new DanhSachTacGia();
        DanhSachNhaXuatBan danhSachNhaXuatBan = new DanhSachNhaXuatBan();
        DanhSachHoaDon danhSachHoaDon = new DanhSachHoaDon(danhSachSach);
        DSPhieuNhap dsPhieuNhap = new DSPhieuNhap(danhSachSach);
        DSPhieuXuat dsPhieuXuat;

        bookShop = (BookShop) Check.load(bookShop, "bookshop.txt");
        if (bookShop == null)
            bookShop = new BookShop();

        danhSachSach = (DanhSachSach) Check.load(danhSachSach, "danhsachsach.txt");
        if (danhSachSach == null)
            danhSachSach = new DanhSachSach();

        danhSachTacGia = (DanhSachTacGia) Check.load(danhSachTacGia, "danhsachtacgia.txt");
        if (danhSachTacGia == null)
            danhSachTacGia = new DanhSachTacGia();

        danhSachNhaXuatBan = (DanhSachNhaXuatBan) Check.load(danhSachNhaXuatBan, "danhsachnhaxuatban.txt");
        if (danhSachNhaXuatBan == null)
            danhSachNhaXuatBan = new DanhSachNhaXuatBan();

        danhSachHoaDon = (DanhSachHoaDon) Check.load(danhSachHoaDon, "danhsachhoadon.txt");
        if (danhSachHoaDon == null)
            danhSachHoaDon = new DanhSachHoaDon(danhSachSach);

        dsPhieuNhap = (DSPhieuNhap) Check.load(dsPhieuNhap, "danhsachphieunhap.txt");
        if (dsPhieuNhap == null)
            dsPhieuNhap = new DSPhieuNhap(danhSachSach);

        while (true) {
            Check.clearScreen();
            System.out.println(Check.toBlueText("CHUONG TRINH QUAN LY CUA HANG SACH"));
            System.out.println("1. Dang nhap");
            System.out.println("2. Dang ky");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 2)) {
                case 1 -> {
                    Nguoi nguoi = bookShop.login();
                    if (nguoi == null)
                        Check.printError("Sai thong tin dang nhap");
                    else {
                        Check.printMessage("Dang nhap thanh cong");
                        Check.clearScreen();
                        if (nguoi instanceof QuanLy) {
                            boolean logout = false;
                            do {
                                System.out.println(Check.toBlueText("MENU QUAN LY"));
                                System.out.println("1. Quan ly nhan vien");
                                System.out.println("2. Quan ly khach hang");
                                System.out.println("3. Quan ly sach");
                                System.out.println("4. Quan ly tac gia");
                                System.out.println("5. Quan ly nha xuat ban");
                                System.out.println("0. Dang xuat");
                                switch (Check.takeInputChoice(0, 5)) {
                                    case 1 -> bookShop.menuDanhSachNhanVien();
                                    case 2 -> bookShop.menuDanhSachKhachHang();
                                    case 3 -> danhSachSach.menu(danhSachTacGia, danhSachNhaXuatBan);
                                    case 4 -> danhSachTacGia.menu();
                                    case 5 -> danhSachNhaXuatBan.menuNXB();
                                    case 0 -> logout = true;
                                }
                                if (!logout)
                                    Check.clearScreen();
                            } while (!logout);
                        } else if (nguoi instanceof NhanVienThuKho) {
                            boolean logout = false;
                            dsPhieuXuat = new DSPhieuXuat(nguoi, danhSachHoaDon);
                            do {
                                System.out.println(Check.toBlueText("MENU THU KHO"));
                                System.out.println("1. Xem danh sach phieu nhap");
                                System.out.println("2. Xem danh sach phieu xuat");
                                System.out.println("0. Dang xuat");
                                switch (Check.takeInputChoice(0, 2)) {
                                    case 1 -> dsPhieuNhap.menu(danhSachSach, nguoi, bookShop, danhSachNhaXuatBan);
                                    case 2 -> dsPhieuXuat.menu(nguoi, danhSachHoaDon);
                                    case 0 -> logout = true;
                                }
                                if (!logout)
                                    Check.clearScreen();
                            } while (!logout);
                        } else if (nguoi instanceof NhanVienBanHang) {
                            boolean logout = false;
                            do {
                                System.out.println(Check.toBlueText("MENU BAN HANG"));
                                System.out.println("1. Xem danh sach sach");
                                System.out.println("2. Xem danh sach khach hang");
                                System.out.println("3. Xem danh sach hoa don");
                                System.out.println("0. Dang xuat");
                                switch (Check.takeInputChoice(0, 3)) {
                                    case 1 -> danhSachSach.xuatDS();
                                    case 2 -> bookShop.xuatDanhSachKhachHang();
                                    case 3 -> danhSachHoaDon.menuHD(danhSachSach, nguoi, bookShop, null);
                                    case 0 -> logout = true;
                                }
                                if (!logout)
                                    Check.clearScreen();
                            } while (!logout);
                        } else {
                            boolean logout = false;
                            do {
                                System.out.println(Check.toBlueText("MENU KHACH HANG"));
                                System.out.println("1. Xem danh sach sach");
                                System.out.println("2. Xem danh sach hoa don");
                                System.out.println("0. Dang xuat");
                                switch (Check.takeInputChoice(0, 2)) {
                                    case 1 -> danhSachSach.xuatDS();
                                    case 2 -> danhSachHoaDon.menuHD(danhSachSach, nguoi, bookShop, null);
                                    case 0 -> logout = true;
                                }
                                if (!logout)
                                    Check.clearScreen();
                            } while (!logout);
                        }
                    }
                }
                case 2 -> bookShop.taoNguoi("KhachHang");
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }

        Check.save(bookShop, "bookshop.txt");
        Check.save(danhSachSach, "danhsachsach.txt");
        Check.save(danhSachTacGia, "danhsachtacgia.txt");
        Check.save(danhSachNhaXuatBan, "danhsachnhaxuatban.txt");
        Check.save(danhSachHoaDon, "danhsachhoadon.txt");
        Check.save(dsPhieuNhap, "danhsachphieunhap.txt");
    }
}