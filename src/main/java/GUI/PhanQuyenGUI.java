package GUI;

<<<<<<< HEAD
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.QuyenBUS;
import DTO.QuyenDTO;
=======
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7

public class PhanQuyenGUI {
    private Tool tool = new Tool();
    private JPanel panel;
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int) (width * 0.625);
    private JPanel panel_buttons, panel_table;
    private JButton btn_add, btn_edit, btn_delete;
    private JTable table;
    private DefaultTableModel model;
    private QuyenBUS quyenBUS = new QuyenBUS();
    private static final Color MENU_BACKGROUND = new Color(0, 36, 107);
    private static final Color MENU_HOVER = new Color(15, 76, 104);
    private static final String[] PERMISSION_LABELS = {
        "Bán sách", "Nhập sách", "Hóa đơn bán", "Hóa đơn nhập", "Sách",
        "Nhà xuất bản", "Tác giả", "Nhân viên, Tài Khoản, Phân Quyền", "Khách hàng", "Thống kê"
    };

    public PhanQuyenGUI() {
        // T o panel chia b c c
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel_buttons = tool.createPanel(width - width_sideMenu, 50, new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel_table = tool.createPanel(width - width_sideMenu, height - 50, new BorderLayout());
<<<<<<< HEAD

        // Thêm các nội dung vào các panel
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(7, 3, 10, 10));
        panel_table.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Tạo các nút
=======
        // Thêm các n i dung vào c c panel
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(7, 3, 10, 10));
        panel_table.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
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
        btn_add.setBackground(MENU_BACKGROUND);
        btn_edit.setBackground(MENU_BACKGROUND);
        btn_delete.setBackground(MENU_BACKGROUND);
        btn_add.setForeground(Color.WHITE);
        btn_edit.setForeground(Color.WHITE);
        btn_delete.setForeground(Color.WHITE);
        Dimension buttonSize = new Dimension(150, 35);
        btn_add.setPreferredSize(buttonSize);
        btn_edit.setPreferredSize(buttonSize);
        btn_delete.setPreferredSize(buttonSize);
<<<<<<< HEAD

        // Hiệu ứng nút bấm
=======
        btn_add.setBorderPainted(false);
        btn_edit.setBorderPainted(false);
        btn_delete.setBorderPainted(false);
        // Hi u ng nút b m
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
        btn_add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_add.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_add.setBackground(MENU_BACKGROUND);
            }
        });
        btn_edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_edit.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_edit.setBackground(MENU_BACKGROUND);
            }
        });

        btn_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_delete.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_delete.setBackground(MENU_BACKGROUND);
            }
        });

<<<<<<< HEAD
        panel_buttons.add(btn_add);
        panel_buttons.add(btn_edit);
        panel_buttons.add(btn_delete);

        // Tạo bảng
        String[] columnNames = {"MÃ QUYỀN", "TÊN QUYỀN"};
        model = new DefaultTableModel(columnNames, 0);
        refreshTable(); // Load initial data from database
        table = new JTable(model);
        table.setRowHeight(25);
=======
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
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
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
        table.getTableHeader().setForeground(Color.WHITE);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        table.setDragEnabled(false);
        table.setFocusable(false);
<<<<<<< HEAD
        table.setBackground(Color.WHITE); // Changed from MENU_BACKGROUND for better readability
=======
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
        JScrollPane scrollPane = new JScrollPane(table);
