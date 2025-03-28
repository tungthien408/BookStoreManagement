package DTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class QuanLyKhachHang extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1980, 820);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Quản lý khách hàng");
        frame.setLayout(new FlowLayout());
        frame.setLocation(-5, 0);
        // Panels
        // Tìm kiếm khách hàng
        JPanel panelTimKhachHang = new JPanel();
        panelTimKhachHang.setLayout(null);
        panelTimKhachHang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "<html><font color='red'>Tìm kiếm khách hàng</font></html>", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 20)));
        JLabel nhapTen_maKH = new JLabel("Nhập tên hoặc mã khách hàng");
        nhapTen_maKH.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField nhapTen_maKH_Field = new JTextField(50);
        nhapTen_maKH_Field.setBounds(25, 120, 580, 40);
        JButton btnTimKiem = new JButton("Tìm Kiếm");
        JButton btnTatCa = new JButton("Tất cả");
        btnTimKiem.setBounds(25,200,150,35);
        btnTatCa.setBounds(454,200,150,35);
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setFocusable(false);
        btnTatCa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTatCa.setFocusable(false);
        nhapTen_maKH.setBounds(25, 40, 300, 100);
        panelTimKhachHang.add(nhapTen_maKH);
        panelTimKhachHang.add(nhapTen_maKH_Field);
        panelTimKhachHang.add(btnTimKiem);
        panelTimKhachHang.add(btnTatCa);
        // Thông tin khách hàng
        JPanel panelThongTinKhachHang = new JPanel();
        panelThongTinKhachHang.setLayout(null);
        panelTimKhachHang.setPreferredSize(new Dimension(630, 318));
        panelThongTinKhachHang.setPreferredSize(new Dimension(890, 318));
        panelThongTinKhachHang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"<html><font color = 'red'>Thông tin khách hàng</font></html>", TitledBorder.LEFT, TitledBorder.TOP,new Font("Arial", Font.BOLD, 20)));
        //Mã khách hàng
        JLabel addMaKH = new JLabel("Mã khách hàng");
        JTextField addMaKH_Field = new JTextField(3);
        addMaKH.setBounds(25,70,125,20);
        addMaKH_Field.setBounds(160,67,260,30);
        addMaKH.setFont(new Font("Arial",Font.BOLD,15));
        //Số điện thoại
        JLabel addSoDienThoai = new JLabel("Số điện thoại");
        JTextField addSoDienThoai_Field = new JTextField(3);
        addSoDienThoai.setFont(new Font("Arial",Font.BOLD,15));
        addSoDienThoai.setBounds(490,70,125,20);
        addSoDienThoai_Field.setBounds(610,67,260,30);
        //Tên khách hàng
        JLabel addTenKhachHang = new JLabel("Tên khách hàng");
        JTextField addTenKhachHang_Field = new JTextField(3);
        addTenKhachHang.setFont(new Font("Arial",Font.BOLD,15));
        addTenKhachHang.setBounds(25,150,125,20);
        addTenKhachHang_Field.setBounds(160,147,260,30);

        //Điểm tích lũy
        JLabel addDiemTichLuy = new JLabel("Điểm tích lũy");
        JTextField addDiemTichLuy_Field = new JTextField(3);
        addDiemTichLuy.setFont(new Font("Arial",Font.BOLD,15));
        addDiemTichLuy.setBounds(490,150,260,20);
        addDiemTichLuy_Field.setBounds(610,147,260,30);

        JButton add = new JButton("Thêm");
        add.setBounds(25,220,130,40);
        add.setFocusable(false);
        add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton update = new JButton("Sửa");
        update.setBounds(165,220,130,40);
        update.setCursor(new Cursor(Cursor.HAND_CURSOR));
        update.setFocusable(false);
        JButton retype = new JButton("Nhập lại");
        retype.setBounds(305,220,130,40);
        retype.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retype.setFocusable(false);
        JButton delete = new JButton("Xóa");
        delete.setBounds(445,220,130,40);
        delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        delete.setFocusable(false);
        panelThongTinKhachHang.add(addMaKH);
        panelThongTinKhachHang.add(addMaKH_Field);
        panelThongTinKhachHang.add(addSoDienThoai);
        panelThongTinKhachHang.add(addSoDienThoai_Field);
        panelThongTinKhachHang.add(addTenKhachHang);
        panelThongTinKhachHang.add(addTenKhachHang_Field);
        panelThongTinKhachHang.add(addDiemTichLuy);
        panelThongTinKhachHang.add(addDiemTichLuy_Field);
        panelThongTinKhachHang.add(add);
        panelThongTinKhachHang.add(update);
        panelThongTinKhachHang.add(retype);
        panelThongTinKhachHang.add(delete);
        // Bảng thống kê
        JPanel panelBangKhachHang = new JPanel();
        panelBangKhachHang.setPreferredSize(new Dimension(1525, 500));
        String[] columnNames = { "<html><font size=5>Mã khách hàng</font></html>",
                "<html><font size=5>Tên khách hàng</font></html>", "<html><font size=5>Số Điện Thoại</font></html>",
                "<html><font size=5>Điểm tích lũy</font></html>" };
        Object[][] datas = {
                { "kh01", "Lâm Quốc Đạt", "0933754987", 19.135 },
                { "kh02", "Lưu Thượng Vỹ", "0123456789", 7.485 },
                { "kh03", "Nguyễn Phước An Vũ", "9876543210", 15.000 },
                { "kh04", "Nguyễn Văn A", "0707237806", 3.200 },
                { "kh05", "Nguyễn Thị B", "0362434969", 5.600 },
                { "kh06", "Nguyễn Thị A", "0362434969", 11.500 },
                { "kh07", "Lê Phi Vũ", "0327209786", 100 }
        };
        DefaultTableModel model = new DefaultTableModel(datas, columnNames);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(1525, 500));
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setSelectionBackground(Color.YELLOW);
        table.setSelectionForeground(Color.RED);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        panelBangKhachHang.add(scrollPane);

        frame.add(panelTimKhachHang);
        frame.add(panelThongTinKhachHang);
        frame.add(panelBangKhachHang);
        frame.setVisible(true);

    }
}