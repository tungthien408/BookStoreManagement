package QuanLyCuaHangBanSach;

import java.io.Serializable;
import java.util.*;

public class BookShop implements Serializable {
    ArrayList<Nguoi> listNguoi;

    public BookShop() {
        listNguoi = new ArrayList<>();
        taoNguoiQuanLy();
        Nguoi nvbh = new NhanVienBanHang(
                "nvbh", "Doraemom", "TPHCM", "1234",
                "08/08/2000", "Nam", "231512", "123", 8000000);
        Nguoi nvtk = new NhanVienThuKho(
                "nvtk", "Doramin", "Binh Duong", "4321",
                "08/08/2000", "Nu", "4325456", "123", 7000000);
        Nguoi kh1 = new KhachHang(
                "kh1", "Nobita", "Ha Noi", "34234",
                "08/08/2000", "Nam", "354235", "123");
        Nguoi kh2 = new KhachHang(
                "kh2", "Chaien", "Da Nang", "1235",
                "12/3/2000", "Nam", "42343", "123");
        listNguoi.add(nvbh);
        listNguoi.add(nvtk);
        listNguoi.add(kh1);
        listNguoi.add(kh2);
    }

    public Nguoi login() {
        Check.printMessage("Dang nhap");
        String id = Check.takeStringInput("Nhap id: ");
        String pass = Check.takeStringInput("Nhap password: ");
        return listNguoi.stream()
                .filter(x -> x.getId().equals(id) && x.getPassword().equals(pass))
                .findAny()
                .orElse(null);
    }

    private void taoNguoiQuanLy() {
        if (listNguoi.stream().anyMatch(x -> x instanceof QuanLy)) {
            System.out.println("Cua hang chi co 1 nguoi quan ly");
            return;
        }
        Nguoi quanLy = new QuanLy(
                "admin", "Dang Thai Tu",
                "Binh Duong", "8565", "13/02/2005",
                "Nam", "85659712", "admin", 15000000);
        listNguoi.add(quanLy);
    }

