package DTO;

import java.io.Serializable;
import java.util.*;

public class BookShopDTO implements Serializable {
    ArrayList<NhanVienDTO> listNV;
    ArrayList<KhachHangDTO> listKH;
    ArrayList<NguoiDTO> listNguoi;

    public BookShopDTO() {
        listNV = new ArrayList<>();
        listKH = new ArrayList<>();
        listNguoi = new ArrayList<>();
        taoNguoiQuanLy();
        // NguoiDTO nvbh = new NhanVienBanHangDTO(
        // );
        // NguoiDTO kh1 = new KhachHangDTO("Thien","'0123456789");
        // NguoiDTO kh2 = new KhachHangDTO(
        // "kh2", "Chaien", "Da Nang", "1235",
        // "12/3/2000", "Nam", "42343", "123");
        // listNguoi.add(nvbh);
        // listNguoi.add(kh1);
        // listNguoi.add(kh2);
    }

    public NhanVienDTO login() {
        Check.printMessage("Dang nhap");
        String sdt = Check.takeStringInput("Nhap SDT: ");
        String pass = Check.takeStringInput("Nhap password: ");
        return listNV.stream()
                .filter(x -> x.getSDT().equals(sdt) && x.getPassword().equals(pass))
                .findAny()
                .orElse(null);
    }

