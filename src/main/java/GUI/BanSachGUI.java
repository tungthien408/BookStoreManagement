package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
    private JTextField[] txt_array_top = new JTextField[7];
    private JTextField[] txt_array_down = new JTextField[2];
    private JTextField txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName, txt_point, txt_date,
            txt_total;
    private JTextField txt_bookId, txt_quantity;
    private JTextField txt_search;
    private JButton[] buttons = new JButton[3];
    private JButton[] searchbutton = new JButton[1];
    private JTable table_down, table_top;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private int selectedRow = -1;
    private int lastSelectedRow = -1;
    private int count = 0;
    private int tienGiamGia = 0;
    private JTextField[] txt_array_search = new JTextField[1];
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
        txt_array_search = new JTextField[] { txt_search };
        txt_invoiceId = new JTextField();
        txt_employeeName = new JTextField();
        txt_customerPhone = new JTextField();
        txt_customerName = new JTextField();
        txt_point = new JTextField();
        txt_date = new JTextField();
        txt_total = new JTextField();
        txt_bookId = new JTextField();
        txt_quantity = new JTextField();
        txt_array_top = new JTextField[] { txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName,
                txt_point, txt_date, txt_total };
        txt_array_down = new JTextField[] { txt_bookId, txt_quantity };
    }

    private void initializeMainPanel() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        paymentPanel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, (int) (HEIGHT * 0.55), new BorderLayout());
    }

    private void setupPanelLayout() {
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createTable_top(), BorderLayout.WEST);

        String[] txt_label_top = { "Mã hóa đơn", "Nhân viên", "SDT KH", "Tên KH", "Điểm tích lũy", "Ngày bán",
                "Tổng tiền" };
        panel.add(createDetailPanel_top(450, -10, txt_array_top, txt_label_top, null), BorderLayout.CENTER);

        JPanel lowerPanel = new JPanel(new BorderLayout(10, 0));
        String[] txt_label_down = { "Mã sách", "Số lượng" };
        JPanel detailPanelDown = createDetailPanel_down(300, 10, txt_array_down, txt_label_down);
        lowerPanel.add(detailPanelDown, BorderLayout.CENTER);

        imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.setPreferredSize(new Dimension(200, 260));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 0, 0));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        lowerPanel.add(imagePanel, BorderLayout.WEST);

        paymentPanel.add(lowerPanel, BorderLayout.WEST);
        // paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        paymentPanel.add(createTable_down(), BorderLayout.EAST);

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
                            sach.getDonGia() + 10000
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
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
                        sach.getDonGia() + 10000
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
                    txt_array_down[1].setText("");
                    txt_array_down[1].setEditable(true);

                    String bookId = table_top.getValueAt(selectedRow, 0).toString();
                    SachDTO sach = sachBUS.getSachByMaSach(bookId);

                    if (sach != null) {
                        ImageIcon finalIcon = null;
                        BufferedImage originalImage = null;
                        try {
                            String imgName = sach.getImg();
                            System.out.println("Image name from database: " + imgName);
                            if (imgName != null && !imgName.trim().isEmpty()) {
                                String absoluteImagePath = System.getProperty("user.dir") + "/images/Book/" + imgName;
                                System.out.println("Constructed image path: " + absoluteImagePath);
                                java.nio.file.Path imagePath = java.nio.file.Paths.get(absoluteImagePath);
                                File imageFile = imagePath.toFile();
                                if (imageFile.exists() && imageFile.isFile()) {
                                    originalImage = ImageIO.read(imageFile);
                                    if (originalImage != null) {
                                        System.out.println("Successfully read image file: " + absoluteImagePath);
                                        int targetWidth = imagePanel.getPreferredSize().width;
                                        int targetHeight = imagePanel.getPreferredSize().height;
                                        if (targetWidth <= 0)
                                            targetWidth = 200;
                                        if (targetHeight <= 0)
                                            targetHeight = 250;
                                        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight,
                                                Image.SCALE_SMOOTH);
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
                            System.err.println(
                                    "IOException reading image file: " + sach.getImg() + " - " + ioEx.getMessage());
                            ioEx.printStackTrace();
                        } catch (Exception ex) {
                            System.err.println(
                                    "General error processing image " + sach.getImg() + ": " + ex.getMessage());
                            ex.printStackTrace();
                        }

                        if (finalIcon == null) {
                            System.err.println("Attempting to load and scale default image...");
                            try {
                                BufferedImage defaultOriginal = null;
                                String defaultImagePath = System.getProperty("user.dir") + "/images/Book/default.jpg";
                                File defaultImageFile = new File(defaultImagePath);
                                if (defaultImageFile.exists()) {
                                    defaultOriginal = ImageIO.read(defaultImageFile);
                                } else {
                                    System.err.println("Default image file not found at: " + defaultImagePath);
                                }
                                if (defaultOriginal != null) {
                                    int targetWidth = imagePanel.getPreferredSize().width;
                                    int targetHeight = imagePanel.getPreferredSize().height;
                                    if (targetWidth <= 0)
                                        targetWidth = 200;
                                    if (targetHeight <= 0)
                                        targetHeight = 250;
                                    Image scaledDefault = defaultOriginal.getScaledInstance(targetWidth, targetHeight,
                                            Image.SCALE_SMOOTH);
                                    finalIcon = new ImageIcon(scaledDefault);
                                    System.out.println("Loaded and scaled default image.");
                                }
                            } catch (IOException ioEx) {
                                System.err.println("IOException reading default image: " + ioEx.getMessage());
                            } catch (Exception ex) {
                                System.err.println("General error processing default image: " + ex.getMessage());
                            }
                        }

                        imageLabel.setIcon(finalIcon);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                    } else {
                        System.err.println("SachDTO not found for ID: " + bookId);
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
        // table
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
                    lastSelectedRow = -1;
                } else if (selectedRow >= 0) {
                    String bookId = (String) table_down.getValueAt(selectedRow, 0);
                    txt_array_down[0].setText(bookId);
                    txt_array_down[1].setText(String.valueOf(table_down.getValueAt(selectedRow, 2)));
                    for (JTextField txt : txt_array_down) {
                        txt.setEditable(false);
                    }
                    lastSelectedRow = selectedRow;
                }
            }
        });

        JPanel panelTable = new JPanel();
        panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS)); // Stack components vertically
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTable.add(scrollPane);
        panelTable.add(createButtonPanel());
        panelTable.add(Box.createVerticalStrut(50)); // Add 20px spacing
        panelTable.setBorder(BorderFactory.createEmptyBorder(90, 10, 0, 10));
        // panelTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panelTable;
    }

    private JPanel createDetailPanel_top(int width, int padding_top, JTextField[] txt_array,
            String[] txt_label, ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 320, 2, 7, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));

        // txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName,
        // txt_point, txt_date, txt_total
        txt_array[0].setEditable(false); // txt_invoiceId
        txt_array[1].setEditable(false); // txt_employeeName
        txt_array[1].setText(nv.getHoTen());
        txt_array[2].setEditable(true); // txt_customerPhone
        txt_array[3].setText("Anonymous"); // txt_customerName
        txt_array[5].setEditable(false); // txt_date
        txt_array[5].setText(LocalDate.now().toString());
        txt_array[6].setText("");
        txt_array[6].setEditable(false); // txt_total

        txt_array[2].addKeyListener(new java.awt.event.KeyAdapter() {
            private String previousPhoneNumber = "";

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String sdt = txt_array[2].getText().trim();
                if (sdt.equals(previousPhoneNumber)) {
                    return; // Skip if the phone number hasn't changed
                }
                previousPhoneNumber = sdt;
                if (sdt.matches("(02|03|05|07|08|09)\\d{8}")) {
                    KhachHangDTO khachHang = khachHangBUS.getMaKhachHangBySdt(sdt);
                    if (khachHang != null) {
                        txt_array[3].setText(khachHang.getHoTen());
                        txt_array[3].setEditable(false);
                        txt_array[4].setText(khachHang.getDiem() + "");
                        txt_array[4].setEditable(true);
                        System.out.println("Tong tien: " + txt_array[6].getText());

                        int tien = Integer.parseInt(txt_array[6].getText());
                        if (khachHang.getDiem() > tien) {
                            tienGiamGia = 0;
                        } else
                            tienGiamGia = Integer.parseInt(txt_array[4].getText()) * 1000;
                    } else {
                        txt_array[3].setEditable(true);
                        txt_array[3].setText("");
                        txt_array[4].setText("0");
                        txt_array[4].setEditable(false);
                        tienGiamGia = 0;
                    }
                } else if (!sdt.isBlank()) {
                    tienGiamGia = 0;
                    txt_array[3].setText("Số điện thoại không hợp lệ!");
                } else {
                    tienGiamGia = 0;
                    txt_array[4].setText("");
                    txt_array[4].setEditable(false);
                }
                updateTotal();
            }
        });

        txt_array[4].addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String diemStr = txt_array[4].getText();
                if (diemStr.isBlank() || !diemStr.matches("\\d+")) {
                    tienGiamGia = 0;
                } else {
                    int diem = Integer.parseInt(diemStr);
                    int tien = Integer.parseInt(txt_array[6].getText());
                    if (diem * 1000 > tien) {
                        tienGiamGia = 0;
                    } else {
                        tienGiamGia = diem * 1000;
                    }
                }
                updateTotal();
            }
        });
        return wrappedPanel;
    }

    private JPanel createDetailPanel_down(int width, int padding_top, JTextField[] txt_array,
            String[] txt_label) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, null, width, 300, 1, 2, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    private JPanel createButtonPanel() {
        String[] buttonTexts = { "Thêm", "Xóa", "Thanh toán" };
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
        // buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 25));
        // buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        buttons[0].addActionListener(e -> addChiTietHoaDon());
        buttons[1].addActionListener(e -> deleteChiTietHoaDon());
        buttons[2].addActionListener(e -> thanhToan());
        return buttonPanel;
    }

    private void addChiTietHoaDon() {
        txt_array_top[0].setEditable(false);
        txt_array_top[0].setText(getID());
        txt_array_top[5].setText(LocalDate.now().toString());

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
        model.addRow(new Object[] { maSach, tenSach, soLuong, donGia });

        updateTotal();
        int diem = Integer.parseInt(txt_array_top[4].getText());
        int tien = Integer.parseInt(txt_array_top[6].getText());
        if (diem * 1000 > tien) {
            tienGiamGia = 0;
        } else {
            tienGiamGia = diem * 1000;
        }

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

    private void thanhToan() {
        try {
            // txt_invoiceId, txt_employeeName, txt_customerPhone, txt_customerName,
            // txt_point, txt_date, txt_total
            String maHD = txt_array_top[0].getText().trim();
            String maNV = nv.getMaNV();
            String sdtKhach = txt_array_top[2].getText().trim();
            String tenKhach = txt_array_top[3].getText().trim();
            String diemStr = txt_array_top[4].getText().trim();
            String ngayBanStr = txt_array_top[5].getText().trim();
            String tongTienStr = txt_array_top[6].getText().trim();

            KhachHangDTO maKH;
            int diem;

            if (maNV.isEmpty() || tenKhach.isEmpty() || ngayBanStr.isEmpty() || tongTienStr.isEmpty()
                    || (!sdtKhach.isBlank() && diemStr.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin hóa đơn!");
                return;
            }

            if (!sdtKhach.isBlank() && !sdtKhach.matches("(02|03|05|07|08|09)\\d{8}")) {
                JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!");
                return;
            }

            if (!nhanVienBUS.isNhanVienExists(maNV)) {
                JOptionPane.showMessageDialog(null, "Mã nhân viên không tồn tại!");
                return;
            }

            if (!sdtKhach.isBlank() && !diemStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Điểm tích lũy được áp dụng phải chứa ký tự số");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table_down.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng thêm ít nhất một sách vào hóa đơn!");
                return;
            }

            if (!sdtKhach.isEmpty()) {
                maKH = khachHangBUS.getMaKhachHangBySdt(sdtKhach);
                if (maKH == null) {
                    maKH = new KhachHangDTO();
                    maKH.setMaKH(getNextMaKH());
                    maKH.setHoTen(tenKhach);
                    maKH.setSdt(sdtKhach);
                    maKH.setTrangThaiXoa(0);
                    maKH.setDiem(0);
                    boolean b = khachHangBUS.addKhachHang(maKH);
                }
            } else {
                maKH = khachHangBUS.getKhachHangByMaKH("KH000");
                diemStr = "null";
            }

            if (!diemStr.equals("null")) {
                diem = Integer.parseInt(diemStr);

                if (diem < 0) {
                    JOptionPane.showMessageDialog(null,
                            "Điểm tích lũy được áp dụng phải lớn hơn 0 và phải chứa ký tự số");
                    return;
                }

                if (maKH.getDiem() < diem) {
                    JOptionPane.showMessageDialog(null, "Điểm tích lũy của khách hàng không đủ để được áp dụng");
                    return;
                }
            } else {
                diem = 0;
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
            Inhoadon();
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
            txt_array_top[0].setEditable(false);
            txt_array_top[1].setText(nv.getHoTen());
            txt_array_top[1].setEditable(false);
            txt_array_top[3].setText("Anonymous");
            txt_array_top[3].setEditable(false);
            txt_array_top[4].setEditable(false);
            txt_array_top[5].setText(LocalDate.now().toString());
            txt_array_top[5].setEditable(false);
            txt_array_top[6].setEditable(false);
            initializeHoaDon();
            if (diem != 0)
                maKH.setDiem(maKH.getDiem() - diem);
            else
                maKH.setDiem(maKH.getDiem() + (int) (tongTien / 1000));
            khachHangBUS.updateKhachHang(maKH);

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

        if (tienGiamGia > tongTien) {
            txt_array_top[4].setText(String.valueOf(tongTien));
        }
        tongTien -= tienGiamGia;
        txt_array_top[6].setText(String.valueOf(tongTien));
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
        g2d.setFont(new Font("New Times Roman", Font.BOLD, 18));
        g2d.drawString("HÓA ĐƠN BÁN HÀNG", 500, 30);
        g2d.drawString("CỬA HÀNG BÁN SÁCH", 75, 30);
        g2d.setFont(new Font("New Times Roman", Font.BOLD, 16));
        g2d.drawString("Địa chỉ:............................. ", 51, 54);
        g2d.drawString("ĐT:....................................", 51, 78);
        g2d.drawString("Mặt hàng bán (Hoặc nghành nghề kinh doanh)", 423, 66);
        g2d.drawString("Tên Khách hàng:.....................................", 51, 128);
        g2d.drawString("Địa chỉ:.....................................", 51, 152);
        g2d.drawLine(51, 160, imgWidth - 51, 160);

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
                g2d.drawString(String.valueOf(txt_array_top[6].getText()) + "VNĐ", 700, 235 + row * 25);
            }
        }
        g2d.drawLine(51, 204 - 12, imgWidth - 51, 204 - 12);

        // Draw footer
        g2d.getFont().deriveFont(16f);
        g2d.drawString(
                "Thành tiền:........................." + txt_array_top[6].getText()
                        + "..VNĐ..............................................................",
                51, 245 + rowCount * 25);
        g2d.drawString("Ngày ......... Tháng .......... Năm ..........", 490, 270 + rowCount * 25);
        g2d.drawString("KHÁCH HÀNG", 150, 350 + rowCount * 25);
        g2d.drawString("NGƯỜI BÁN HÀNG", 470, 350 + rowCount * 25);
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File("HoaDonBan\\Hoa_Don_Ban.png"));
            Desktop.getDesktop().open(new File("HoaDonBan\\Hoa_Don_Ban.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JFrame frame = new JFrame("Hóa đơn bán hàng");
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