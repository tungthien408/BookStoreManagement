package QuanLyCuaHangBanSach;

import java.io.Serializable;
import java.util.ArrayList;

public class DSPhieuNhap implements Serializable, DanhSach {
    private ArrayList<PhieuNhap> dsPhieuNhap;
    private int count = 0;

    public DSPhieuNhap(DanhSachSach danhSachSach) {
        dsPhieuNhap = new ArrayList<>();
        Sach s1 = danhSachSach.getListSach()[0];
        Sach s2 = danhSachSach.getListSach()[1];
        Sach s3 = danhSachSach.getListSach()[2];

        DSChiTietPhieu ct1 = new DSChiTietPhieu();
        ct1.add(new ChiTietPhieu(s1.getMaSach(), 5, s1.getGiaBan()));
        dsPhieuNhap.add(new PhieuNhap(getID(), "23/8/2023", "NXB001", "vu", ct1, ct1.tinhTongTien()));

        DSChiTietPhieu ct2 = new DSChiTietPhieu();
        ct2.add(new ChiTietPhieu(s2.getMaSach(), 6, s2.getGiaBan()));
        ct2.add(new ChiTietPhieu(s3.getMaSach(), 9, s3.getGiaBan()));
        dsPhieuNhap.add(new PhieuNhap(getID(), "8/8/2023", "NXB001", "vu", ct2, ct2.tinhTongTien()));
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while(str.length() != 3)
            str = "0" + str;
        return "PN" + str;
    }

