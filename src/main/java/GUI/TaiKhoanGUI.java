package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;

public class TaiKhoanGUI {
    Tool tool = new Tool();
    JPanel panel, panel_searchCombo, panel_buttons, panel_Table, panel_textField;
    JButton btn_add, btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_huy;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    // private JComboBox<String> comboBox;
    // private JTextField[] txt_array_search = new JTextField[1];

    public TaiKhoanGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel_searchCombo = tool.createSearchTextField(width - width_sideMenu, height,
                new String[] { "Mã Nhân viên", "Tên Nhân viên" });
        panel_searchCombo.setBorder(BorderFactory.createEmptyBorder(5, 43, 0, 0));
        panel_buttons = tool.createPanel(180, height, new FlowLayout(0, 0, 20));
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        panel_textField = new JPanel(null);
        panel_textField.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        panel_textField.setPreferredSize(new Dimension(width - width_sideMenu, 200));
        panel_Table = tool.createPanel(width - width_sideMenu, height - 200, null);
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

        String[] columnNames = { "Mã Nhân viên", "Tên Nhân viên", "Chức vụ", "Số điện thoại" };
        NhanVienBUS nhanVienBUS = new NhanVienBUS();
        List<NhanVienDTO> nhanVienList = nhanVienBUS.getAllNhanVien();
        DefaultTableModel modelNhanVien = new DefaultTableModel(columnNames, 0);
        JTable table = tool.createTable(modelNhanVien, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(800, height - 280));
        table.getTableHeader().setBackground(new Color(0, 36, 107));
        table.getTableHeader().setForeground(Color.white);
        table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(true);
        panel_Table.setBorder(BorderFactory.createEmptyBorder(-2, 40, 0, 0));
        for (NhanVienDTO nv : nhanVienList) {
            modelNhanVien.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getChucVu(),
                    nv.getSdt()
            });
        }
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel_Table.add(scrollPane, BorderLayout.CENTER);

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

        table.addMouseListener(new MouseAdapter() {
            int lastSelectedRow = -1;

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
                } else {
                    lastSelectedRow = row;
                    textFieldMaNV.setText(table.getValueAt(row, 0).toString());
                    textFieldTenNV.setText(table.getValueAt(row, 1).toString());
                    textFieldChucVu.setText(table.getValueAt(row, 2).toString());
                    textFieldSoDienThoai.setText(table.getValueAt(row, 3).toString());
                }
            }
        });

        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = modelNhanVien.getRowCount();
                String newId;

                if (rowCount > 0) {
                    String lastId = modelNhanVien
                            .getValueAt(rowCount - 1, 0)
                            .toString(); // e.g. "NV05"
                    // separate prefix and number
                    String prefix = lastId.replaceAll("\\d+", ""); // "NV"
                    String digits = lastId.replaceAll("\\D+", ""); // "05"
                    int num = 1;
                    try {
                        num = Integer.parseInt(digits) + 1;
                    } catch (NumberFormatException ex) {
                        // fallback if something’s off
                        num = 1;
                    }
                    // rebuild with zero-padding to 2 digits
                    newId = String.format("%s%02d", prefix, num);
                } else {
                    // empty table → start at NV01
                    newId = "NV01";
                }

                textFieldMaNV.setText(newId);
                textFieldTenNV.setEditable(true);
                textFieldChucVu.setEditable(true);
                textFieldSoDienThoai.setEditable(true);
            }
        });

        // btn_edit.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseClicked(MouseEvent e) {
        // textFieldMaNV.setEditable(true);
        // textFieldTenNV.setEditable(true);
        // textFieldChucVu.setEditable(true);
        // textFieldSoDienThoai.setEditable(true);
        // }
        // });
        btn_delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    modelNhanVien.removeRow(row);
                    textFieldMaNV.setText("");
                    textFieldTenNV.setText("");
                    textFieldChucVu.setText("");
                    textFieldSoDienThoai.setText("");
                }
            }
        });
        panel_buttons.add(btn_add);
        panel_buttons.add(btn_edit);
        panel_buttons.add(btn_delete);
        panel_buttons.add(btn_nhapExcel);
        panel_buttons.add(btn_xuatExcel);
        panel_buttons.add(btn_huy);

        panel.add(panel_Table, BorderLayout.CENTER);
        panel.add(panel_buttons, BorderLayout.EAST);
        panel.add(panel_searchCombo, BorderLayout.NORTH);
        panel.add(panel_textField, BorderLayout.SOUTH);

    }

    public JPanel getPanel() {
        return this.panel;
    }
}
