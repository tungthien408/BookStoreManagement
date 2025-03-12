package DTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

public class DanhSachSachDTO implements Serializable, DanhSach {
    protected SachDTO[] listSach;
    private int count = 0;

    public DanhSachSachDTO() {
        listSach = new SachDTO[5];
        listSach[0] = new SachGiaoKhoa(getID(), "Toan 12", "TG001", "16-18", 45000, "Giao khoa", "NXB001", 200);
        listSach[1] = new SachThamKhao(getID(), "Luyen thi THPT", "TG002", "16-18", 65000, "Tham khao", "NXB002", 150);
        listSach[2] = new SachTruyen(getID(), "Doremon", "TG003", "6-15", 25000, "Truyen tranh", "NXB001", 120);
        listSach[3] = new SachKyNangSong(getID(), "Dac Nhan Tam", "TG004", "16+", 108000, "Ky nang song", "NXB002", 80);
        listSach[4] = new SachGiaoKhoa(getID(), "Ngu Van 12", "TG001", "16-18", 42000, "Giao khoa", "NXB001", 100);
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "S" + str;
    }

    public void xuatTieuDe() {
        System.out.format("┌%-15s┬%-18s┬%-15s┬%-10s┬%-15s┬%-15s┬%-15s┬%-15s┐%n",
                Check.repeatStr("─", 15), Check.repeatStr("─", 18), Check.repeatStr("─", 15),
                Check.repeatStr("─", 10), Check.repeatStr("─", 15), Check.repeatStr("─", 15),
                Check.repeatStr("─", 15), Check.repeatStr("─", 15));
        System.out.format("│%-15s│%-18s│%-15s│%-10s│%-15s│%-15s│%-15s│%-15s│%n",
                "Ma sach", "Ten sach", "Ma tac gia", "Lua tuoi", "Gia ban",
                "Ma NXB", "Ngon ngu", "Ton kho");
        System.out.format("├%-15s┼%-18s┼%-15s┼%-10s┼%-15s┼%-15s┼%-15s┼%-15s┤%n",
                Check.repeatStr("─", 15), Check.repeatStr("─", 18), Check.repeatStr("─", 15),
                Check.repeatStr("─", 10), Check.repeatStr("─", 15), Check.repeatStr("─", 15),
                Check.repeatStr("─", 15), Check.repeatStr("─", 15));
    }

    public void xuatDS() {
        if (getSoLuong() <= 0) {
            Check.printError("Khong co sach nao trong danh sach!");
            return;
        }
        xuatTieuDe();
        for (int i = 0; i < listSach.length; i++) {
            listSach[i].xuatThongTin();
        }
    }

    public void themSach(DanhSachTacGiaDTO danhSachTacGia, DanhSachNhaXuatBanDTO dsNXB) {
        System.out.println("1. Sach giao khoa");
        System.out.println("2. Sach tham khao");
        System.out.println("3. Sach truyen");
        System.out.println("4. Sach ky nang song");
        System.out.println("0. Thoat");

        switch (Check.takeInputChoice(0, 4)) {
            case 1 -> {
                listSach = Arrays.copyOf(listSach, getSoLuong() + 1);
                listSach[getSoLuong() - 1] = new SachGiaoKhoa();
                listSach[getSoLuong() - 1].nhapThongTin();
                listSach[getSoLuong() - 1].setMaSach(getID());
                listSach[getSoLuong() - 1].setMaTacGia(nhapMaTacGia(danhSachTacGia));
                listSach[getSoLuong() - 1].setMaNhaXuatBan(nhapMaNXB(dsNXB));
            }
            case 2 -> {
                listSach = Arrays.copyOf(listSach, getSoLuong() + 1);
                listSach[getSoLuong() - 1] = new SachThamKhao();
                listSach[getSoLuong() - 1].nhapThongTin();
                listSach[getSoLuong() - 1].setMaSach(getID());
                listSach[getSoLuong() - 1].setMaTacGia(nhapMaTacGia(danhSachTacGia));
                listSach[getSoLuong() - 1].setMaNhaXuatBan(nhapMaNXB(dsNXB));
            }
            case 3 -> {
                listSach = Arrays.copyOf(listSach, getSoLuong() + 1);
                listSach[getSoLuong() - 1] = new SachTruyen();
                listSach[getSoLuong() - 1].nhapThongTin();
                listSach[getSoLuong() - 1].setMaSach(getID());
                listSach[getSoLuong() - 1].setMaTacGia(nhapMaTacGia(danhSachTacGia));
                listSach[getSoLuong() - 1].setMaNhaXuatBan(nhapMaNXB(dsNXB));
            }
            case 4 -> {
                listSach = Arrays.copyOf(listSach, getSoLuong() + 1);
                listSach[getSoLuong() - 1] = new SachKyNangSong();
                listSach[getSoLuong() - 1].nhapThongTin();
                listSach[getSoLuong() - 1].setMaSach(getID());
                listSach[getSoLuong() - 1].setMaTacGia(nhapMaTacGia(danhSachTacGia));
                listSach[getSoLuong() - 1].setMaNhaXuatBan(nhapMaNXB(dsNXB));
            }
        }
    }

