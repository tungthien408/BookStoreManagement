package DTO;

public class Main {
    public static void main(String[] args) {
        BookShopDTO bookShop = new BookShopDTO();
        DanhSachSachDTO danhSachSach = new DanhSachSachDTO();
        DanhSachTacGiaDTO danhSachTacGia = new DanhSachTacGiaDTO();
        DanhSachNhaXuatBanDTO danhSachNhaXuatBan = new DanhSachNhaXuatBanDTO();
        DanhSachHoaDonDTO danhSachHoaDon = new DanhSachHoaDonDTO(danhSachSach);
        DSPhieuNhapDTO dsPhieuNhap = new DSPhieuNhapDTO(danhSachSach);

        bookShop = (BookShopDTO) Check.load(bookShop, "bookshop.txt");
        if (bookShop == null)
            bookShop = new BookShopDTO();

        danhSachSach = (DanhSachSachDTO) Check.load(danhSachSach, "danhsachsach.txt");
        if (danhSachSach == null)
            danhSachSach = new DanhSachSachDTO();

        danhSachTacGia = (DanhSachTacGiaDTO) Check.load(danhSachTacGia, "danhsachtacgia.txt");
        if (danhSachTacGia == null)
            danhSachTacGia = new DanhSachTacGiaDTO();

        danhSachNhaXuatBan = (DanhSachNhaXuatBanDTO) Check.load(danhSachNhaXuatBan, "danhsachnhaxuatban.txt");
        if (danhSachNhaXuatBan == null)
            danhSachNhaXuatBan = new DanhSachNhaXuatBanDTO();

        danhSachHoaDon = (DanhSachHoaDonDTO) Check.load(danhSachHoaDon, "danhsachhoadon.txt");
        if (danhSachHoaDon == null)
            danhSachHoaDon = new DanhSachHoaDonDTO(danhSachSach);

        dsPhieuNhap = (DSPhieuNhapDTO) Check.load(dsPhieuNhap, "danhsachphieunhap.txt");
        if (dsPhieuNhap == null)
            dsPhieuNhap = new DSPhieuNhapDTO(danhSachSach);


        while (true) {
            System.out.println(Check.toBlueText("CHUONG TRINH QUAN LY CUA HANG SACH"));
            System.out.println("1. Dang nhap");
            // System.out.println("2. Dang ky");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 1)) {
                case 1 -> {
                    NguoiDTO nguoi = bookShop.login();
                    if (nguoi == null)
                        Check.printError("Sai thong tin dang nhap");
                    else {
                        Check.printMessage("Dang nhap thanh cong");
                        Check.clearScreen();
                        if (nguoi instanceof QuanLyDTO) {
                            boolean logout = false;
                            do {
                                Check.printMessage("Welcome to the bookstore " + nguoi.getHoTen());
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
                        // } else if (nguoi instanceof NhanVienThuKho) {
                        //     boolean logout = false;
                        //     dsPhieuXuat = new DSPhieuXuat(nguoi, danhSachHoaDon);
                        //     do {
                        //         Check.printMessage("Welcome to the bookstore " + nguoi.getHoTen());
                        //         System.out.println(Check.toBlueText("MENU THU KHO"));
                        //         System.out.println("1. Xem danh sach phieu nhap");
                        //         System.out.println("2. Xem danh sach phieu xuat");
                        //         System.out.println("0. Dang xuat");
                        //         switch (Check.takeInputChoice(0, 2)) {
                        //             case 1 -> dsPhieuNhap.menu(danhSachSach, nguoi, bookShop, danhSachNhaXuatBan);
                        //             case 2 -> dsPhieuXuat.menu(nguoi, danhSachHoaDon);
                        //             case 0 -> logout = true;
                        //         }
                        //         if (!logout)
                        //             Check.clearScreen();
                        //     } while (!logout);
                         } else if (nguoi instanceof NhanVienBanHangDTO) {
                            boolean logout = false;
                            do {
                                Check.printMessage("Welcome to the bookstore " + nguoi.getHoTen());
                                System.out.println(Check.toBlueText("MENU BAN HANG"));
                                System.out.println("1. Xem danh sach sach");
                                System.out.println("2. Xem danh sach khach hang");
                                System.out.println("3. Xem danh sach hoa don");
                                System.out.println("0. Dang xuat");
                                switch (Check.takeInputChoice(0, 3)) {
                                    case 1 -> danhSachSach.xuatDS();
                                    case 2 -> bookShop.xuatDanhSachKhachHang();
                                    case 3 -> danhSachHoaDon.menuHD(danhSachSach, nguoi, bookShop);
                                    case 0 -> logout = true;
                                }
                                if (!logout)
                                    Check.clearScreen();
                            } while (!logout);
                        } else {
                            boolean logout = false;
                            do {
                                Check.printMessage("Welcome to the bookstore " + nguoi.getHoTen());
                                System.out.println(Check.toBlueText("MENU KHACH HANG"));
                                System.out.println("1. Xem danh sach sach");
                                System.out.println("2. Xem danh sach hoa don");
                                System.out.println("0. Dang xuat");
                                switch (Check.takeInputChoice(0, 2)) {
                                    case 1 -> danhSachSach.xuatDS();
                                    case 2 -> danhSachHoaDon.menuHD(danhSachSach, nguoi, bookShop);
                                    case 0 -> logout = true;
                                }
                                if (!logout)
                                    Check.clearScreen();
                            } while (!logout);
                        }
                    }
                }
                // case 2 -> bookShop.taoNguoi("KhachHang");
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