package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.SachBUS;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.SachDTO;

public class HoaDonBanGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 660;
    private static final int SEARCH_FIELD_WIDTH = 300;
    private static final int SEARCH_FIELD_HEIGHT = 30;

    private Tool tool = new Tool();
    private JButton[] buttons = new JButton[3];
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<HoaDonDTO> hoaDonList;
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private  SachBUS sachBUS = new SachBUS();
    private  KhachHangBUS khachHangBUS = new KhachHangBUS();
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();

    public HoaDonBanGUI() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        initializeData();
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createHoaDonBanTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);
    }

    private void initializeData() {
        try {
            hoaDonList = hoaDonBUS.getAllHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }

    private JPanel createHoaDonBanTable() {
        String[] columns = {"Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày bán", "Tổng tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        try {
            if (hoaDonList != null) {
                for (HoaDonDTO hoaDon : hoaDonList) {
                    tableModel.addRow(new Object[]{
                            hoaDon.getMaHD(),
                            hoaDon.getMaNV(),
                            hoaDon.getMaKH(),
                            hoaDon.getNgayBan().toString(),
                            hoaDon.getTongTien()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }

        table = tool.createTable(tableModel, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] btnText = {"Chi tiết", "Nhập Excel", "Xuất Excel"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, btnText, new Color(0, 36, 107), Color.WHITE, "y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Add action listeners for buttons
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInvoiceDetails();
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromExcel();
            }
        });
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });

        return panelBtn;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã nhân viên", "SĐT khách hàng"};
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextField(300, 30, searchOptions));
        return searchPanel;
    }

    private void filterTable(String query) {
        tableModel.setRowCount(0);
        try {
            for (HoaDonDTO hoaDon : hoaDonList) {
                String maHD = hoaDon.getMaHD().toLowerCase();
                String maNV = hoaDon.getMaNV().toLowerCase();
                if (maHD.contains(query) || maNV.contains(query)) {
                    tableModel.addRow(new Object[]{
                            hoaDon.getMaHD(),
                            hoaDon.getMaNV(),
                            hoaDon.getMaKH(),
                            hoaDon.getNgayBan().toString(),
                            hoaDon.getTongTien()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private void showInvoiceDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hóa đơn để xem chi tiết!");
            return;
        }

        String maHD = (String) tableModel.getValueAt(selectedRow, 0);
        try {
            // Assume a method to get invoice details
            List<ChiTietHoaDonDTO> chiTietList = chiTietHoaDonBUS.getChiTietByMaHD(maHD);
            if (chiTietList == null || chiTietList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết hóa đơn!");
                return;
            }

            // Create a new dialog or panel to display details
            StringBuilder details = new StringBuilder("Chi tiết hóa đơn " + maHD + ":\n");
            for (ChiTietHoaDonDTO chiTiet : chiTietList) {
                SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
                details.append("Sách: ").append(sach != null ? sach.getTenSach() : chiTiet.getMaSach())
                        .append(", Số lượng: ").append(chiTiet.getSoLuong())
                        .append(", Đơn giá: ").append(chiTiet.getGia())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, details.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải chi tiết hóa đơn: " + e.getMessage());
        }
    }

    private void importFromExcel() {
        // Placeholder for Excel import
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel đang được phát triển!");
        // Implement Excel import logic using a library like Apache POI
    }

    private void exportToExcel() {
        // Placeholder for Excel export
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
        // Implement Excel export logic using a library like Apache POI
    }

    public JPanel getPanel() {
        return this.panel;
    }
}