    public void xuatTieuDe() {
        System.out.printf("┌%-16s┬%-16s┬%-16s┬%-16s┬%-16s┐\n",
                Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 16));
        System.out.printf("│%-16s│%-16s│%-16s│%-16s│%-16s│\n", 
            "Ma phieu", "Ngay lap", "Tong tien", "Ma nhan vien", "Ma NXB");
        System.out.printf("├%-16s┼%-16s┼%-16s┼%-16s┼%-16s┤\n",
                Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 16), Check.repeatStr("─", 16),
                Check.repeatStr("─", 16));
    }

    public void xuatDS() {
        if (!dsPhieuNhap.isEmpty()) {
            for (PhieuNhap phieuNhap : dsPhieuNhap) {
                phieuNhap.inPhieu();
            }
        } else {
            Check.printError("Danh sach phieu dang rong");
        }
    }

    public void menu(DanhSachSach danhSachSach, Nguoi nguoi, BookShop bookShop, DanhSachNhaXuatBan dsNXB) {
        boolean thoatXemDSPhieu = false;
        while(true) {
            xuatTieuDe();
            xuatDS();
            System.out.println("1. Xem chi tiet phieu nhap");
            System.out.println("2. Them phieu nhap");
            System.out.println("3. Xoa phieu nhap");
            System.out.println("4. Tim kiem trong bang");
            System.out.println("0. Quay lai");
            switch (Check.takeInputChoice(0,4)) {
                case 1 -> xemChiTietPhieu();
                case 2 -> add(nguoi, danhSachSach, dsNXB);
                case 3 -> xoaPhieu();
                case 4 -> timKiemTrongBang();
                case 0 -> thoatXemDSPhieu = true;
            }
            if (thoatXemDSPhieu)
                break;
            Check.clearScreen();
        }
    }

    public void xemChiTietPhieu() {
        if(dsPhieuNhap.isEmpty())
            Check.printError("Danh sach dang rong");
        else {
            String id = Check.takeStringInput("Nhap ID phieu can xem chi tiet: ");
            PhieuNhap obj = timKiemTheoID(id);
            if(obj == null) {
                Check.printError("Khong co ID do trong danh sach");
                return;
            }
            obj.xemChiTietPhieu();
        }
    }

    public PhieuNhap timKiemTheoID(String id) {
        return dsPhieuNhap.stream()
            .filter(x -> x.getMaPhieu().equals(id))
            .findFirst()
            .orElse(null);
    }

    public void xoaPhieu() {
        if (dsPhieuNhap.isEmpty()) {
            Check.printError("Danh sach phieu dang rong");
            return;
        }
        String idPhieu = Check.takeStringInput("Nhap vao id phieu can xoa: ");
        PhieuNhap phieu = dsPhieuNhap.stream()
            .filter(x -> x.getMaPhieu().equals(idPhieu))
            .findAny()
            .orElse(null);
        if (phieu == null)
            Check.printError("Khong co ma phieu do trong danh sach");
        else {
            dsPhieuNhap.remove(phieu);
            Check.printMessage("Xoa phieu thanh cong");
        }
    }

    public void timKiemTrongBang() {
        String tuKhoa = Check.takeStringInput("Nhap tu khoa can tim: ");
        ArrayList<PhieuNhap> filter = new ArrayList<>();
        int tongTien = 0;

        for (PhieuNhap phieuNhap : dsPhieuNhap) {
            try {
                tongTien = Integer.parseInt(tuKhoa);
            } catch (NumberFormatException ignored) {}

            boolean check = Check.subStrInStrIgnoreCase(phieuNhap.getNgayLap(), tuKhoa) ||
                Check.subStrInStrIgnoreCase(phieuNhap.getMaNhanVien(), tuKhoa) ||
                Check.subStrInStrIgnoreCase(phieuNhap.getMaNhaXuatBan(), tuKhoa) ||
                phieuNhap.getTongTien() == tongTien;
            if(check)
                filter.add(phieuNhap);
        }

        System.out.println(Check.toBlueText("Ket qua tim kiem theo tu khoa: ") + 
            Check.toGreenText(tuKhoa));
        if(filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
            xuatTieuDe();
            for(PhieuNhap phieuNhap : filter)
                phieuNhap.inPhieu();
        }
    }

    public void add(Nguoi nguoi, DanhSachSach danhSachSach, DanhSachNhaXuatBan dsNXB) {
        String idPhieu = getID();
        String idNXB = nhapNXB(dsNXB);

        DSChiTietPhieu dsChiTietPhieu = new DSChiTietPhieu();
        boolean finish = false;
        do {
            danhSachSach.xuatDS();
            System.out.println("1. Nhap sach");
            System.out.println("2. Xac nhan nhap hang");
            switch (Check.takeInputChoice(1, 2)) {
                case 1 -> {
                    String maSach = Check.takeStringInput("Nhap ma sach: ");
                    int index = danhSachSach.timKiemTheoMa(maSach);
                    if(index != -1) {
                        int sl = Check.takeIntegerInput("Nhap so luong: ");
                        ChiTietPhieu chiTietPhieu = dsChiTietPhieu.search(maSach);
                        if(chiTietPhieu != null)
                            chiTietPhieu.setSoLuong(chiTietPhieu.getSoLuong() + sl);
                        else
                            dsChiTietPhieu.add(new ChiTietPhieu(maSach, sl, 
                                danhSachSach.getListSach()[index].getGiaBan()));
                    }
                    else
                        Check.printError("Khong co sach nay");
                }
                case 2 -> finish = true;
            }
            if(!finish)
                Check.clearScreen();
        } while (!finish);

        dsPhieuNhap.add(new PhieuNhap(idPhieu, Check.getDateNow(), idNXB, nguoi.getId(), 
            dsChiTietPhieu, dsChiTietPhieu.tinhTongTien()));

        for (int i = 0; i < dsChiTietPhieu.getSize(); i++) {
            ChiTietPhieu chiTietPhieu = dsChiTietPhieu.getChiTietPhieus()[i];
            Sach sach = danhSachSach.getListSach()[danhSachSach.timKiemTheoMa(chiTietPhieu.getMaSach())];
            sach.setSoLuong(sach.getSoLuong() + chiTietPhieu.getSoLuong());
        }
    }

    public String nhapNXB(DanhSachNhaXuatBan danhSachNhaXuatBan) {
        String idNXB;
        while(true) {
            danhSachNhaXuatBan.xuatDS();
            idNXB = Check.takeStringInput("Nhap ma nha xuat ban: ");
            if(danhSachNhaXuatBan.timKiemTheoID(idNXB) != null)
                return idNXB;
            else
                Check.printError("Khong co nha xuat ban nay");
        }
    }
} 