package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
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

import BUS.ChiTietPhieuNhapBUS;
import BUS.NXBBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.NXBDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;
import DTO.TaiKhoanNVDTO;
import Utils.EventManager;
import Utils.TableRefreshListener;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class HoaDonNhapGUI implements TableRefreshListener {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 660;

    private Tool tool = new Tool();
    private JButton[] buttons = new JButton[4]; // Increased to 4 for "Xem chi tiết"
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;

    private List<PhieuNhapDTO> phieuNhapList;
    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
    private NXBBUS nxbBUS = new NXBBUS();
    private SachBUS sachBUS = new SachBUS();
    private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    public HoaDonNhapGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        initializeData();
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createHoaDonNhapTable(), BorderLayout.WEST);
        panel.add(createPanelButton(null), BorderLayout.CENTER);
        timkiem();
        EventManager.getInstance().registerListener(this);
    }

    private void initializeData() {
        try {
            phieuNhapList = phieuNhapBUS.getAllPhieuNhap();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu nhập: " + e.getMessage());
        }
    }

    private JPanel createHoaDonNhapTable() {
        String[] columns = { "Mã phiếu nhập", "Mã nhân viên", "Ngày nhập", "Tổng tiền", "Mã NXB" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (phieuNhapList != null) {
            for (PhieuNhapDTO phieuNhap : phieuNhapList) {
                tableModel.addRow(new Object[] {
                        phieuNhap.getMaPN(),
                        phieuNhap.getMaNV(),
                        phieuNhap.getNgayNhap().toString(),
                        phieuNhap.getTongTien(),
                        phieuNhap.getMaNXB()
                });
            }
        }

        table = tool.createTable(tableModel, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton(TaiKhoanNVDTO account) {
        String[] btnText = { "Chi tiết", "Nhập Excel", "Xuất Excel", "Xem chi tiết" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, btnText, new Color(0, 36, 107), Color.WHITE, "y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttons[i].setFocusable(false);
        }
        buttons[0].addActionListener(e -> showInvoiceDetails());
        // buttons[1].addActionListener(e -> importFromExcel());
        buttons[2].addActionListener(e -> exportToExcel());
        buttons[3].addActionListener(e -> viewInvoiceDetails());

        return panelBtn;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã phiếu nhập", "Mã nhân viên", "Mã NXB" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
    }

    private void timkiem() {
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

    private void filterTable(String query, String searchType) {
        tableModel.setRowCount(0);
        try {
            for (PhieuNhapDTO phieuNhap : phieuNhapList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã phiếu nhập":
                        match = phieuNhap.getMaPN().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Mã nhân viên":
                        match = phieuNhap.getMaNV().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Mã NXB":
                        match = phieuNhap.getMaNXB().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    tableModel.addRow(new Object[] {
                            phieuNhap.getMaPN(),
                            phieuNhap.getMaNV(),
                            phieuNhap.getNgayNhap().toString(),
                            phieuNhap.getTongTien(),
                            phieuNhap.getMaNXB()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    private void viewInvoiceDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu nhập để xem chi tiết!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String maPN = table.getValueAt(selectedRow, 0).toString();
        String maNXB = table.getValueAt(selectedRow, 4).toString();
        String ngayNhap = table.getValueAt(selectedRow, 2).toString();
        double tongTien = Double.parseDouble(table.getValueAt(selectedRow, 3).toString());

        // Fetch supplier details
        NXBDTO nxb = nxbBUS.getNhaXuatBanByMaNXB(maNXB);
        String tenNXB = nxb != null ? nxb.getTenNXB() : "N/A";

        // Fetch invoice details
        List<ChiTietPhieuNhapDTO> chiTietList = chiTietPhieuNhapBUS.getChiTietPhieuNhapByMaPN(maPN);
        if (chiTietList == null || chiTietList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết phiếu nhập!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate image dimensions
        int rowCount = chiTietList.size();
        int imgWidth = 900;
        int imgHeight = 500 + rowCount * 25 + 150;
        BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();

        // Set background and font
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, imgHeight);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 18));

        // Draw header
        g2d.drawString("HÓA ĐƠN NHẬP HÀNG", imgWidth / 2 - 100, 30);
        g2d.drawString("CỬA HÀNG BÁN SÁCH", 50, 50);
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        // TODO: Replace with actual store address and phone from config or database
        g2d.drawString("Địa chỉ: 123 Đường Sách, Quận 1, TP.HCM", 50, 70);
        g2d.drawString("ĐT: 0123-456-789", 50, 90);

        // Draw supplier info
        g2d.drawString("Nhà Xuất Bản: " + tenNXB, 50, 120);
        g2d.drawString("Mã NXB: " + maNXB, 50, 140);
        g2d.drawLine(50, 150, imgWidth - 50, 150);

        // Draw table headers
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 16));
        g2d.drawString("STT", 60, 170);
        g2d.drawString("TÊN HÀNG", 120, 170);
        g2d.drawString("SỐ LƯỢNG", 400, 170);
        g2d.drawString("ĐƠN GIÁ", 520, 170);
        g2d.drawString("THÀNH TIỀN", 650, 170);
        g2d.drawLine(50, 175, imgWidth - 50, 175);

        // Draw table content
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        int totalAmount = 0;
        for (int i = 0; i < rowCount; i++) {
            ChiTietPhieuNhapDTO chiTiet = chiTietList.get(i);
            SachDTO sach = sachBUS.getSachByMaSach(chiTiet.getMaSach());
            if (sach == null) {
                JOptionPane.showMessageDialog(null, "Sách không tồn tại: " + chiTiet.getMaSach(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                g2d.dispose();
                return;
            }

            String stt = String.valueOf(i + 1);
            String tenHang = sach.getTenSach();
            String soLuong = String.valueOf(chiTiet.getSoLuong());
            String donGia = String.valueOf(chiTiet.getGiaNhap());
            int thanhTien;
            try {
                thanhTien = chiTiet.getSoLuong() * chiTiet.getGiaNhap();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Dữ liệu số lượng hoặc đơn giá không hợp lệ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                g2d.dispose();
                return;
            }
            totalAmount += thanhTien;

            int y = 195 + i * 25;
            g2d.drawString(stt, 60, y);
            g2d.drawString(tenHang.length() > 30 ? tenHang.substring(0, 30) + "..." : tenHang, 120, y);
            g2d.drawString(soLuong, 400, y);
            g2d.drawString(donGia, 520, y);
            g2d.drawString(String.valueOf(thanhTien), 650, y);

            g2d.drawLine(50, 150, 50, 200 + rowCount * 25);
            g2d.drawLine(100, 150, 100, 200 + rowCount * 25);
            g2d.drawLine(380, 150, 380, 200 + rowCount * 25);
            g2d.drawLine(500, 150, 500, 200 + rowCount * 25);
            g2d.drawLine(620, 150, 620, 200 + rowCount * 25);
            g2d.drawLine(imgWidth - 50, 150, imgWidth - 50, 200 + rowCount * 25);
            g2d.drawLine(50, 200 + i * 25, imgWidth - 50, 200 + i * 25);
        }

        // Draw total row
        int tableEndY = 200 + rowCount * 25;
        g2d.drawLine(50, tableEndY, imgWidth - 50, tableEndY);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 16));
        g2d.drawString("TỔNG CỘNG:", 120, tableEndY + 25);
        g2d.drawString(totalAmount + " VNĐ", 650, tableEndY + 25);

        // Draw footer
        g2d.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        try {
            LocalDate date = LocalDate.parse(ngayNhap);
            g2d.drawString(String.format("Ngày %02d Tháng %02d Năm %04d",
                    date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                    500, tableEndY + 60);
        } catch (Exception e) {
            g2d.drawString("Ngày: " + ngayNhap, 500, tableEndY + 60);
        }
        g2d.drawString("NHÀ CUNG CẤP", 150, tableEndY + 100);
        g2d.drawString("NGƯỜI NHẬN HÀNG", 500, tableEndY + 100);
        g2d.drawString("(Ký và ghi rõ họ tên)", 150, tableEndY + 120);
        g2d.drawString("(Ký và ghi rõ họ tên)", 500, tableEndY + 120);

        g2d.dispose();

        // Save and display invoice
        try {
            File outputDir = new File("HoaDonNhap");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File outputFile = new File(outputDir, "Chi_Tiet_Hoa_Don_Nhap_" + maPN + ".png");
            ImageIO.write(img, "png", outputFile);
            Desktop.getDesktop().open(outputFile);
            JFrame frame = new JFrame("Hóa đơn nhập hàng - " + maPN);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(imgWidth, imgHeight);
            frame.setLocationRelativeTo(null);
            JLabel label = new JLabel(new ImageIcon(img));
            frame.add(label);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu hoặc hiển thị hóa đơn: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showInvoiceDetails() {
        BufferedImage img = new BufferedImage(600, 800, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 800);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("HÓA ĐƠN NHẬP HÀNG", 200, 50);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawRect(30, 80, 540, 80);

        int sel = table.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hóa đơn để xem chi tiết!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            g.dispose();
            return;
        }
        String maNV = (String) table.getValueAt(sel, 1);
        NhanVienDTO nv = nhanVienBUS.getNhanVienByMaNV(maNV);
        if (nv != null) {
            g.drawString("Mã Nhân Viên: " + nv.getMaNV(), 40, 100);
            g.drawString("Tên Nhân Viên: " + nv.getHoTen(), 40, 150);
        }

        g.drawRect(30, 180, 540, 300);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        int[] colX = { 30, 150, 260, 380, 480 };
        for (int x : colX) {
            g.drawLine(x, 180, x, 180 + 300);
        }

        int[] rows = table.getSelectedRows();
        int n = rows.length;
        if (n == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hóa đơn để xem chi tiết!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            g.dispose();
            return;
        }
        if (n > 5) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn không quá 5 hóa đơn để xem chi tiết!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            g.dispose();
            return;
        }

        int detailH = (300 - 30) / n;
        int ascent = g.getFontMetrics().getAscent();
        g.drawLine(30, 210, 30 + 540, 210);
        for (int i = 1; i <= n; i++) {
            int y = 180 + 30 + i * detailH;
            g.drawLine(30, y, 30 + 540, y);
        }

        String[] labels = { "Mã Phiếu Nhập", "Mã Nhân Viên", "Ngày Nhập", "Tổng tiền", "Mã NXB" };
        int yLabel = 180 + (30 + ascent) / 2;
        for (int c = 0; c < labels.length; c++) {
            g.drawString(labels[c], colX[c] + 10, yLabel);
        }

        g.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < n; i++) {
            int rowIndex = rows[i];
            int yRow = 180 + 30 + i * detailH + (detailH + ascent) / 2;
            for (int c = 0; c < labels.length; c++) {
                String txt = table.getValueAt(rowIndex, c).toString();
                g.drawString(txt, colX[c] + 10, yRow);
            }
        }

        g.drawString("Ký tên:", 330, 520);
        LocalDate t = LocalDate.now();
        String dateStr = "Ngày " + t.getDayOfMonth() + " Tháng " + t.getMonthValue() + " Năm " + t.getYear();
        g.drawString(dateStr, 330, 640);
        g.dispose();

        try {
            File outputDir = new File("HoaDonNhap");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File outputFile = new File(outputDir, "Hoa_Don_Nhap.png");
            ImageIO.write(img, "png", outputFile);
            Desktop.getDesktop().open(outputFile);
            JFrame frame = new JFrame("Hóa đơn nhập hàng");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 800);
            frame.setLocationRelativeTo(null);
            JLabel label = new JLabel(new ImageIcon(img));
            frame.add(label);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu hóa đơn: " + e.getMessage());
        }
    }

    // private void importFromExcel() {
    // //Tú bảo bỏ cái này
    // }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu Phiếu Nhập dưới dạng Excel");
        fileChooser.setSelectedFile(new File("Danh_sach_phieu_nhap.xlsx"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Phiếu Nhập");

                // Tạo header
                XSSFRow headerRow = sheet.createRow(0);
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    XSSFCell cell = headerRow.createCell(col);
                    cell.setCellValue(tableModel.getColumnName(col));
                }

                // Ghi dữ liệu
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    XSSFRow excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        XSSFCell cell = excelRow.createCell(col);
                        Object value = tableModel.getValueAt(row, col);
                        if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue());
                        } else {
                            cell.setCellValue(value != null ? value.toString() : "");
                        }
                    }
                }

                // Tự động điều chỉnh độ rộng cột
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
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

    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public void refreshTable() {
        initializeData();
        tableModel.setRowCount(0);
        for (PhieuNhapDTO phieuNhap : phieuNhapList) {
            tableModel.addRow(new Object[] {
                    phieuNhap.getMaPN(),
                    phieuNhap.getMaNV(),
                    phieuNhap.getNgayNhap().toString(),
                    phieuNhap.getTongTien(),
                    phieuNhap.getMaNXB()
            });
        }
    }
}