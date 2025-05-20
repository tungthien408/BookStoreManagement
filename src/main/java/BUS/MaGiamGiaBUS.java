package BUS;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import DAO.MaGiamGiaDAO;
import DTO.MaGiamGiaDTO;

public class MaGiamGiaBUS {
    private MaGiamGiaDAO dao = new MaGiamGiaDAO();

    public ArrayList<MaGiamGiaDTO> getAllMaGiamGia() {
        return dao.getAll();
    }

    public boolean addMaGiamGia(MaGiamGiaDTO dto) {
        if (!validate(dto)) return false;
        return dao.add(dto) > 0;
    }

    public boolean updateMaGiamGia(MaGiamGiaDTO dto) {
        if (!validate(dto)) return false;
        return dao.update(dto) > 0;
    }

    public boolean deleteMaGiamGia(String maGiamGia) {
        return dao.delete(maGiamGia) > 0;
    }

    private boolean validate(MaGiamGiaDTO dto) {
        if (dto.getMaGiamGia() == null || dto.getMaGiamGia().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã giảm giá không được để trống");
            return false;
        }
        if (dto.getLoaiGiamGia() == 1) {
            if (dto.getPhanTramGiam() < 1 || dto.getPhanTramGiam() > 100) {
                JOptionPane.showMessageDialog(null, "Phần trăm giảm phải từ 1 đến 100");
                return false;
            }
        } else if (dto.getLoaiGiamGia() == 0) {
            if (dto.getSoTienGiam() <= 0) {
                JOptionPane.showMessageDialog(null, "Số tiền giảm phải lớn hơn 0");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Loại giảm giá không hợp lệ (1: phần trăm, 0: số tiền)");
            return false;
        }
        if (dto.getNgayBatDau().after(dto.getNgayKetThuc())) {
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được sau ngày kết thúc");
            return false;
        }
        return true;
    }

    public MaGiamGiaDTO getMaGiamGiaById(String maGiamGia) {
        MaGiamGiaDTO dto = dao.getByMaGiamGia(maGiamGia);
        if (dto == null) {
         
            return null;
        }
      
        Date today = new Date(System.currentTimeMillis());
        if (dto.getNgayBatDau().after(today) || dto.getNgayKetThuc().before(today)) {
            return null;
        }
        return dto;
    }
}