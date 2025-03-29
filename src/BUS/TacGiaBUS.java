package BUS;

import java.sql.SQLException;
import java.util.List;
import DAO.TacGiaDAO;

import DTO.TacGiaDTO;

public class TacGiaBUS {
    TacGiaDAO TacGiaDAO = new TacGiaDAO();

    public TacGiaDTO getTacGiaByID(String nvma) throws SQLException {
        return TacGiaDAO.getTacGiaByID(nvma);
    }

    public TacGiaDTO getTacGiaBySDT(String sdtma) throws SQLException {
        return TacGiaDAO.getTacGiaBySDT(sdtma);
    }

    public boolean addTacGia(TacGiaDTO nv) throws SQLException {
        return TacGiaDAO.addTacGia(nv);
    }

    public boolean updateTacGia(TacGiaDTO nv) throws SQLException {
        return TacGiaDAO.updateTacGia(nv); // Gọi DAO để cập nhật
    }
    
    public boolean deleteTacGia(String nvma) throws SQLException {
        return TacGiaDAO.deleteTacGia(nvma);
    }

    public List<TacGiaDTO> getAllTacGia() throws SQLException {
        return TacGiaDAO.getAllTacGia();
    }

    public boolean isSDTvsIDExists(String nvsdt,String maTG) throws SQLException {
        return TacGiaDAO.isSDTvsIDExists(nvsdt,maTG);
    }

}
