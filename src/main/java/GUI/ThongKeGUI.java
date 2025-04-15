package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        String[] columnNames = { "STT", "Mã Sách", "Tên Sách", "Số lượng Nhập", "Số lượng Bán" };
        String[][] tableData = {
                { "1", "S001", "Sách 1", "100", "50" },
                { "2", "S002", "Sách 2", "150", "75" },
                { "3", "S003", "Sách 3", "200", "100" },
                { "4", "S004", "Sách 4", "250", "125" },
                { "5", "S005", "Sách 5", "300", "150" },
                { "6", "S006", "Sách 6", "350", "175" },
                { "7", "S007", "Sách 7", "400", "200" },
                { "8", "S008", "Sách 8", "450", "225" },
                { "9", "S009", "Sách 9", "500", "250" },
                { "10", "S010", "Sách 10", "550", "275" },
                { "11", "S011", "Sách 11", "600", "300" },
                { "12", "S012", "Sách 12", "650", "325" },
                { "13", "S013", "Sách 13", "700", "350" },
                { "14", "S014", "Sách 14", "750", "375" },
                { "15", "S015", "Sách 15", "800", "400" },
                { "16", "S016", "Sách 16", "850", "425" },
                { "17", "S017", "Sách 17", "900", "450" },
                { "18", "S018", "Sách 18", "950", "475" },
                { "19", "S019", "Sách 19", "1000", "500" },
                { "20", "S020", "Sách 20", "1050", "525" }
        };
        JTable table = tool.createTable(tableData, columnNames);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210)); // Set size to fit frame

        // Add scroll pane to panel with padding
        panel_Table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel_Table.add(scrollPane, BorderLayout.NORTH);

        panel.add(panel_button, BorderLayout.NORTH);
        panel.add(panel_Finding, BorderLayout.CENTER);
        panel.add(panel_Table, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
