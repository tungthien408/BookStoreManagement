package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.NXBDAO;
import DTO.NXBDTO;

public class NXBBUS {
    private NXBDAO nhaXuatBanDAO;

    public NXBBUS() {
        nhaXuatBanDAO = new NXBDAO();
    }

    // Thêm nhà xuất bản mới
    public boolean addNhaXuatBan(NXBDTO nxb) {
        // Kiểm tra dữ liệu đầu vào
        if (nxb.getMaNXB() == null || nxb.getMaNXB().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhà xuất bản không được để trống!");
        }
        if (nxb.getTenNXB() == null || nxb.getTenNXB().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhà xuất bản không được để trống!");
        }
        if (nxb.getSdt() != null && !nxb.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }

        // Kiểm tra xem MANXB đã tồn tại chưa
        NXBDTO existing = nhaXuatBanDAO.getByMaNXB(nxb.getMaNXB());
        if (existing != null) {
            throw new IllegalArgumentException("Mã nhà xuất bản đã tồn tại!");
        }

        // Gọi DAO để thêm
        return nhaXuatBanDAO.create(nxb);
    }

    // Lấy danh sách nhà xuất bản
    public List<NXBDTO> getAllNhaXuatBan() {
        return nhaXuatBanDAO.getAll();
    }

    // Lấy nhà xuất bản theo MANXB
    public NXBDTO getNhaXuatBanByMaNXB(String maNXB) {
        if (maNXB == null || maNXB.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhà xuất bản không được để trống!");
        }
        return nhaXuatBanDAO.getByMaNXB(maNXB);
    }

    // Cập nhật nhà xuất bản
    public boolean updateNhaXuatBan(NXBDTO nxb) {
        if (nxb.getMaNXB() == null || nxb.getMaNXB().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhà xuất bản không được để trống!");
        }
        if (nxb.getTenNXB() == null || nxb.getTenNXB().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhà xuất bản không được để trống!");
        }
        if (nxb.getSdt() != null && !nxb.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        return nhaXuatBanDAO.update(nxb);
    }

    // Xóa nhà xuất bản (xóa mềm)
    public boolean deleteNhaXuatBan(String maNXB) {
        if (maNXB == null || maNXB.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhà xuất bản không được để trống!");
        }
        return nhaXuatBanDAO.delete(maNXB);
    }

    // Tìm kiếm nhà xuất bản theo tên (gần đúng)
    public List<NXBDTO> searchByTenNXB(String tenNXB) {
        List<NXBDTO> result = new ArrayList<>();
        for (NXBDTO nxb : nhaXuatBanDAO.getAll()) {
            if (nxb.getTenNXB().toLowerCase().contains(tenNXB.toLowerCase())) {
                result.add(nxb);
            }
        }
        return result;
    }
}