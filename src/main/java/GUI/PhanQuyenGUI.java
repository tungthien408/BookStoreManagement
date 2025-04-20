package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

public class PhanQuyenGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JPanel panel_buttons, panel_table;
    JButton btn_add, btn_edit, btn_delete;
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);

    public PhanQuyenGUI() {
        // T o panel chia b c c
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel_buttons = tool.createPanel(width - width_sideMenu, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_table = tool.createPanel(width - width_sideMenu, height - 50, new BorderLayout());
        // Thêm các n i dung vào c c panel
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(7, 3, 10, 10));
        panel_table.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        btn_add = new JButton("THÊM");
        btn_edit = new JButton("SỬA");
        btn_delete = new JButton("XÓA");
        btn_add.setFont(new Font("Arial", Font.BOLD, 14));
        btn_edit.setFont(new Font("Arial", Font.BOLD, 14));
        btn_delete.setFont(new Font("Arial", Font.BOLD, 14));
        btn_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_add.setFocusable(false);
        btn_edit.setFocusable(false);
        btn_delete.setFocusable(false);
        btn_add.setBackground(new Color(0, 36, 107));
        btn_edit.setBackground(new Color(0, 36, 107));
        btn_delete.setBackground(new Color(0, 36, 107));
        btn_add.setForeground(Color.white);
        btn_edit.setForeground(Color.white);
        btn_delete.setForeground(Color.white);
        Dimension buttonSize = new Dimension(150, 35);
        btn_add.setPreferredSize(buttonSize);
        btn_edit.setPreferredSize(buttonSize);
        btn_delete.setPreferredSize(buttonSize);
        btn_add.setBorderPainted(false);
        btn_edit.setBorderPainted(false);
        btn_delete.setBorderPainted(false);
        // Hi u ng nút b m
        btn_add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_add.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                btn_add.setBackground(MENU_BACKGROUND);
            }
        });
        btn_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_edit.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                btn_edit.setBackground(MENU_BACKGROUND);
            }
        });

        btn_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_delete.setBackground(MENU_HOVER);
            }

            public void mouseExited(MouseEvent e) {
                btn_delete.setBackground(MENU_BACKGROUND);
            }
        });

        panel_buttons.add(btn_add); // Thêm nút thêm
        panel_buttons.add(btn_edit); // Thêm nút sửa
        panel_buttons.add(btn_delete); // Thêm nút xóa
        // Tạo các nút chức năng
        String[] columnNames = { "MÃ QUYỀN", "TÊN QUYỀN" };
        String[][] data = {
                { "1", "Quản lý nhân viên (admin)" },
                { "2", "Quản lý " },
                { "3", "Bán hàng" },
                { "4", "Kế toán" },
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : data) {
            model.addRow(row);
        }
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(true);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(new Color(240, 240, 240));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(Color.white);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setBackground(MENU_BACKGROUND);
        table.getTableHeader().setForeground(Color.white);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        table.setDragEnabled(false);
        table.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(width - width_sideMenu - 40, height - 120));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel_table.add(scrollPane, BorderLayout.NORTH);
        panel.add(panel_table, BorderLayout.SOUTH);
        panel.add(panel_buttons, BorderLayout.NORTH);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    String name = table.getValueAt(selectedRow, 1).toString();
                }
            }
        });
        btn_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(panel, "Vui lòng chọn một quyền chỉnh sửa!", "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String ValueMaQuyen = table.getValueAt(selectedRow, 0).toString();
                String ValueTenQuyen = table.getValueAt(selectedRow, 1).toString();
                new EditPhanQuyenGUI(ValueMaQuyen, ValueTenQuyen);
            }
        });
        // Làm đỡ cái thêm quyền
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenQuyen = JOptionPane.showInputDialog(panel, "Nhập Tên Quyền:", "Thêm Quyền",
                        JOptionPane.QUESTION_MESSAGE);
                if (tenQuyen != null && !tenQuyen.trim().isEmpty()) {
                    int maxMaQuyen = 0;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        int currentMaQuyen = Integer.parseInt(table.getValueAt(i, 0).toString());
                        if (currentMaQuyen > maxMaQuyen) {
                            maxMaQuyen = currentMaQuyen;
                        }
                    }
                    String newMaQuyen = String.valueOf(maxMaQuyen + 1);
                    // Add logic to add the new "Quyen" with newMaQuyen and tenQuyen
                    JOptionPane.showMessageDialog(panel,
                            "Quyền " + tenQuyen + " với Mã Quyền " + newMaQuyen + " đã được thêm thành công!",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    String[] data = { newMaQuyen, tenQuyen };
                    ((DefaultTableModel) table.getModel()).addRow(data);
                } else {
                    JOptionPane.showMessageDialog(panel, "Tên Quyền không được để trống!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int maQuyen = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    String tenQuyen = table.getValueAt(selectedRow, 1).toString();
                    int result = JOptionPane.showConfirmDialog(panel,
                            "Bản xóa quyền " + tenQuyen + " với Mã Quyền " + maQuyen + " ?", "Xóa quyền",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                        JOptionPane.showMessageDialog(panel, "Quyền đã được xóa thành công!", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Vui lòng chọn một quyền để xóa!", "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }
    // Làm đỡ xóa quyền cho dễ hình dung

    public JPanel getPanel() {
        return this.panel;
    }
}
