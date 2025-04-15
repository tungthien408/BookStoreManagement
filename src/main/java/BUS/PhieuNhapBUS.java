package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;
    private NhanVienBUS nhanVienBUS; // Để kiểm tra MANV
    private NXBBUS nhaXuatBanBUS; // Để kiểm tra MANXB

    public PhieuNhapBUS() {
        phieuNhapDAO = new PhieuNhapDAO();
        nhanVienBUS = new NhanVienBUS();
        nhaXuatBanBUS = new NXBBUS();
    }

    // Thêm phiếu nhập mới
    public boolean addPhieuNhap(PhieuNhapDTO phieuNhap) {
        // Kiểm tra dữ liệu đầu vào
        if (phieuNhap.getMaPN() == null || phieuNhap.getMaPN().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu nhập không được để trống!");
        }
        if (phieuNhap.getTongTien() < 0) {
            throw new IllegalArgumentException("Tổng tiền không được nhỏ hơn 0!");
        }

        // Kiểm tra khóa ngoại MANV (nếu có)
        if (phieuNhap.getMaNV() != null && !phieuNhap.getMaNV().trim().isEmpty()) {
            if (nhanVienBUS.getNhanVienByMaNV(phieuNhap.getMaNV()) == null) {
                throw new IllegalArgumentException("Mã nhân viên không tồn tại!");
            }
        }

        // Kiểm tra khóa ngoại MANXB (nếu có)
        if (phieuNhap.getMaNXB() != null && !phieuNhap.getMaNXB().trim().isEmpty()) {
            if (nhaXuatBanBUS.getNhaXuatBanByMaNXB(phieuNhap.getMaNXB()) == null) {
                throw new IllegalArgumentException("Mã nhà xuất bản không tồn tại!");
            }
        }

        // Kiểm tra xem MAPN đã tồn tại chưa
        PhieuNhapDTO existing = phieuNhapDAO.getByMaPN(phieuNhap.getMaPN());
        if (existing != null) {
            throw new IllegalArgumentException("Mã phiếu nhập đã tồn tại!");
        }

        // Gọi DAO để thêm
        return phieuNhapDAO.create(phieuNhap);
    }

    // Lấy danh sách phiếu nhập
    public List<PhieuNhapDTO> getAllPhieuNhap() {
        return phieuNhapDAO.getAll();
    }

    // Lấy phiếu nhập theo MAPN
    public PhieuNhapDTO getPhieuNhapByMaPN(String maPN) {
        if (maPN == null || maPN.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu nhập không được để trống!");
        }
        return phieuNhapDAO.getByMaPN(maPN);
    }


    // Xóa phiếu nhập (xóa mềm)
    public boolean deletePhieuNhap(String maPN) {
        if (maPN == null || maPN.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu nhập không được để trống!");
        }
        return phieuNhapDAO.delete(maPN);
    }

    // Tìm kiếm phiếu nhập theo mã nhân viên
    public List<PhieuNhapDTO> searchByMaNV(String maNV) {
        List<PhieuNhapDTO> result = new ArrayList<>();
        for (PhieuNhapDTO pn : phieuNhapDAO.getAll()) {
            if (pn.getMaNV() != null && pn.getMaNV().equals(maNV)) {
                result.add(pn);
            }
        }
        return result;
    }

    // Tìm kiếm phiếu nhập theo mã nhà xuất bản
    public List<PhieuNhapDTO> searchByMaNXB(String maNXB) {
        List<PhieuNhapDTO> result = new ArrayList<>();
        for (PhieuNhapDTO pn : phieuNhapDAO.getAll()) {
            if (pn.getMaNXB() != null && pn.getMaNXB().equals(maNXB)) {
                result.add(pn);
            }
        }
        return result;
    }
}