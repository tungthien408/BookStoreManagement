package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.MaGiamGiaDTO;
import Service.Data;

public class MaGiamGiaDAO {

    public ArrayList<MaGiamGiaDTO> getAll() {
        ArrayList<MaGiamGiaDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM MaGiamGia WHERE trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaGiamGiaDTO dto = new MaGiamGiaDTO(
                    rs.getString("maGiamGia"),
                    rs.getString("tenGiamGia"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayBatDau"),
                    rs.getDate("ngayKetThuc"),
                    rs.getInt("trangThaiXoa"),
                    rs.getInt("loaiGiamGia"),
                    rs.getInt("soTienGiam")
                );
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int add(MaGiamGiaDTO dto) {
        String sql = "INSERT INTO MaGiamGia (maGiamGia, tenGiamGia, phanTramGiam, ngayBatDau, ngayKetThuc, trangThaiXoa, loaiGiamGia, soTienGiam) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getMaGiamGia());
            ps.setString(2, dto.getTenGiamGia());
            ps.setInt(3, dto.getPhanTramGiam());
            ps.setDate(4, dto.getNgayBatDau());
            ps.setDate(5, dto.getNgayKetThuc());
            ps.setInt(6, dto.getTrangThaiXoa());
            ps.setInt(7, dto.getLoaiGiamGia());
            ps.setInt(8, dto.getSoTienGiam());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(MaGiamGiaDTO dto) {
        String sql = "UPDATE MaGiamGia SET tenGiamGia = ?, phanTramGiam = ?, ngayBatDau = ?, ngayKetThuc = ?, loaiGiamGia = ?, soTienGiam = ? WHERE maGiamGia = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getTenGiamGia());
            ps.setInt(2, dto.getPhanTramGiam());
            ps.setDate(3, dto.getNgayBatDau());
            ps.setDate(4, dto.getNgayKetThuc());
            ps.setInt(5, dto.getLoaiGiamGia());
            ps.setInt(6, dto.getSoTienGiam());
            ps.setString(7, dto.getMaGiamGia());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(String maGiamGia) {
        String sql = "UPDATE MaGiamGia SET trangThaiXoa = 1 WHERE maGiamGia = ?";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGiamGia);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public MaGiamGiaDTO getByMaGiamGia(String maGiamGia) {
        String sql = "SELECT * FROM MaGiamGia WHERE maGiamGia = ? AND trangThaiXoa = 0";
        try (Connection conn = Data.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGiamGia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new MaGiamGiaDTO(
                    rs.getString("maGiamGia"),
                    rs.getString("tenGiamGia"),
                    rs.getInt("phanTramGiam"),
                    rs.getDate("ngayBatDau"),
                    rs.getDate("ngayKetThuc"),
                    rs.getInt("trangThaiXoa"),
                    rs.getInt("loaiGiamGia"),
                    rs.getInt("soTienGiam")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}