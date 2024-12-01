package QuanLyCuaHangBanSach;

import java.io.Serializable;
import java.util.ArrayList;

public class DSPhieuXuat implements Serializable, DanhSach {
    private ArrayList<PhieuXuat> dsPhieuXuat;
    private int count = 0;

    public DSPhieuXuat(Nguoi nguoi, DanhSachHoaDon danhSachHoaDon) {
        dsPhieuXuat = new ArrayList<>();
        int count = 0;
        for (HoaDon hoaDon : danhSachHoaDon.getHoaDonArrayList()) {
            if(hoaDon.getTinhTrang().equals("Da xac nhan")) {
                DSChiTietPhieu dsctp = new DSChiTietPhieu();
                DanhSachChiTietHoaDon dscthd = hoaDon.getDsChiTietHoaDon();
                for (ChiTietHoaDon cthd : dscthd.getCthd()) {
                    ChiTietPhieu obj = new ChiTietPhieu(cthd.getMaSach(), 
                        cthd.getSoLuong(), cthd.getGia());
                    dsctp.add(obj);
                }
                if(count <= 4) {
                    PhieuXuat phieuXuat = new PhieuXuat(getID(), hoaDon.getNgayLap(), 
                        hoaDon.getMaNhanVien(), nguoi.getId(), dsctp, dsctp.tinhTongTien(), 
                        hoaDon.getMaHoaDon());
                    dsPhieuXuat.add(phieuXuat);
                    phieuXuat.xuatKho(nguoi, danhSachHoaDon);
                }
                else if(count == 5) {
                    PhieuXuat phieuXuat = new PhieuXuat(getID(), hoaDon.getNgayLap(), 
                        hoaDon.getMaNhanVien(), nguoi.getId(), dsctp, dsctp.tinhTongTien(), 
                        hoaDon.getMaHoaDon());
                    dsPhieuXuat.add(phieuXuat);
                }
                count++;
            }
        }

        count = 0;
        for (HoaDon hoaDon : danhSachHoaDon.getHoaDonArrayList()) {
            if(count <= 3) {
                if(hoaDon.getTinhTrang().equals("Da xuat kho")) {
                    hoaDon.setTinhTrang("Da nhan hang");
                }
            }
            count++;
        }
    }

    public String getID() {
        count++;
        String str = String.valueOf(count);
        while(str.length() != 3)
            str = "0" + str;
        return "PX" + str;
    }

    public void xuatTieuDe() {
        System.out.println(Check.toBlueText("Danh sach phieu xuat"));
        System.out.printf("┌%-8s┬%-8s┬%-12s┬%-16s┬%-25s┬%-25s┬%-16s┐\n", 
            Check.repeatStr("─", 8), Check.repeatStr("─", 8), Check.repeatStr("─", 12),
            Check.repeatStr("─", 16), Check.repeatStr("─", 25),
            Check.repeatStr("─", 25), Check.repeatStr("─", 16));
        System.out.printf("│%-8s│%-8s│%-12s│%-16s│%-25s│%-25s│%-16s│\n", 
            "Ma phieu", "Hoa don", "Ngay lap", "Tong tien",
            "Ma NV ban hang", "Ma NV thu kho", "Tinh trang");
        System.out.printf("├%-8s┼%-8s┼%-12s┼%-16s┼%-25s┼%-25s┼%-16s┤\n", 
            Check.repeatStr("─", 8), Check.repeatStr("─", 8), Check.repeatStr("─", 12),
            Check.repeatStr("─", 16), Check.repeatStr("─", 25),
            Check.repeatStr("─", 25), Check.repeatStr("─", 16));
    }

    public void xuatDS() {
        if (!dsPhieuXuat.isEmpty()) {
            for (PhieuXuat phieuXuat : dsPhieuXuat) {
                phieuXuat.inPhieu();
            }
        } else {
            Check.printError("Danh sach phieu dang rong");
        }
    }

