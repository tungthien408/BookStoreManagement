package BUS;

<<<<<<< HEAD
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
=======
import DAO.QuyenDAO;
import DTO.QuyenDTO;
import java.util.ArrayList;

public class QuyenBUS {
    private QuyenDAO quyenDAO;
    
    public QuyenBUS(){
        quyenDAO = new QuyenDAO();
    }
    
    public ArrayList<QuyenDTO> getAllQuyen(){
        return quyenDAO.getAll();
    }
    
    public boolean addQuyen(QuyenDTO quyen){
        return quyenDAO.add(quyen) > 0;
    }
    
    public boolean updateQuyen(QuyenDTO quyen){
        return quyenDAO.update(quyen) > 0;
    }
    
    public boolean deleteQuyen(int maQuyen){
        return quyenDAO.delete(maQuyen) > 0;
    }
        
    public ArrayList<QuyenDTO> searchQuyen(String keyword){
        if(keyword == null || keyword.trim().isEmpty()) return quyenDAO.getAll(); 
        
        ArrayList<QuyenDTO> ketQua = new ArrayList<>();
        keyword = keyword.toLowerCase();
        ArrayList<QuyenDTO> danhSach = quyenDAO.getAll();
        if (danhSach != null){
            for(QuyenDTO quyen: danhSach){
                if(quyen.getTenQuyen().toLowerCase().contains(keyword)){
                    ketQua.add(quyen);
                }
            }
        }
        return ketQua;
    }
    
    public String getTenQuyenByMaQuyen(int maQuyen){
        return quyenDAO.getTenQuyenByMaQuyen(maQuyen);
    }
    
    public int getMaQuyenByTenQuyen(String tenQuyen) {
        return quyenDAO.getMaQuyenByTenQuyen(tenQuyen);
    }
}
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
