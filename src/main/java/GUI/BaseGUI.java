package GUI;
import java.util.ArrayList;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietHoaDonBUS;
import BUS.ChiTietPhieuNhapBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NXBBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.HoaDonDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import Utils.TableRefreshListener;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.Dimension;

public abstract class BaseGUI extends JPanel implements TableRefreshListener {
    protected static final int WIDTH = 1200;
    protected static final int SIDE_MENU_WIDTH = 151;
    protected static final int HEIGHT = (int) (WIDTH * 0.625);

    protected ToolTmp tool = new ToolTmp();
    protected JTextField txt_search;
    protected ArrayList<JButton> buttons;
    protected ArrayList<JTextField> txt_array_top;
    protected ArrayList<JTextField> txt_array_down;
    protected JComboBox<String> comboBox;

    protected JTable table_down, table_top;
    protected JLabel imageLabel;
    protected JPanel imagePanel;
    protected ImageIcon finalIcon;

    protected int selectedRow = -1;
    protected int lastSelectedRow = -1;
    protected int count = 0;

    // sell books
    protected SachBUS sachBUS = new SachBUS();
    protected List<SachDTO> sachList;
    protected List<HoaDonDTO> hoaDonList;
    protected NhanVienBUS nhanVienBUS = new NhanVienBUS();
    protected HoaDonBUS hoaDonBUS = new HoaDonBUS();
    protected ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();
    protected KhachHangBUS khachHangBUS = new KhachHangBUS();
    protected NhanVienDTO nv;

    // import books
    protected List<ChiTietPhieuNhapDTO> chiTietPhieuNhapList;
    protected List<PhieuNhapDTO> phieuNhapList;
    protected PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    protected NXBBUS nhaXuatBanBUS = new NXBBUS();
    protected ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();



    protected void initializeTextFields(ArrayList<JTextField> textFieldList, int count) {
        for (int i = 0; i < count; i++) {
            JTextField textField = new JTextField();
            textFieldList.add(textField);
        }
    }

