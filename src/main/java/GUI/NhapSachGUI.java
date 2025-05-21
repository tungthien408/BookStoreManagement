package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietPhieuNhapBUS;
import BUS.NXBBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.TaiKhoanNVDTO;

public class NhapSachGUI {
    private Tool tool = new Tool();
    private JPanel panel, paymentPanel;
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);

    private JTextField[] txt_array_top = new JTextField[5];
    private JTextField[] txt_array_down = new JTextField[2];
    private JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total;
    private JTextField txt_name, txt_quantity;
    private JTextField txt_search;
    private JTextField[] txt_array_search;
    private JButton[] buttons = new JButton[3];
    private JTable table_down, table_top;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private JComboBox<String> comboBox;
    String[] txt_label = { "Mã Sách", "Số lượng" };

    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private int count = 0;

    private List<SachDTO> sachList;
    private List<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;
    private List<PhieuNhapDTO> phieuNhapList;

    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private NXBBUS nhaXuatBanBUS = new NXBBUS();
    private SachBUS sachBUS = new SachBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
    private NhanVienDTO nv;

    public NhapSachGUI(TaiKhoanNVDTO account) {
        nv = nhanVienBUS.getNhanVienByMaNV(account.getMaNV());
        initializeTextFields();
        initializeMainPanel();
        setupPanelLayout();
        initializePhieuNhap();
        timkiem();
    }

    private void initializeTextFields() {
        txt_importId = new JTextField();
        txt_employeeId = new JTextField();
        txt_nxb = new JTextField();
        txt_date = new JTextField();
        txt_total = new JTextField();
        txt_name = new JTextField();
        txt_quantity = new JTextField();
        txt_search = new JTextField();
        txt_array_top = new JTextField[] { txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total };
        txt_array_down = new JTextField[] { txt_name, txt_quantity };
        txt_array_search = new JTextField[] { txt_search };
    }

    private void initializeMainPanel() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        paymentPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, (int) (HEIGHT * 0.55), new BorderLayout());
    }

    private void setupPanelLayout() {
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createTable_top(), BorderLayout.WEST);

        String[] txt_label_top = { "Mã phiếu nhập", "Nhân viên", "NXB", "Ngày nhập", "Tổng tiền" };
        panel.add(createDetailPanel_top(400, -10, txt_array_top, txt_label_top, null), BorderLayout.CENTER);

        JPanel lowerPanel = new JPanel(new BorderLayout(10, 0));
        JPanel detailPanelDown = createDetailPanel_down(300, 10, txt_array_down, txt_label);
        lowerPanel.add(detailPanelDown, BorderLayout.CENTER);

        imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.setPreferredSize(new Dimension(200, 260));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 0));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        lowerPanel.add(imagePanel, BorderLayout.WEST);

        paymentPanel.add(lowerPanel, BorderLayout.CENTER);
        paymentPanel.add(createTable_down(), BorderLayout.EAST);

        panel.add(paymentPanel, BorderLayout.SOUTH);
    }

    private void initializePhieuNhap() {
        try {
            phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
            count = 0;
            if (!phieuNhapList.isEmpty()) {
                String maPN = phieuNhapList.get(phieuNhapList.size() - 1).getMaPN();
                String numericPart = maPN.substring(2);
                try {
                    count = Integer.parseInt(numericPart) + 1;
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing maPN: " + maPN);
                    count = 1;
                }
            }
            txt_array_top[0].setText(getID());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi khởi tạo phiếu nhập: " + e.getMessage());
        }
    }

    private String getID() {
        return "PN" + String.format("%02d", count);
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã sách", "Tên sách" };
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
        DefaultTableModel model = (DefaultTableModel) table_top.getModel();
        model.setRowCount(0);
        try {
            for (SachDTO sach : sachList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã sách":
                        match = sach.getMaSach().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Tên sách":
                        match = sach.getTenSach().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            sach.getMaSach(),
                            sach.getTenSach(),
                            sach.getSoLuong(),
                            sach.getDonGia()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private ImageIcon loadBookImage(SachDTO sach, String bookId) {
        ImageIcon finalIcon = null;
        BufferedImage originalImage = null;
        try {
            String imgName = sach.getImg();
            if (imgName != null && !imgName.trim().isEmpty()) {
                String absoluteImagePath = System.getProperty("user.dir") + "/images/Book/" + imgName;
                File imageFile = new File(absoluteImagePath);
                if (imageFile.exists() && imageFile.isFile()) {
                    originalImage = ImageIO.read(imageFile);
                    if (originalImage != null) {
                        int targetWidth = imagePanel.getPreferredSize().width > 0 ? imagePanel.getPreferredSize().width : 200;
                        int targetHeight = imagePanel.getPreferredSize().height > 0 ? imagePanel.getPreferredSize().height : 250;
                        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                        finalIcon = new ImageIcon(scaledImage);
                    }
                }
            }
        } catch (IOException ioEx) {
            System.err.println("IOException reading image file: " + sach.getImg() + " - " + ioEx.getMessage());
        } catch (Exception ex) {
            System.err.println("General error processing image " + sach.getImg() + ": " + ex.getMessage());
        }

        if (finalIcon == null) {
            try {
                String defaultImagePath = System.getProperty("user.dir") + "/images/Book/default.jpg";
                File defaultImageFile = new File(defaultImagePath);
                if (defaultImageFile.exists()) {
                    BufferedImage defaultOriginal = ImageIO.read(defaultImageFile);
                    int targetWidth = imagePanel.getPreferredSize().width > 0 ? imagePanel.getPreferredSize().width : 200;
                    int targetHeight = imagePanel.getPreferredSize().height > 0 ? imagePanel.getPreferredSize().height : 250;
                    Image scaledDefault = defaultOriginal.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                    finalIcon = new ImageIcon(scaledDefault);
                }
            } catch (IOException ioEx) {
                System.err.println("IOException reading default image: " + ioEx.getMessage());
            }
        }
        return finalIcon;
    }

    private JPanel createTable_top() {
        String[] column = { "Mã sách", "Tên sách", "Số lượng", "Đơn giá" };
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getDonGia()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu sách: " + e.getMessage());
        }

        table_top = tool.createTable(model, column);
        table_top.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_top);
        scrollPane.setPreferredSize(new Dimension(650, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);

        table_top.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table_top.getSelectedRow();
                if (selectedRow >= 0) {
                    txt_array_down[0].setText((String) table_top.getValueAt(selectedRow, 0));
                    txt_array_down[0].setEditable(false);
                    txt_array_down[1].setEditable(true);
                    txt_array_down[1].setText("");

                    String bookId = table_top.getValueAt(selectedRow, 0).toString();
                    SachDTO sach = sachBUS.getSachByMaSach(bookId);
                    if (sach != null) {
                        imageLabel.setIcon(loadBookImage(sach, bookId));
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    } else {
                        imageLabel.setIcon(null);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                } else {
                    txt_array_down[0].setText("");
                    txt_array_down[1].setText("");
                    txt_array_down[0].setEditable(true);
                    txt_array_down[1].setEditable(true);
                    imageLabel.setIcon(null);
                    imagePanel.revalidate();
                    imagePanel.repaint();
                }
            }
        });

        return panelTable;
    }

    private JPanel createTable_down() {
        String[] column = { "Mã sách", "Tên sách", "Số lượng", "Đơn giá" };
        DefaultTableModel model = new DefaultTableModel(column, 0);
        table_down = tool.createTable(model, column);
        table_down.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(500, 240));

        table_down.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table_down.getSelectedRow();
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table_down.clearSelection();
                    for (JTextField txt : txt_array_down) {
                        txt.setText("");
                        txt.setEditable(true);
                    }
                    imageLabel.setIcon(null);
                    imagePanel.revalidate();
                    imagePanel.repaint();
                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    String bookId = (String) table_down.getValueAt(selectedRow, 0);
                    SachDTO sach = sachBUS.getSachByMaSach(bookId);
                    txt_array_down[0].setText(bookId);
                    txt_array_down[1].setText(String.valueOf(table_down.getValueAt(selectedRow, 2)));
                    if (sach != null) {
                        imageLabel.setIcon(loadBookImage(sach, bookId));
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                    for (JTextField txt : txt_array_down) {
                        txt.setEditable(false);
                    }
                    lastSelectedRow = selectedRow;
                }
            }
        });

        JPanel panelTable = new JPanel();
        panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTable.add(scrollPane);
        panelTable.add(createButtonPanel());
        panelTable.add(Box.createVerticalStrut(50));
        panelTable.setBorder(BorderFactory.createEmptyBorder(90, 10, 0, 10));
        return panelTable;
    }

    private JPanel createDetailPanel_top(int width, int padding_top, JTextField[] txt_array,
            String[] txt_label, ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, null, width, 300, 2, 5, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));

        txt_array[0].setEditable(false); // Mã phiếu nhập
        txt_array[1].setText(nv.getHoTen());
        txt_array[1].setEditable(false); // Nhân viên
        txt_array[2].setEditable(true); // NXB
        txt_array[3].setText(LocalDate.now().toString());
        txt_array[3].setEditable(false); // Ngày nhập
        txt_array[4].setEditable(false); // Tổng tiền
        return wrappedPanel;
    }

    private JPanel createDetailPanel_down(int width, int padding_top, JTextField[] txt_array,
            String[] txt_label) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, null, width, 300, 2, 5, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    private JPanel createButtonPanel() {
        String[] buttonTexts = { "Thêm", "Xóa", "Thanh toán" };
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttons[i].setFocusable(false);
        }
        buttons[0].addActionListener(e -> addChiTietPhieuNhap());
        buttons[1].addActionListener(e -> deleteChiTietPhieuNhap());
        buttons[2].addActionListener(e -> thanhToan());
        return buttonPanel;
    }

    private void addChiTietPhieuNhap() {
        // Ensure top fields are in correct state
        txt_array_top[0].setText(getID());
        txt_array_top[1].setText(nv.getHoTen());
        txt_array_top[3].setText(LocalDate.now().toString());
        txt_array_top[0].setEditable(false);
        txt_array_top[1].setEditable(false);
        txt_array_top[3].setEditable(false);
        txt_array_top[4].setEditable(false);
        txt_array_top[2].setEditable(true);

        selectedRow = table_top.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách từ danh sách!");
            return;
        }

        try {
            String maSach = table_top.getValueAt(selectedRow, 0).toString();
            String tenSach = table_top.getValueAt(selectedRow, 1).toString();
            String soLuongStr = txt_array_down[1].getText().trim();
            int donGia = Integer.parseInt(table_top.getValueAt(selectedRow, 3).toString());

            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!");
                return;
            }

            if (!soLuongStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương!");
                return;
            }

            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                return;
            }

            SachDTO sach = sachBUS.getSachByMaSach(maSach);
            if (sach == null) {
                JOptionPane.showMessageDialog(null, "Sách không tồn tại!");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String maSachCu = model.getValueAt(i, 0).toString().trim();
                if (maSach.equals(maSachCu)) {
                    int soLuongCu = Integer.parseInt(model.getValueAt(i, 2).toString());
                    soLuong += soLuongCu;
                    model.removeRow(i);
                    break;
                }
            }
            model.addRow(new Object[] { maSach, tenSach, soLuong, donGia });

            updateTotal();
            txt_array_down[0].setText("");
            txt_array_down[1].setText("");
            imageLabel.setIcon(null);
            imagePanel.revalidate();
            imagePanel.repaint();

            JOptionPane.showMessageDialog(null, "Thêm chi tiết phiếu nhập thành công!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm chi tiết phiếu nhập: " + e.getMessage());
        }
    }

    private void deleteChiTietPhieuNhap() {
        selectedRow = table_down.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        model.removeRow(selectedRow);
        lastSelectedRow = -1;
        updateTotal();
        txt_array_down[0].setText("");
        txt_array_down[1].setText("");
        imageLabel.setIcon(null);
        imagePanel.revalidate();
        imagePanel.repaint();
        JOptionPane.showMessageDialog(null, "Xóa chi tiết phiếu nhập thành công!");
    }

    private void thanhToan() {
        int maxRetries = 3;
        int retryCount = 0;
        boolean success = false;

        while (retryCount < maxRetries && !success) {
            try {
                String maPN = txt_array_top[0].getText().trim();
                String maNV = nv.getMaNV();
                String maNXB = txt_array_top[2].getText().trim();
                String ngayNhapStr = txt_array_top[3].getText().trim();
                String tongTienStr = txt_array_top[4].getText().trim();

                if (maNV.isEmpty() || maNXB.isEmpty() || ngayNhapStr.isEmpty() || tongTienStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phiếu nhập!");
                    return;
                }

                if (!nhanVienBUS.isNhanVienExists(maNV)) {
                    JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
                    return;
                }

                if (!nhaXuatBanBUS.isNhaXuatBanExists(maNXB)) {
                    JOptionPane.showMessageDialog(null, "Mã nhà xuất bản không hợp lệ!");
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) table_down.getModel();
                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào phiếu nhập!");
                    return;
                }

                int tongTien = Integer.parseInt(tongTienStr);
                Date ngayNhap;
                try {
                    ngayNhap = Date.valueOf(ngayNhapStr);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Định dạng ngày nhập không hợp lệ (YYYY-MM-DD)!");
                    return;
                }

                PhieuNhapDTO phieuNhap = new PhieuNhapDTO(maPN, maNV, ngayNhap, tongTien, maNXB);
                if (!phieuNhapBUS.addPhieuNhap(phieuNhap)) {
                    throw new SQLException("Thêm phiếu nhập thất bại!");
                }

                for (int i = 0; i < model.getRowCount(); i++) {
                    String maSach = model.getValueAt(i, 0).toString().trim();
                    int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                    int giaNhap = Integer.parseInt(model.getValueAt(i, 3).toString());

                    SachDTO sach = sachBUS.getSachByMaSach(maSach);
                    if (sach == null) {
                        throw new SQLException("Mã sách " + maSach + " không tồn tại!");
                    }

                    ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO(maSach, maPN, soLuong, giaNhap);
                    if (!chiTietPhieuNhapBUS.addChiTietPhieuNhap(chiTiet)) {
                        throw new SQLException("Thêm chi tiết phiếu nhập thất bại cho sách " + maSach);
                    }

                    int soLuongHienTai = sachBUS.getSoLuongTonSanPham(maSach);
                    sachBUS.updateSoLuongTonSanPham(maSach, soLuongHienTai + soLuong);
                }

                Inhoadon();
                model.setRowCount(0);
                resetGUIState();
                JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
                success = true;
            } catch (SQLException e) {
                retryCount++;
                String errorMessage = e.getMessage().toLowerCase();
                if (errorMessage.contains("duplicate") || errorMessage.contains("unique constraint") || e.getSQLState().startsWith("23")) {
                    if (retryCount < maxRetries) {
                        count++;
                        txt_array_top[0].setText(getID());
                        JOptionPane.showMessageDialog(null, "Mã phiếu nhập !!đã tồn tại, đang thử mã mới: " + getID());
                        continue;
                    } else {
                        JOptionPane.showMessageDialog(null, "Lỗi: Không thể tạo mã phiếu nhập mới sau " + maxRetries + " lần thử. Vui lòng kiểm tra cơ sở dữ liệu!");
                        e.printStackTrace();
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Tổng tiền không hợp lệ!");
                return;
            }
        }
    }

    private void resetGUIState() {
        initializePhieuNhap(); // Refresh phieuNhapList and count
        count++; // Increment count for next invoice
        selectedRow = -1;
        lastSelectedRow = -1;
        table_down.clearSelection();
        table_top.clearSelection();

        txt_array_top[0].setText(getID());
        txt_array_top[1].setText(nv != null ? nv.getHoTen() : "");
        txt_array_top[2].setText("");
        txt_array_top[3].setText(LocalDate.now().toString());
        txt_array_top[4].setText("");

        txt_array_top[0].setEditable(false);
        txt_array_top[1].setEditable(false);
        txt_array_top[2].setEditable(true);
        txt_array_top[3].setEditable(false);
        txt_array_top[4].setEditable(false);

        for (JTextField txt : txt_array_down) {
            txt.setText("");
            txt.setEditable(true);
        }

        imageLabel.setIcon(null);
        imagePanel.revalidate();
        imagePanel.repaint();
        refreshTable();
    }

    private void updateTotal() {
        int tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());
            tongTien += soLuong * donGia;
        }
        txt_array_top[4].setText(String.valueOf(tongTien));
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table_top.getModel();
        model.setRowCount(0);
        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[] {
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getDonGia()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi làm mới bảng: " + e.getMessage());
        }
    }

    public void Inhoadon() {
        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        int rowCount = model.getRowCount();
        int imgWidth = 900;
        int imgHeight = 500 + rowCount * 25 + 100;
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, imgHeight);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("HÓA ĐƠN NHẬP HÀNG", 350, 30);
        g2d.drawString("CỬA HÀNG BÁN SÁCH", 51, 50);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("Địa chỉ: 123 Đường Sách, Quận 1, TP.HCM", 51, 70);
        g2d.drawString("ĐT: 0123 456 789", 51, 90);
        g2d.drawString("Nhà xuất bản: " + txt_array_top[2].getText(), 51, 110);
        g2d.drawString("Nhân viên: " + txt_array_top[1].getText(), 51, 130);
        g2d.drawLine(51, 150, imgWidth - 51, 150);

        // Draw column headers
        g2d.drawString("STT", 68, 182);
        g2d.drawString("TÊN HÀNG", 200, 182);
        g2d.drawString("SỐ LƯỢNG", 370, 182);
        g2d.drawString("ĐƠN GIÁ", 520, 182);
        g2d.drawString("THÀNH TIỀN", 700, 182);
        g2d.drawLine(51, 160, imgWidth - 51, 160); // Horizontal line
        // Draw vertical lines to separate columns
        g2d.setFont(new Font("New Times Roman", Font.BOLD, 14));
        for (int row = 0; row < rowCount; row++) {
            String stt = String.valueOf(row + 1);
            String tenHang = model.getValueAt(row, 1).toString();
            String soLuong = model.getValueAt(row, 2).toString();
            String donGia = model.getValueAt(row, 3).toString();
            String thanhTien = String.valueOf(Integer.parseInt(soLuong) * Integer.parseInt(donGia));

            g2d.drawString(stt, 79, 210 + row * 25);
            g2d.drawString(tenHang, 125, 210 + row * 25);
            g2d.drawString(soLuong, 410, 210 + row * 25);
            g2d.drawString(donGia, 520, 210 + row * 25);
            g2d.drawString(thanhTien, 700, 210 + row * 25);

            g2d.drawLine(51, 160, 51, 214 + rowCount * 25);
            g2d.drawLine(120, 160, 120, 214 + rowCount * 25); // STT - TÊN HÀNG
            g2d.drawLine(365, 160, 365, 214 + rowCount * 25); // TÊN HÀNG - SỐ LƯỢNG
            g2d.drawLine(460, 160, 460, 214 + rowCount * 25); // SỐ LƯỢNG - ĐƠN GIÁ
            g2d.drawLine(650, 160, 650, 214 + rowCount * 25); // ĐƠN GIÁ - THÀNH TIỀN
            g2d.drawLine(imgWidth - 51, 160, imgWidth - 51, 214 + rowCount * 25); // THÀNH TIỀN - right border

            g2d.drawLine(51, 214 + row * 25, imgWidth - 51, 214 + row * 25); // Horizontal line
            if (row == rowCount - 1) {
                g2d.drawLine(51, 240 + row * 25, imgWidth - 51, 240 + row * 25); // Horizontal line
                g2d.setFont(new Font("New Times Roman", Font.BOLD, 16));
                g2d.drawString("CỘNG: ", 125, 235 + row * 25);
                g2d.setFont(new Font("New Times Roman", Font.BOLD, 16));
                g2d.drawString(String.valueOf(txt_array_top[4].getText()) + "VNĐ", 700, 235 + row * 25);
            }
        }
        g2d.drawLine(51, 204 - 12, imgWidth - 51, 204 - 12);

        // Draw footer
        g2d.getFont().deriveFont(16f);
        g2d.drawString(
                "Thành tiền:........................." + txt_array_top[4].getText()
                        + "..VNĐ..............................................................",
                51, 245 + rowCount * 25);
        g2d.drawString("Ngày " + LocalDate.now().getDayOfMonth() + " Tháng " + LocalDate.now().getMonthValue() + " Năm " + LocalDate.now().getYear(), 350, 260 + rowCount * 25);
        g2d.drawString("NGƯỜI GIAO HÀNG", 150, 350 + rowCount * 25);
        g2d.drawString("NHÂN VIÊN", 470, 350 + rowCount * 25);
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File("HoaDonNhap\\Hoa_Don_Nhap.png"));
            Desktop.getDesktop().open(new File("HoaDonNhap\\Hoa_Don_Nhap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JFrame frame = new JFrame("Hóa đơn nhập hàng");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(900, 800);
            frame.setLocationRelativeTo(null);

            JLabel label = new JLabel(new ImageIcon(img));
            label.setBackground(Color.black);
            frame.add(label);

            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in hóa đơn: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}