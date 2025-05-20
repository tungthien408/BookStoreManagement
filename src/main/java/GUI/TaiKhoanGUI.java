package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.NhanVienBUS;
import BUS.QuyenBUS;
import BUS.TaiKhoanNVBUS;
import DTO.NhanVienDTO;
import DTO.QuyenDTO;
import DTO.TaiKhoanNVDTO;

public class TaiKhoanGUI {
    private Tool Tool = new Tool();
    private JPanel panel, panel_searchCombo, panel_buttons, panel_Table, panel_textField;
    private JButton btn_add, btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_huy, btn_viewDetails;
    private int width = 1200;
    private int width_sideMenu = 151;
    private int height = (int) (width * 0.625);
    private JComboBox<String> comboBox;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTable table;
    private DefaultTableModel modelTaiKhoan;
    private List<NhanVienDTO> nhanVienList;
    private List<TaiKhoanNVDTO> taiKhoanList;
    private TaiKhoanNVBUS taiKhoanNVBUS = new TaiKhoanNVBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private QuyenBUS quyenBUS = new QuyenBUS();
    private JTextField textFieldMaNV, textFieldMatKhau, textFieldChucVu, textFieldMaQuyen, textFieldTenQuyen;
    private boolean isAdding = false; // Track add mode

    public TaiKhoanGUI() {
        panel = Tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        // Initialize search components
        txt_array_search[0] = new JTextField();
        panel_searchCombo = Tool.createSearchTextField(width - width_sideMenu, height,
                new String[] { "Mã Nhân viên", "Chức vụ", "Mã quyền" });
        panel_searchCombo.setBorder(BorderFactory.createEmptyBorder(5, 43, 0, 0));
        for (java.awt.Component comp : panel_searchCombo.getComponents()) {
            if (comp instanceof JComboBox) {
                comboBox = (JComboBox<String>) comp;
            } else if (comp instanceof JTextField) {
                txt_array_search[0] = (JTextField) comp;
            }
        }

        panel_buttons = Tool.createPanel(180, height, new FlowLayout(0, 0, 20));
        panel_buttons.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        panel_textField = new JPanel(null);
        panel_textField.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        panel_textField.setPreferredSize(new Dimension(width - width_sideMenu, 200));
        panel_Table = Tool.createPanel(width - width_sideMenu, height - 200, new BorderLayout());
        panel_Table.setBorder(BorderFactory.createEmptyBorder(-2, 40, 0, 0));

        // Text fields and labels
        JLabel labelMaNV = new JLabel("Mã Nhân viên:");
        JLabel labelMatKhau = new JLabel("Mật khẩu:");
        JLabel labelChucVu = new JLabel("Chức vụ:");
        JLabel labelMaQuyen = new JLabel("Mã quyền:");
        JLabel labelTenQuyen = new JLabel("Tên quyền:");

        textFieldMaNV = new JTextField();
        textFieldMatKhau = new JTextField();
        textFieldChucVu = new JTextField();
        textFieldMaQuyen = new JTextField();
        textFieldTenQuyen = new JTextField();

        labelMaNV.setBounds(181, 30, 100, 30);
        labelMatKhau.setBounds(500, 30, 100, 30);
        labelChucVu.setBounds(181, 80, 100, 30);
        labelMaQuyen.setBounds(500, 80, 100, 30);
        labelTenQuyen.setBounds(181, 130, 100, 30);

        textFieldMaNV.setBounds(270, 30, 200, 27);
        textFieldMatKhau.setBounds(594, 30, 200, 27);
        textFieldChucVu.setBounds(270, 80, 200, 27);
        textFieldMaQuyen.setBounds(594, 80, 200, 27);
        textFieldTenQuyen.setBounds(270, 130, 200, 27);

        textFieldMaNV.setBackground(new Color(202, 220, 252));
        textFieldMatKhau.setBackground(new Color(202, 220, 252));
        textFieldChucVu.setBackground(new Color(202, 220, 252));
        textFieldMaQuyen.setBackground(new Color(202, 220, 252));
        textFieldTenQuyen.setBackground(new Color(202, 220, 252));

        textFieldMaNV.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldMatKhau.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldChucVu.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldMaQuyen.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        textFieldTenQuyen.setCursor(new Cursor(Cursor.TEXT_CURSOR));

        textFieldMaNV.setEditable(false);
        textFieldMatKhau.setEditable(false);
        textFieldChucVu.setEditable(false);
        textFieldMaQuyen.setEditable(false);
        textFieldTenQuyen.setEditable(false);

        panel_textField.add(labelMaNV);
        panel_textField.add(labelMatKhau);
        panel_textField.add(labelChucVu);
        panel_textField.add(labelMaQuyen);
        panel_textField.add(labelTenQuyen);
        panel_textField.add(textFieldMaNV);
        panel_textField.add(textFieldMatKhau);
        panel_textField.add(textFieldChucVu);
        panel_textField.add(textFieldMaQuyen);
        panel_textField.add(textFieldTenQuyen);

        // Add DocumentListener to update Tên quyền when Mã quyền changes
        textFieldMaQuyen.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTenQuyen();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTenQuyen();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTenQuyen();
            }

