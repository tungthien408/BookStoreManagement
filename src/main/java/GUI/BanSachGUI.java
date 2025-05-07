package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SachBUS;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SachDTO;
import DTO.TaiKhoanNVDTO;

public class BanSachGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final String INVOICE_ID_PREFIX = "HD";

    private Tool tool = new Tool();
    private JPanel panel, paymentPanel;
    private JTextField[] txt_array_top = new JTextField[6];
    private JTextField[] txt_array_down = new JTextField[2];
    // private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName, txt_date, txt_total;
    private JTextField txt_bookId, txt_quantity;
    // private JTextField txt_search;
    private JButton[] buttons = new JButton[3];
    private JButton[] searchbutton = new JButton[1];
    private JTable table_down, table_top;
    private JLabel imageLabel; // <<< ADD THIS
    private JPanel imagePanel; // <<< ADD THIS
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private int count = 0;  
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    private List<SachDTO> sachList;
    private List<HoaDonDTO> hoaDonList;
    private SachBUS sachBUS = new SachBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private NhanVienDTO nv;

    public BanSachGUI(TaiKhoanNVDTO account) {
        nv = nhanVienBUS.getNhanVienByMaNV(account.getMaNV());
        initializeTextFields();
        initializeMainPanel();
        setupPanelLayout();
        initializeHoaDon();
        timkiem();
    }

    private void initializeTextFields() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[]{txt_search};
        txt_array_top = new JTextField[]{txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName, txt_date, txt_total};
        txt_array_down = new JTextField[]{txt_bookId, txt_quantity};
        txt_array_search = new JTextField[]{txt_search};
    }

    private void initializeMainPanel() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        paymentPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, (int) (HEIGHT * 0.55), new BorderLayout());
    }

    private void setupPanelLayout() {
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createTable_top(), BorderLayout.WEST);

        String[] txt_label_top = {"Mã hóa đơn", "Nhân viên", "SDT KH", "Tên KH", "Ngày bán", "Tổng tiền"};
        panel.add(createDetailPanel_top(400, 0, txt_array_top, txt_label_top, null), BorderLayout.CENTER);

        // --- Setup for the lower part (details + image) ---
        JPanel lowerPanel = new JPanel(new BorderLayout(10, 0)); // Use BorderLayout

        String[] txt_label_down = {"Mã sách", "Số lượng"}; // Renamed from txt_label for clarity
        // Create the detail panel for book ID and quantity (pass null for img)
        JPanel detailPanelDown = createDetailPanel_down(300, 10, txt_array_down, txt_label_down /* REMOVED img */);
        lowerPanel.add(detailPanelDown, BorderLayout.CENTER); // Add details to the center

        // Create and add the image panel
        imagePanel = new JPanel(new BorderLayout()); // Panel to hold the image label
        imageLabel = new JLabel(); // Initialize the image label
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        // Set a preferred size for the image area
        imagePanel.setPreferredSize(new Dimension(200, 260)); // Adjust size as needed
        imagePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 0)); // Adjust padding
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        lowerPanel.add(imagePanel, BorderLayout.WEST); // Add image panel to the left

        // Add the lower panel containing details and image to the paymentPanel
        paymentPanel.add(lowerPanel, BorderLayout.WEST); // Place details+image on the WEST side of paymentPanel

        // Add other components to paymentPanel
        paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        paymentPanel.add(createTable_down(), BorderLayout.EAST); // Keep table_down on the EAST

        panel.add(paymentPanel, BorderLayout.SOUTH);
    }

    private void initializeHoaDon() {
        hoaDonList = hoaDonBUS.getAllHoaDon();
        if (!hoaDonList.isEmpty()) {
            String maHD = hoaDonList.get(hoaDonList.size() - 1).getMaHD();
            String numericPart = maHD.substring(2);
            count = Integer.parseInt(numericPart) + 1;
        }
    }

    private String getID() {
        return INVOICE_ID_PREFIX + String.format("%03d", count);
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã sách", "Tên sách"};
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
            model.setRowCount(0); // Xóa dữ liệu cũ
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
                    model.addRow(new Object[]{
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getDonGia()+10000
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private JPanel createTable_top() {
        String[] column = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
                        sach.getMaSach(),
                        sach.getTenSach(),
                        sach.getSoLuong(),
                        sach.getDonGia()+10000
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

        // --- COMBINED LISTENER FOR TEXT FIELDS AND IMAGE ---
        table_top.getSelectionModel().addListSelectionListener(e -> {
            // Prevent processing during model updates
            if (!e.getValueIsAdjusting()) {
                selectedRow = table_top.getSelectedRow();
                if (selectedRow >= 0) {
                    // --- Update TextFields ---
                    txt_array_down[0].setText((String) table_top.getValueAt(selectedRow, 0)); // Book ID
                    txt_array_down[0].setEditable(false);
                    txt_array_down[1].setText(""); // Clear quantity field
                    txt_array_down[1].setEditable(true); // Allow entering quantity

                    // --- Get Book Data for Image ---
                    String bookId = table_top.getValueAt(selectedRow, 0).toString();
                    SachDTO sach = sachBUS.getSachByMaSach(bookId);

                    if (sach != null) {
<<<<<<< HEAD
                        // --- Load, Resize, and Update Image using BufferedImage ---
                        ImageIcon finalIcon = null;
                        BufferedImage originalImage = null;
                        try {
                            String imgName = sach.getImg();
                            if (imgName != null && !imgName.trim().isEmpty()) {
                                // String absoluteImagePath = "/home/thien408/Documents/programming/java/Java/DoAn/BookStoreManagement/images/Book/" + imgName;
                                String absoluteImagePath = "D:" + File.separator + "K2_Y2" + File.separator + "BookStoreManagement" + File.separator + "images" + File.separator + "Book" + File.separator + sach.getImg();
                                File imageFile = new File(absoluteImagePath);
                                if (imageFile.exists() && imageFile.isFile()) {
                                    originalImage = ImageIO.read(imageFile);
                                    if (originalImage != null) {
                                        System.out.println("Successfully read image file: " + absoluteImagePath);
                                        int targetWidth = imagePanel.getPreferredSize().width;
                                        int targetHeight = imagePanel.getPreferredSize().height;
                                        if (targetWidth <= 0) targetWidth = 200;
                                        if (targetHeight <= 0) targetHeight = 250;
                                        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                                        finalIcon = new ImageIcon(scaledImage);
                                        System.out.println("Scaled image to: " + targetWidth + "x" + targetHeight);
                                    } else {
                                        System.err.println("ImageIO.read returned null for file: " + absoluteImagePath);
                                    }
                                } else {
                                    System.err.println("Image file not found or is not a file: " + absoluteImagePath);
                                }
                            } else {
                                System.err.println("Image name is null or empty for book: " + bookId);
                            }
                        } catch (IOException ioEx) {
                            System.err.println("IOException reading image file: " + sach.getImg() + " - " + ioEx.getMessage());
                            ioEx.printStackTrace();
                        } catch (Exception ex) {
                            System.err.println("General error processing image " + sach.getImg() + ": " + ex.getMessage());
                            ex.printStackTrace();
                        }

                        // --- Load and Scale Default Image if necessary ---
                        if (finalIcon == null) {
                            System.err.println("Attempting to load and scale default image...");
                            try {
                                BufferedImage defaultOriginal = null;
                                // Assuming default.jpg is a RESOURCE
                                URL defaultUrl = getClass().getResource("/images/Book/default.jpg");
                                if (defaultUrl != null) {
                                    defaultOriginal = ImageIO.read(defaultUrl);
                                } else {
                                    System.err.println("Default image resource not found!");
                                }
                                // --- OR --- If default.jpg is also an ABSOLUTE path file:
                                /*
                                String defaultImagePath = "/home/thien408/Documents/programming/java/Java/DoAn/BookStoreManagement/images/Book/default.jpg";
                                File defaultImageFile = new File(defaultImagePath);
                                if (defaultImageFile.exists()) {
                                    defaultOriginal = ImageIO.read(defaultImageFile);
                                } else {
                                    System.err.println("Default image file not found at: " + defaultImagePath);
                                }
                                */
                                if (defaultOriginal != null) {
                                    int targetWidth = imagePanel.getPreferredSize().width;
                                    int targetHeight = imagePanel.getPreferredSize().height;
                                    if (targetWidth <= 0) targetWidth = 200;
                                    if (targetHeight <= 0) targetHeight = 250;
                                    Image scaledDefault = defaultOriginal.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                                    finalIcon = new ImageIcon(scaledDefault);
                                    System.out.println("Loaded and scaled default image.");
                                }
                            } catch (IOException ioEx) {
                                System.err.println("IOException reading default image: " + ioEx.getMessage());
                            } catch (Exception ex) {
                                System.err.println("General error processing default image: " + ex.getMessage());
                            }
                        }

=======
>>>>>>> 95a07a9df8ff9dd68b115af66e0265971153ede1
                        // --- Update the existing imageLabel ---
                        imageLabel.setIcon(tool.showImage(sach, imagePanel));

                        // --- Refresh the panel containing the imageLabel ---
                        imagePanel.revalidate();
                        imagePanel.repaint();

                    } else { // sach == null
                        System.err.println("SachDTO not found for ID: " + bookId);
                        imageLabel.setIcon(null); // Clear image
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    }
                } else { // selectedRow < 0 (deselected)
                    // Optionally clear fields and image on deselection
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

        // Remove the old MouseAdapter if it only handled text fields
        // table_top.addMouseListener(new MouseAdapter() { ... }); // REMOVE or comment out

        return panelTable;
    }

    private JPanel createTable_down() {
        String[] column = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        table_down = tool.createTable(model, column);
        table_down.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        table_down.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = table_down.getSelectedRow();
                if (selectedRow == lastSelectedRow && selectedRow >= 0) {
                    table_down.clearSelection();
                    // Clear fields if needed when deselecting from table_down
                    // txt_array_down[0].setText("");
                    // txt_array_down[1].setText("");
                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    // You might want to update fields based on table_down selection
                    // for editing purposes, but DON'T load the image here.
                    String bookId = (String) table_down.getValueAt(selectedRow, 0); // Get ID from table_down
                    txt_array_down[0].setText(bookId);
                    txt_array_down[1].setText(String.valueOf(table_down.getValueAt(selectedRow, 2))); // Get quantity from table_down

                    // REMOVE IMAGE LOADING LOGIC FROM HERE
                    // try {
                    //     img = new ImageIcon(...)
                    // } catch (...) { ... }

                    // Make fields non-editable if selecting from table_down
                    for (JTextField txt : txt_array_down) {
                        txt.setEditable(false);
                    }
                    lastSelectedRow = selectedRow;
                }
            }
        });

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createDetailPanel_top(int width, int padding_top, JTextField[] txt_array,
                                        String[] txt_label, ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 250, 2, 6, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));

        // {txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName, txt_date, txt_total}
        txt_array[0].setEditable(false);
        txt_array[1].setEditable(false);
        txt_array[1].setText(nv.getHoTen());
        txt_array[2].setEditable(true);
        txt_array[4].setEditable(false);

        // on txt_array[2] add event to check if the phone number is typed correctly
        // if so, check that phone number on the customer list
        // if the phone number is existed, print the customer's name on txt_array[3]
        // else enable the txt_array[3] editable the let the user type the customer's name

        txt_array[2].addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String sdt = txt_array[2].getText();
                if (sdt.matches("(02|03|05|07|08|09)\\d{8}")) { // Check if the phone number is valid
                    KhachHangDTO khachHang = khachHangBUS.getMaKhachHangBySdt(sdt);
                    if (khachHang != null) {
                        txt_array[3].setText(khachHang.getHoTen());
                        txt_array[3].setEditable(false);
                    } else {
                        txt_array[3].setEditable(true);
                        txt_array[3].setText("");
                    }
                } else {
                    txt_array[3].setText("Số điện thoại không hợp lệ!");
                }
            }
        });
        return wrappedPanel;
    }

    private JPanel createDetailPanel_down(int width, int padding_top, JTextField[] txt_array,
                                         String[] txt_label /* REMOVED ImageIcon img */) {
        // Pass null for the image parameter to tool.createDetailPanel
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, null, width, 300, 1, 2, false); // Use 1 column, 2 rows for label/field pairs
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    private JPanel createButtonPanel() {
        String[] buttonTexts = {"Thêm", "Xóa", "Thanh toán"};
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 110, 25));

        buttons[0].addActionListener(e -> addChiTietHoaDon());
        buttons[1].addActionListener(e -> deleteChiTietHoaDon());
        buttons[2].addActionListener(e -> thanhToan());
        return buttonPanel;
    }

    private void addChiTietHoaDon() {
        txt_array_top[0].setEditable(false);
        txt_array_top[0].setText(getID());
        // txt_array_top[1].setEditable(true);
        txt_array_top[4].setText(LocalDate.now().toString());

        selectedRow = table_top.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách từ danh sách!");
            return;
        }
            String maSach = table_top.getValueAt(selectedRow, 0).toString();
            String tenSach = table_top.getValueAt(selectedRow, 1).toString();
            String soLuongStr = txt_array_down[1].getText().trim();
            int donGia = Integer.parseInt(table_top.getValueAt(selectedRow, 3).toString());

            if (soLuongStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng!");
                return;
            }

            if (!soLuongStr.matches("\\d")) {
                JOptionPane.showMessageDialog(null, "Số lượng phải là ký tự số");
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

            if (soLuong > sach.getSoLuong()) {
                JOptionPane.showMessageDialog(null, "Số lượng sách trong kho không đủ!");
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
            model.addRow(new Object[]{maSach, tenSach, soLuong, donGia});

            updateTotal();
            txt_array_down[0].setText("");
            txt_array_down[1].setText("");


    }

    private void deleteChiTietHoaDon() {
        selectedRow = table_down.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        model.removeRow(selectedRow);
        updateTotal();
        JOptionPane.showMessageDialog(null, "Xóa chi tiết hóa đơn thành công!");
    }

    private void thanhToan()  {
        try {
            String maHD = txt_array_top[0].getText().trim();
            String maNV = nv.getMaNV();
            String sdtKhach = txt_array_top[2].getText().trim();
            String tenKhach = txt_array_top[3].getText().trim(); // Ensure this line is correctly placed within a valid method or block
            String ngayBanStr = txt_array_top[4].getText().trim();
            String tongTienStr = txt_array_top[5].getText().trim();

            if (maNV.isEmpty() || sdtKhach.isEmpty() || tenKhach.isEmpty() || ngayBanStr.isEmpty() || tongTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin hóa đơn!");
                return;
            }

            if (!nhanVienBUS.isNhanVienExists(maNV)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào hóa đơn!");
                return;
            }

            KhachHangDTO maKH = khachHangBUS.getMaKhachHangBySdt(sdtKhach);
            if (maKH == null) {
                maKH = new KhachHangDTO(); // Initialize maKH
                System.out.println("Ma Khach Hang: " + getNextMaKH());
                maKH.setMaKH(getNextMaKH());
                maKH.setHoTen(tenKhach);
                maKH.setSdt(sdtKhach);
                maKH.setTrangThaiXoa(0);
                maKH.setDiem(0);
                khachHangBUS.addKhachHang(maKH);
            }


            int tongTien = Integer.parseInt(tongTienStr);
            Date ngayBan;
            try {
                ngayBan = Date.valueOf(ngayBanStr);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Định dạng ngày bán không hợp lệ (YYYY-MM-DD)!");
                return;
            }

            HoaDonDTO hoaDon = new HoaDonDTO(maHD, maNV, maKH.getMaKH(), ngayBan, tongTien);
            if (!hoaDonBUS.addHoaDon(hoaDon)) {
                throw new SQLException("Thêm hóa đơn thất bại!");
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                String maSach = model.getValueAt(i, 0).toString().trim();
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());

                SachDTO sach = sachBUS.getSachByMaSach(maSach);
                if (sach == null) {
                    throw new SQLException("Mã sách " + maSach + " không tồn tại!");
                }

                if (soLuong > sach.getSoLuong()) {
                    throw new SQLException("Số lượng sách trong kho không đủ cho sách " + maSach);
                }

                ChiTietHoaDonDTO chiTiet = new ChiTietHoaDonDTO(maSach, maHD, soLuong, donGia);
                if (!chiTietHoaDonBUS.addChiTietHoaDon(chiTiet)) {
                    throw new SQLException("Thêm chi tiết hóa đơn thất bại cho sách " + maSach);
                }

                int soLuongHienTai = sach.getSoLuong();
                sachBUS.updateSoLuongTonSanPham(maSach, soLuongHienTai - soLuong);
            }

      
            refreshTable();
            model.setRowCount(0);

            for (JTextField txt : txt_array_top) {
                if (txt != txt_employeeName) {
                    txt.setText("");
                }
                txt.setEditable(txt != txt_invoiceId && txt != txt_employeeName && txt != txt_date && txt != txt_total);
            }
            for (JTextField txt : txt_array_down) {
                txt.setText("");
            }

            count++;
            txt_array_top[0].setText(getID());
            txt_array_top[4].setText(LocalDate.now().toString());
            initializeHoaDon();

            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thanh toán: " + e.getMessage());
        }

    }

    private void updateTotal() {
        int tongTien = 0;
        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
            int donGia = Integer.parseInt(model.getValueAt(i, 3).toString());
            tongTien += soLuong * donGia;
        }
        txt_array_top[5].setText(String.valueOf(tongTien));
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table_top.getModel();
        model.setRowCount(0);
        try {
            sachList = sachBUS.getAllSach();
            for (SachDTO sach : sachList) {
                model.addRow(new Object[]{
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

    private String getNextMaKH() {
        String maKH = "KH";
        int count = khachHangBUS.getCountKhachHang() + 1;
        if (count < 10) {
            maKH += "00" + count;
        } else if (count < 100) {
            maKH += "0" + count;
        } else {
            maKH += count;
        }
        return maKH;
    }

    public JPanel getPanel() {
        return this.panel;
    }
}
