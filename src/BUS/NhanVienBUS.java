package BUS;

import java.sql.SQLException;
import java.util.List;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public NhanVienDTO getNhanVienBySDT(String sdt) throws SQLException {
        return nhanVienDAO.getNhanVienBySDT(sdt);
    }

    public boolean addNhanVien(NhanVienDTO nv) throws SQLException {
        return nhanVienDAO.addNhanVien(nv);
    }

    public boolean updateNhanVien(NhanVienDTO nv) throws SQLException {
        return nhanVienDAO.updateNhanVien(nv);
    }

    public boolean deleteNhanVien(String maNV) throws SQLException {
        return nhanVienDAO.deleteNhanVien(maNV);
    }

    public List<NhanVienDTO> getAllNhanVien() throws SQLException {
        return nhanVienDAO.getAllNhanVien();
    }

    public boolean isSDTExists(String sdt) throws SQLException {
        return nhanVienDAO.isSDTExists(sdt);
    }
}
