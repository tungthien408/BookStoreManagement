package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.TacGiaDAO;
import DTO.TacGiaDTO;

public class TacGiaBUS {
    private TacGiaDAO tacGiaDAO;

    public TacGiaBUS() {
        tacGiaDAO = new TacGiaDAO();
    }
        private boolean isValidName(TacGiaDTO tacGia) {
        return tacGia.getTenTG().matches("^[\\p{L}\\s'-]+$");
    }

    // Thêm tác giả mới
    public boolean addTacGia(TacGiaDTO tacGia) {
        // Kiểm tra dữ liệu đầu vào
        if (!isValidName(tacGia)) {
            throw new IllegalArgumentException("Tên tác giả không hợp lệ!");
        }
        if (tacGia.getMaTG() == null || tacGia.getMaTG().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tác giả không được để trống!");
        }
        if (tacGia.getTenTG() == null || tacGia.getTenTG().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống!");
        }
        if (tacGia.getSdt() != null && !tacGia.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }

        // Kiểm tra xem MATG đã tồn tại chưa
        TacGiaDTO existing = tacGiaDAO.getByMaTG(tacGia.getMaTG());
        if (existing != null) {
            throw new IllegalArgumentException("Mã tác giả đã tồn tại!");
        }

        // Gọi DAO để thêm
        return tacGiaDAO.create(tacGia);
    }

    // Lấy danh sách tác giả
    public List<TacGiaDTO> getAllTacGia() {
        return tacGiaDAO.getAll();
    }

    // Lấy tác giả theo MATG
    public TacGiaDTO getTacGiaByMaTG(String maTG) {
        if (maTG == null || maTG.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tác giả không được để trống!");
        }
        return tacGiaDAO.getByMaTG(maTG);
    }

    // Cập nhật tác giả
    public boolean updateTacGia(TacGiaDTO tacGia) {
        if (!isValidName(tacGia)) {
            throw new IllegalArgumentException("Tên tác giả không hợp lệ!");
        }
        if (tacGia.getMaTG() == null || tacGia.getMaTG().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tác giả không được để trống!");
        }
        if (tacGia.getTenTG() == null || tacGia.getTenTG().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống!");
        }
        if (tacGia.getSdt() != null && !tacGia.getSdt().matches("\\d{10}")) {
            throw new IllegalArgumentException("Số điện thoại phải có đúng 10 chữ số!");
        }
        return tacGiaDAO.update(tacGia);
    }

    // Xóa tác giả (xóa mềm)
    public boolean deleteTacGia(String maTG) {
        if (maTG == null || maTG.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tác giả không được để trống!");
        }
        return tacGiaDAO.delete(maTG);
    }

    // Tìm kiếm tác giả theo tên (gần đúng)
    public List<TacGiaDTO> searchByTenTG(String tenTG) {
        List<TacGiaDTO> result = new ArrayList<>();
        for (TacGiaDTO tg : tacGiaDAO.getAll()) {
            if (tg.getTenTG().toLowerCase().contains(tenTG.toLowerCase())) {
                result.add(tg);
            }
        }
        return result;
    }
    // kiểm tra tác giả có tồn tại 
    public boolean isTacGiaExists(String maTG){
        if (maTG == null || maTG.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tác giả không được để trống!");
        }
        return tacGiaDAO.isTacGiaExists(maTG);
    }
}