    public void xuatDanhSachNhanVien() {
        System.out.println(Check.toBlueText("Danh sach nhan vien"));
        System.out.printf("┌%-16s┬%-16s┬%-16s┬%-10s┬%-10s┬%-10s┬%-9s┬%-16s┬%-10s┬%-16s┐%n",
                Check.repeatStr("─", 16), Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 10), Check.repeatStr("─", 10), Check.repeatStr("─", 10),
                Check.repeatStr("─", 9), Check.repeatStr("─", 16), Check.repeatStr("─", 10),
                Check.repeatStr("─", 16));
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│%-10s│%-16s│%n",
                "ID", "Ho ten", "Dia chi", "SDT", "Ngay sinh", "Gioi tinh", "CMND", "Password", "Luong", "Chuc vu");
        System.out.printf("├%-16s┼%-16s┼%-16s┼%-10s┼%-10s┼%-10s┼%-9s┼%-16s┼%-10s┼%-16s┤%n",
                Check.repeatStr("─", 16), Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 10), Check.repeatStr("─", 10), Check.repeatStr("─", 10),
                Check.repeatStr("─", 9), Check.repeatStr("─", 16), Check.repeatStr("─", 10),
                Check.repeatStr("─", 16));

        for (Nguoi nguoi : listNguoi) {
            if (nguoi instanceof NhanVien)
                nguoi.xuatThongTin();
        }
    }

    public void xuatDanhSachKhachHang() {
        System.out.println(Check.toBlueText("Danh sach khach hang"));
        System.out.printf("┌%-16s┬%-16s┬%-16s┬%-10s┬%-10s┬%-10s┬%-9s┬%-16s┬%-10s┐%n",
                Check.repeatStr("─", 16), Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 10), Check.repeatStr("─", 10), Check.repeatStr("─", 10),
                Check.repeatStr("─", 9), Check.repeatStr("─", 16), Check.repeatStr("─", 10));
        System.out.printf("│%-16s│%-16s│%-16s│%-10s│%-10s│%-10s│%-9s│%-16s│%-10s│%n",
                "ID", "Ho ten", "Dia chi", "SDT", "Ngay sinh", "Gioi tinh", "CMND", "Password", "Diem");
        System.out.printf("├%-16s┼%-16s┼%-16s┼%-10s┼%-10s┼%-10s┼%-9s┼%-16s┼%-10s┤%n",
                Check.repeatStr("─", 16), Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 10), Check.repeatStr("─", 10), Check.repeatStr("─", 10),
                Check.repeatStr("─", 9), Check.repeatStr("─", 16), Check.repeatStr("─", 10));

        for (Nguoi nguoi : listNguoi) {
            if (nguoi instanceof KhachHang)
                nguoi.xuatThongTin();
        }
    }

    public void menuDanhSachNhanVien() {
        while (true) {
            xuatDanhSachNhanVien();
            System.out.println("1. Them nhan vien");
            System.out.println("2. Xoa nhan vien");
            System.out.println("3. Tim kiem nhan vien");
            System.out.println("4. Sua thong tin nhan vien");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 4)) {
                case 1 -> taoNguoi("NhanVien");
                case 2 -> xoaNguoi("NhanVien");
                case 3 -> timKiemNguoi("NhanVien");
                case 4 -> suaNguoi("NhanVien");
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }
    }

    public void menuDanhSachKhachHang() {
        while (true) {
            xuatDanhSachKhachHang();
            System.out.println("1. Them khach hang");
            System.out.println("2. Xoa khach hang");
            System.out.println("3. Tim kiem khach hang");
            System.out.println("4. Sua thong tin khach hang");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 4)) {
                case 1 -> taoNguoi("KhachHang");
                case 2 -> xoaNguoi("KhachHang");
                case 3 -> timKiemNguoi("KhachHang");
                case 4 -> suaNguoi("KhachHang");
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }
    }

    public void taoNguoi(String choice) {
        String id;
        while (true) {
            id = Check.takeStringInput("Nhap id: ");
            if (timKiemTheoID(id) != null) {
                Check.printError("ID nay da co vui long chon ID khac");
            } else {
                break;
            }
        }

        String hoTen = Check.takeStringInput("Nhap ho ten: ");
        String diaChi = Check.takeStringInput("Nhap dia chi: ");
        String sdt = Check.takePhoneNumberInput("Nhap so dien thoai: ");
        String ns = Check.takeDateInput("Nhap ngay sinh: ");
        String gt = chonGioiTinh();
        String cmnd = Check.takeStringInput("Nhap CMND: ");
        String pass = Check.takeStringInput("Nhap password: ");

        if (choice.equals("KhachHang")) {
            Nguoi kh = new KhachHang(id, hoTen, diaChi, sdt, ns, gt, cmnd, pass);
            listNguoi.add(kh);
        } else {
            int mucLuong = Check.takeIntegerInput("Nhap muc luong: ");
            System.out.println("1. Nhan vien thu kho");
            System.out.println("2. Nhan vien ban hang");
            switch (Check.takeInputChoice(1, 2)) {
                case 1 -> {
                    Nguoi nhanVienThuKho = new NhanVienThuKho(id, hoTen, diaChi, sdt, ns, gt, cmnd, pass, mucLuong);
                    listNguoi.add(nhanVienThuKho);
                }
                case 2 -> {
                    Nguoi nhanVienBanHang = new NhanVienBanHang(id, hoTen, diaChi, sdt, ns, gt, cmnd, pass, mucLuong);
                    listNguoi.add(nhanVienBanHang);
                }
            }
        }
    }

    private String chonGioiTinh() {
        String gt = "";
        System.out.println("***Lua chon gioi tinh***");
        System.out.println("1. Nam");
        System.out.println("2. Nu");
        System.out.println("3. Khac");
        switch (Check.takeInputChoice(1, 3)) {
            case 1 -> {
                gt = "Nam";
            }
            case 2 -> {
                gt = "Nu";
            }
            case 3 -> {
                gt = "Khac";
            }
        }
        return gt;
    }

    public Nguoi timKiemTheoID(String id) {
        return listNguoi.stream()
                .filter(x -> x.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    // dk = "KhachHang" | "NhanVienThuKho" | "NhanVienBanHang" | "NhanVien"
    public Nguoi timKiemTheoID(String id, String dk) {
        if (dk.equals("KhachHang"))
            return listNguoi.stream()
                    .filter(x -> x.getId().equals(id) && x instanceof KhachHang)
                    .findFirst()
                    .orElse(null);
        else if (dk.equals("NhanVienThuKho"))
            return listNguoi.stream()
                    .filter(x -> x.getId().equals(id) && x instanceof NhanVienThuKho)
                    .findFirst()
                    .orElse(null);
        else if (dk.equals("NhanVienBanHang"))
            return listNguoi.stream()
                    .filter(x -> x.getId().equals(id) && x instanceof NhanVienBanHang)
                    .findFirst()
                    .orElse(null);
        else
            return listNguoi.stream()
                    .filter(x -> x.getId().equals(id) &&
                            (x instanceof NhanVienBanHang || x instanceof NhanVienThuKho))
                    .findFirst()
                    .orElse(null);
    }

    public void xoaNguoi(String choice) {
        String id = Check.takeStringInput("Nhap ID can xoa: ");
        Nguoi nguoi = timKiemTheoID(id, choice);
        if (nguoi == null)
            Check.printError("Khong tim thay ID");
        else {
            listNguoi.remove(nguoi);
            Check.printMessage("Xoa thanh cong");
        }
    }

    public void timKiemNguoi(String choice) {
        String ID = Check.takeStringInput("Nhap ID can tim can tim: ");
        ArrayList<Nguoi> filter = new ArrayList<>();
        for (Nguoi nguoi : listNguoi) {
            if (choice.equals("KhachHang") && nguoi instanceof KhachHang ||
                    choice.equals("NhanVien") && nguoi instanceof NhanVien) {
                if (nguoi.getId().toLowerCase(Locale.ROOT).contains(ID.toLowerCase(Locale.ROOT))) {
                    filter.add(nguoi);
                }
            }
        }

        System.out.println(Check.toBlueText("Tim kiem theo ID: ") + Check.toGreenText(ID));
        if (filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
            if (choice.equals("KhachHang"))
                xuatDanhSachKhachHang();
            else
                for (Nguoi nguoi : filter)
                    nguoi.xuatThongTin();
        }
    }

    public void suaNguoi(String choice) {
        String id = Check.takeStringInput("Nhap ID can sua: ");
        Nguoi nguoi = timKiemTheoID(id, choice);
        if (nguoi == null)
            Check.printError("Khong tim thay ID");
        else {
            boolean out = false;
            do {
                if (choice.equals("KhachHang"))
                    xuatDanhSachKhachHang();
                else
                    xuatDanhSachNhanVien();
                nguoi.xuatThongTin();
                System.out.println("1. Sua ho ten");
                System.out.println("2. Sua dia chi");
                System.out.println("3. Sua so dien thoai");
                System.out.println("4. Sua ngay sinh");
                System.out.println("5. Sua gioi tinh");
                System.out.println("6. Sua CMND");
                System.out.println("7. Sua password");
                if (nguoi instanceof NhanVien)
                    System.out.println("8. Sua muc luong");
                System.out.println("0. Thoat");
                int max = (nguoi instanceof NhanVien) ? 8 : 7;
                switch (Check.takeInputChoice(0, max)) {
                    case 1 -> nguoi.setHoTen(Check.takeStringInput("Nhap ho ten moi: "));
                    case 2 -> nguoi.setDiaChi(Check.takeStringInput("Nhap dia chi moi: "));
                    case 3 -> nguoi.setSDT(Check.takePhoneNumberInput("Nhap so dien thoai moi: "));
                    case 4 -> nguoi.setNgaySinh(Check.takeDateInput("Nhap ngay sinh moi: "));
                    case 5 -> nguoi.setGioiTinh(chonGioiTinh());
                    case 6 -> nguoi.setCMND(Check.takeStringInput("Nhap CMND moi: "));
                    case 7 -> nguoi.setPassword(Check.takeStringInput("Nhap password moi: "));
                    case 8 -> {
                        if (nguoi instanceof NhanVien) {
                            ((NhanVien) nguoi).setMucLuong(Check.takeIntegerInput("Nhap muc luong moi: "));
                        }
                    }
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        }
    }
}