<<<<<<< HEAD
        panel_table.add(scrollPane, BorderLayout.CENTER);

        panel.add(panel_table, BorderLayout.CENTER);
        panel.add(panel_buttons, BorderLayout.NORTH);

        // Gắn sự kiện cho các nút
        btn_add.addActionListener(e -> addQuyen());
        btn_edit.addActionListener(e -> editQuyen());
        btn_delete.addActionListener(e -> deleteQuyen());
    }

    private void refreshTable() {
        model.setRowCount(0); // Clear existing rows
        for (QuyenDTO quyen : quyenBUS.getAllQuyen()) {
            String maQuyenDisplay = quyen.getMaQuyen().isEmpty() ? "" : String.join(",", quyen.getMaQuyen().split(""));
            model.addRow(new Object[] {
                maQuyenDisplay, // Display as "0,1,2,3,4,5,6,7,8,9"
                quyen.getTenQuyen()
            });
        }
    }

    private void addQuyen() {
        // Tạo dialog để thêm quyền
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm Quyền");
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(panel);

        // Panel chứa các checkbox
        JPanel checkBoxPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JCheckBox[] permissionCheckBoxes = new JCheckBox[10];
        for (int i = 0; i < 10; i++) {
            permissionCheckBoxes[i] = new JCheckBox(PERMISSION_LABELS[i]);
            checkBoxPanel.add(permissionCheckBoxes[i]);
        }

        // Panel chứa tên quyền và nút
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel lblTenQuyen = new JLabel("Tên Quyền:");
        JTextField txtTenQuyen = new JTextField();
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        inputPanel.add(lblTenQuyen);
        inputPanel.add(txtTenQuyen);
        inputPanel.add(btnSave);
        inputPanel.add(btnCancel);

        // Styling buttons
        btnSave.setBackground(MENU_BACKGROUND);
        btnCancel.setBackground(MENU_BACKGROUND);
        btnSave.setForeground(Color.WHITE);
        btnCancel.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setFocusable(false);
        btnCancel.setFocusable(false);

        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(MENU_BACKGROUND);
            }
        });
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(MENU_BACKGROUND);
            }
        });

        dialog.add(checkBoxPanel, BorderLayout.CENTER);
        dialog.add(inputPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                StringBuilder maQuyenBuilder = new StringBuilder();
                for (int i = 0; i < permissionCheckBoxes.length; i++) {
                    if (permissionCheckBoxes[i].isSelected()) {
                        maQuyenBuilder.append(i);
                    }
                }
                String maQuyen = maQuyenBuilder.toString();
                String tenQuyen = txtTenQuyen.getText().trim();

                if (tenQuyen.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tên quyền không được để trống!");
                    return;
                }
                if (tenQuyen.length() > 50) {
                    JOptionPane.showMessageDialog(dialog, "Tên quyền không được dài hơn 50 ký tự!");
                    return;
                }
                if (maQuyen.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng chọn ít nhất một quyền!");
                    return;
                }

                // Kiểm tra xem maQuyen đã tồn tại chưa
                QuyenDTO existingQuyen = quyenBUS.getQuyenById(maQuyen);
                if (existingQuyen != null) {
                    JOptionPane.showMessageDialog(dialog, "Mã quyền đã tồn tại!");
                    return;
                }

                QuyenDTO quyen = new QuyenDTO(maQuyen, tenQuyen, 0);
                if (quyenBUS.addQuyen(quyen)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm quyền thành công!");
                    refreshTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm quyền thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void editQuyen() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn một quyền để sửa!");
            return;
        }

        String maQuyenCurrent = table.getValueAt(selectedRow, 0).toString().replace(",", "");
        String tenQuyenCurrent = table.getValueAt(selectedRow, 1).toString();

        // Tạo dialog để sửa quyền
        JDialog dialog = new JDialog();
        dialog.setTitle("Sửa Quyền");
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(panel);

        // Panel chứa các checkbox
        JPanel checkBoxPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JCheckBox[] permissionCheckBoxes = new JCheckBox[10];
        for (int i = 0; i < 10; i++) {
            permissionCheckBoxes[i] = new JCheckBox(PERMISSION_LABELS[i]);
            if (maQuyenCurrent.contains(String.valueOf(i))) {
                permissionCheckBoxes[i].setSelected(true);
            }
            checkBoxPanel.add(permissionCheckBoxes[i]);
        }

        // Panel chứa tên quyền và nút
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel lblTenQuyen = new JLabel("Tên Quyền:");
        JTextField txtTenQuyen = new JTextField(tenQuyenCurrent);
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        inputPanel.add(lblTenQuyen);
        inputPanel.add(txtTenQuyen);
        inputPanel.add(btnSave);
        inputPanel.add(btnCancel);

        // Styling buttons
        btnSave.setBackground(MENU_BACKGROUND);
        btnCancel.setBackground(MENU_BACKGROUND);
        btnSave.setForeground(Color.WHITE);
        btnCancel.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setFocusable(false);
        btnCancel.setFocusable(false);

        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(MENU_BACKGROUND);
            }
        });
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(MENU_BACKGROUND);
            }
        });

        dialog.add(checkBoxPanel, BorderLayout.CENTER);
        dialog.add(inputPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            try {
                StringBuilder maQuyenBuilder = new StringBuilder();
                for (int i = 0; i < permissionCheckBoxes.length; i++) {
                    if (permissionCheckBoxes[i].isSelected()) {
                        maQuyenBuilder.append(i);
                    }
                }
                String maQuyen = maQuyenBuilder.toString();
                String tenQuyen = txtTenQuyen.getText().trim();

                if (tenQuyen.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tên quyền không được để trống!");
                    return;
                }
                if (tenQuyen.length() > 50) {
                    JOptionPane.showMessageDialog(dialog, "Tên quyền không được dài hơn 50 ký tự!");
                    return;
                }
                if (maQuyen.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng chọn ít nhất một quyền!");
                    return;
                }

                QuyenDTO quyen = new QuyenDTO(maQuyen, tenQuyen, 0);
                if (quyenBUS.updateQuyen(quyen, maQuyenCurrent)) {
                    JOptionPane.showMessageDialog(dialog, "Sửa quyền thành công!");
                    refreshTable();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Sửa quyền thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void deleteQuyen() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn một quyền để xóa!");
            return;
        }

        String maQuyen = table.getValueAt(selectedRow, 0).toString().replace(",", "");
        int confirm = JOptionPane.showConfirmDialog(panel, "Bạn có chắc chắn muốn xóa quyền này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (quyenBUS.deleteQuyen(maQuyen)) {
                    JOptionPane.showMessageDialog(panel, "Xóa quyền thành công!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(panel, "Xóa quyền thất bại! (Kiểm tra ràng buộc khóa ngoại)");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Lỗi: " + e.getMessage());
            }
        }
=======
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
>>>>>>> 7b71cabb0245129aa9c13762ed971e2043a02cd7
    }
    // Làm đỡ xóa quyền cho dễ hình dung

    public JPanel getPanel() {
        return this.panel;
    }
}
