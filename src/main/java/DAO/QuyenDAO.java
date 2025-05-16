package DAO;

<<<<<<< HEAD
=======
import DTO.QuyenDTO;
import Service.Data;

>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.ArrayList;

import DTO.QuyenDTO;
import Service.Data;

public class QuyenDAO {

    public int add(QuyenDTO obj) {
        String sql = "INSERT INTO quyen (maQuyen, tenQuyen) VALUES (?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaQuyen());
            ps.setString(2, obj.getTenQuyen());
            return ps.executeUpdate();
        } catch (SQLException e) {
=======
import java.sql.Statement;
import java.util.ArrayList;


public class QuyenDAO {
    public int add(QuyenDTO obj){
        String sql = "INSERT INTO quyen (maQuyen , tenQuyen) VALUES (?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, obj.getMaQuyen());
            ps.setString(2, obj.getTenQuyen());
            return ps.executeUpdate();
        }catch(SQLException e){
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
            e.printStackTrace();
        }
        return 0;
    }
<<<<<<< HEAD

    public int update(QuyenDTO obj, String originalMaQuyen) { // Added originalMaQuyen parameter
        String sql = "UPDATE quyen SET maQuyen = ?, tenQuyen = ? WHERE maQuyen = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaQuyen()); // New maQuyen
            ps.setString(2, obj.getTenQuyen()); // New tenQuyen
            ps.setString(3, originalMaQuyen);   // Original maQuyen for WHERE clause
            return ps.executeUpdate();
        } catch (SQLException e) {
=======
    
    public int update(QuyenDTO obj){
        String sql = "UPDATE quyen SET tenQuyen=? WHERE maQuyen=?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, obj.getTenQuyen());
            ps.setInt(2, obj.getMaQuyen());
            return ps.executeUpdate();
        }catch(SQLException e){
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
            e.printStackTrace();
        }
        return 0;
    }
<<<<<<< HEAD

    public int delete(String maQuyen) {
        String sql = "UPDATE quyen SET trangThaiXoa = 1 WHERE maQuyen = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maQuyen);
            return ps.executeUpdate();
        } catch (SQLException e) {
=======
    
    public int delete(int maQuyen){
        String sql ="UPDATE quyen SET trangThaiXoa=1 WHERE maQuyen=?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, maQuyen);
            return ps.executeUpdate();
        }catch(SQLException e){
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
            e.printStackTrace();
        }
        return 0;
    }
<<<<<<< HEAD

    public ArrayList<QuyenDTO> getAll() {
        ArrayList<QuyenDTO> dsQuyen = new ArrayList<>();
        String sql = "SELECT * FROM quyen WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dsQuyen.add(new QuyenDTO(
                    rs.getString("maQuyen"),
                    rs.getString("tenQuyen"),
                    rs.getInt("trangThaiXoa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsQuyen;
    }

    public QuyenDTO getById(String maQuyen) {
        String sql = "SELECT * FROM quyen WHERE maQuyen = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maQuyen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new QuyenDTO(
                        rs.getString("maQuyen"),
                        rs.getString("tenQuyen"),
                        rs.getInt("trangThaiXoa")
                    );
                }
            }
=======
    
    public ArrayList<QuyenDTO> getAll(){
        ArrayList<QuyenDTO> dsquyen = new ArrayList<>();
        String sql = "SELECT * FROM quyen WHERE trangThaiXoa=0";
        try (Connection conn = Data.getConnection();
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery(sql)){
            while(rs.next()){
                dsquyen.add( new QuyenDTO(
                    rs.getInt("maQuyen"),
                    rs.getString("tenQuyen")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return dsquyen;
    }
    
    public QuyenDTO getById(int id){
        String sql ="SELECT * FROM quyen WHERE maQuyen=? AND trangThaiXoa=0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new QuyenDTO(
                        rs.getInt("maQuyen"),
                        rs.getString("tenQuyen")
                    );
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String getTenQuyenByMaQuyen(int maQuyen) {
        String sql = "SELECT tenQuyen FROM quyen WHERE maQuyen=? AND trangThaiXoa=0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maQuyen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tenQuyen");
                }
            }
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
<<<<<<< HEAD
}
=======
    
    public int getMaQuyenByTenQuyen(String tenQuyen) {
        String sql = "SELECT maQuyen FROM quyen WHERE tenQuyen=? AND trangThaiXoa=0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenQuyen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("maQuyen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }


    
}
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
