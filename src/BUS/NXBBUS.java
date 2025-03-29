package BUS;

import java.sql.SQLException;

import java.util.List;

import DAO.NXBDAO;

import DTO.NXBDTO;

public class NXBBUS {
        NXBDAO NXBDAO = new NXBDAO();

    public NXBDTO getNXBByID(String nvma) throws SQLException {
        return NXBDAO.getNXBByID(nvma);
    }

    public NXBDTO getNXBBySDT(String nvma) throws SQLException {
        return NXBDAO.getNXBBySDT(nvma);
    }
   
    public boolean addNXB(NXBDTO nv) throws SQLException {
        return NXBDAO.addNXB(nv);
    }
    public boolean updateNXB(NXBDTO nv) throws SQLException {
        return NXBDAO.updateNXB(nv); // Gọi DAO để cập nhật
    }
    public boolean deleteNXB(String nvma) throws SQLException {
        return NXBDAO.deleteNXB(nvma);
    }
    public List<NXBDTO> getAllNXB() throws SQLException {
        return NXBDAO.getAllNXB();
    }
    public boolean isSDTvsIDExists(String nvsdt,String maNXB) throws SQLException {
        return NXBDAO.isSDTvsIDExists(nvsdt,maNXB);
    }
    
}
