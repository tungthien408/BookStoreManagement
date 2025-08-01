package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.NXBBUS;
import DTO.NXBDTO;

public class NhaXuatBanGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    List<NXBDTO> nxbList;
    JTextField[] txt_array = new JTextField[4];
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int) (width * 0.625);
    JButton[] buttons = new JButton[6];
    JTable table;
    NXBBUS nxbBUS = new NXBBUS();
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private boolean update = false;
    private boolean add = false;
    private boolean delete = false;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;
    int count = 1; // Default to 1 for empty list

    public String getID() {
        String str = String.valueOf(count);
        while (str.length() != 3) {
            str = "0" + str;
        }
        return "NXB" + str;
    }

    public NhaXuatBanGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        panel.add(createNXBTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);
        String txt_label[] = { "Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại" };
        panel.add(createPanelDetail(txt_array, txt_label), BorderLayout.SOUTH);
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        timkiem();
    }

    private JPanel createNXBTable() {
        String column[] = { "Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại" };
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            nxbList = nxbBUS.getAllNhaXuatBan();
            for (NXBDTO nxb : nxbList) {
                model.addRow(new Object[] {
                        nxb.getMaNXB() != null ? nxb.getMaNXB() : "",
                        nxb.getTenNXB() != null ? nxb.getTenNXB() : "",
                        nxb.getDiaChi() != null ? nxb.getDiaChi() : "",
                        nxb.getSdt() != null ? nxb.getSdt() : ""
                });
            }
            if (!nxbList.isEmpty()) {
                String maNXB = nxbList.get(nxbList.size() - 1).getMaNXB();
                String numericPart = maNXB.substring(3);
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
        scrollPane.setPreferredSize(new Dimension(850, 540));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (add || update) {
                    return;
                }

                tool.clearFields(txt_array);
                tool.clearButtons(buttons);
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
                        Object value = table.getValueAt(selectedRow, i);
                        txt_array[i].setText(value != null ? value.toString() : "");
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
        String[] txt_btn = { "Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Hủy" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, txt_btn, new Color(0, 36, 107), Color.WHITE, "y"));
        for (int i = 0; i < txt_btn.length; i++) {
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttons[i].setFocusable(false);
        }
        buttons[0].addActionListener(e -> addNhaXuatBan());
        buttons[1].addActionListener(e -> updateNhaXuatBan());
        buttons[2].addActionListener(e -> deleteNhaXuatBan());
        buttons[3].addActionListener(e -> importExcel());
        buttons[4].addActionListener(e -> exportToExcel());
        buttons[5].addActionListener(e -> cancel());

        return panelBtn;
    }

    private JPanel createPanelDetail(JTextField[] txt_array, String[] txt_label) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850, 120, 0.5, 2, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã NXB", "Tên NXB", "SDT" };
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
            for (NXBDTO nxb : nxbList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã NXB":
                        match = nxb.getMaNXB() != null && nxb.getMaNXB().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên NXB":
                        match = nxb.getTenNXB() != null && nxb.getTenNXB().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "SDT":
                        match = nxb.getSdt() != null && nxb.getSdt().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            nxb.getMaNXB() != null ? nxb.getMaNXB() : "",
                            nxb.getTenNXB() != null ? nxb.getTenNXB() : "",
                            nxb.getDiaChi() != null ? nxb.getDiaChi() : "",
                            nxb.getSdt() != null ? nxb.getSdt() : ""
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
            nxbList = nxbBUS.getAllNhaXuatBan();
            for (NXBDTO nxb : nxbList) {
                model.addRow(new Object[] {
                        nxb.getMaNXB() != null ? nxb.getMaNXB() : "",
                        nxb.getTenNXB() != null ? nxb.getTenNXB() : "",
                        nxb.getDiaChi() != null ? nxb.getDiaChi() : "",
                        nxb.getSdt() != null ? nxb.getSdt() : ""
                });
            }
            if (!nxbList.isEmpty()) {
                String maNXB = nxbList.get(nxbList.size() - 1).getMaNXB();
                String numericPart = maNXB.substring(3);
                count = Integer.parseInt(numericPart) + 1;
            } else {
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    private void addNhaXuatBan() {
        table.clearSelection();
        update = false;
        delete = false;
        if (!add) {
            add = true;
            tool.clearFields(txt_array);
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            txt_array[0].setText(getID());
            txt_array[0].setEditable(false);
            buttons[0].setBackground(new Color(202, 220, 252));
            buttons[0].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(i == 0 || i == 5);
            }
        } else {
            try {
                NXBDTO nxb = new NXBDTO();
                nxb.setMaNXB(txt_array[0].getText().trim());
                nxb.setTenNXB(txt_array[1].getText().trim());
                nxb.setDiaChi(txt_array[2].getText().trim());
                nxb.setSdt(txt_array[3].getText().trim());
                nxb.setTrangThaiXoa(0);

                if (!checkValidate(nxb)) {
                    return;
                }

                if (nxbBUS.addNhaXuatBan(nxb)) {
                    JOptionPane.showMessageDialog(null, "Thêm nhà xuất bản thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm nhà xuất bản thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm nhà xuất bản: " + e.getMessage());
            }
        }
    }

    private void updateNhaXuatBan() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản để sửa!");
        } else if (!update) {
            add = false;
            delete = false;
            update = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, true);
            txt_array[0].setEditable(false);
            buttons[1].setBackground(new Color(202, 220, 252));
            buttons[1].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(i == 1 || i == 5);
            }
        } else {
            try {
                NXBDTO nxb = new NXBDTO();
                nxb.setMaNXB(txt_array[0].getText().trim());
                nxb.setTenNXB(txt_array[1].getText().trim());
                nxb.setDiaChi(txt_array[2].getText().trim());
                nxb.setSdt(txt_array[3].getText().trim());
                nxb.setTrangThaiXoa(0);

                if (nxb.getMaNXB().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã nhà xuất bản không được để trống!");
                    return;
                }

                if (!checkValidate(nxb)) {
                    return;
                }

                if (nxbBUS.updateNhaXuatBan(nxb)) {
                    JOptionPane.showMessageDialog(null, "Sửa nhà xuất bản thành công!");
                    cancel();
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa nhà xuất bản thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa nhà xuất bản: " + e.getMessage());
            }
        }
    }

    private void deleteNhaXuatBan() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà xuất bản để xóa!");
        } else if (!delete) {
            add = false;
            update = false;
            delete = true;
            tool.clearButtons(buttons);
            tool.Editable(txt_array, false);
            buttons[2].setBackground(new Color(202, 220, 252));
            buttons[2].setForeground(Color.BLACK);
            buttons[5].setBackground(Color.RED);

            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(i == 2 || i == 5);
            }
        } else {
            try {
                String maNXB = txt_array[0].getText().trim();
                if (maNXB.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã nhà xuất bản không được để trống!");
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhà xuất bản này?",
                        "Xóa thông tin nhà xuất bản", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (nxbBUS.deleteNhaXuatBan(maNXB)) {
                        JOptionPane.showMessageDialog(null, "Xóa nhà xuất bản thành công!");
                        cancel();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa nhà xuất bản thất bại!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhà xuất bản: " + e.getMessage());
            }
        }
    }

    private void importExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel chưa được triển khai!");
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        fileChooser.setDialogTitle("Lưu Phiếu Nhập dưới dạng Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_NXB.xlsx"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Danh sách Nhà Xuất Bản");

                // Tạo header
                XSSFRow headerRow = sheet.createRow(0);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    XSSFCell cell = headerRow.createCell(col);
                    cell.setCellValue(model.getColumnName(col));
                }

                // Ghi dữ liệu
                for (int row = 0; row < model.getRowCount(); row++) {
                    XSSFRow excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        XSSFCell cell = excelRow.createCell(col);
                        Object value = model.getValueAt(row, col);
                        if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue());
                        } else {
                            cell.setCellValue(value != null ? value.toString() : "");
                        }
                    }
                }

                // Tự động điều chỉnh độ rộng cột
                for (int col = 0; col < model.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }

                // Ghi file xuống đĩa
                try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                    workbook.write(fos);
                }

                JOptionPane.showMessageDialog(null,
                        "Xuất Excel thành công! File được lưu tại: " + fileToSave.getAbsolutePath(),
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Lỗi khi xuất Excel: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            Desktop.getDesktop().open(fileChooser.getSelectedFile());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi mở file: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        add = false;
        update = false;
        delete = false;
        refreshTable();
        tool.clearButtons(buttons);
        tool.clearFields(txt_array);
        tool.Editable(txt_array, false);
        selectedRow = -1;
        lastSelectedRow = -1;
    }

    private boolean checkValidate(NXBDTO nxb) {
        if (nxb.getMaNXB().isEmpty() || nxb.getTenNXB().isEmpty() || nxb.getDiaChi().isEmpty()
                || nxb.getSdt().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin");
            return false;
        }

        if (!isValidName(nxb.getTenNXB())) {
            JOptionPane.showMessageDialog(null,
                    "Tên nhà xuất bản chỉ được chứa chữ cái, số, khoảng trắng, dấu gạch ngang hoặc dấu nháy đơn, không chứa ký tự đặc biệt khác");
            return false;
        }

        if (nxb.getTenNXB().length() > 100) {
            JOptionPane.showMessageDialog(null, "Tên nhà xuất bản không được nhiều hơn 100 ký tự");
            return false;
        }

        if (nxb.getDiaChi().length() > 255) {
            JOptionPane.showMessageDialog(null, "Địa chỉ nhà xuất bản không được nhiều hơn 255 ký tự");
            return false;
        }

        if (!tool.checkPhoneNumber(nxb.getSdt())) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
            return false;
        }

        for (NXBDTO nxban : nxbList) {
            if (!nxban.getMaNXB().equals(nxb.getMaNXB()) &&
                    nxban.getSdt().toLowerCase().equals(nxb.getSdt().toLowerCase())) {
                JOptionPane.showMessageDialog(null, "Số điện thoại đã được sử dụng");
                return false;
            }
        }

        return true;
    }

    private boolean isValidName(String name) {
        return name.matches("^[\\p{L}\\p{N}\\s'-]+$");
    }

    public JPanel getPanel() {
        return this.panel;
    }
}