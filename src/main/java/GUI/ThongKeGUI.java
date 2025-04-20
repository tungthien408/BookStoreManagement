
package GUI;

import javax.swing.*;

import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.SachBUS;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.SachDTO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ThongKeGUI {
    Tool tool = new Tool();
    JPanel panel, panel_button, panel_Finding, panel_Table, panel_Finding_timKiem, panel_Finding_locTheoNgay,
            panel_Finding_buttons;
    JButton buttonDoanhThu, buttonKhachHang, buttonSanPham, buttonLamMoi, buttonLoc;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);
    JTable table_KhachHang, table_SanPham, table_DoanhThu;
    DefaultTableModel model_KhachHang, model_SanPham, model_DoanhThu;

    public ThongKeGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(Color.RED);

        // Button panel at top - small height
        panel_button = tool.createPanel(width - width_sideMenu, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        // panel_button.setBackground(Color.GREEN); mấy ông tự chỉnh màu phù hợp đi chứ
        // tui mù màu cmnr
        panel_button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and style buttons
        buttonDoanhThu = new JButton("DOANH THU");
        buttonKhachHang = new JButton("KHÁCH HÀNG");
        buttonSanPham = new JButton("SẢN PHẨM");
        buttonDoanhThu.setFont(new Font("Arial", Font.BOLD, 14));
        buttonKhachHang.setFont(new Font("Arial", Font.BOLD, 14));
        buttonSanPham.setFont(new Font("Arial", Font.BOLD, 14));
        buttonDoanhThu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonKhachHang.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonSanPham.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonDoanhThu.setFocusable(false);
        buttonKhachHang.setFocusable(false);
        buttonSanPham.setFocusable(false);
        buttonDoanhThu.setBorderPainted(false);
        buttonKhachHang.setBorderPainted(false);
        buttonSanPham.setBorderPainted(false);
        buttonDoanhThu.setBackground(new Color(0, 36, 107));
        buttonKhachHang.setBackground(new Color(0, 36, 107));
        buttonSanPham.setBackground(new Color(0, 36, 107));
        buttonDoanhThu.setForeground(Color.WHITE);
        buttonKhachHang.setForeground(Color.WHITE);
        buttonSanPham.setForeground(Color.WHITE);
        buttonDoanhThu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonDoanhThu.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                buttonDoanhThu.setBackground(MENU_BACKGROUND);
            }

            public void mouseReleased(MouseEvent e) {
                buttonDoanhThu.setBackground(MENU_BACKGROUND);
            }
        });
        buttonKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonKhachHang.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                buttonKhachHang.setBackground(MENU_BACKGROUND);
            }

            public void mouseReleased(MouseEvent e) {
                buttonKhachHang.setBackground(MENU_BACKGROUND);
            }
        });
        buttonSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonSanPham.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                buttonSanPham.setBackground(MENU_BACKGROUND);
            }

            public void mouseReleased(MouseEvent e) {
                buttonSanPham.setBackground(MENU_BACKGROUND);
            }
        });
        // Set size for all buttons
        Dimension buttonSize = new Dimension(150, 35);
        buttonDoanhThu.setPreferredSize(buttonSize);
        buttonKhachHang.setPreferredSize(buttonSize);
        buttonSanPham.setPreferredSize(buttonSize);
        // Add button to panel
        panel_button.add(buttonDoanhThu);
        panel_button.add(buttonKhachHang);
        panel_button.add(buttonSanPham);
        panel_button.setBorder(BorderFactory.createEmptyBorder(7, 3, 0, 0));
        // Finding panel in center - medium height
        panel_Finding = tool.createPanel(width - width_sideMenu, 70, new FlowLayout(FlowLayout.LEFT, 35, 0));
        panel_Finding.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 10));
        // Create các thành phần con bên trong panel_Finding
        panel_Finding_timKiem = tool.createPanel(300, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_Finding_locTheoNgay = tool.createPanel(300, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_Finding_buttons = tool.createPanel(300, 42, new FlowLayout(FlowLayout.CENTER, 29, 5));
        panel_Finding_buttons.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel_Finding_timKiem.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Tìm kiếm"));
        JTextField textFieldTimKiem = new JTextField(20);
        textFieldTimKiem.setPreferredSize(new Dimension(200, 25));
        panel_Finding_locTheoNgay.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Lọc theo ngày"));
        // panel_Finding_timKiem.setBackground(Color.BLACK);
        JLabel labelTu = new JLabel("Từ ngày: ");
        JLabel labelDen = new JLabel("Đến ngày: ");
        JTextField textFieldTu = new JTextField(4);
        JTextField textFieldDen = new JTextField(4);
        // panel_Finding_locTheoNgay.setBackground(Color.GRAY);
        panel_Finding_locTheoNgay.add(labelTu);
        panel_Finding_locTheoNgay.add(textFieldTu);
        panel_Finding_locTheoNgay.add(labelDen);
        panel_Finding_locTheoNgay.add(textFieldDen);
        // Buttons
        buttonLamMoi = new JButton("Làm mới");
        buttonLoc = new JButton("Lọc");

        // Set size for buttons - slightly smaller to ensure they fit side by side
        Dimension buttonSizeFilter = new Dimension(120, 30);
        buttonLamMoi.setPreferredSize(buttonSizeFilter);
        buttonLoc.setPreferredSize(buttonSizeFilter);

        // Add internal padding to buttons
        buttonLamMoi.setMargin(new Insets(5, 5, 5, 5));
        buttonLoc.setMargin(new Insets(5, 5, 5, 5));

        buttonLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonLoc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonLamMoi.setFocusable(false);
        buttonLoc.setFocusable(false);
        buttonLamMoi.setBorderPainted(false);
        buttonLoc.setBorderPainted(false);
        buttonLamMoi.setBackground(new Color(0, 36, 107));
        buttonLoc.setBackground(new Color(0, 36, 107));
        buttonLamMoi.setForeground(Color.WHITE);
        buttonLoc.setForeground(Color.WHITE);
        buttonLamMoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonLamMoi.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                buttonLamMoi.setBackground(MENU_BACKGROUND);
            }

            public void mouseReleased(MouseEvent e) {
                buttonLamMoi.setBackground(MENU_BACKGROUND);
            }
        });
        buttonLoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonLoc.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                buttonLoc.setBackground(MENU_BACKGROUND);
            }

            public void mouseReleased(MouseEvent e) {
                buttonLoc.setBackground(MENU_BACKGROUND);
            }
        });
        panel_Finding_buttons.add(buttonLamMoi);
        panel_Finding_buttons.add(buttonLoc);
        panel_Finding_buttons.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        // panel_Finding_buttons.setBackground(Color.LIGHT_GRAY);

        panel_Finding_timKiem.add(textFieldTimKiem);
        panel_Finding.add(panel_Finding_timKiem);
        panel_Finding.add(panel_Finding_locTheoNgay);
        panel_Finding.add(panel_Finding_buttons);

        // Table panel at bottom takes remaining space
        panel_Table = tool.createPanel(width - width_sideMenu, height - 150, new BorderLayout());
        // Create table with columns and data
        String[] columnNames_SanPham = { "STT", "Mã Sách", "Tên Sách", "Số lượng Nhập", "Số lượng Bán" };
        String[] columnNames_KhachHang = { "STT", "SĐT", "Tên Khách Hàng", "Điểm tích lũy" };
        String[] columnNames_DoanhThu = { "STT", "Mã Hóa Đơn", "Ngày", "Tổng Tiền" };

        // =====================================Lấy và thêm dữ liệu từ database vào
        // bảng=====================================
        // Sách
        SachBUS sachBUS = new SachBUS();
        List<SachDTO> sachList = sachBUS.getAllSach();

        String[][] tableData_SanPham = new String[sachList.size()][columnNames_SanPham.length];
        for (int i = 0; i < sachList.size(); i++) {
            tableData_SanPham[i][0] = String.valueOf(i + 1);
            tableData_SanPham[i][1] = sachList.get(i).getMaSach();
            tableData_SanPham[i][2] = sachList.get(i).getTenSach();
            tableData_SanPham[i][3] = String.valueOf(sachList.get(i).getSoLuong());
            tableData_SanPham[i][4] = String.valueOf(sachList.get(i).getDonGia());
        }
        // Khách hàng
        KhachHangBUS khachHangBUS = new KhachHangBUS();
        List<KhachHangDTO> khachHangList = khachHangBUS.getAllKhachHang();
        String[][] tableData_KhachHang = new String[khachHangList.size()][columnNames_KhachHang.length];
        for (int i = 0; i < khachHangList.size(); i++) {
            tableData_KhachHang[i][0] = String.valueOf(i + 1);
            tableData_KhachHang[i][1] = khachHangList.get(i).getSdt();
            tableData_KhachHang[i][2] = khachHangList.get(i).getHoTen();
            tableData_KhachHang[i][3] = String.valueOf(khachHangList.get(i).getDiem());
        }
        // Hóa đơn
        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        List<HoaDonDTO> hoaDonList = hoaDonBUS.getAllHoaDon();
        String[][] tableData_DoanhThu = new String[hoaDonList.size()][columnNames_DoanhThu.length];
        for (int i = 0; i < hoaDonList.size(); i++) {
            tableData_DoanhThu[i][0] = String.valueOf(i + 1);
            tableData_DoanhThu[i][1] = hoaDonList.get(i).getMaHD();
            tableData_DoanhThu[i][2] = hoaDonList.get(i).getNgayBan().toString();
            tableData_DoanhThu[i][3] = String.valueOf(hoaDonList.get(i).getTongTien());
        }
        // ================================End thêm và lấy dữ liệu từ database vào
        // bảng============================
        // String[][] tableData_SanPham = {
        // { "1", "S001", "Sách 1", "100", "50" },
        // { "2", "S002", "Sách 2", "150", "75" },
        // { "3", "S003", "Sách 3", "200", "100" },
        // { "4", "S004", "Sách 4", "250", "125" },
        // { "5", "S005", "Sách 5", "300", "150" },
        // { "6", "S006", "Sách 6", "350", "175" },
        // { "7", "S007", "Sách 7", "400", "200" },
        // { "8", "S008", "Sách 8", "450", "225" },
        // { "9", "S009", "Sách 9", "500", "250" },
        // { "10", "S010", "Sách 10", "550", "275" },
        // { "11", "S011", "Sách 11", "600", "300" },
        // { "12", "S012", "Sách 12", "650", "325" },
        // { "13", "S013", "Sách 13", "700", "350" },
        // { "14", "S014", "Sách 14", "750", "375" },
        // { "15", "S015", "Sách 15", "800", "400" },
        // { "16", "S016", "Sách 16", "850", "425" },
        // { "17", "S017", "Sách 17", "900", "450" },
        // { "18", "S018", "Sách 18", "950", "475" },
        // { "19", "S019", "Sách 19", "1000", "500" },
        // { "20", "S020", "Sách 20", "1050", "525" }
        // };
        // String[][] tableData_KhachHang = {
        // { "1", "0909090909", "Nguyễn Văn A", "100" },
        // { "2", "0909090909", "Nguyễn Văn B", "150" },
        // { "3", "0909090909", "Nguyễn Văn C", "200" },
        // { "4", "0909090909", "Nguyễn Văn D", "250" },
        // { "5", "0909090909", "Nguyễn Văn E", "300" }
        // };
        // String[][] tableData_DoanhThu = {
        // { "1", "HD001", "2024-01-01", "500000" },
        // { "2", "HD002", "2024-01-02", "750000" },
        // { "3", "HD003", "2024-01-03", "1000000" },
        // { "4", "HD004", "2024-01-04", "1250000" },
        // { "5", "HD005", "2024-01-05", "1500000" }
        // };
        table_KhachHang = tool.createTable(tableData_KhachHang, columnNames_KhachHang);
        table_SanPham = tool.createTable(tableData_SanPham, columnNames_SanPham);
        table_DoanhThu = tool.createTable(tableData_DoanhThu, columnNames_DoanhThu);
        // Set model for table
        model_KhachHang = (DefaultTableModel) table_KhachHang.getModel();
        model_SanPham = (DefaultTableModel) table_SanPham.getModel();
        model_DoanhThu = (DefaultTableModel) table_DoanhThu.getModel();
        // Tạo màu cho tiêu đề bảng
        table_KhachHang.getTableHeader().setBackground(MENU_BACKGROUND);
        table_SanPham.getTableHeader().setBackground(MENU_BACKGROUND);
        table_DoanhThu.getTableHeader().setBackground(MENU_BACKGROUND);
        table_KhachHang.getTableHeader().setForeground(Color.WHITE);
        table_SanPham.getTableHeader().setForeground(Color.WHITE);
        table_DoanhThu.getTableHeader().setForeground(Color.WHITE);
        // Add table to scroll pane
        JScrollPane scrollPane_KhachHang = new JScrollPane(table_KhachHang);
        JScrollPane scrollPane_SanPham = new JScrollPane(table_SanPham);
        JScrollPane scrollPane_DoanhThu = new JScrollPane(table_DoanhThu);
        scrollPane_KhachHang.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_SanPham.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_DoanhThu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_KhachHang.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210)); // Set size to
                                                                                                         // fit frame
        scrollPane_SanPham.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210)); // Set size to
                                                                                                       // fit frame
        scrollPane_DoanhThu.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210)); // Set size to
                                                                                                        // fit frame
        // Add scroll pane to panel with padding
        panel_Table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel_Table.add(scrollPane_DoanhThu, BorderLayout.NORTH); // Default show DoanhThu table

        panel.add(panel_button, BorderLayout.NORTH);
        panel.add(panel_Finding, BorderLayout.CENTER);
        panel.add(panel_Table, BorderLayout.SOUTH);
        // hide thanh kéo
        scrollPane_KhachHang.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane_KhachHang.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_SanPham.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane_SanPham.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_DoanhThu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane_DoanhThu.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        buttonDoanhThu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_Table.removeAll();
                panel_Table.add(scrollPane_DoanhThu, BorderLayout.NORTH);
                panel_Table.revalidate();
                panel_Table.repaint();
            }
        });
        buttonKhachHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_Table.removeAll();
                panel_Table.add(scrollPane_KhachHang, BorderLayout.NORTH);
                panel_Table.revalidate();
                panel_Table.repaint();
            }
        });
        buttonSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_Table.removeAll();
                panel_Table.add(scrollPane_SanPham, BorderLayout.NORTH);
                panel_Table.revalidate();
                panel_Table.repaint();
            }
        });
        buttonLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        buttonLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lọc từ các dữ kiện đã nhập trong panel_Finding_locTheoNgay,
                // panel_Finding_timKiem
                // và hiển thị các dữ kiện đã lọc trong panel_Table
                // Sử dụng câu lệnh SQL để lọc và hiển thị
                // Sử dụng phương thức setModel của JTable để hiển thị dữ liệu

            }
        });
    }

    // Refresh lại 1 phát 3 bảng
    public void refreshTable() {

        model_DoanhThu.setRowCount(0);
        model_KhachHang.setRowCount(0);
        model_SanPham.setRowCount(0);
        try {
            KhachHangBUS khachHangBUS = new KhachHangBUS();
            List<KhachHangDTO> khachHangList = khachHangBUS.getAllKhachHang();
            SachBUS sachBUS = new SachBUS();
            List<SachDTO> sachList = sachBUS.getAllSach();
            HoaDonBUS hoaDonBUS = new HoaDonBUS();
            List<HoaDonDTO> hoaDonList = hoaDonBUS.getAllHoaDon();

            for (KhachHangDTO khachHang : khachHangList) {
                model_KhachHang.addRow(new Object[] {
                        khachHangList.indexOf(khachHang) + 1,
                        khachHang.getSdt(),
                        khachHang.getHoTen(),
                        khachHang.getDiem()
                });
            }
            for (SachDTO sach : sachList) {
                model_SanPham.addRow(new Object[] {
                        sachList.indexOf(sach) + 1,
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getDonGia()
                });
            }
            for (HoaDonDTO hoaDon : hoaDonList) {
                model_DoanhThu.addRow(new Object[] {
                        hoaDonList.indexOf(hoaDon) + 1,
                        hoaDon.getMaHD(),
                        hoaDon.getNgayBan().toString(),
                        hoaDon.getTongTien()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
