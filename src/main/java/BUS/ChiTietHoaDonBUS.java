package BUS;

import java.util.List;

import DAO.ChiTietHoaDonDAO;
import DTO.ChiTietHoaDonDTO;
import DTO.SachDTO;

public class ChiTietHoaDonBUS {
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
    private SachBUS sachBUS; // Để kiểm tra MASACH
    private HoaDonBUS hoaDonBUS; // Để kiểm tra MAHD

    public ChiTietHoaDonBUS() {
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
        sachBUS = new SachBUS();
        hoaDonBUS = new HoaDonBUS();
    }

    // Thêm chi tiết hóa đơn mới
    public boolean addChiTietHoaDon(ChiTietHoaDonDTO chiTiet) {
        // Kiểm tra dữ liệu đầu vào
        if (chiTiet.getMaSach() == null || chiTiet.getMaSach().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        if (chiTiet.getMaHD() == null || chiTiet.getMaHD().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        if (chiTiet.getSoLuong() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
        }
        if (chiTiet.getGia() <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0!");
        }

        // Kiểm tra khóa ngoại MASACH
        if (sachBUS.getSachByMaSach(chiTiet.getMaSach()) == null) {
            throw new IllegalArgumentException("Mã sách không tồn tại!");
        }

        // Kiểm tra khóa ngoại MAHD
        if (hoaDonBUS.getHoaDonByMaHD(chiTiet.getMaHD()) == null) {
            throw new IllegalArgumentException("Mã hóa đơn không tồn tại!");
        }

        // Kiểm tra xem cặp MAHD-MASACH đã tồn tại chưa
        ChiTietHoaDonDTO existing = chiTietHoaDonDAO.getByMaHDAndMaSach(chiTiet.getMaHD(), chiTiet.getMaSach());
        if (existing != null) {
            throw new IllegalArgumentException("Chi tiết hóa đơn với mã sách và mã hóa đơn này đã tồn tại!");
        }

        // Kiểm tra số lượng sách còn lại
        SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
        if (sach.getSoLuong() < chiTiet.getSoLuong()) {
            throw new IllegalArgumentException("Số lượng sách không đủ! Còn lại: " + sach.getSoLuong());
        }

        // Cập nhật số lượng sách
        sach.setSoLuong(sach.getSoLuong() - chiTiet.getSoLuong());
        sachBUS.updateSach(sach);

        // Gọi DAO để thêm chi tiết hóa đơn
        return chiTietHoaDonDAO.create(chiTiet);
    }

    // Lấy danh sách chi tiết hóa đơn
    public List<ChiTietHoaDonDTO> getAllChiTietHoaDon() {
        return chiTietHoaDonDAO.getAll();
    }

    // Lấy chi tiết hóa đơn theo MAHD và MASACH
    public ChiTietHoaDonDTO getChiTietHoaDon(String maHD, String maSach) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        if (maSach == null || maSach.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        return chiTietHoaDonDAO.getByMaHDAndMaSach(maHD, maSach);
    }

    // Lấy danh sách chi tiết hóa đơn theo MAHD
    public List<ChiTietHoaDonDTO> getChiTietByMaHD(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        return chiTietHoaDonDAO.getByMaHD(maHD);
    }

    // Cập nhật chi tiết hóa đơn
    public boolean updateChiTietHoaDon(ChiTietHoaDonDTO chiTiet) {
        if (chiTiet.getMaSach() == null || chiTiet.getMaSach().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }
        if (chiTiet.getMaHD() == null || chiTiet.getMaHD().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        if (chiTiet.getSoLuong() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0!");
        }
        if (chiTiet.getGia() <= 0) {
            throw new IllegalArgumentException("Giá phải lớn hơn 0!");
        }

        // Lấy chi tiết hiện tại để tính toán sự thay đổi số lượng
        ChiTietHoaDonDTO existing = chiTietHoaDonDAO.getByMaHDAndMaSach(chiTiet.getMaHD(), chiTiet.getMaSach());
        if (existing == null) {
            throw new IllegalArgumentException("Chi tiết hóa đơn không tồn tại!");
        }

        // Cập nhật số lượng sách
        SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
        int soLuongCu = existing.getSoLuong();
        int soLuongMoi = chiTiet.getSoLuong();
        int soLuongThayDoi = soLuongMoi - soLuongCu;

        if (soLuongThayDoi > 0) {
            // Nếu tăng số lượng, kiểm tra xem còn đủ sách không
            if (sach.getSoLuong() < soLuongThayDoi) {
                throw new IllegalArgumentException("Số lượng sách không đủ! Còn lại: " + sach.getSoLuong());
            }
            sach.setSoLuong(sach.getSoLuong() - soLuongThayDoi);
        } else {
            // Nếu giảm số lượng, trả lại số lượng cho sách
            sach.setSoLuong(sach.getSoLuong() + (-soLuongThayDoi));
        }
        sachBUS.updateSach(sach);

        return chiTietHoaDonDAO.update(chiTiet);
    }

    // Xóa chi tiết hóa đơn
    public boolean deleteChiTietHoaDon(String maHD, String maSach) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }
        if (maSach == null || maSach.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống!");
        }

        // Lấy chi tiết hóa đơn để hoàn lại số lượng sách
        ChiTietHoaDonDTO chiTiet = chiTietHoaDonDAO.getByMaHDAndMaSach(maHD, maSach);
        if (chiTiet == null) {
            throw new IllegalArgumentException("Chi tiết hóa đơn không tồn tại!");
        }

        // Hoàn lại số lượng sách
        SachDTO sach = sachBUS.getSachByMaSach(maSach);
        sach.setSoLuong(sach.getSoLuong() + chiTiet.getSoLuong());
        sachBUS.updateSach(sach);

        return chiTietHoaDonDAO.delete(maHD, maSach);
    }

    // Xóa tất cả chi tiết hóa đơn theo MAHD
    public boolean deleteByMaHD(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được để trống!");
        }

        // Lấy danh sách chi tiết hóa đơn để hoàn lại số lượng sách
        List<ChiTietHoaDonDTO> list = chiTietHoaDonDAO.getByMaHD(maHD);
        for (ChiTietHoaDonDTO chiTiet : list) {
            SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
            sach.setSoLuong(sach.getSoLuong() + chiTiet.getSoLuong());
            sachBUS.updateSach(sach);
        }

        return chiTietHoaDonDAO.deleteByMaHD(maHD);
    }

    public int getSoLuongByMaSach(String maSach){
        return chiTietHoaDonDAO.getSoLuongByMaSach(maSach);
    }
}