    private void taoNguoiQuanLy() {
    if (listNV.stream().anyMatch(x -> x instanceof QuanLyDTO)) {
    System.out.println("Cua hang chi co 1 nguoi quan ly");
    return;
    }
    NhanVienDTO quanLy = new QuanLyDTO(
    "Dang Thai Tu", "Fuck u",
    "123", "13/2/2005", "nam",
    "113", "123", "admin", 15000000);
    listNV.add(quanLy);
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

        for (NguoiDTO nguoi : listNV) {
            if (nguoi instanceof NhanVienDTO)
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

        for (NguoiDTO nguoi : listKH) {
            if (nguoi instanceof KhachHangDTO)
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
        String sdt;
        while (true) {
            sdt = Check.takeStringInput("Nhap SDT: ");
            if (timKiemTheoSDT(sdt) != null) {
                Check.printError("SDT nay da co vui long chon SDT khac");
            } else {
                break;
            }
        }

        String hoTen = Check.takeStringInput("Nhap ho ten: ");
        // String sdt = Check.takePhoneNumberInput("Nhap so dien thoai: ");

        if (choice.equals("KhachHang")) {
            NguoiDTO kh = new KhachHangDTO(hoTen, sdt, '0');
            listNguoi.add(kh);
        } else {
            String diaChi = Check.takeStringInput("Nhap dia chi: ");
            // String id = Check.takePhoneNumberInput("Nhap id: ");
            String ns = Check.takeDateInput("Nhap ngay sinh: ");
            String gt = chonGioiTinh();
            String cmnd = Check.takeStringInput("Nhap CMND: ");
            String pass = Check.takeStringInput("Nhap password: ");
            // String chucvu = Check.takeStringInput("Nhap password: ");
            int mucLuong = Check.takeIntegerInput("Nhap muc luong: ");
            System.out.println("1. Nhan vien thu kho");
            System.out.println("2. Nhan vien ban hang");
            switch (Check.takeInputChoice(1, 2)) {
                case 1 -> {
                NguoiDTO nhanVienThuKho = new QuanLyDTO( hoTen, diaChi, sdt, ns, gt,cmnd, pass,"QuanLy", mucLuong);
                listNguoi.add(nhanVienThuKho);
                }
                case 2 -> {
                    NguoiDTO nhanVienBanHang = new NhanVienBanHangDTO(hoTen, diaChi, sdt, ns, gt, cmnd, pass,"NhanVienBanHang",mucLuong);
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

    public NguoiDTO timKiemTheoSDT(String id) {
        return listNV.stream()
                .filter(x -> x.getSDT().equals(id))
                .findAny()
                .orElse(null);
    }

    // dk = "KhachHang" | "NhanVienThuKho" | "NhanVienBanHang" | "NhanVien"
    public NguoiDTO timKiemTheoSDT(String id, String dk) {
        if (dk.equals("QuanLy"))
            return listNV.stream()
                    .filter(x -> x.getSDT().equals(id) && x instanceof QuanLyDTO)
                    .findFirst()
                    .orElse(null);
        else if (dk.equals("NhanVienBanHang"))
            return listNV.stream()
                    .filter(x -> x.getSDT().equals(id) && x instanceof NhanVienBanHangDTO)
                    .findFirst()
                    .orElse(null);
        else
            return listNV.stream()
                    .filter(x -> x.getSDT().equals(id) &&
                            (x instanceof NhanVienBanHangDTO))
                    .findFirst()
                    .orElse(null);
    }
    // delete nv
    public void xoaNguoi(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can xoa: ");
        NguoiDTO nguoi = timKiemTheoSDT(sdt, choice);
        if (nguoi == null)
            Check.printError("Khong tim thay sdt");
        else {
            listNV.remove(nguoi);
            Check.printMessage("Xoa thanh cong");
        }
    }

    public void timKiemNguoi(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can tim can tim: ");
        ArrayList<NguoiDTO> filter = new ArrayList<>();
        for (NguoiDTO nguoi : listNV) {
            if (choice.equals("QuanLy") && nguoi instanceof NhanVienDTO ||
                    choice.equals("NhanVien") && nguoi instanceof NhanVienDTO) {
                if (nguoi.getSDT().toLowerCase(Locale.ROOT).contains(sdt.toLowerCase(Locale.ROOT))) {
                    filter.add(nguoi);
                }
            }
        }

        System.out.println(Check.toBlueText("Tim kiem theo sdt: ") + Check.toGreenText(sdt));
        if (filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
            if (choice.equals("KhachHang"))
                xuatDanhSachKhachHang();
            else
                for (NguoiDTO nguoi : filter)
                    nguoi.xuatThongTin();
        }
    }

    public void suaNguoi(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can sua: ");
        NguoiDTO nguoi = timKiemTheoSDT(sdt, choice);
        if (nguoi == null)
            Check.printError("Khong tim thay sdt");
        else {
            boolean out = false;
            do {
                if (choice.equals("NhanVien"))
                    xuatDanhSachNhanVien();
                else
                    xuatDanhSachKhachHang();
                nguoi.xuatThongTin();
                System.out.println("1. Sua ho ten");
                System.out.println("3. Sua so dien thoai");

                if (nguoi instanceof NhanVienDTO)
                    System.out.println("2. Sua dia chi");
                System.out.println("4. Sua ngay sinh");
                System.out.println("5. Sua gioi tinh");
                System.out.println("6. Sua CMND");
                System.out.println("7. Sua password");
                System.out.println("8. Sua muc luong");
                System.out.println("0. Thoat");
                int max = (nguoi instanceof NhanVienDTO) ? 9 : 2;
                switch (Check.takeInputChoice(0, max)) {
                    case 1 -> nguoi.setHoTen(Check.takeStringInput("Nhap ho ten moi: "));
                    case 3 -> ((NhanVienDTO) nguoi).setDiaChi(Check.takeStringInput("Nhap dia chi moi: "));
                    case 2 -> nguoi.setSDT(Check.takePhoneNumberInput("Nhap so dien thoai moi: "));
                    case 4 -> ((NhanVienDTO) nguoi).setNgaySinh(Check.takeDateInput("Nhap ngay sinh moi: "));
                    case 5 -> ((NhanVienDTO) nguoi).setGioiTinh(chonGioiTinh());
                    case 6 -> ((NhanVienDTO) nguoi).setCMND(Check.takeStringInput("Nhap CMND moi: "));
                    case 7 -> ((NhanVienDTO) nguoi).setPassword(Check.takeStringInput("Nhap password moi: "));
                    case 8 -> ((NhanVienDTO) nguoi).setMucLuong(Check.takeIntegerInput("Nhap muc luong moi: "));
                    case 9 -> ((NhanVienDTO) nguoi).setChucVu(Check.takeStringInput("Nhap muc luong moi: "));
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        }
    }
}