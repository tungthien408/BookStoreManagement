package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.SachBUS;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.SachDTO;

public class ThongKeGUI {
    private Tool tool = new Tool();
    private JPanel panel, panel_button, panel_Finding, panel_Table, panel_Finding_timKiem, panel_Finding_locTheoNgay,
            panel_Finding_buttons;
    private JButton buttonDoanhThu, buttonKhachHang, buttonSanPham, buttonLamMoi, buttonLoc, buttonTimKiem;
    private JTextField textFieldTimKiem, textFieldTu, textFieldDen;
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int) (width * 0.625);
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);
    private JTable table_KhachHang, table_SanPham, table_DoanhThu;
    private DefaultTableModel model_KhachHang, model_SanPham, model_DoanhThu;
    private SachBUS sachBUS = new SachBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();

    public ThongKeGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        // Button panel at top
        panel_button = tool.createPanel(width - width_sideMenu, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_button.setBorder(BorderFactory.createEmptyBorder(7, 3, 0, 0));

        // Create and style buttons
        buttonDoanhThu = createStyledButton("DOANH THU");
        buttonKhachHang = createStyledButton("KHÁCH HÀNG");
        buttonSanPham = createStyledButton("SẢN PHẨM");
        panel_button.add(buttonDoanhThu);
        panel_button.add(buttonKhachHang);
        panel_button.add(buttonSanPham);

        // Finding panel in center
        panel_Finding = tool.createPanel(width - width_sideMenu, 70, new FlowLayout(FlowLayout.LEFT, 35, 0));
        panel_Finding.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 10));

        // Search panel
        panel_Finding_timKiem = tool.createPanel(300, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_Finding_timKiem.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Tìm kiếm"));
        textFieldTimKiem = new JTextField(20);
        textFieldTimKiem.setPreferredSize(new Dimension(200, 25));
        buttonTimKiem = createStyledButton("Tìm", 65, 25);
        panel_Finding_timKiem.add(textFieldTimKiem);
        panel_Finding_timKiem.add(buttonTimKiem);

        // Date filter panel
        panel_Finding_locTheoNgay = tool.createPanel(325, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_Finding_locTheoNgay.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Lọc theo ngày"));
        JLabel labelTu = new JLabel("Từ ngày: ");
        JLabel labelDen = new JLabel("Đến ngày: ");
        textFieldTu = new JTextField(7);
        textFieldDen = new JTextField(7);
        panel_Finding_locTheoNgay.add(labelTu);
        panel_Finding_locTheoNgay.add(textFieldTu);
        panel_Finding_locTheoNgay.add(labelDen);
        panel_Finding_locTheoNgay.add(textFieldDen);

        // Filter buttons panel
        panel_Finding_buttons = tool.createPanel(300, 42, new FlowLayout(FlowLayout.CENTER, 29, 5));
        buttonLamMoi = createStyledButton("Làm mới", 120, 30);
        buttonLoc = createStyledButton("Lọc", 120, 30);
        panel_Finding_buttons.add(buttonLamMoi);
        panel_Finding_buttons.add(buttonLoc);
        panel_Finding_buttons.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        panel_Finding.add(panel_Finding_timKiem);
        panel_Finding.add(panel_Finding_locTheoNgay);
        panel_Finding.add(panel_Finding_buttons);

        // Table panel
        panel_Table = tool.createPanel(width - width_sideMenu, height - 150, new BorderLayout());
        panel_Table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize tables
        String[] columnNames_SanPham = { "STT", "Mã Sách", "Tên Sách", "Số lượng Nhập", "Số lượng đã Bán" };
        String[] columnNames_KhachHang = { "STT", "SĐT", "Tên Khách Hàng", "Điểm tích lũy" };
        String[] columnNames_DoanhThu = { "STT", "Mã Hóa Đơn", "Ngày", "Tổng Tiền" };

        table_KhachHang = tool.createTable(new String[0][], columnNames_KhachHang);
        table_SanPham = tool.createTable(new String[0][], columnNames_SanPham);
        table_DoanhThu = tool.createTable(new String[0][], columnNames_DoanhThu);

        model_KhachHang = (DefaultTableModel) table_KhachHang.getModel();
        model_SanPham = (DefaultTableModel) table_SanPham.getModel();
        model_DoanhThu = (DefaultTableModel) table_DoanhThu.getModel();

        styleTable(table_KhachHang);
        styleTable(table_SanPham);
        styleTable(table_DoanhThu);

        JScrollPane scrollPane_KhachHang = new JScrollPane(table_KhachHang);
        JScrollPane scrollPane_SanPham = new JScrollPane(table_SanPham);
        JScrollPane scrollPane_DoanhThu = new JScrollPane(table_DoanhThu);
        scrollPane_KhachHang.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_SanPham.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_DoanhThu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_KhachHang.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210));
        scrollPane_SanPham.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210));
        scrollPane_DoanhThu.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210));

        panel_Table.add(scrollPane_DoanhThu, BorderLayout.NORTH);
        refreshTable();

        panel.add(panel_button, BorderLayout.NORTH);
        panel.add(panel_Finding, BorderLayout.CENTER);
        panel.add(panel_Table, BorderLayout.SOUTH);

        // Event listeners
        buttonDoanhThu.addActionListener(e -> switchTable(scrollPane_DoanhThu));
        buttonKhachHang.addActionListener(e -> switchTable(scrollPane_KhachHang));
        buttonSanPham.addActionListener(e -> switchTable(scrollPane_SanPham));
        buttonLamMoi.addActionListener(e -> {
            textFieldTimKiem.setText("");
            textFieldTu.setText("");
            textFieldDen.setText("");
            refreshTable();
        });
        buttonLoc.addActionListener(e -> filterByDate());
        textFieldTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchTables(textFieldTimKiem.getText().trim().toLowerCase());
            }
        });
    }

    private JButton createStyledButton(String text) {
        return createStyledButton(text, 150, 35);
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(MENU_BACKGROUND);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(MENU_BACKGROUND);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(MENU_BACKGROUND);
            }
        });
        return button;
    }

    private void styleTable(JTable table) {
        table.getTableHeader().setBackground(MENU_BACKGROUND);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setDefaultEditor(Object.class, null);
    }

    private void switchTable(JScrollPane scrollPane) {
        panel_Table.removeAll();
        panel_Table.add(scrollPane, BorderLayout.NORTH);
        panel_Table.revalidate();
        panel_Table.repaint();
    }

    private void refreshTable() {
        model_KhachHang.setRowCount(0);
        model_SanPham.setRowCount(0);
        model_DoanhThu.setRowCount(0);
        try {
            // Load customers
            List<KhachHangDTO> khachHangList = khachHangBUS.getAllKhachHang();
            for (int i = 0; i < khachHangList.size(); i++) {
                KhachHangDTO kh = khachHangList.get(i);
                model_KhachHang.addRow(new Object[] {
                        i + 1,
                        kh.getSdt(),
                        kh.getHoTen(),
                        kh.getDiem()
                });
            }

            // Load products
            List<SachDTO> sachList = sachBUS.getAllSach();
            List<SachDTO> sachDaBanList = sachBUS.getSoLuongDaBan();
            for (SachDTO sach : sachList) {
                for (SachDTO sachBan : sachDaBanList) {
                    if (sach.getMaSach().equals(sachBan.getMaSach())) {
                        sach.setSoLuongDaBan(sachBan.getSoLuongDaBan());
                        break;
                    }
                }
            }
            for (int i = 0; i < sachList.size(); i++) {
                SachDTO sach = sachList.get(i);
                model_SanPham.addRow(new Object[] {
                        i + 1,
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getSoLuongDaBan()
                });
            }

            // Load invoices
            List<HoaDonDTO> hoaDonList = hoaDonBUS.getAllHoaDon();
            for (int i = 0; i < hoaDonList.size(); i++) {
                HoaDonDTO hd = hoaDonList.get(i);
                model_DoanhThu.addRow(new Object[] {
                        i + 1,
                        hd.getMaHD(),
                        new SimpleDateFormat("dd-MM-yyyy").format(hd.getNgayBan()),
                        hd.getTongTien()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
    }

    private void searchTables(String query) {
        refreshTable(); // Reset tables before filtering
        if (query.isEmpty())
            return;

        // Search Doanh Thu (Invoices)
        for (int i = model_DoanhThu.getRowCount() - 1; i >= 0; i--) {
            String maHD = model_DoanhThu.getValueAt(i, 1).toString().toLowerCase();
            if (!maHD.contains(query)) {
                model_DoanhThu.removeRow(i);
            }
        }

        // Search Khach Hang (Customers)
        for (int i = model_KhachHang.getRowCount() - 1; i >= 0; i--) {
            String sdt = model_KhachHang.getValueAt(i, 1).toString().toLowerCase();
            String hoTen = model_KhachHang.getValueAt(i, 2).toString().toLowerCase();
            if (!sdt.contains(query) && !hoTen.contains(query)) {
                model_KhachHang.removeRow(i);
            }
        }

        // Search San Pham (Products)
        for (int i = model_SanPham.getRowCount() - 1; i >= 0; i--) {
            String maSach = model_SanPham.getValueAt(i, 1).toString().toLowerCase();
            String tenSach = model_SanPham.getValueAt(i, 2).toString().toLowerCase();
            if (!maSach.contains(query) && !tenSach.contains(query)) {
                model_SanPham.removeRow(i);
            }
        }
    }

    private void filterByDate() {
        String startDateStr = textFieldTu.getText().trim();
        String endDateStr = textFieldDen.getText().trim();
        if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập cả ngày bắt đầu và ngày kết thúc!");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            java.util.Date startDate = sdf.parse(startDateStr);
            java.util.Date endDate = sdf.parse(endDateStr);
            if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được sau ngày kết thúc!");
                return;
            }

            // Filter Doanh Thu table only
            refreshTable();
            for (int i = model_DoanhThu.getRowCount() - 1; i >= 0; i--) {
                String dateStr = model_DoanhThu.getValueAt(i, 2).toString();
                java.util.Date date = sdf.parse(dateStr);
                if (date.before(startDate) || date.after(endDate)) {
                    model_DoanhThu.removeRow(i);
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Định dạng ngày không hợp lệ! Vui lòng nhập theo định dạng dd-MM-yyyy (ví dụ: 25-12-2023)");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}