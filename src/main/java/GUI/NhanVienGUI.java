package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    List<NhanVienDTO> nhanVienList;
    JTextField[] txt_array = new JTextField[6];
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JButton btn[] = new JButton[6];
    JTable table;
    NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    private int selectedRow = -1;
    private int lastSelectedRow = -1; // Lưu dòng được chọn trước đó
    private boolean update = false;
    private boolean add = false;
    private boolean delete = false;
    int count = 0;

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3)
            str = "0" + str;
        return "TG" + str;
    }

    public NhanVienGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        // birth_choose = new JCalendar();
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        panel.add(createNhanVienTable(), BorderLayout.WEST);

        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Chi tiết sản phẩm
        String txt_label[] = { "Mã NV", "Tên", "Địa chỉ", "Số điện thoại", "Chức vụ", "Ngày sinh" };
        panel.add(createPanelDetail(txt_array, txt_label), BorderLayout.SOUTH);
        for (int i = 0; i < txt_array.length; i++) {
            txt_array[i].setCursor(new Cursor(Cursor.TEXT_CURSOR));

        }

        panel.add(createSearchPanel(), BorderLayout.NORTH);
        timkiem();
    }

    private JPanel createNhanVienTable() {
        String column[] = { "Mã NV", "Họ Tên", "Chức vụ", "Địa chỉ", "Số điện thoại", "Ngày sinh" };
        DefaultTableModel model = new DefaultTableModel(column, 0);

        // Lấy dữ liệu từ cơ sở dữ liệu
        try {
            nhanVienList = nhanVienBUS.getAllNhanVien();
            for (NhanVienDTO nv : nhanVienList) {
                model.addRow(new Object[] {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getDiaChi(),
                        nv.getSdt(),
                        nv.getNgaySinh()
                });
                String maNV = nhanVienList.get(nhanVienList.size() - 1).getMaNV();
                String numericPart = maNV.substring(2);
                count = Integer.parseInt(numericPart) + 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }

        // Bảng
        table = tool.createTable(model, column);
        table.setDefaultEditor(Object.class, null); // Không cho chỉnh sửa trực tiếp trên bảng
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 440));

        // Thêm MouseListener cho bảng
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

                // Nếu click vào cùng một dòng đã chọn trước đó
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    // Hủy chọn dòng
                    table.clearSelection();
                    update = false;
                    delete = false;

                    // Reset dữ liệu trong các ô nhập
                    for (JTextField txt : txt_array) {
                        txt.setText("");
                        txt.setEditable(true);
                    }

                    lastSelectedRow = -1; // Reset chỉ số dòng
                } else if (selectedRow >= 0) {
                    // Click vào dòng mới
                    for (int i = 0; i < txt_array.length; i++) {
                        if (table.getValueAt(selectedRow, i) instanceof java.util.Date) {
                            java.util.Date date = (java.util.Date) table.getValueAt(selectedRow, i);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            txt_array[i].setText(sdf.format(date));
                            continue;
                        }
                        txt_array[i].setText((String) table.getValueAt(selectedRow, i));
                        txt_array[i].setEditable(false);
                    }
                    if (update == true) {
                        tool.Editable(txt_array, true);
                        txt_array[0].setEditable(false);
                    }
                    if (delete == true) {
                        tool.Editable(txt_array, false);
                    }

                    lastSelectedRow = selectedRow;
                }

            }
        });

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10)); // Top, Left, Bottom, Right

        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        // Panel chứa button
        String[] btn_txt = { "Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE, "y"));
        // panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Gắn sự kiện cho các nút
        btn[0].addActionListener(e -> addNhanVien());
        btn[1].addActionListener(e -> updateNhanVien());
        btn[2].addActionListener(e -> deleteNhanVien());
        btn[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createPanelDetail(JTextField[] txt_array, String[] txt_label) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850, 200, 0.5, 3, false);

        // JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // wrappedPanel.add(panelDetail);
        // wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        // return wrappedPanel;
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

    }

    private void filterTable(String query, String searchType) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
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

    // Phương thức làm mới bảng
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        // Lấy dữ liệu từ cơ sở dữ liệu
        try {
            nhanVienList = nhanVienBUS.getAllNhanVien();
            for (NhanVienDTO nv : nhanVienList) {
                model.addRow(new Object[] {
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getDiaChi(),
                        nv.getSdt(),
                        nv.getNgaySinh()
                });
                String maNV = nhanVienList.get(nhanVienList.size() - 1).getMaNV();
                String numericPart = maNV.substring(2);
                count = Integer.parseInt(numericPart) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Phương thức thêm nhân viên
    private void addNhanVien() {
        table.clearSelection();
        update = false;
        delete = false;
        if (add == false) {
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
                nv.setChucVu(txt_array[2].getText().trim());
                nv.setDiaChi(txt_array[3].getText().trim());
                nv.setSdt(txt_array[4].getText().trim());
                String startDate = txt_array[5].getText();
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date date = sdf1.parse(startDate);
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                nv.setNgaySinh(sqlStartDate);

                // bất hợp lí ở code 247

                if (!checkValidate(nv)) {
                    return;
                }

                if (nhanVienBUS.addNhanVien(nv)) {
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

    // Phương thức sửa nhân viên
    private void updateNhanVien() {
        if (add == true) {
            tool.clearFields(txt_array);
        }
        add = false;
        delete = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
        } else if (update == false) {
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
                nv.setChucVu(txt_array[2].getText().trim());
                nv.setDiaChi(txt_array[3].getText().trim());
                nv.setSdt(txt_array[4].getText().trim());
                String startDate = txt_array[5].getText();
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date date = sdf1.parse(startDate);
                java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
                nv.setNgaySinh(sqlStartDate);

                if (nv.getMaNV().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
                    return;
                }

                if (!checkValidate(nv)) {
                    return;
                }

                if (nhanVienBUS.updateNhanVien(nv)) {
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

    // Phương thức xóa nhân viên
    private void deleteNhanVien() {
        if (add == true) {
            tool.clearFields(txt_array);
        }
        add = false;
        update = false;
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!");
        }
        // else if (delete==false){
        // tool.clearButtons(btn);
        // tool.Editable(txt_array,false);
        // btn[2].setBackground(new Color(202, 220, 252));
        // btn[2].setForeground(Color.BLACK);
        // btn[5].setBackground(Color.RED);

        // // for (int i = 0, length = btn.length - 1; i < length; i++) {
        // // if (i != 0) {
        // // btn[i].setVisible(false);
        // // }
        // // }

        // delete=true;
        // }
        else {
            try {
                String maTG = txt_array[0].getText().trim();
                if (maTG.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?",
                        "Xóa thông tin nhân viên", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (nhanVienBUS.deleteNhanVien(maTG)) {
                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
                        cancel();
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
        // Kiểm tra dữ liệu đầu vào
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

        if (!nv.getNgaySinh().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                .isAfter(LocalDate.now().minusYears(18))) {
            JOptionPane.showMessageDialog(null, "Nhân viên phải trên 18 tuổi");
            return false;
        }
        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
