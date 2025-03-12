package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class DanhSachNhaXuatBanDTO implements Serializable, DanhSach {
    private ArrayList<NhaXuatBanDTO> listNhaXuatBan = new ArrayList<>();
    private int count = 0;

    public DanhSachNhaXuatBanDTO() {
        listNhaXuatBan.add(new NhaXuatBanDTO("Kim Dong", "TPHCM", "9045495", "kimdong@email", getID()));
        listNhaXuatBan.add(new NhaXuatBanDTO("Tre", "Ha Noi", "549844", "nxbtre@email", getID()));
        listNhaXuatBan.add(new NhaXuatBanDTO("Giao Duc", "Ha Noi", "123456", "nxbgd@email", getID()));
        listNhaXuatBan.add(new NhaXuatBanDTO("Tong Hop", "TPHCM", "987654", "tonghop@email", getID()));
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while(str.length() != 3)
            str = "0" + str;
        return "NXB" + str;
    }

    public void xuatTieuDe() {
        System.out.printf("┌%-20s┬%-20s┬%-20s┬%-20s┬%-20s┐ \n", 
            Check.repeatStr("─", 20), Check.repeatStr("─", 20), 
            Check.repeatStr("─", 20), Check.repeatStr("─", 20), 
            Check.repeatStr("─", 20));
        System.out.printf("│%-20s│%-20s│%-20s│%-20s│%-20s│ \n",
            "Ma NXB", "Ten NXB", "Dia chi", "So dien thoai", "Email");
        System.out.printf("├%-20s┼%-20s┼%-20s┼%-20s┼%-20s┤ \n", 
            Check.repeatStr("─", 20), Check.repeatStr("─", 20), 
            Check.repeatStr("─", 20), Check.repeatStr("─", 20), 
            Check.repeatStr("─", 20));
    }

    public void xuatDS() {
        System.out.println(Check.toBlueText("Danh sach nha xuat ban"));
        xuatTieuDe();
        for(NhaXuatBanDTO nhaXuatBan : listNhaXuatBan) {
            nhaXuatBan.xuatThongTin();
        }
    }

    public void themNXB() {
        System.out.println(Check.toBlueText("Nhap nha xuat ban moi"));
        String tenNXB = Check.takeStringInput("Ten nha xuat ban: ");
        String diaChi = Check.takeStringInput("Dia chi: ");
        String sdt = Check.takeStringInput("So dien thoai lien lac: ");
        String email = Check.takeStringInput("Email: ");
        String maNXB = getID();

        listNhaXuatBan.add(new NhaXuatBanDTO(tenNXB, diaChi, sdt, email, maNXB));
    }

    public void suaNXB() {
        String id = Check.takeStringInput("Ma nha xuat ban can sua: ");
        NhaXuatBanDTO nhaXuatBan = timKiemTheoID(id);
        if(nhaXuatBan != null) {
            boolean out = false;
            do {
                xuatTieuDe();
                nhaXuatBan.xuatThongTin();
                System.out.println(Check.toBlueText("Sua thong tin nha xuat ban"));
                System.out.println("1. Sua ten nha xuat ban");
                System.out.println("2. Sua dia chi");
                System.out.println("3. Sua so dien thoai");
                System.out.println("4. Sua email");
                System.out.println("0. Thoat");
                switch (Check.takeInputChoice(0, 4)) {
                    case 1 -> nhaXuatBan.setTen(Check.takeStringInput("Nhap ten moi: "));
                    case 2 -> nhaXuatBan.setDiaChi(Check.takeStringInput("Nhap dia chi moi: "));
                    case 3 -> nhaXuatBan.setSdt(Check.takeStringInput("Nhap dien thoai moi: "));
                    case 4 -> nhaXuatBan.setEmail(Check.takeStringInput("Nhap email moi: "));
                    case 0 -> out = true;
                }
                if(!out)
                    Check.clearScreen();
            } while (!out);
        }
    }

    public NhaXuatBanDTO timKiemTheoID(String id) {
        for(NhaXuatBanDTO nhaXuatBan : listNhaXuatBan)
            if(nhaXuatBan.getMaNXB().equals(id))
                return nhaXuatBan;
        return null;
    }

    public void xoaNXB() {
        String id = Check.takeStringInput("Ma nha xuat ban can xoa: ");
        NhaXuatBanDTO find = timKiemTheoID(id);

        if(find != null) {
            listNhaXuatBan.remove(find);
            Check.printMessage("Nha xuat ban da xoa khoi danh sach");
        } else {
            Check.printError("Nha xuat ban khong co trong danh sach");
        }
    }

    public void timKiem() {
        String tuKhoa = Check.takeStringInput("Nhap tu khoa can tim: ");
        ArrayList<NhaXuatBanDTO> listFilter = new ArrayList<>();
        for(NhaXuatBanDTO nhaXuatBan : listNhaXuatBan) {
            if(
                nhaXuatBan.getMaNXB().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                nhaXuatBan.getDiaChi().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                nhaXuatBan.getEmail().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                nhaXuatBan.getTen().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT)) ||
                nhaXuatBan.getSdt().toLowerCase(Locale.ROOT).contains(tuKhoa.toLowerCase(Locale.ROOT))
            ) {
                listFilter.add(nhaXuatBan);
            }
        }

        System.out.println(Check.toBlueText("Ket qua tim kiem theo tu khoa: ") + Check.toGreenText(tuKhoa));
        if(listFilter.isEmpty())
            Check.printError("Khong tim duoc tu khoa nay trong danh sach");
        else {
            // xuatTieuDe();
            for(NhaXuatBanDTO nhaXuatBan : listFilter)
                nhaXuatBan.xuatThongTin();
        }
    }

    public void menuNXB() {
        boolean out = false;
        do {
            xuatDS();
            System.out.println(Check.toBlueText("MENU NHA XUAT BAN"));
            System.out.println("1. Them nha xuat ban moi");
            System.out.println("2. Sua thong tin nha xuat ban");
            System.out.println("3. Xoa nha xuat ban");
            System.out.println("4. Tim kiem trong bang");
            System.out.println("0. Thoat menu");
            switch (Check.takeInputChoice(0, 4)) {
                case 1 -> themNXB();
                case 2 -> suaNXB();
                case 3 -> xoaNXB();
                case 4 -> timKiem();
                case 0 -> out = true;
            }
            if(!out)
                Check.clearScreen();
        } while(!out);
    }

    public ArrayList<NhaXuatBanDTO> getListNhaXuatBan() {
        return listNhaXuatBan;
    }

    public void setListNhaXuatBan(ArrayList<NhaXuatBanDTO> listNhaXuatBan) {
        this.listNhaXuatBan = listNhaXuatBan;
    }
} 