    public void menu(Nguoi nguoi, DanhSachHoaDon danhSachHoaDon) {
        boolean thoatXemDSPhieu = false;
        while(true) {
            xuatTieuDe();
            xuatDS();
            System.out.println(Check.toBlueText("Menu phieu xuat"));
            System.out.println("1. Xem chi tiet phieu xuat");
            System.out.println("2. Xoa phieu xuat");
            System.out.println("3. Tim kiem trong bang");
            System.out.println("0. Quay lai");
            switch (Check.takeInputChoice(0,3)) {
                case 1 -> xemChiTietPhieu(nguoi, danhSachHoaDon);
                case 2 -> xoaPhieu();
                case 3 -> timKiemTrongBang();
                case 0 -> thoatXemDSPhieu = true;
            }
            if (thoatXemDSPhieu)
                break;
            Check.clearScreen();
        }
    }

    public void xemChiTietPhieu(Nguoi nguoi, DanhSachHoaDon danhSachHoaDon) {
        if(dsPhieuXuat.isEmpty())
            Check.printError("Danh sach dang rong");
        else {
            String id = Check.takeStringInput("Nhap ID phieu can xem chi tiet: ");
            PhieuXuat obj = timKiemTheoID(id);
            if(obj == null) {
                Check.printError("Khong co ID do trong danh sach");
                return;
            }
            boolean out = false;
            do {
                obj.xemChiTietPhieu();
                System.out.println("1. Xac nhan xuat kho");
                System.out.println("0. Thoat");
                switch (Check.takeInputChoice(0, 1)) {
                    case 1 -> obj.xuatKho(nguoi, danhSachHoaDon);
                    case 0 -> out = true;
                }
                if(!out)
                    Check.clearScreen();
            } while(!out);
        }
    }

    public PhieuXuat timKiemTheoID(String id) {
        return dsPhieuXuat.stream()
            .filter(x -> x.getMaPhieu().equals(id))
            .findFirst()
            .orElse(null);
    }

    public void xoaPhieu() {
        if (dsPhieuXuat.isEmpty()) {
            Check.printError("Danh sach phieu dang rong");
            return;
        }
        String idPhieu = Check.takeStringInput("Nhap vao id phieu can xoa: ");
        PhieuXuat phieu = dsPhieuXuat.stream()
            .filter(x -> x.getMaPhieu().equals(idPhieu))
            .findAny()
            .orElse(null);
        if (phieu == null)
            Check.printError("Khong co ma phieu do trong danh sach");
        else {
            dsPhieuXuat.remove(phieu);
            Check.printMessage("Xoa phieu thanh cong");
        }
    }

    public void timKiemTrongBang() {
        String tuKhoa = Check.takeStringInput("Nhap tu khoa can tim: ");
        ArrayList<PhieuXuat> filter = new ArrayList<>();
        int tongTien = 0;

        for (PhieuXuat phieuXuat : dsPhieuXuat) {
            try {
                tongTien = Integer.parseInt(tuKhoa);
            } catch (NumberFormatException ignored) {}

            boolean check = Check.subStrInStrIgnoreCase(phieuXuat.getNgayLap(), tuKhoa) ||
                Check.subStrInStrIgnoreCase(phieuXuat.getMaNhanVien(), tuKhoa) ||
                Check.subStrInStrIgnoreCase(phieuXuat.getMaNhanVienThuKho(), tuKhoa) ||
                Check.subStrInStrIgnoreCase(phieuXuat.getTinhTrang(), tuKhoa) ||
                phieuXuat.getTongTien() == tongTien;
            if(check)
                filter.add(phieuXuat);
        }

        System.out.println(Check.toBlueText("Ket qua tim kiem theo tu khoa: ") + 
            Check.toGreenText(tuKhoa));
        if(filter.isEmpty())
            Check.printError("Khong tim thay");
        else {
            xuatTieuDe();
            for(PhieuXuat phieuXuat : filter)
                phieuXuat.inPhieu();
        }
    }
} 