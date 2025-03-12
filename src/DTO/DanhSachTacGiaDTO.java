package DTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

public class DanhSachTacGiaDTO implements Serializable, DanhSach {
    protected TacGiaDTO[] listTacGia;
    private int count = 0;

    public DanhSachTacGiaDTO() {
        listTacGia = new TacGiaDTO[4];
        listTacGia[0] = new TacGiaDTO(getID(), "Nguyen Nhat Anh", "0123456789", "Ha Noi", "nhatanh@email.com");
        listTacGia[1] = new TacGiaDTO(getID(), "To Hoai", "0987654321", "Ha Noi", "tohoai@email.com");
        listTacGia[2] = new TacGiaDTO(getID(), "Nguyen Ngoc Tu", "0123498765", "Can Tho", "ngoctu@email.com");
        listTacGia[3] = new TacGiaDTO(getID(), "Dale Carnegie", "0456789123", "USA", "carnegie@email.com");
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "TG" + str;
    }

    public void xuatTieuDe() {
        System.out.printf("┌%-20s┬%-20s┬%-20s┬%-20s┬%-20s┐%n",
                Check.repeatStr("─", 20), Check.repeatStr("─", 20), Check.repeatStr("─", 20),
                Check.repeatStr("─", 20), Check.repeatStr("─", 20));
        System.out.printf("│%-20s│%-20s│%-20s│%-20s│%-20s│%n",
                "Ma tac gia", "Ten tac gia", "SDT", "Dia chi", "Email");
        System.out.printf("├%-20s┼%-20s┼%-20s┼%-20s┼%-20s┤%n",
                Check.repeatStr("─", 20), Check.repeatStr("─", 20), Check.repeatStr("─", 20),
                Check.repeatStr("─", 20), Check.repeatStr("─", 20));
    }

    public void xuatDS() {
        if (getSoLuong() <= 0) {
            Check.printError("Khong co tac gia nao trong danh sach!");
            return;
        }
        xuatTieuDe();
        for (TacGiaDTO tacGia : listTacGia) {
            tacGia.xuatThongTin();
        }
    }

    public void themTacGia() {
        listTacGia = Arrays.copyOf(listTacGia, getSoLuong() + 1);
        listTacGia[getSoLuong() - 1] = new TacGiaDTO();
        listTacGia[getSoLuong() - 1].nhapThongTin(getID());
    }

    public int timKiemTacGia(String maTacGia) {
        for (int i = 0; i < listTacGia.length; i++) {
            if (listTacGia[i].getMaTacGia().equals(maTacGia)) {
                return i;
            }
        }
        return -1;
    }

    public void xoaTacGia() {
        String maTacGia = Check.takeStringInput("Nhap ma tac gia can xoa: ");
        int index = timKiemTacGia(maTacGia);
        if (index == -1) {
            Check.printError("Khong co ma tac gia nay");
        } else {
            for (int i = index; i < listTacGia.length - 1; i++) {
                listTacGia[i] = listTacGia[i + 1];
            }
            listTacGia[listTacGia.length - 1] = null;
            listTacGia = Arrays.copyOf(listTacGia, listTacGia.length - 1);
        }
    }

    public TacGiaDTO timKiemTheoID(String id) {
        for (TacGiaDTO tacGia : listTacGia) {
            if (tacGia.getMaTacGia().equals(id))
                return tacGia;
        }
        return null;
    }
    // public int timKiemTheoID(String id) {
    // for (int i = 0; i < listTacGia.length; i++) {
    // if (listTacGia[i].getMaTacGia().equals(id))
    // return i;
    // }
    // return null;
    // }

    public void menuSuaTacGia() {
        String idSua = Check.takeStringInput("Nhap ma tac gia can sua: ");
        TacGiaDTO tacGiaSua = timKiemTheoID(idSua);
        if (tacGiaSua == null)
            Check.printError("Khong tim thay");
        else {
            boolean outChange;
            do {
                outChange = false;
                xuatTieuDe();
                tacGiaSua.xuatThongTin();
                System.out.println("1. Sua ten");
                System.out.println("2. Sua SDT");
                System.out.println("3. Sua dia chi");
                System.out.println("4. Sua email");
                System.out.println("0. Thoat sua");
                switch (Check.takeInputChoice(0, 4)) {
                    case 1 -> tacGiaSua.setTen(Check.takeStringInput("Nhap ten moi: "));
                    case 2 -> tacGiaSua.setSDT(Check.takeStringInput("Nhap SDT moi: "));
                    case 3 -> tacGiaSua.setDiaChi(Check.takeStringInput("Nhap dia chi moi: "));
                    case 4 -> tacGiaSua.setEmail(Check.takeStringInput("Nhap email moi: "));
                    case 0 -> outChange = true;
                }
                if (!outChange)
                    Check.clearScreen();
            } while (!outChange);
        }
    }

    public void menu() {
        while (true) {
            xuatDS();
            System.out.println("1. Xoa tac gia");
            System.out.println("2. Them tac gia");
            System.out.println("3. Tim kiem");
            System.out.println("4. Sua");
            System.out.println("0. Thoat");
            boolean out = false;
            switch (Check.takeInputChoice(0, 4)) {
                case 1 -> xoaTacGia();
                case 2 -> themTacGia();
                case 3 -> timKiemTrongBang();
                case 4 -> menuSuaTacGia();
                case 0 -> out = true;
            }
            if (out)
                break;
            Check.clearScreen();
        }
    }

    public void timKiemTrongBang() {
        String tuKhoa = Check.takeStringInput("Nhap tu khoa: ");
        TacGiaDTO[] filter = new TacGiaDTO[getSoLuong()];
        int length = 0;
        for (int i = 0; i < getSoLuong(); i++) {
            if (listTacGia[i].getTen().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    listTacGia[i].getDiaChi().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    listTacGia[i].getSDT().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                    listTacGia[i].getEmail().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT))) {
                filter[length++] = listTacGia[i];
            }
        }

        System.out.println(Check.toBlueText("Tim kiem trong bang theo tu khoa: ") + Check.toGreenText(tuKhoa));
        if (length == 0) {
            Check.printError("Khong tim thay");
            return;
        }
        // xuatTieuDe();
        for (int i = 0; i < length; i++) {
            filter[i].xuatThongTin();
        }
    }

    public int getSoLuong() {
        return listTacGia.length;
    }

    public TacGiaDTO[] getListTacGia() {
        return listTacGia;
    }

    public void setListTacGia(TacGiaDTO[] listTacGia) {
        this.listTacGia = listTacGia;
    }
}