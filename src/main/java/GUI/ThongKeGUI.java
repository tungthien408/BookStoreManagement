
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import BUS.ChiTietHoaDonBUS;
import BUS.ChiTietPhieuNhapBUS;
import BUS.HoaDonBUS;
import BUS.SachBUS;
import DTO.HoaDonDTO;
import DTO.SachDTO;

public class ThongKeGUI {
    Tool tool = new Tool();
    JPanel panel, panel_button, panel_Finding, panel_Table, panel_Finding_locTheoNgay, panel_Finding_buttons;
    JButton[] buttons = new JButton[4]; // DoanhThu, SanPham, LamMoi, Loc
    JTextField[] txt_array_date = new JTextField[3]; // textFieldTu, textFieldDen, textFieldHienTai
    JTable table_SanPham, table_DoanhThu;
    JScrollPane scrollPane_SanPham, scrollPane_DoanhThu;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);
    private String activeTable = "DoanhThu";
    SachBUS sachBUS = new SachBUS();
    ChiTietHoaDonBUS cthdbus = new ChiTietHoaDonBUS();
    ChiTietPhieuNhapBUS ctpnbus = new ChiTietPhieuNhapBUS();

    public ThongKeGUI() {
        // Main panel
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        // Button panel (DoanhThu, SanPham)
        panel_button = tool.createPanel(width - width_sideMenu, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] btn_labels = {"DOANH THU", "SẢN PHẨM"};
        JButton[] topButtons = new JButton[2];
        panel_button.add(tool.createButtonPanel(topButtons, btn_labels, MENU_BACKGROUND, Color.WHITE, "x"));
        buttons[0] = topButtons[0]; // DoanhThu
        buttons[1] = topButtons[1]; // SanPham

        // Finding panel
        panel_Finding = tool.createPanel(width - width_sideMenu, 70, new FlowLayout(FlowLayout.LEFT, 35, 0));
        panel_Finding.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 10));

        // Date filter panel
        panel_Finding_locTheoNgay = tool.createPanel(600, 60, new FlowLayout(FlowLayout.LEFT, 10, 0));
        String[] date_labels = {"Từ ngày: ", "Đến ngày: ", "Ngày hiện tại: "};
        txt_array_date[0] = new JTextField(10); // Từ ngày
        txt_array_date[1] = new JTextField(10); // Đến ngày
        txt_array_date[2] = new JTextField(10); // Ngày hiện tại
        for (JTextField txt : txt_array_date) {
            txt.setPreferredSize(new Dimension(100, 30));
            txt.setBackground(new Color(202, 220, 252));
            txt.setToolTipText("yyyy-MM-dd");
        }
        tool.Editable(txt_array_date, true); // Make date fields editable
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        for (int i = 0; i < date_labels.length; i++) {
            datePanel.add(new JLabel(date_labels[i]));
            datePanel.add(txt_array_date[i]);
        }
        panel_Finding_locTheoNgay.add(datePanel);

        // Filter buttons panel (LamMoi, Loc)
        panel_Finding_buttons = tool.createPanel(300, 90, new FlowLayout(FlowLayout.CENTER, 29, 5));
        panel_Finding_buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        String[] filter_btn_labels = {"Làm mới", "Lọc"};
        JButton[] filterButtons = new JButton[2];
        panel_Finding_buttons.add(tool.createButtonPanel(filterButtons, filter_btn_labels, MENU_BACKGROUND, Color.WHITE, "x"));
        buttons[2] = filterButtons[0]; // LamMoi
        buttons[3] = filterButtons[1]; // Loc

        panel_Finding.add(panel_Finding_locTheoNgay);
        panel_Finding.add(panel_Finding_buttons);

        // Table panel
        panel_Table = tool.createPanel(width - width_sideMenu, height - 150, new BorderLayout());
        panel_Table.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize tables
        initializeTables();
        panel_Table.add(scrollPane_DoanhThu, BorderLayout.NORTH); // Default to DoanhThu

        // Add panels to main panel
        panel.add(panel_button, BorderLayout.NORTH);
        panel.add(panel_Finding, BorderLayout.CENTER);
        panel.add(panel_Table, BorderLayout.SOUTH);

        // Configure scrollbars
        scrollPane_SanPham.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_SanPham.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_DoanhThu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane_DoanhThu.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Button action listeners
        buttons[0].addActionListener(e -> {
            activeTable = "DoanhThu";
            panel_Table.removeAll();
            panel_Table.add(scrollPane_DoanhThu, BorderLayout.NORTH);
            panel_Table.revalidate();
            panel_Table.repaint();
            refreshTable();
        });

        buttons[1].addActionListener(e -> {
            activeTable = "SanPham";
            panel_Table.removeAll();
            panel_Table.add(scrollPane_SanPham, BorderLayout.NORTH);
            panel_Table.revalidate();
            panel_Table.repaint();
            refreshTable();
        });

        buttons[2].addActionListener(e -> refreshTable());

        buttons[3].addActionListener(e -> filterTable());
    }

    private void initializeTables() {
        // Doanh Thu table
        String[] columnNames_DoanhThu = {"STT", "Mã Hóa Đơn", "Ngày", "Tổng Tiền"};
        DefaultTableModel modelDoanhThu = new DefaultTableModel(columnNames_DoanhThu, 0);
        table_DoanhThu = tool.createTable(modelDoanhThu, columnNames_DoanhThu);
        table_DoanhThu.getTableHeader().setBackground(MENU_BACKGROUND);
        table_DoanhThu.getTableHeader().setForeground(Color.WHITE);
        scrollPane_DoanhThu = new JScrollPane(table_DoanhThu);
        scrollPane_DoanhThu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_DoanhThu.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210));

        // San Pham table
        String[] columnNames_SanPham = {"STT", "Mã Sách", "Tên Sách", "Số lượng Nhập", "Số lượng Bán"};
        DefaultTableModel modelSanPham = new DefaultTableModel(columnNames_SanPham, 0);
        table_SanPham = tool.createTable(modelSanPham, columnNames_SanPham);
        table_SanPham.getTableHeader().setBackground(MENU_BACKGROUND);
        table_SanPham.getTableHeader().setForeground(Color.WHITE);
        scrollPane_SanPham = new JScrollPane(table_SanPham);
        scrollPane_SanPham.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane_SanPham.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 210));

        // Load initial data
        refreshTable();
    }

    private void refreshTable() {
        tool.clearFields(txt_array_date);
        if (activeTable.equals("DoanhThu")) {
            DefaultTableModel model = (DefaultTableModel) table_DoanhThu.getModel();
            model.setRowCount(0);
            HoaDonBUS hoaDonBUS = new HoaDonBUS();
            try {
                List<HoaDonDTO> hoaDonList = hoaDonBUS.getAllHoaDon();
                for (int i = 0; i < hoaDonList.size(); i++) {
                    HoaDonDTO hd = hoaDonList.get(i);
                    model.addRow(new Object[]{
                        i + 1,
                        hd.getMaHD(),
                        hd.getNgayBan().toString(),
                        hd.getTongTien()
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng Doanh Thu: " + e.getMessage());
            }
        } else if (activeTable.equals("SanPham")) {
            DefaultTableModel model = (DefaultTableModel) table_SanPham.getModel();
            model.setRowCount(0);
            try {
                List<SachDTO> sachList = sachBUS.getAllSach();
                for (int i = 0; i < sachList.size(); i++) {
                    SachDTO sach = sachList.get(i);
                    model.addRow(new Object[]{
                        i + 1,
                        sach.getMaSach(),
                        sach.getTenSach(),
                        ctpnbus.getSoLuongByMaSach(sach.getMaSach()),
                        cthdbus.getSoLuongByMaSach(sach.getMaSach())
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng Sản Phẩm: " + e.getMessage());
            }
        }
    }

    private void filterTable() {
        String fromDateStr = txt_array_date[0].getText().trim();
        String toDateStr = txt_array_date[1].getText().trim();
        String currentDateStr = txt_array_date[2].getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null, toDate = null, currentDate = null;

        // Parse dates if provided
        try {
            if (!currentDateStr.isEmpty()) {
                currentDate = sdf.parse(currentDateStr);
            } else {
                if (!fromDateStr.isEmpty()) {
                    fromDate = sdf.parse(fromDateStr);
                }
                if (!toDateStr.isEmpty()) {
                    toDate = sdf.parse(toDateStr);
                }
                if (fromDate != null && toDate != null && fromDate.after(toDate)) {
                    JOptionPane.showMessageDialog(null, "Ngày 'Từ' phải trước ngày 'Đến'");
                    return;
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ (yyyy-MM-dd)");
            return;
        }

        if (activeTable.equals("DoanhThu")) {
            DefaultTableModel model = (DefaultTableModel) table_DoanhThu.getModel();
            model.setRowCount(0);
            HoaDonBUS hoaDonBUS = new HoaDonBUS();
            try {
                List<HoaDonDTO> hoaDonList = hoaDonBUS.getAllHoaDon();
                int stt = 1;
                for (HoaDonDTO hd : hoaDonList) {
                    boolean matchesDate = true;
                    if (currentDate != null) {
                        // Compare only the date part (ignore time)
                        String hdDateStr = sdf.format(hd.getNgayBan());
                        String currentDateStrFormatted = sdf.format(currentDate);
                        matchesDate = hdDateStr.equals(currentDateStrFormatted);
                    } else {
                        if (fromDate != null) {
                            matchesDate = !hd.getNgayBan().before(fromDate);
                        }
                        if (toDate != null) {
                            matchesDate = matchesDate && !hd.getNgayBan().after(toDate);
                        }
                    }
                    if (matchesDate) {
                        model.addRow(new Object[]{
                            stt++,
                            hd.getMaHD(),
                            hd.getNgayBan().toString(),
                            hd.getTongTien()
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi lọc bảng Doanh Thu: " + e.getMessage());
            }
        } else if (activeTable.equals("SanPham")) {
            // No filtering for SanPham
            refreshTable();
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
