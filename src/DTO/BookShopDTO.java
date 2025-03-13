package DTO;

import java.io.Serializable;
import java.util.*;

public class BookShopDTO implements Serializable {
    ArrayList<NhanVienDTO> listNV;
    ArrayList<KhachHangDTO> listKH;
    // ArrayList<NguoiDTO> listNguoi;

    public BookShopDTO() {
        listNV = new ArrayList<>();
        listKH = new ArrayList<>();
        // listNguoi = new ArrayList<>();
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
    "113", "123", "QuanLy", 15000000);
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
                case 1 -> create("NhanVien");
                case 2 -> delete("NhanVien");
                case 3 -> find("NhanVien");
                case 4 -> repair("NhanVien");
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }
    }

    public void menuDanhSachQuanLy() {
        while (true) {
            xuatDanhSachNhanVien();
            System.out.println("1. Them Quan Ly");
            System.out.println("2. Xoa Quan Ly");
            System.out.println("3. Tim kiem Quan Ly");
            System.out.println("4. Sua thong tin Quan Ly");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 4)) {
                case 1 -> create("QuanLy");
                case 2 -> delete("QuanLy");
                case 3 -> find("QuanLy");
                case 4 -> repair("QuanLy");
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
                case 1 -> create("KhachHang");
                case 2 -> delete("KhachHang");
                case 3 -> find("KhachHang");
                case 4 -> repair("KhachHang");
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }
    }


    public void create(String choice) {
        String SDT;
        while (true) {
            SDT = Check.takeStringInput("Nhap SDT: ");
            if (timKiemTheoSDT(SDT) != null) {
                Check.printError("ID nay da co vui long chon SDT khac");
            } else {
                break;
            }
        }
        if (choice.equals("KhachHang")) {
            String hoTen = Check.takeStringInput("Nhap ho ten: ");
            KhachHangDTO kh = new KhachHangDTO(hoTen,SDT,'0');
            listKH.add(kh);
        }

       
        
        if (choice.equals("NhanVienBanHang")){
            String hoTen = Check.takeStringInput("Nhap ho ten: ");
            String diaChi = Check.takeStringInput("Nhap dia chi: ");
            String ns = Check.takeDateInput("Nhap ngay sinh: ");
            String gt = chonGioiTinh();
            String cmnd = Check.takeStringInput("Nhap CMND: ");
            String pass = Check.takeStringInput("Nhap password: ");
            int mucLuong = Check.takeIntegerInput("Nhap muc luong: ");
            NhanVienDTO nhanVienBanHang = new NhanVienBanHangDTO(hoTen, diaChi, SDT, ns, gt, cmnd, pass,"Nhan Vien Ban Hang",mucLuong);
            listNV.add(nhanVienBanHang);

        }
        if (choice.equals("QuanLy")){
            String hoTen = Check.takeStringInput("Nhap ho ten: ");
            String diaChi = Check.takeStringInput("Nhap dia chi: ");
            String ns = Check.takeDateInput("Nhap ngay sinh: ");
            String gt = chonGioiTinh();
            String cmnd = Check.takeStringInput("Nhap CMND: ");
            String pass = Check.takeStringInput("Nhap password: ");
            int mucLuong = Check.takeIntegerInput("Nhap muc luong: ");
            NhanVienDTO QuanLy = new NhanVienBanHangDTO(hoTen, diaChi, SDT, ns, gt, cmnd, pass,"Quan Ly",mucLuong);
            listNV.add(QuanLy);

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

    public NguoiDTO timKiemTheoSDT(String SDT) {

        if( listKH.stream()
                .filter(x -> x.getSDT().equals(SDT))
                .findAny()
                .orElse(null) != null)
            return  listKH.stream()
                .filter(x -> x.getSDT().equals(SDT))
                .findAny()
                .orElse(null);
        else{
            return  listNV.stream()
            .filter(x -> x.getSDT().equals(SDT))
            .findAny()
            .orElse(null);
        }
    }

    public NguoiDTO timKiemTheoSDT(String SDT, String dk) {
        if (dk.equals("QuanLy"))
            return listNV.stream()
                    .filter(x -> x.getSDT().equals(SDT) && x instanceof QuanLyDTO)
                    .findFirst()
                    .orElse(null);
        else if (dk.equals("NhanVienBanHang"))
            return listNV.stream()
                    .filter(x -> x.getSDT().equals(SDT) && x instanceof NhanVienBanHangDTO)
                    .findFirst()
                    .orElse(null);
        else
            return listKH.stream()
                    .filter(x -> x.getSDT().equals(SDT) &&
                            (x instanceof KhachHangDTO))
                    .findFirst()
                    .orElse(null);
    }
    // delete nv,kh
    public void delete(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can xoa: ");
        NguoiDTO nguoi = timKiemTheoSDT(sdt, choice);
        if (nguoi == null)
            Check.printError("Khong tim thay sdt");
        else {
            listNV.remove(nguoi);
            listKH.remove(nguoi);
            Check.printMessage("Xoa thanh cong");
        }
    }

    public void timKiemNguoi(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can tim can tim: ");
        ArrayList<NhanVienDTO> filter = new ArrayList<>();
        // ông trùm nên tìm được QL vs NVBH
        for (NhanVienDTO nguoi : listNV) {
            if (choice.equals("QuanLy") && nguoi instanceof QuanLyDTO ||
                    choice.equals("NhanVien") && nguoi instanceof NhanVienBanHangDTO) {
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
    public void find(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can tim can tim: ");
        ArrayList<NhanVienDTO> filter = new ArrayList<>();
        ArrayList<KhachHangDTO> filter1 = new ArrayList<>();
        if(choice.equals("KhachHang")){
            for (KhachHangDTO nguoi : listKH) {
                if (choice.equals("KhachHang")) {
                    if (nguoi.getSDT().toLowerCase(Locale.ROOT).contains(sdt.toLowerCase(Locale.ROOT))) {
                        filter1.add(nguoi);
                    }
                }
            }
    
            System.out.println(Check.toBlueText("Tim kiem theo sdt: ") + Check.toGreenText(sdt));
            if (filter.isEmpty())
                Check.printError("Khong tim thay");
            else {
                    for (KhachHangDTO nguoi : filter1)
                        nguoi.xuatThongTin();
            }
        }
        // ông trùm nên tìm được QL vs NVBH
        for (NhanVienDTO nguoi : listNV) {
            if (choice.equals("QuanLy") && nguoi instanceof QuanLyDTO ||
                    choice.equals("NhanVien") && nguoi instanceof NhanVienBanHangDTO) {
                if (nguoi.getSDT().toLowerCase(Locale.ROOT).contains(sdt.toLowerCase(Locale.ROOT))) {
                    filter.add(nguoi);
                }
            }
        }

        System.out.println(Check.toBlueText("Tim kiem theo sdt: ") + Check.toGreenText(sdt));
        if (filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
                for (NhanVienDTO nguoi : filter)
                    nguoi.xuatThongTin();
        }
    }
   

    public void repair(String choice) {
        String sdt = Check.takeStringInput("Nhap sdt can sua: ");
        NguoiDTO nguoi = timKiemTheoSDT(sdt, choice);
        if (nguoi == null)
            Check.printError("Khong tim thay sdt");
        else {
            boolean out = false;
            do {
                 if(choice.equals("NhanVien"))
                    xuatDanhSachNhanVien();
                    System.out.println("1. Sua ho ten");
                    System.out.println("2. Sua so dien thoai");
                    System.out.println("3. Sua dia chi");
                    System.out.println("4. Sua ngay sinh");
                    System.out.println("5. Sua gioi tinh");
                    System.out.println("6. Sua CMND");
                    System.out.println("7. Sua password");
                    System.out.println("8. Sua muc luong");
                    System.out.println("9. Sua chuc vu");
                    System.out.println("0. Thoat");    
                if(choice.equals("NhanVien"))
                    xuatDanhSachKhachHang();
                    System.out.println("1. Sua ho ten");
                    System.out.println("2. Sua so dien thoai");


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
                    case 9 -> ((NhanVienDTO) nguoi).setChucVu(Check.takeStringInput("chuc vụ mới : "));
                    case 0 -> out = true;
                }
                if (!out)
                    Check.clearScreen();
            } while (!out);
        }
    }
}