package BUS;

import java.util.ArrayList;

import DAO.QuyenDAO;
import DTO.QuyenDTO;

public class QuyenBUS {
    private QuyenDAO quyenDAO = new QuyenDAO();

    public ArrayList<QuyenDTO> getAllQuyen() {
        return quyenDAO.getAll();
    }

    public boolean updateQuyen(QuyenDTO quyen, String originalMaQuyen) { // Added originalMaQuyen parameter
        return quyenDAO.update(quyen, originalMaQuyen) > 0;
    }

    public boolean addQuyen(QuyenDTO quyen) {
        return quyenDAO.add(quyen) > 0;
    }

    public boolean deleteQuyen(String maQuyen) {
        return quyenDAO.delete(maQuyen) > 0;
    }

    public QuyenDTO getQuyenById(String maQuyen) {
        return quyenDAO.getById(maQuyen);
    }
}
