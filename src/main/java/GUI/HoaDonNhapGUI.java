package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.TaiKhoanNVDTO;

public class HoaDonNhapGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 660;

    private Tool tool = new Tool();
    private JButton[] buttons = new JButton[3];
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
        String[] btnText = { "Chi tiết", "Nhập Excel", "Xuất Excel" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, btnText, new Color(0, 36, 107), Color.WHITE, "y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        for (int i = 0; i < btnText.length; i++) {
            buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            buttons[i].setFocusable(false);
        }
        buttons[0].addActionListener(e -> showInvoiceDetails());
        buttons[1].addActionListener(e -> importFromExcel());
        buttons[2].addActionListener(e -> exportToExcel());

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
        comboBox.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            filterTable(txt_array_search[0].getText(), selectedOption);
        });
        // Add key listener for real-time search
        txt_array_search[0].addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                filterTable(txt_array_search[0].getText(), selectedOption);
            }
        });
    }

    private void filterTable(String query, String searchType) {
        tableModel.setRowCount(0); // Clear existing data
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

    private void showInvoiceDetails() {
        // 1. Tạo ảnh trống RGB với nền trắng
        BufferedImage img = new BufferedImage(600, 800, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 600, 800);

        // 2. Vẽ header
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("HÓA ĐƠN NHẬP HÀNG", 200, 50);

        // 3. Khung thông tin nhân viên
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawRect(30, 80, 540, 80);

        int sel = table.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn một hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            g.dispose();
            return;
        }
        String maNV = (String) table.getValueAt(sel, 1);
        NhanVienDTO nv = nhanVienBUS.getNhanVienByMaNV(maNV);
        if (nv != null) {
            g.drawString("Mã Nhân Viên: " + nv.getMaNV(), 40, 100);
            g.drawString("Tên Nhân Viên: " + nv.getHoTen(), 40, 150);
        }

        // 4. Bảng chi tiết sản phẩm
        g.drawRect(30, 180, 540, 300);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        int[] colX = { 30, 150, 260, 380, 480 };
        for (int x : colX) {
            g.drawLine(x, 180, x, 180 + 300);
        }

        int[] rows = table.getSelectedRows();
        int n = rows.length;
        if (n == 0) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn một hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            g.dispose();
            return;
        }
        if (n > 5) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn không quá 5 hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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

        String[] labels = {
                "Mã Phiếu Nhập", "Mã Nhân Viên",
                "Ngày Nhập", "Tổng tiền", "Mã NXB"
        };
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

        // 5. Tổng cộng và ký tên
        g.drawString("Ký tên:", 330, 520);
        LocalDate t = LocalDate.now();
        String dateStr = "Ngày " + t.getDayOfMonth()
                + " Tháng " + t.getMonthValue()
                + " Năm " + t.getYear();
        g.drawString(dateStr, 330, 640);
        g.dispose();

        // 6. Xuất file
        try {
            File outputDir = new File("HoaDonNhap");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File outputFile = new File(outputDir, "Hóa đơn nhập hàng.png");
            ImageIO.write(img, "png", outputFile);
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

    private void importFromExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel đang được phát triển!");
    }

    private void exportToExcel() {
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
    }

    public JPanel getPanel() {
        return this.panel;
    }
}