package QuanLyCuaHangBanSach;

import java.io.Serializable;
import java.util.Arrays;

public class DSChiTietPhieu implements Serializable {
    private ChiTietPhieu[] chiTietPhieus;
    private int index;
    private int capacity;

    public DSChiTietPhieu() {
        index = 0;
        capacity = 2;
        chiTietPhieus = new ChiTietPhieu[capacity];
    }

    public boolean empty() {
        return index == 0;
    }

    public void add(ChiTietPhieu obj) {
        if(index + 1 > capacity)
            reSizeArray();

        chiTietPhieus[index] = obj;
        index++;
    }

    public void remove(String id) {
        int z = -1;
        for (int i = 0; i < index; i++) {
            if(chiTietPhieus[i].getMaSach().equals(id)) {
                z = i;
                break;
            }
        }

        if(z == -1) {
            Check.printError("Khong tim thay ma sach: " + id);
            return;
        }

        index--;
        for (int j = z; j < index; j++) {
            chiTietPhieus[j] = chiTietPhieus[j+1];
        }
        Check.printMessage("Xoa thanh cong");
    }

    public ChiTietPhieu search(String id) {
        for (int i = 0; i < index; i++) {
            if(chiTietPhieus[i].getMaSach().equals(id))
                return chiTietPhieus[i];
        }
        return null;
    }

    public DSChiTietPhieu searchSoLuong(int soLuong) {
        DSChiTietPhieu searchArr = new DSChiTietPhieu();
        for (int i = 0; i < index; i++) {
            if(chiTietPhieus[i].getSoLuong() == soLuong)
                searchArr.add(chiTietPhieus[i]);
        }
        return searchArr;
    }

    public DSChiTietPhieu searchDonGia(int donGia) {
        DSChiTietPhieu searchArr = new DSChiTietPhieu();
        for (int i = 0; i < index; i++) {
            if(chiTietPhieus[i].getDonGia() == donGia)
                searchArr.add(chiTietPhieus[i]);
        }
        return searchArr;
    }

    public static void xuatTuaDe() {
        System.out.printf("├%-16s┬%-16s┬%-16s┤\n", 
            Check.repeatStr("─", 16), Check.repeatStr("─", 16), Check.repeatStr("─", 16));
        System.out.printf("│%-16s│%-16s│%-16s│\n", "Ma sach", "So luong", "Don gia");
        System.out.printf("├%-16s┼%-16s┼%-16s┤\n", 
            Check.repeatStr("─", 16), Check.repeatStr("─", 16), Check.repeatStr("─", 16));
    }

    public void xuatDS() {
        xuatTuaDe();
        for (int i = 0; i < index; i++) {
            chiTietPhieus[i].inChiTietPhieu();
        }
    }

    public int tinhTongTien() {
        int sum = 0;
        for (int i = 0; i < index; i++) {
            sum += chiTietPhieus[i].getDonGia() * chiTietPhieus[i].getSoLuong();
        }
        return sum;
    }

    private void reSizeArray() {
        capacity = capacity * 2;
        chiTietPhieus = Arrays.copyOf(chiTietPhieus, capacity);
    }

    public ChiTietPhieu[] getChiTietPhieus() {
        return chiTietPhieus;
    }

    public void setChiTietPhieus(ChiTietPhieu[] chiTietPhieus) {
        this.chiTietPhieus = chiTietPhieus;
    }

    public int getSize() {
        return index;
    }

    public int getCapacity() {
        return capacity;
    }
} 