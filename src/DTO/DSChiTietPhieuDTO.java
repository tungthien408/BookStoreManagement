package DTO;

import java.io.Serializable;
import java.util.Arrays;

public class DSChiTietPhieuDTO implements Serializable {
    private ChiTietPhieuDTO[] chiTietPhieus;
    private int index;
    private int capacity;

    public DSChiTietPhieuDTO() {
        index = 0;
        capacity = 2;
        chiTietPhieus = new ChiTietPhieuDTO[capacity];
    }

    public boolean empty() {
        return index == 0;
    }

    public void add(ChiTietPhieuDTO obj) {
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

    public ChiTietPhieuDTO search(String id) {
        for (int i = 0; i < index; i++) {
            if(chiTietPhieus[i].getMaSach().equals(id))
                return chiTietPhieus[i];
        }
        return null;
    }

    public DSChiTietPhieuDTO searchSoLuong(int soLuong) {
        DSChiTietPhieuDTO searchArr = new DSChiTietPhieuDTO();
        for (int i = 0; i < index; i++) {
            if(chiTietPhieus[i].getSoLuong() == soLuong)
                searchArr.add(chiTietPhieus[i]);
        }
        return searchArr;
    }

    public DSChiTietPhieuDTO searchDonGia(int donGia) {
        DSChiTietPhieuDTO searchArr = new DSChiTietPhieuDTO();
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

    public ChiTietPhieuDTO[] getChiTietPhieus() {
        return chiTietPhieus;
    }

    public void setChiTietPhieus(ChiTietPhieuDTO[] chiTietPhieus) {
        this.chiTietPhieus = chiTietPhieus;
    }

    public int getSize() {
        return index;
    }

    public int getCapacity() {
        return capacity;
    }
} 