    protected JPanel createButtonPanel(String [] buttonTexts, String xy) {
        if (buttons == null && buttonTexts.length > 0) {
            buttons = new ArrayList<>(buttonTexts.length);
            for (int i = 0; i < buttonTexts.length; i++) {
                JButton btn = new JButton();
                buttons.add(btn);
            }
        }
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons, buttonTexts, new Color(0, 36, 107), Color.WHITE, xy));
        return buttonPanel;
    }

    protected JPanel createSearchPanel() {
        String[] searchOptions = { "Mã sách", "Tên sách" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_search));
        return searchPanel;
    }

    protected JPanel createTable_top(String[] column, DefaultTableModel model, int width, int height) {
        table_top = tool.createTable(model, column);
        table_top.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_top);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        addEventTable(table_top, 1);
        return panelTable;
    }

    protected JPanel createTable_down(String []column, DefaultTableModel model, int width, int height) {
        table_down = tool.createTable(model, column);
        table_down.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(table_down);
        scrollPane.setPreferredSize(new Dimension(width, height));

        addEventTable(table_down, 2);
        JPanel panelTable = new JPanel();
        panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS)); // Stack components vertically
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelTable.add(scrollPane);
        panelTable.add(createButtonPanel(new String[] { "Thêm", "Xóa", "Thanh toán" }, "x"));
        panelTable.add(Box.createVerticalStrut(50)); // Add 20px spacing
        panelTable.setBorder(BorderFactory.createEmptyBorder(90, 10, 0, 10));
        // panelTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panelTable;
    }

    protected JPanel createDetailPanel(int width, int padding_top, ArrayList<JTextField> txt_array,
        String[] txt_label, ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 320, 2, txt_label.length, false);
        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    protected abstract void addEventTable(JTable table, int type);

    protected void initializeBaseFields() {
        txt_search = new JTextField();
        txt_array_top = new ArrayList<>();
        txt_array_down = new ArrayList<>();
        sachList = sachBUS.getAllSach();
    }

    protected void addChiTietHoaDon(int status, int date_index, int count, String prefix) {
        // status = 0: sell; status = 1; import
        txt_array_top.get(0).setEditable(false);
        txt_array_top.get(0).setText(getID(prefix, count));
        txt_array_top.get(date_index).setText(LocalDate.now().toString());
                selectedRow = table_top.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sách từ danh sách!");
            return;
        }
        String maSach = table_top.getValueAt(selectedRow, 0).toString();
        String tenSach = table_top.getValueAt(selectedRow, 1).toString();
        String soLuongStr = txt_array_down.get(1).getText().trim();
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

        if (status == 0 && soLuong > sach.getSoLuong()) {
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
    }

    protected void deleteChiTietHoaDon() {
        selectedRow = table_down.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_down.getModel();
        model.removeRow(selectedRow);
        // JOptionPane.showMessageDialog(null, "Xóa chi tiết hóa đơn thành công!");
    }

    @Override
    public void refreshTable() {
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

    public void Inhoadon(int status) {
        // status = 0: sell; status = 1: import
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
        g2d.drawString((status == 0 ? "HÓA ĐƠN BÁN HÀNG" : "HÓA ĐƠN NHẬP HÀNG"), 500, 30);
        g2d.drawString("CỬA HÀNG BÁN SÁCH", 75, 30);
        g2d.setFont(new Font("New Times Roman", Font.BOLD, 16));
        g2d.drawString("Địa chỉ:............................. ", 51, 54);
        g2d.drawString("ĐT:....................................", 51, 78);
        g2d.drawString("Mặt hàng nhập (Hoặc nghành nghề kinh doanh)", 423, 66);
        g2d.drawString("Tên nhân viên:.....................................", 51, 128);
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
                g2d.drawString(String.valueOf((status == 0 ? txt_array_top.get(6).getText() : txt_array_top.get(4).getText())) + "VNĐ", 700, 235 + row * 25);
            }
        }
        g2d.drawLine(51, 204 - 12, imgWidth - 51, 204 - 12);

        // Draw footer
        g2d.getFont().deriveFont(16f);
        g2d.drawString(
                "Thành tiền:........................." + (status == 0 ? txt_array_top.get(6).getText() : txt_array_top.get(4).getText())
                        + "..VNĐ..............................................................",
                51, 245 + rowCount * 25);
        g2d.drawString("Ngày ......... Tháng .......... Năm ..........", 490, 270 + rowCount * 25);
        g2d.drawString((status == 0 ? "KHÁCH HÀNG" : "NGƯỜI GIAO HÀNG"), 150, 350 + rowCount * 25);
        g2d.drawString((status == 0 ? "NGƯỜI BÁN HÀNG" : "NHÂN VIÊN"), 470, 350 + rowCount * 25);
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File((status == 0 ? "HoaDonBan\\Hoa_Don_Ban.png" : "HoaDonNhap\\Hoa_Don_Nhap.png")));
            Desktop.getDesktop().open(new File((status == 0 ? "HoaDonBan\\Hoa_Don_Ban.png" : "HoaDonNhap\\Hoa_Don_Nhap.png")));
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

    public void renderBookImage(String bookId, SachDTO sach) {
        finalIcon = null;
        BufferedImage originalImage = null;
        try {
            String imgName = sach.getImg();
            System.out.println("Image name from database: " + imgName);
            if (imgName != null && !imgName.trim().isEmpty()) {
                String absoluteImagePath = System.getProperty("user.dir") + "\\images\\Book\\" + imgName;
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
                String defaultImagePath = System.getProperty("user.dir") + "\\images\\Book\\default.jpg";
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

    }

    protected String getID(String prefix, int count) {
        String str = String.format("%02d", count);
        return prefix + str;
    }

    protected void timkiem(int status) {
        txt_search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(status, txt_search.getText(), (String) comboBox.getSelectedItem());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(status, txt_search.getText(), (String) comboBox.getSelectedItem());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(status, txt_search.getText(), (String) comboBox.getSelectedItem());
            }
        });

        comboBox.addActionListener(
                e -> filterTable(status, txt_search.getText(), (String) comboBox.getSelectedItem()));
    }


    protected void filterTable(int status, String query, String searchType) {
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
                            sach.getDonGia() + (status == 0 ? 10000 : 0)
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }


    protected abstract void initializeCustomFields();
}