            private void updateTenQuyen() {
                String maQuyen = textFieldMaQuyen.getText().trim();
                if (!maQuyen.isEmpty()) {
                    QuyenDTO quyen = quyenBUS.getQuyenById(maQuyen);
                    textFieldTenQuyen.setText(quyen != null ? quyen.getTenQuyen() : "");
                } else {
                    textFieldTenQuyen.setText("");
                }
            }
        });

        // Add DocumentListener to update Chức vụ when Mã nhân viên changes
        textFieldMaNV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateChucVu();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateChucVu();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateChucVu();
            }

            private void updateChucVu() {
                String maNV = textFieldMaNV.getText().trim();
                if (!maNV.isEmpty()) {
                    NhanVienDTO nv = nhanVienBUS.getNhanVienByMaNV(maNV);
                    textFieldChucVu.setText(nv != null ? nv.getChucVu() : "");
                } else {
                    textFieldChucVu.setText("");
                }
            }
        });

        // Table setup
        String[] columnNames = { "Mã Nhân viên", "Mật khẩu", "Chức vụ", "Mã quyền", "Tên quyền" };
        taiKhoanList = taiKhoanNVBUS.getAllTaiKhoan();
        nhanVienList = nhanVienBUS.getAllNhanVien();
        modelTaiKhoan = new DefaultTableModel(columnNames, 0);
        table = Tool.createTable(modelTaiKhoan, columnNames);
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
        btn_viewDetails = new JButton("Xem chi tiết");
        // Cursor
        btn_add.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_nhapExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_xuatExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_huy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_viewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Set button properties
        btn_add.setFocusable(false);
        btn_edit.setFocusable(false);
        btn_delete.setFocusable(false);
        btn_nhapExcel.setFocusable(false);
        btn_xuatExcel.setFocusable(false);
        btn_huy.setFocusable(false);
        btn_viewDetails.setFocusable(false);
        JButton[] buttons = { btn_add, btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_huy, btn_viewDetails };
        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(130, 30));
            btn.setBackground(new Color(0, 36, 107));
            btn.setForeground(Color.white);
            btn.setFocusable(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel_buttons.add(btn);
        }

        // Table mouse listener
        table.addMouseListener(new MouseAdapter() {
            private int lastSelectedRow = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isAdding) {
                    return; // Prevent table interaction during add mode
                }
                int row = table.getSelectedRow();
                if (row == lastSelectedRow && row >= 0) {
                    table.clearSelection();
                    lastSelectedRow = -1;
                    textFieldMaNV.setText("");
                    textFieldMatKhau.setText("");
                    textFieldChucVu.setText("");
                    textFieldMaQuyen.setText("");
                    textFieldTenQuyen.setText("");
                } else if (row >= 0) {
                    lastSelectedRow = row;
                    textFieldMaNV.setText(table.getValueAt(row, 0).toString());
                    textFieldMatKhau.setText(table.getValueAt(row, 1).toString());
                    textFieldChucVu.setText(table.getValueAt(row, 2).toString());
                    textFieldMaQuyen.setText(table.getValueAt(row, 3).toString());
                    textFieldTenQuyen.setText(table.getValueAt(row, 4).toString());
                }
            }
        });

        // Button action listeners
        btn_add.addActionListener(e -> addTaiKhoan(textFieldMaNV, textFieldMatKhau, textFieldChucVu, textFieldMaQuyen,
                textFieldTenQuyen));
        btn_edit.addActionListener(e -> editTaiKhoan(textFieldMaNV, textFieldMatKhau, textFieldChucVu, textFieldMaQuyen,
                textFieldTenQuyen));
        btn_delete.addActionListener(e -> deleteTaiKhoan(textFieldMaNV));
        btn_nhapExcel.addActionListener(
                e -> JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel chưa được triển khai!"));
        btn_xuatExcel.addActionListener(
                e -> exportToExcel());
        btn_huy.addActionListener(
                e -> cancel(textFieldMaNV, textFieldMatKhau, textFieldChucVu, textFieldMaQuyen, textFieldTenQuyen));
        btn_viewDetails.addActionListener(e -> viewDetails());

        panel.add(panel_searchCombo, BorderLayout.NORTH);
        panel.add(panel_Table, BorderLayout.CENTER);
        panel.add(panel_buttons, BorderLayout.EAST);
        panel.add(panel_textField, BorderLayout.SOUTH);

        timkiem();
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        fileChooser.setDialogTitle("Lưu Phiếu Nhập dưới dạng Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_Tai_Khoan.xlsx"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Danh sách Tài Khoản");

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

    private void timkiem() {
        if (comboBox != null && txt_array_search[0] != null) {
            txt_array_search[0].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem());
                }
            });

            comboBox.addActionListener(
                    e -> filterTable(txt_array_search[0].getText(), (String) comboBox.getSelectedItem()));
        }
    }

    private void filterTable(String query, String searchType) {
        modelTaiKhoan.setRowCount(0);
        try {
            for (TaiKhoanNVDTO tk : taiKhoanList) {
                NhanVienDTO nv = nhanVienBUS.getNhanVienByMaNV(tk.getMaNV());
                QuyenDTO quyen = quyenBUS.getQuyenById(tk.getMaQuyen());
                String chucVu = nv != null ? nv.getChucVu() : "";
                String tenQuyen = quyen != null ? quyen.getTenQuyen() : "";
                boolean match = false;
                switch (searchType) {
                    case "Mã Nhân viên":
                        match = tk.getMaNV().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Chức vụ":
                        match = chucVu.toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Mã quyền":
                        match = tk.getMaQuyen().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    modelTaiKhoan.addRow(new Object[] {
                            tk.getMaNV(),
                            tk.getMatKhau(),
                            chucVu,
                            tk.getMaQuyen(),
                            tenQuyen
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private void refreshTable() {
        modelTaiKhoan.setRowCount(0);
        try {
            taiKhoanList = taiKhoanNVBUS.getAllTaiKhoan();
            nhanVienList = nhanVienBUS.getAllNhanVien();
            for (TaiKhoanNVDTO tk : taiKhoanList) {
                NhanVienDTO nv = nhanVienBUS.getNhanVienByMaNV(tk.getMaNV());
                QuyenDTO quyen = quyenBUS.getQuyenById(tk.getMaQuyen());
                String chucVu = nv != null ? nv.getChucVu() : "";
                String tenQuyen = quyen != null ? quyen.getTenQuyen() : "";
                modelTaiKhoan.addRow(new Object[] {
                        tk.getMaNV(),
                        tk.getMatKhau(),
                        chucVu,
                        tk.getMaQuyen(),
                        tenQuyen
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    private void viewDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản để xem chi tiết!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String maNV = table.getValueAt(selectedRow, 0).toString();
        String matKhau = table.getValueAt(selectedRow, 1).toString();
        String chucVu = table.getValueAt(selectedRow, 2).toString();
        String maQuyen = table.getValueAt(selectedRow, 3).toString();
        String tenQuyen = table.getValueAt(selectedRow, 4).toString();
        TaiKhoanNVDTO tk = taiKhoanNVBUS.getTaiKhoanById(maNV);
        String trangThaiXoa = tk != null ? (tk.getTrangThaiXoa() == 0 ? "Hoạt động" : "Đã xóa") : "N/A";

        // Create image
        int imgWidth = 900;
        int imgHeight = 600;
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();

        // Set background and font
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, imgHeight);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 18));

        // Draw header
        g2d.drawString("THÔNG TIN TÀI KHOẢN NHÂN VIÊN", imgWidth / 2 - 150, 30);
        g2d.drawString("CỬA HÀNG BÁN SÁCH", 50, 50);
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        g2d.drawString("Địa chỉ: 123 Đường Sách, Quận 1, TP.HCM", 50, 70);
        g2d.drawString("ĐT: 0123-456-789", 50, 90);
        g2d.drawLine(50, 100, imgWidth - 50, 100);

        // Draw account details
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 16));
        g2d.drawString("Mã Nhân viên:", 50, 130);
        g2d.drawString("Mật khẩu:", 50, 160);
        g2d.drawString("Chức vụ:", 50, 190);
        g2d.drawString("Mã quyền:", 50, 220);
        g2d.drawString("Tên quyền:", 50, 250);
        g2d.drawString("Trạng thái:", 50, 280);

        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        g2d.drawString(maNV, 200, 130);
        g2d.drawString(matKhau, 200, 160);
        g2d.drawString(chucVu, 200, 190);
        g2d.drawString(maQuyen, 200, 220);
        g2d.drawString(tenQuyen, 200, 250);
        g2d.drawString(trangThaiXoa, 200, 280);

        // Draw footer
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        LocalDate date = LocalDate.now();
        g2d.drawString(String.format("Ngày %02d Tháng %02d Năm %04d",
                date.getDayOfMonth(), date.getMonthValue(), date.getYear()), 500, 500);
        g2d.drawString("QUẢN LÝ", 150, 540);
        g2d.drawString("(Ký và ghi rõ họ tên)", 150, 560);

        g2d.dispose();

        // Save and display
        try {
            File outputDir = new File("TaiKhoanNV");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File outputFile = new File(outputDir, "Tai_Khoan_" + maNV + ".png");
            ImageIO.write(img, "png", outputFile);

            JFrame frame = new JFrame("Thông tin tài khoản - " + maNV);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(imgWidth, imgHeight);
            frame.setLocationRelativeTo(null);
            JLabel label = new JLabel(new ImageIcon(img));
            frame.add(label);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu hoặc hiển thị thông tin: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTaiKhoan(JTextField maNV, JTextField matKhau, JTextField chucVu, JTextField maQuyen,
            JTextField tenQuyen) {
        if (!isAdding) {
            isAdding = true;
            maNV.setEditable(true);
            matKhau.setEditable(true);
            maQuyen.setEditable(true);
            // ChucVu and TenQuyen remain non-editable
            maNV.setText("");
            matKhau.setText("");
            chucVu.setText("");
            maQuyen.setText("");
            tenQuyen.setText("");
            btn_add.setBackground(new Color(202, 220, 252));
            btn_add.setForeground(Color.BLACK);
            btn_huy.setBackground(Color.RED);
            for (JButton btn : new JButton[] { btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_viewDetails }) {
                btn.setVisible(false);
            }
        } else {
            try {
                TaiKhoanNVDTO tk = new TaiKhoanNVDTO();
                tk.setMaNV(maNV.getText().trim());
                tk.setMatKhau(matKhau.getText().trim());
                tk.setMaQuyen(maQuyen.getText().trim());
                tk.setTrangThaiXoa(0);
                if (!checkValidate(tk)) {
                    return;
                }
                if (taiKhoanNVBUS.addTaiKhoan(tk)) {
                    JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công!");
                    cancel(maNV, matKhau, chucVu, maQuyen, tenQuyen);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi thêm tài khoản: " + e.getMessage());
            }
        }
    }

    private void editTaiKhoan(JTextField maNV, JTextField matKhau, JTextField chucVu, JTextField maQuyen,
            JTextField tenQuyen) {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để sửa!");
        } else if (!matKhau.isEditable()) {
            matKhau.setEditable(true);
            maQuyen.setEditable(true);

            btn_edit.setBackground(new Color(202, 220, 252));
            btn_edit.setForeground(Color.BLACK);
            btn_huy.setBackground(Color.RED);
            for (JButton btn : new JButton[] { btn_add, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_viewDetails }) {
                btn.setVisible(false);
            }
        } else {
            try {
                TaiKhoanNVDTO tk = new TaiKhoanNVDTO();
                tk.setMaNV(maNV.getText().trim());
                tk.setMatKhau(matKhau.getText().trim());
                tk.setMaQuyen(maQuyen.getText().trim());
                tk.setTrangThaiXoa(taiKhoanNVBUS.getTaiKhoanById(tk.getMaNV()).getTrangThaiXoa());
                if (!checkValidate(tk)) {
                    return;
                }
                if (taiKhoanNVBUS.updateTaiKhoan(tk)) {
                    JOptionPane.showMessageDialog(null, "Sửa tài khoản thành công!");
                    cancel(maNV, matKhau, chucVu, maQuyen, tenQuyen);
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa tài khoản thất bại!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi sửa tài khoản: " + e.getMessage());
            }
        }
    }

    private void deleteTaiKhoan(JTextField maNV) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tài khoản này?", "Xóa tài khoản",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    String maNVText = maNV.getText().trim();
                    if (taiKhoanNVBUS.deleteTaiKhoan(maNVText)) {
                        JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
                        cancel(maNV, null, null, null, null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa tài khoản thất bại!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi xóa tài khoản: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản để xóa!");
        }
    }

    private void cancel(JTextField maNV, JTextField matKhau, JTextField chucVu, JTextField maQuyen,
            JTextField tenQuyen) {
        isAdding = false;
        maNV.setText("");
        if (matKhau != null)
            matKhau.setText("");
        if (chucVu != null)
            chucVu.setText("");
        if (maQuyen != null)
            maQuyen.setText("");
        if (tenQuyen != null)
            tenQuyen.setText("");
        maNV.setEditable(false);
        if (matKhau != null)
            matKhau.setEditable(false);
        if (chucVu != null)
            chucVu.setEditable(false);
        if (maQuyen != null)
            maQuyen.setEditable(false);
        if (tenQuyen != null)
            tenQuyen.setEditable(false);
        table.clearSelection();
        refreshTable();
        for (JButton btn : new JButton[] { btn_add, btn_edit, btn_delete, btn_nhapExcel, btn_xuatExcel, btn_huy,
                btn_viewDetails }) {
            btn.setVisible(true);
            btn.setBackground(new Color(0, 36, 107));
            btn.setForeground(Color.white);
        }
    }

    private boolean checkValidate(TaiKhoanNVDTO tk) {
        if (tk.getMaNV().trim().isEmpty() || tk.getMatKhau().trim().isEmpty() || tk.getMaQuyen().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các trường thông tin!");
            return false;
        }
        if (tk.getMatKhau().trim().length() > 50) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không được nhiều hơn 50 ký tự!");
            return false;
        }
        if (tk.getMaQuyen().trim().length() > 50) {
            JOptionPane.showMessageDialog(null, "Mã quyền không được nhiều hơn 50 ký tự!");
            return false;
        }
        // Check if maNV exists in nhanVienList
        boolean maNVExists = false;
        for (NhanVienDTO nv : nhanVienList) {
            if (nv.getMaNV().equals(tk.getMaNV())) {
                maNVExists = true;
                break;
            }
        }
        if (!maNVExists) {
            JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
            return false;
        }
        // Check if maQuyen exists
        QuyenDTO quyen = quyenBUS.getQuyenById(tk.getMaQuyen());
        if (quyen == null) {
            JOptionPane.showMessageDialog(null, "Mã quyền không tồn tại!");
            return false;
        }
        // Check for duplicate maNV in taiKhoanList (except for current record)
        for (TaiKhoanNVDTO existingTK : taiKhoanList) {
            if (!existingTK.getMaNV().equals(tk.getMaNV()) && existingTK.getMaNV().equals(tk.getMaNV())) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên đã được sử dụng cho tài khoản khác!");
                return false;
            }
        }
        return true;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}