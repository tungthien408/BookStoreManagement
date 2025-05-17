package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;

public class TaiKhoanGUI {
    private Tool tool = new Tool();
    private JPanel panel, panel_searchCombo, panel_buttons, panel_Table, panel_textField;
    private JButton btn_add, btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_huy;
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int) (width * 0.625);
    private JComboBox<String> comboBox;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTable table;
    private DefaultTableModel modelNhanVien;
    private List<NhanVienDTO> nhanVienList;
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    public TaiKhoanGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        // Initialize search components
        txt_array_search[0] = new JTextField();
        panel_searchCombo = tool.createSearchTextField(width - width_sideMenu, height,
                new String[] { "Mã Nhân viên", "Tên Nhân viên" });
        panel_searchCombo.setBorder(BorderFactory.createEmptyBorder(5, 43, 0, 0));
        // Extract comboBox and text field from panel_searchCombo
        for (java.awt.Component comp : panel_searchCombo.getComponents()) {
            if (comp instanceof JComboBox) {
                comboBox = (JComboBox<String>) comp;
            } else if (comp instanceof JTextField) {
                txt_array_search[0] = (JTextField) comp;
            }
        }

        panel_buttons = tool.createPanel(180, height, new FlowLayout(0, 0, 20));
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        panel_textField = new JPanel(null);
        panel_textField.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        panel_textField.setPreferredSize(new Dimension(width - width_sideMenu, 200));
        panel_Table = tool.createPanel(width - width_sideMenu, height - 200, new BorderLayout());
        panel_Table.setBorder(BorderFactory.createEmptyBorder(-2, 40, 0, 0));

        // Text fields and labels
        JLabel labelMaNV = new JLabel("Mã Nhân viên:");
        JLabel labelTenNV = new JLabel("Tên Nhân viên:");
        JLabel labelChucVu = new JLabel("Chức vụ:");
        JLabel labelSoDienThoai = new JLabel("Số điện thoại:");

        JTextField textFieldMaNV = new JTextField();
        JTextField textFieldTenNV = new JTextField();
        JTextField textFieldChucVu = new JTextField();
        JTextField textFieldSoDienThoai = new JTextField();

        labelMaNV.setBounds(181, 30, 100, 30);
        labelTenNV.setBounds(500, 30, 100, 30);
        labelChucVu.setBounds(181, 80, 100, 30);
        labelSoDienThoai.setBounds(500, 80, 100, 30);

        textFieldMaNV.setBounds(270, 30, 200, 27);
        textFieldTenNV.setBounds(594, 30, 200, 27);
        textFieldChucVu.setBounds(270, 80, 200, 27);
        textFieldSoDienThoai.setBounds(594, 80, 200, 27);

        textFieldMaNV.setBackground(new Color(202, 220, 252));
        textFieldTenNV.setBackground(new Color(202, 220, 252));
        textFieldChucVu.setBackground(new Color(202, 220, 252));
        textFieldSoDienThoai.setBackground(new Color(202, 220, 252));

        textFieldMaNV.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldTenNV.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldChucVu.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldSoDienThoai.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        textFieldMaNV.setEditable(false);
        textFieldTenNV.setEditable(false);
        textFieldChucVu.setEditable(false);
        textFieldSoDienThoai.setEditable(false);

        panel_textField.add(labelMaNV);
        panel_textField.add(labelTenNV);
        panel_textField.add(labelChucVu);
        panel_textField.add(labelSoDienThoai);
        panel_textField.add(textFieldMaNV);
        panel_textField.add(textFieldTenNV);
        panel_textField.add(textFieldChucVu);
        panel_textField.add(textFieldSoDienThoai);

        // Table setup
        String[] columnNames = { "Mã Nhân viên", "Tên Nhân viên", "Chức vụ", "Số điện thoại" };
        nhanVienList = nhanVienBUS.getAllNhanVien();
        modelNhanVien = new DefaultTableModel(columnNames, 0);
        table = tool.createTable(modelNhanVien, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(800, height - 280));
        table.getTableHeader().setBackground(new Color(0, 36, 107));
        table.getTableHeader().setForeground(Color.white);
        table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(true);
        refreshTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel_Table.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        btn_add = new JButton("Thêm");
        btn_edit = new JButton("Sửa");
        btn_delete = new JButton("Xóa");
        btn_nhapExcel = new JButton("Nhập Excel");
        btn_xuatExcel = new JButton("Xuất Excel");
        btn_huy = new JButton("Hủy");

        btn_add.setPreferredSize(new Dimension(130, 30));
        btn_edit.setPreferredSize(new Dimension(130, 30));
        btn_delete.setPreferredSize(new Dimension(130, 30));
        btn_nhapExcel.setPreferredSize(new Dimension(130, 30));
        btn_xuatExcel.setPreferredSize(new Dimension(130, 30));
        btn_huy.setPreferredSize(new Dimension(130, 30));

        btn_add.setBackground(new Color(0, 36, 107));
        btn_edit.setBackground(new Color(0, 36, 107));
        btn_delete.setBackground(new Color(0, 36, 107));
        btn_nhapExcel.setBackground(new Color(0, 36, 107));
        btn_xuatExcel.setBackground(new Color(0, 36, 107));
        btn_huy.setBackground(new Color(0, 36, 107));

        btn_add.setForeground(Color.white);
        btn_edit.setForeground(Color.white);
        btn_delete.setForeground(Color.white);
        btn_nhapExcel.setForeground(Color.white);
        btn_xuatExcel.setForeground(Color.white);
        btn_huy.setForeground(Color.white);

        btn_add.setFocusable(false);
        btn_edit.setFocusable(false);
        btn_delete.setFocusable(false);
        btn_nhapExcel.setFocusable(false);
        btn_xuatExcel.setFocusable(false);
        btn_huy.setFocusable(false);

        btn_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_nhapExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_xuatExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_huy.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Table mouse listener
        table.addMouseListener(new MouseAdapter() {
            private int lastSelectedRow = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row == lastSelectedRow && row >= 0) {
                    table.clearSelection();
                    lastSelectedRow = -1;
                    textFieldMaNV.setText("");
                    textFieldTenNV.setText("");
                    textFieldChucVu.setText("");
                    textFieldSoDienThoai.setText("");
                } else if (row >= 0) {
                    lastSelectedRow = row;
                    textFieldMaNV.setText(table.getValueAt(row, 0).toString());
                    textFieldTenNV.setText(table.getValueAt(row, 1).toString());
                    textFieldChucVu.setText(table.getValueAt(row, 2).toString());
                    textFieldSoDienThoai.setText(table.getValueAt(row, 3).toString());
                }
            }
        });

        // Button action listeners
        btn_add.addActionListener(e -> addNhanVien(textFieldMaNV, textFieldTenNV, textFieldChucVu, textFieldSoDienThoai));
        btn_edit.addActionListener(e -> editNhanVien(textFieldMaNV, textFieldTenNV, textFieldChucVu, textFieldSoDienThoai));
        btn_delete.addActionListener(e -> deleteNhanVien(textFieldMaNV));
        btn_nhapExcel.addActionListener(e -> JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel chưa được triển khai!"));
        btn_xuatExcel.addActionListener(e -> JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel chưa được triển khai!"));
        btn_huy.addActionListener(e -> cancel(textFieldMaNV, textFieldTenNV, textFieldChucVu, textFieldSoDienThoai));

        panel_buttons.add(btn_add);
        panel_buttons.add(btn_edit);
        panel_buttons.add(btn_delete);
        panel_buttons.add(btn_nhapExcel);
        panel_buttons.add(btn_xuatExcel);
        panel_buttons.add(btn_huy);

        panel.add(panel_searchCombo, BorderLayout.NORTH);
        panel.add(panel_Table, BorderLayout.CENTER);
        panel.add(panel_buttons, BorderLayout.EAST);
        panel.add(panel_textField, BorderLayout.SOUTH);

        timkiem();
    }

    private void timkiem() {
        if (comboBox != null && txt_array_search[0] != null) {
            comboBox.addActionListener(e -> {
                String selectedOption = (String) comboBox.getSelectedItem();
                filterTable(txt_array_search[0].getText(), selectedOption);
            });

            txt_array_search[0].addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    String selectedOption = (String) comboBox.getSelectedItem();
                    filterTable(txt_array_search[0].getText(), selectedOption);
                }
            });
        }
    }

    private void filterTable(String query, String searchType) {
        modelNhanVien.setRowCount(0);
        try {
            for (NhanVienDTO nv : nhanVienList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã Nhân viên":
                        match = nv.getMaNV().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên Nhân viên":
                        match = nv.getHoTen().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    modelNhanVien.addRow(new Object[] {
                            nv.getMaNV(),
                            nv.getHoTen(),
                            nv.getChucVu(),
                            nv.getSdt()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private void refreshTable() {
        modelNhanVien.setRowCount(0);
        try {
            nhanVienList = nhanVienBUS.getAllNhanVien();
            for (NhanVienDTO nv : nhanVienList) {
                modelNhanVien.addRow(new Object[] {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getSdt()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    private void addNhanVien(JTextField maNV, JTextField tenNV, JTextField chucVu, JTextField sdt) {
        if (!maNV.isEditable()) {
            maNV.setEditable(true);
            tenNV.setEditable(true);
            chucVu.setEditable(true);
            sdt.setEditable(true);
            int rowCount = modelNhanVien.getRowCount();
            String newId = rowCount > 0
                    ? String.format("NV%02d", Integer.parseInt(modelNhanVien.getValueAt(rowCount - 1, 0).toString().replace("NV", "")) + 1)
                    : "NV01";
            maNV.setText(newId);
            tenNV.setText("");
            chucVu.setText("");
            sdt.setText("");
            btn_add.setBackground(new Color(202, 220, 252));
            btn_add.setForeground(Color.BLACK);
            btn_huy.setBackground(Color.RED);
            for (JButton btn : new JButton[] { btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel }) {
                btn.setVisible(false);
            }
        } else {
            try {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(maNV.getText().trim());
                nv.setHoTen(tenNV.getText().trim());
                nv.setChucVu(chucVu.getText().trim());
                nv.setSdt(sdt.getText().trim());
                if (!checkValidate(nv)) {
                    return;
                }
                if (nhanVienBUS.addNhanVien(nv)) {
                    JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
                    cancel(maNV, tenNV, chucVu, sdt);
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên: " + e.getMessage());
            }
        }
    }

    private void editNhanVien(JTextField maNV, JTextField tenNV, JTextField chucVu, JTextField sdt) {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
        } else if (!maNV.isEditable()) {
            maNV.setEditable(true);
            tenNV.setEditable(true);
            chucVu.setEditable(true);
            sdt.setEditable(true);
            maNV.setEditable(false); // MaNV is not editable
            btn_edit.setBackground(new Color(202, 220, 252));
            btn_edit.setForeground(Color.BLACK);
            btn_huy.setBackground(Color.RED);
            for (JButton btn : new JButton[] { btn_add, btn_delete, btn_nhapExcel, btn_xuatExcel }) {
                btn.setVisible(false);
            }
        } else {
            try {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(maNV.getText().trim());
                nv.setHoTen(tenNV.getText().trim());
                nv.setChucVu(chucVu.getText().trim());
                nv.setSdt(sdt.getText().trim());
                if (!checkValidate(nv)) {
                    return;
                }
                if (nhanVienBUS.updateNhanVien(nv)) {
                    JOptionPane.showMessageDialog(null, "Sửa nhân viên thành công!");
                    cancel(maNV, tenNV, chucVu, sdt);
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa nhân viên thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa nhân viên: " + e.getMessage());
            }
        }
    }

    private void deleteNhanVien(JTextField maNV) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?", "Xóa nhân viên",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    String maNVText = maNV.getText().trim();
                    if (nhanVienBUS.deleteNhanVien(maNVText)) {
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
                        cancel(maNV, null, null, null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa!");
        }
    }

    private void cancel(JTextField maNV, JTextField tenNV, JTextField chucVu, JTextField sdt) {
        maNV.setText("");
        if (tenNV != null) tenNV.setText("");
        if (chucVu != null) chucVu.setText("");
        if (sdt != null) sdt.setText("");
        maNV.setEditable(false);
        if (tenNV != null) tenNV.setEditable(false);
        if (chucVu != null) chucVu.setEditable(false);
        if (sdt != null) sdt.setEditable(false);
        table.clearSelection();
        refreshTable();
        for (JButton btn : new JButton[] { btn_add, btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_huy }) {
            btn.setVisible(true);
            btn.setBackground(new Color(0, 36, 107));
            btn.setForeground(Color.white);
        }
    }

    private boolean checkValidate(NhanVienDTO nv) {
        if (nv.getMaNV().trim().isEmpty() || nv.getHoTen().trim().isEmpty() || nv.getChucVu().trim().isEmpty()
                || nv.getSdt().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin!");
            return false;
        }
        if (nv.getHoTen().trim().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên không được nhiều hơn 100 ký tự!");
            return false;
        }
        if (!tool.checkPhoneNumber(nv.getSdt())) {
            return false;
        }
        for (NhanVienDTO existingNV : nhanVienList) {
            if (!existingNV.getMaNV().equals(nv.getMaNV()) && existingNV.getSdt().equals(nv.getSdt())) {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã được sử dụng!");
                return false;
            }
        }
        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}