    public String nhapMaTacGia(DanhSachTacGiaDTO danhSachTacGia) {
        String maTG;
        boolean check;
        do {
            check = false;
            maTG = Check.takeStringInput("Nhap ma tac gia: ");
            if (danhSachTacGia.timKiemTheoID(maTG) == null) {
                Check.printError("Khong co ma tac gia nay");
                check = true;
            } else {
                Check.printMessage("Nhap ma tac gia thanh cong");
                check = false;
            }

        } while (check);
        return maTG;
    }

    public String nhapMaNXB(DanhSachNhaXuatBanDTO dsNXB) {
        String maNXB;
        boolean check;
        do {
            check = false;
            maNXB = Check.takeStringInput("Nhap ma nha xuat ban: ");
            if (dsNXB.timKiemTheoID(maNXB) == null) {
                Check.printError("Khong co ma nha xuat ban nay");
                check = true;
            } else {
                Check.printMessage("Nhap ma nha xuat thanh cong");
                check = false;
            }

        } while (check);
        return maNXB;
    }

    public int timKiemTheoMa(String maSach) {
        for (int i = 0; i < listSach.length; i++) {
            if (listSach[i].getMaSach().equals(maSach)) {
                return i;
            }
        }
        return -1;
    }

    public void xoaSach() {
        if (getSoLuong() == 0) {
            Check.printError("Danh sach dang rong");
            return;
        }

        String maSach = Check.takeStringInput("Nhap ma sach can xoa: ");
        int index = timKiemTheoMa(maSach);
        if (index == -1) {
            Check.printError("Khong co ma sach nay");
        } else {
            for (int i = index; i < listSach.length - 1; i++) {
                listSach[i] = listSach[i + 1];
            }
            listSach[listSach.length - 1] = null;
            listSach = Arrays.copyOf(listSach, listSach.length - 1);
        }
    }

    public void timKiem() {
        String tuKhoa = Check.takeStringInput("Nhap tu khoa can tim: ");
        SachDTO[] dsSach = new SachDTO[listSach.length];

        int gia;
        try {
            gia = Integer.parseInt(tuKhoa);
        } catch (NumberFormatException e) {
            gia = Integer.MIN_VALUE;
        }

        int index = 0;
        for (SachDTO sach : listSach) {
            if (sach.getMaSach().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    sach.getTenSach().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    sach.getMaNhaXuatBan().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    sach.getMaTacGia().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    sach.getLuaTuoi().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    sach.getNgonNgu().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    sach.getGiaBan() == gia) {
                dsSach[index++] = sach;
            }
        }

        System.out.println(Check.toBlueText("Tim kiem theo tu khoa: ") + Check.toGreenText(tuKhoa));
        if (index == 0)
            Check.printError("Khong tim thay");
        else {
            // xuatTieuDe();
            for (int i = 0; i < index; i++) {
                dsSach[i].xuatThongTin();
            }
        }
    }

    public void menu(DanhSachTacGiaDTO danhSachTacGia, DanhSachNhaXuatBanDTO dsNXB) {
        while (true) {
            xuatDS();
            System.out.println("1. Them sach");
            System.out.println("2. Tim kiem trong bang");
            System.out.println("3. Xoa sach");
            System.out.println("4. Sua thong tin sach");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 4)) {
                case 1 -> themSach(danhSachTacGia, dsNXB);
                case 2 -> timKiem();
                case 3 -> xoaSach();
                case 4 -> menuSua(danhSachTacGia, dsNXB);
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }
    }

    public void menuSua(DanhSachTacGiaDTO danhSachTacGia, DanhSachNhaXuatBanDTO dsNXB) {
        String maSach = Check.takeStringInput("Nhap ma sach can sua: ");
        int index = timKiemTheoMa(maSach);
        if (index == -1)
            Check.printError("Khong tim thay ma sach");
        else {
            boolean outChange = false;
            do {
                xuatTieuDe();
                listSach[index].xuatThongTin();
                System.out.println("1. Sua ten sach");
                System.out.println("2. Sua ma tac gia");
                System.out.println("3. Sua lua tuoi");
                System.out.println("4. Sua gia ban");
                System.out.println("5. Sua ma nha xuat ban");
                System.out.println("0. Thoat");
                switch (Check.takeInputChoice(0, 5)) {
                    case 1 -> listSach[index].setTenSach(Check.takeStringInput("Nhap ten moi: "));
                    case 2 -> listSach[index].setMaTacGia(nhapMaTacGia(danhSachTacGia));
                    case 3 -> listSach[index].setLuaTuoi(Check.takeStringInput("Nhap lua tuoi moi: "));
                    case 4 -> listSach[index].setGiaBan(Check.takeIntegerInput("Nhap gia ban moi: "));
                    case 5 -> listSach[index].setMaNhaXuatBan(nhapMaNXB(dsNXB));
                    case 0 -> outChange = true;
                }
                if (!outChange)
                    Check.clearScreen();
            } while (!outChange);
        }
    }

    public int getSoLuong() {
        return listSach.length;
    }

    public SachDTO[] getListSach() {
        return listSach;
    }

    public void setListSach(SachDTO[] listSach) {
        this.listSach = listSach;
    }
}