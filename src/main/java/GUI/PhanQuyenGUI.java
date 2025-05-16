package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BUS.QuyenBUS;
import BUS.TaiKhoanNVBUS;
import DTO.QuyenDTO;
import DTO.TaiKhoanNVDTO;
import GUI.MainContentDiaLog.AddAndEditDecentralizationGUI;

public class PhanQuyenGUI {
    Tool tool = new Tool();
    JPanel panel, panelButtons, panelTable;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JButton[] btn = new JButton[4];
    JTable table;
    private JTextField txt_search;
    private JTextField[] txt_array_search = new JTextField[1];
    private JComboBox<String> comboBox;
    private QuyenBUS quyenBUS;
    private TaiKhoanNVBUS taiKhoanBUS;
    private TaiKhoanNVDTO taiKhoan;
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);
    private boolean add = false;
    private boolean update = false;
    private int selectedRow = -1;
    private int lastSelectedRow = -1;

    public PhanQuyenGUI(TaiKhoanNVDTO taiKhoan) {
        this.quyenBUS = new QuyenBUS();
        this.taiKhoanBUS = new TaiKhoanNVBUS();
        this.taiKhoan = taiKhoan;
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };

        // Log taiKhoan details
        System.out.println("TaiKhoanNVDTO: maNV=" + (taiKhoan != null ? taiKhoan.getMaNV() : "null"));

        // Create main panel
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        // Add components
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createQuyenTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Apply permissions
        applyPermissions(taiKhoan != null ? taiKhoan.getMaNV() : "unknown", 9);

        // Initialize search functionality
        timkiem();
    }

    private void applyPermissions(String username, int maCN) {
        if (btn[0] == null || btn[1] == null || btn[2] == null) {
            System.err.println("Buttons not initialized in applyPermissions!");
            return;
        }
        boolean canAdd = taiKhoanBUS.hasPermission(username, maCN, "add");
        boolean canEdit = taiKhoanBUS.hasPermission(username, maCN, "edit");
        boolean canDelete = taiKhoanBUS.hasPermission(username, maCN, "delete");
        System.out.println("Permissions for " + username + ", maCN=" + maCN + ": add=" + canAdd + ", edit=" + canEdit + ", delete=" + canDelete);
        btn[0].setVisible(canAdd); // Thêm
        btn[1].setVisible(canEdit); // Sửa
        btn[2].setVisible(canDelete); // Xóa
        if (!canAdd && !canEdit && !canDelete) {
            System.out.println("WARNING: No permissions granted for user " + username + " on maCN=" + maCN);
        }
    }

    private JPanel createQuyenTable() {
        String[] columnNames = { "Mã Quyền", "Tên Quyền" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        loadTableData(model);

        // Create table
        table = tool.createTable(model, columnNames);
        table.setDefaultEditor(Object.class, null); // Non-editable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 440));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));

        // Table mouse listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                selectedRow = table.getSelectedRow();

                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table.clearSelection();
                    update = false;
                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    lastSelectedRow = selectedRow;
                }
            }
        });

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private void loadTableData(DefaultTableModel model) {
        model.setRowCount(0);
        List<QuyenDTO> quyenList = quyenBUS.getAllQuyen();
        System.out.println("Loaded " + quyenList.size() + " quyen records");
        for (QuyenDTO quyen : quyenList) {
            model.addRow(new Object[] { quyen.getMaQuyen(), quyen.getTenQuyen() });
        }
    }

    private JPanel createPanelButton() {
        String[] btn_txt = { "Thêm", "Sửa", "Xóa", "Hủy" };
        panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.add(tool.createButtonPanel(btn, btn_txt, MENU_BACKGROUND, Color.WHITE, "y"));

        // Button hover effects
        for (JButton button : btn) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(MENU_HOVER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(MENU_BACKGROUND);
                }
            });
        }

        // Button actions
        btn[0].addActionListener(e -> addQuyen());
        btn[1].addActionListener(e -> updateQuyen());
        btn[2].addActionListener(e -> deleteQuyen());
        btn[3].addActionListener(e -> cancel());

        return panelButtons;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã Quyền", "Tên Quyền" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
    }

    private void timkiem() {
        comboBox.addActionListener(e -> filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem()));
        txt_array_search[0].addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
            }
        });
    }

    private void filterTable(String query, String searchType) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (QuyenDTO quyen : quyenBUS.getAllQuyen()) {
            boolean match = false;
            switch (searchType) {
                case "Mã Quyền":
                    match = String.valueOf(quyen.getMaQuyen()).toLowerCase().contains(query.toLowerCase());
                    break;
                case "Tên Quyền":
                    match = quyen.getTenQuyen().toLowerCase().contains(query.toLowerCase());
                    break;
            }
            if (match) {
                model.addRow(new Object[] { quyen.getMaQuyen(), quyen.getTenQuyen() });
            }
        }
    }

    private void addQuyen() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(panel);
        new AddAndEditDecentralizationGUI(parent, quyenBUS, "Thêm Quyền", "add");
        cancel(); // Refresh table
    }

    private void updateQuyen() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn quyền để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maQuyen = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        String tenQuyen = (String) table.getValueAt(selectedRow, 1);
        QuyenDTO quyen = new QuyenDTO(maQuyen, tenQuyen);
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(panel);
        new AddAndEditDecentralizationGUI(parent, quyenBUS, "Sửa Quyền", "save", quyen);
        cancel(); // Refresh table
    }

    private void deleteQuyen() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn quyền để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int maQuyen = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        String tenQuyen = (String) table.getValueAt(selectedRow, 1);
        int result = JOptionPane.showConfirmDialog(panel,
                "Bạn có chắc muốn xóa quyền " + tenQuyen + " với Mã Quyền " + maQuyen + " ?",
                "Xóa quyền", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (quyenBUS.deleteQuyen(maQuyen)) {
                JOptionPane.showMessageDialog(panel, "Xóa quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                cancel();
            } else {
                JOptionPane.showMessageDialog(panel, "Xóa quyền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancel() {
        add = false;
        update = false;
        table.clearSelection();
        selectedRow = -1;
        lastSelectedRow = -1;
        loadTableData((DefaultTableModel) table.getModel());
    }

    public JPanel getPanel() {
        return this.panel;
    }
}