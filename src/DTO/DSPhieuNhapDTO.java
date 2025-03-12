package DTO;


import java.io.Serializable;
import java.util.ArrayList;

public class DSPhieuNhapDTO implements Serializable, DanhSach {
    private ArrayList<PhieuNhapDTO> dsPhieuNhap;
    private int count = 0;

    public DSPhieuNhapDTO(DanhSachSachDTO danhSachSach) {
        dsPhieuNhap = new ArrayList<>();
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while (str.length() != 3)
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
            for (PhieuNhapDTO phieuNhap : dsPhieuNhap) {
                phieuNhap.inPhieu();
            }
        } else {
            Check.printError("Danh sach phieu dang rong");
        }
    }

    public void menu(DanhSachSachDTO danhSachSach, NguoiDTO nguoi, BookShopDTO bookShop, DanhSachNhaXuatBanDTO dsNXB) {
        boolean thoatXemDSPhieu = false;
        while (true) {
            xuatTieuDe();
            xuatDS();
            System.out.println("1. Xem chi tiet phieu nhap");
            System.out.println("2. Them phieu nhap");
            System.out.println("3. Xoa phieu nhap");
            System.out.println("4. Tim kiem trong bang");
            System.out.println("0. Quay lai");
            switch (Check.takeInputChoice(0, 4)) {
                // case 1 -> xemChiTietPhieu();
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

    // public void xemChiTietPhieu() {
    //     if (dsPhieuNhap.isEmpty())
    //         Check.printError("Danh sach dang rong");
    //     else {
    //         String id = Check.takeStringInput("Nhap ID phieu can xem chi tiet: ");
    //         PhieuNhapDTO obj = timKiemTheoID(id);
    //         if (obj == null) {
    //             Check.printError("Khong co ID do trong danh sach");
    //             return;
    //         }
    //         obj.xemChiTietPhieu();
    //     }
    // }

    public PhieuNhapDTO timKiemTheoID(String id) {
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
        PhieuNhapDTO phieu = dsPhieuNhap.stream()
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
        ArrayList<PhieuNhapDTO> filter = new ArrayList<>();
        int tongTien = 0;

        for (PhieuNhapDTO phieuNhap : dsPhieuNhap) {
            try {
                tongTien = Integer.parseInt(tuKhoa);
            } catch (NumberFormatException ignored) {
            }

            boolean check = Check.subStrInStrIgnoreCase(phieuNhap.getNgayLap(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(phieuNhap.getSDTNhanVien(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(phieuNhap.getMaNhaXuatBan(), tuKhoa) ||
                    Check.subStrInStrIgnoreCase(phieuNhap.getMaPhieu(), tuKhoa) ||
                    phieuNhap.getTongTien() == tongTien;
            if (check)
                filter.add(phieuNhap);
        }

        System.out.println(Check.toBlueText("Ket qua tim kiem theo tu khoa: ") +
                Check.toGreenText(tuKhoa));
        if (filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
            xuatTieuDe();
            for (PhieuNhapDTO phieuNhap : filter)
                phieuNhap.inPhieu();
        }
    }

    public void add(NguoiDTO nguoi, DanhSachSachDTO danhSachSach, DanhSachNhaXuatBanDTO dsNXB) {
        String idPhieu = getID();
        String idNXB = nhapNXB(dsNXB);

        DSChiTietPhieuDTO dsChiTietPhieu = new DSChiTietPhieuDTO();
        boolean finish = false;
        boolean flag = false;
        do {
            danhSachSach.xuatDS();
            System.out.println("1. Nhap sach");
            System.out.println("2. Xac nhan nhap hang");
            switch (Check.takeInputChoice(1, 2)) {

                case 1 -> {
                    flag = true;
                    String maSach = Check.takeStringInput("Nhap ma sach: ");
                    int index = danhSachSach.timKiemTheoMa(maSach);
                    if (index != -1) {
                        int sl = Check.takeIntegerInput("Nhap so luong: ");
                        ChiTietPhieuDTO chiTietPhieu = dsChiTietPhieu.search(maSach);
                        if (chiTietPhieu != null)
                            chiTietPhieu.setSoLuong(chiTietPhieu.getSoLuong() + sl);
                        else
                            dsChiTietPhieu.add(new ChiTietPhieuDTO(maSach, sl,
                                    danhSachSach.getListSach()[index].getGiaBan()));
                    } else {
                        Check.printError("Khong co sach nay");
                        flag = false;
                    }

                }
                case 2 -> {
                    if (flag == true) {
                        finish = true;
                    } else {
                        Check.printError("vui long nhap sach");
                    }
                }
            }
            if (!finish)
                Check.clearScreen();
        } while (!finish);

        dsPhieuNhap.add(new PhieuNhapDTO(idPhieu, Check.getDateNow(), idNXB, nguoi.getSDT(),
                dsChiTietPhieu, dsChiTietPhieu.tinhTongTien()));

        for (int i = 0; i < dsChiTietPhieu.getSize(); i++) {
            ChiTietPhieuDTO chiTietPhieu = dsChiTietPhieu.getChiTietPhieus()[i];
            SachDTO sach = danhSachSach.getListSach()[danhSachSach.timKiemTheoMa(chiTietPhieu.getMaSach())];
            sach.setSoLuong(sach.getSoLuong() + chiTietPhieu.getSoLuong());
        }
    }

    public String nhapNXB(DanhSachNhaXuatBanDTO danhSachNhaXuatBan) {
        String idNXB;
        while (true) {
            danhSachNhaXuatBan.xuatDS();
            idNXB = Check.takeStringInput("Nhap ma nha xuat ban: ");
            if (danhSachNhaXuatBan.timKiemTheoID(idNXB) != null)
                return idNXB;
            else
                Check.printError("Khong co nha xuat ban nay");
        }
    }
}