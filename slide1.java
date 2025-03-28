import javax.swing.*;
import java.awt.*;

public class slide1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1980, 822);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Slide 1");
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel navBar = new JPanel();
        JPanel content = new JPanel();
        JPanel containerProductInfo = new JPanel();
        JPanel containerProductQuantityandName = new JPanel();
        navBar.setPreferredSize(new Dimension(300, 200));
        navBar.setLayout(new GridLayout(12, 1));
        navBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        content.setPreferredSize(new Dimension(1240, 200));
        content.setBackground(Color.blue);
        content.setLayout(new BorderLayout());
        content.add(containerProductInfo, BorderLayout.NORTH);
        content.add(containerProductQuantityandName, BorderLayout.NORTH);
        containerProductInfo.setBackground(Color.red);
        containerProductInfo.setPreferredSize(new Dimension(1240, 411));
        
        containerProductQuantityandName.setPreferredSize(new Dimension(1240, 411));
        containerProductQuantityandName.setBackground(Color.red);
        
        containerProductInfo.setLayout(null);
        containerProductQuantityandName.setLayout(null);
        frame.add(navBar, BorderLayout.WEST);
        frame.add(content, BorderLayout.EAST);

        JButton btnBanSach = new JButton("Bán sách");
        JButton btnNhapSach = new JButton("Nhập sách");
        JButton btnSach = new JButton("Sách");
        JButton btnNhaXuatBan = new JButton("Nhà xuất bản");
        JButton btnTacGia = new JButton("Tác giả");
        JButton btnHoaDonNhap = new JButton("Hóa đơn nhập");
        JButton btnHoaDonBan = new JButton("Hóa đơn bán");
        JButton btnKhuyenMai = new JButton("Khuyến mãi");
        JButton btnKhachHang = new JButton("Khách hàng");
        JButton btnNhanVien = new JButton("Nhân viên");
        JButton btnTaiKhoan = new JButton("Tài khoản");
        JButton btnPhanQuyen = new JButton("Phân quyền");

        JButton btnBanNhapSach = new JButton("Bán/nhập sách");
        navBar.add(btnBanSach);
        navBar.add(btnNhapSach);
        navBar.add(btnSach);
        navBar.add(btnNhaXuatBan);
        navBar.add(btnTacGia);
        navBar.add(btnHoaDonNhap);
        navBar.add(btnHoaDonBan);
        navBar.add(btnKhuyenMai);
        navBar.add(btnKhachHang);
        navBar.add(btnNhanVien);
        navBar.add(btnTaiKhoan);
        navBar.add(btnPhanQuyen);


        frame.setVisible(true);
    }
}
