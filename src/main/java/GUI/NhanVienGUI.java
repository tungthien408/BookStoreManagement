package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;

public class NhanVienGUI {
    private Tool tool = new Tool();
    private JPanel panel, panelDetail;
    private List<NhanVienDTO> nhanVienList;
    private JTextField[] txt_array = new JTextField[6];
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int) (width * 0.625);
    private JButton btn[] = new JButton[6];
    private JTable table;
    private NhanVienBUS nhanVienBus = new NhanVienBUS();
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean update = false;
    private boolean add = false;
    private boolean delete = false;
    private int count = 0;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3) {
            str = "0" + str;
        }
        return "NV" + str; // Fixed prefix to "NV" for consistency with MaNV
    }

    public NhanVienGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[]{txt_search};
        for (int i = 0; i < txt_array.length; i++) {
            txt_array[i] = new JTextField();
            txt_array[i].setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }

        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createNhanVienTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);

        String[] txt_label = { "Mã NV", "Tên", "Địa chỉ", "Số điện thoại", "Chức vụ", "Ngày sinh" };
        panel.add(createPanelDetail(txt_array, txt_label), BorderLayout.SOUTH);

        timkiem();
    }

    private JPanel createNhanVienTable() {
        String[] column = { "Mã NV", "Họ Tên", "Chức vụ", "Địa chỉ", "Số điện thoại", "Ngày sinh" };
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            nhanVienList = nhanVienBus.getAllNhanVien();
            for (NhanVienDTO nv : nhanVienList) {
                model.addRow(new Object[] {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getDiaChi(),
                        nv.getSdt(),
                        nv.getNgaySinh()
                });
            }
            if (!nhanVienList.isEmpty()) {
                String maNV = nhanVienList.get(nhanVienList.size() - 1).getMaNV();
                String numericPart = maNV.substring(2);
                count = Integer.parseInt(numericPart) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        table = tool.createTable(model, column);
        table.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 440));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
                tool.clearButtons(btn);
                add = false;

                selectedRow = table.getSelectedRow();

                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table.clearSelection();
                    update = false;
                    delete = false;

                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }

                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    for (int i = 0; i < txt_array.length; i++) {
                        if (table.getValueAt(selectedRow, i) instanceof java.util.Date) {
                            java.util.Date date = (java.util.Date) table.getValueAt(selectedRow, i);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            txt_array[i].setText(sdf.format(date));
                            continue;
                        }
                        txt_array[i].setText(String.valueOf(table.getValueAt(selectedRow, i)));
                        txt_array[i].setEditable(false);
                    }
                    if (update) {
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false);
                    }
                    if (delete) {
                        tool.Editable(txt_array, false);
                    }

                    lastSelectedRow = selectedRow;
                }
            }
        });

        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] btn_txt = { "Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE, "y"));

        btn[0].addActionListener(e -> addNhanVien());
        btn[1].addActionListener(e -> updateNhanVien());
        btn[2].addActionListener(e -> deleteNhanVien());
        btn[3].addActionListener(e -> importExcel());
        btn[4].addActionListener(e -> exportExcel());
        btn[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createPanelDetail(JTextField[] txt_array, String[] txt_label) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850, 200, 0.5, 3, false);
        return panelDetail;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã nhân viên", "Tên nhân viên", "SDT" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
    }

    private void timkiem() {
        comboBox.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            filterTable(txt_array_search[0].getText(), selectedOption);
        });

        // Add real-time search
        txt_array_search[0].addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                filterTable(txt_array_search[0].getText(), selectedOption);
            }
        });
    }

    private void filterTable(String query, String searchType) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            for (NhanVienDTO nv : nhanVienList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã nhân viên":
                        match = nv.getMaNV().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên nhân viên":
                        match = nv.getHoTen().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "SDT":
                        match = nv.getSdt().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            nv.getMaNV(),
                            nv.getHoTen(),
                            nv.getChucVu(),
                            nv.getDiaChi(),
                            nv.getSdt(),
                            nv.getNgaySinh()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        try {
            nhanVienList = nhanVienBus.getAllNhanVien();
            for (NhanVienDTO nv : nhanVienList) {
                model.addRow(new Object[] {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getDiaChi(),
                        nv.getSdt(),
                        nv.getNgaySinh()
                });
            }
            if (!nhanVienList.isEmpty()) {
                String maNV = nhanVienList.get(nhanVienList.size() - 1).getMaNV();
                String numericPart = maNV.substring(2);
                count = Integer.parseInt(numericPart) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private void addNhanVien() {
        table.clearSelection();
        update = false;
        delete = false;
        if (!add) {
            add = true;
            tool.clearFields(txt_array);
            tool.clearButtons(btn);
            tool.Editable(txt_array, true);
            txt_array[0].setText(getID());
            btn[0].setBackground(new Color(202, 220, 252));
            btn[0].setForeground(Color.BLACK);
            btn[5].setBackground(Color.RED);

            for (int i = 0, length = btn.length - 1; i < length; i++) {
                if (i != 0) {
                    btn[i].setVisible(false);
                }
            }
            txt_array[0].setEditable(false);
        } else {
            try {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(txt_array[0].getText().trim());
                nv.setHoTen(txt_array[1].getText().trim());
                nv.setDiaChi(txt_array[2].getText().trim());
                nv.setSdt(txt_array[3].getText().trim());
                nv.setChucVu(txt_array[4].getText().trim());

                String startDate = txt_array[5].getText().trim();
                if (startDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ngày sinh không được để trống!");
                    return;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.setLenient(false);
                java.util.Date date;
                try {
                    date = sdf.parse(startDate);
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(null, "Ngày sinh phải có định dạng dd-MM-yyyy (ví dụ: 25-12-2000)");
                    return;
                }
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                nv.setNgaySinh(sqlStartDate);

                if (!checkValidate(nv)) {
                    return;
                }

                if (nhanVienBus.addNhanVien(nv)) {
                    JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhân viên: " + e.getMessage());
            }
        }
    }

    private void updateNhanVien() {
        if (add) {
            tool.clearFields(txt_array);
        }
        add = false;
        delete = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
        } else if (!update) {
            update = true;
            tool.clearButtons(btn);
            tool.Editable(txt_array, true);
            tool.clearButtons(btn);

            btn[1].setBackground(new Color(202, 220, 252));
            btn[1].setForeground(Color.BLACK);
            btn[5].setBackground(Color.RED);

            for (int i = 0, length = btn.length - 1; i < length; i++) {
                if (i != 1) {
                    btn[i].setVisible(false);
                }
            }

            txt_array[0].setEditable(false);
        } else {
            try {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(txt_array[0].getText().trim());
                nv.setHoTen(txt_array[1].getText().trim());
                nv.setDiaChi(txt_array[2].getText().trim());
                nv.setSdt(txt_array[3].getText().trim());
                nv.setChucVu(txt_array[4].getText().trim());

                String startDate = txt_array[5].getText().trim();
                if (startDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ngày sinh không được để trống!");
                    return;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.setLenient(false);
                java.util.Date date;
                try {
                    date = sdf.parse(startDate);
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(null, "Ngày sinh phải có định dạng dd-MM-yyyy (ví dụ: 25-12-2000)");
                    return;
                }
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                nv.setNgaySinh(sqlStartDate);

                if (nv.getMaNV().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
                    return;
                }

                if (!checkValidate(nv)) {
                    return;
                }

                if (nhanVienBus.updateNhanVien(nv)) {
                    JOptionPane.showMessageDialog(null, "Sửa nhân viên thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa nhân viên thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa nhân viên: " + e.getMessage());
            }
        }
    }

    private void deleteNhanVien() {
        if (add) {
            tool.clearFields(txt_array);
        }
        add = false;
        update = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa!");
        } else if (!delete) {
            tool.clearButtons(btn);
            tool.Editable(txt_array, false);
            btn[2].setBackground(new Color(202, 220, 252));
            btn[2].setForeground(Color.BLACK);
            btn[5].setBackground(Color.RED);

            for (int i = 0, length = btn.length - 1; i < length; i++) {
                if (i != 2) {
                    btn[i].setVisible(false);
                }
            }

            delete = true;
        } else {
            try {
                String maNV = txt_array[0].getText().trim();
                if (maNV.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?",
                        "Xóa thông tin nhân viên", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (nhanVienBus.deleteNhanVien(maNV)) {
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
                        cancel();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên: " + e.getMessage());
            }
        }
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(btn);
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(NhanVienDTO nv) {
        if (nv.getMaNV().trim().isEmpty() || nv.getHoTen().trim().isEmpty() || nv.getChucVu().trim().isEmpty()
                || nv.getDiaChi().trim().isEmpty() || nv.getSdt().trim().isEmpty() || nv.getNgaySinh() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin");
            return false;
        }

        if (nv.getHoTen().trim().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên nhân viên không được nhiều hơn 100 ký tự");
            return false;
        }

        if (nv.getDiaChi().trim().length() > 255) {
            JOptionPane.showMessageDialog(null, "Địa chỉ nhân viên không được nhiều hơn 255 ký tự");
            return false;
        }

        if (!tool.checkPhoneNumber(nv.getSdt())) {
            return false;
        }

        for (NhanVienDTO nvien : nhanVienList) {
            if (!nvien.getMaNV().equals(nv.getMaNV()) && nv.getSdt().equals(nvien.getSdt())) {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã được sử dụng");
                return false;
            }
        }

        java.util.Date today = new java.util.Date();
        java.util.Date eighteenYearsAgo = new java.util.Date(today.getTime() - (18L * 365 * 24 * 60 * 60 * 1000));
        if (nv.getNgaySinh().after(eighteenYearsAgo)) {
            JOptionPane.showMessageDialog(null, "Nhân viên phải trên 18 tuổi");
            return false;
        }

        return true;
    }

    private void importExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel chưa được triển khai!");
    }

    private void exportExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel chưa được triển khai!");
    }

    public JPanel getPanel() {
        return this.panel;
    }
}