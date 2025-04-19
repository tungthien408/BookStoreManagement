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

import BUS.ChiTietPhieuNhapBUS;
import BUS.NXBBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;

public class HoaDonNhapGUI {
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
    private List<PhieuNhapDTO> phieuNhapList;
    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
    private NXBBUS nxbBUS = new NXBBUS();
    private SachBUS sachBUS = new SachBUS();

    public HoaDonNhapGUI() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        initializeData();
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createHoaDonNhapTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);
    }

    private void initializeData() {
        try {
            phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu nhập: " + e.getMessage());
        }
    }

    private JPanel createHoaDonNhapTable() {
        String[] columns = {"Mã phiếu nhập", "Mã nhân viên", "Ngày nhập", "Tổng tiền", "Mã NXB"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        try {
            if (phieuNhapList != null) {
                for (PhieuNhapDTO phieuNhap : phieuNhapList) {
                    tableModel.addRow(new Object[]{
                            phieuNhap.getMaPN(),
                            phieuNhap.getMaNV(),
                            phieuNhap.getNgayNhap().toString(),
                            phieuNhap.getTongTien(),
                            phieuNhap.getMaNXB()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu nhập: " + e.getMessage());
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
            for (PhieuNhapDTO phieuNhap : phieuNhapList) {
                String maPN = phieuNhap.getMaPN().toLowerCase();
                String maNV = phieuNhap.getMaNV().toLowerCase();
                String maNXB = phieuNhap.getMaNXB().toLowerCase();
                if (maPN.contains(query) || maNV.contains(query) || maNXB.contains(query)) {
                    tableModel.addRow(new Object[]{
                            phieuNhap.getMaPN(),
                            phieuNhap.getMaNV(),
                            phieuNhap.getNgayNhap().toString(),
                            phieuNhap.getTongTien(),
                            phieuNhap.getMaNXB()
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
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu nhập để xem chi tiết!");
            return;
        }

        String maPN = (String) tableModel.getValueAt(selectedRow, 0);
        try {
            List<ChiTietPhieuNhapDTO> chiTietList = chiTietPhieuNhapBUS.getChiTietPhieuNhapByMaPN(maPN);
            if (chiTietList == null || chiTietList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu nhập!");
                return;
            }

            StringBuilder details = new StringBuilder("Chi tiết phiếu nhập " + maPN + ":\n");
            for (ChiTietPhieuNhapDTO chiTiet : chiTietList) {
                SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
                details.append("Sách: ").append(sach != null ? sach.getTenSach() : chiTiet.getMaSach())
                        .append(", Số lượng: ").append(chiTiet.getSoLuong())
                        .append(", Giá nhập: ").append(chiTiet.getGiaNhap())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, details.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải chi tiết phiếu nhập: " + e.getMessage());
        }
    }

    private void importFromExcel() {
        // Placeholder for Excel import
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel đang được phát triển!");
        // Implement Excel import logic using Apache POI
    }

    private void exportToExcel() {
        // Placeholder for Excel export
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
        // Implement Excel export logic using Apache POI
    }

    public JPanel getPanel() {
        return this.panel;
    }
}