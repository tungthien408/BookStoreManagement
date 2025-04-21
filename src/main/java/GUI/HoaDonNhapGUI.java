package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import BUS.KhachHangBUS;
import BUS.NXBBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SachDTO;

public class HoaDonNhapGUI {
    private static final int WIDTH = 1200;
    private static final int SIDE_MENU_WIDTH = 151;
    private static final int HEIGHT = (int) (WIDTH * 0.625);
    private static final int TABLE_WIDTH = 850;
    private static final int TABLE_HEIGHT = 660;
    private static final int SEARCH_FIELD_WIDTH = 300;
    private static final int SEARCH_FIELD_HEIGHT = 30;

    private Tool tool = new Tool();
    private JButton[] buttons = new JButton[3];
    private JPanel panel;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<PhieuNhapDTO> phieuNhapList;
    private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS = new ChiTietPhieuNhapBUS();
    private NXBBUS nxbBUS = new NXBBUS();
    private SachBUS sachBUS = new SachBUS();

    public HoaDonNhapGUI() {
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        initializeData();
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createHoaDonNhapTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);
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
                return false; // Make table non-editable
            }
        };

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu nhập: " + e.getMessage());
        }

        table = tool.createTable(tableModel, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10));

        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String[] btnText = { "Chi tiết", "Nhập Excel", "Xuất Excel" };
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(buttons, btnText, new Color(0, 36, 107), Color.WHITE, "y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Add action listeners for buttons
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInvoiceDetails();
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromExcel();
            }
        });
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });

        return panelBtn;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = { "Mã nhân viên", "SĐT khách hàng" };
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextField(300, 30, searchOptions));
        return searchPanel;
    }

    private void filterTable(String query) {
        tableModel.setRowCount(0);
        try {
            for (PhieuNhapDTO phieuNhap : phieuNhapList) {
                String maPN = phieuNhap.getMaPN().toLowerCase();
                String maNV = phieuNhap.getMaNV().toLowerCase();
                String maNXB = phieuNhap.getMaNXB().toLowerCase();
                if (maPN.contains(query) || maNV.contains(query) || maNXB.contains(query)) {
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
        // headerX = 600/2 - 100 = 200, headerY = 50
        g.drawString("HÓA ĐƠN NHẬP HÀNG", 200, 50);

        // 3. Khung thông tin khách hàng
        g.setFont(new Font("Arial", Font.BOLD, 16));
        // infoX = 30, infoY = 80, infoW = 600-60 = 540, infoH = 80
        g.drawRect(30, 80, 540, 80);

        int sel = table.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn một hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String maNV = (String) table.getValueAt(sel, 1);
        NhanVienBUS nvBUS = new NhanVienBUS();
        NhanVienDTO nv = nvBUS.getNhanVienByMaNV(maNV);
        if (nv != null) {
            // infoTextX = 30 + 10 = 40, infoTextY = 80 + 20 = 100
            g.drawString("Mã Nhân Viên: " + nv.getMaNV(), 40, 100);
            g.drawString("Tên Nhân Viên: " + nv.getHoTen(), 40, 150);
        }

        // 4. Bảng chi tiết sản phẩm
        // tableX = 30, tableY = 180, tableW = 540, tableH = 300
        g.drawRect(30, 180, 540, 300);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        // colX = {30, 140, 250, 380, 480, 570}
        int[] colX = { 30, 150, 260, 380, 480, 570 };
        for (int x : colX) {
            g.drawLine(x, 180, x, 180 + 300);
        }

        // lấy rows đã chọn
        int[] rows = table.getSelectedRows();
        int n = rows.length;
        if (n == 0) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn một hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (n > 5) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn không quá 5 hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // headerH = 30px, phần còn lại = 300 - 30 = 270px chia cho n
        // detailH = 270 / n
        int detailH = (300 - 30) / n;
        int ascent = g.getFontMetrics().getAscent();

        // vẽ ngang: 1 line header tại y = 180 + 30 = 210
        g.drawLine(30, 210, 30 + 540, 210);
        // n dòng detail
        for (int i = 1; i <= n; i++) {
            int y = 180 + 30 + i * detailH;
            g.drawLine(30, y, 30 + 540, y);
        }

        // in tiêu đề cột: yLabel = 180 + (30 + ascent)/2
        String[] labels = {
                "Mã Phiếu Nhập", "Mã Nhân Viên",
                "Ngày Nhập", "Tổng tiền", "Mã NXB"
        };
        int yLabel = 180 + (30 + ascent) / 2;
        for (int c = 0; c < labels.length; c++) {
            g.drawString(labels[c], colX[c] + 10, yLabel);
        }

        // in detail rows, căn giữa theo detailH
        g.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < n; i++) {
            int rowIndex = rows[i];
            // yRow = 180 + 30 + i*detailH + (detailH + ascent)/2
            int yRow = 180 + 30 + i * detailH + (detailH + ascent) / 2;
            for (int c = 0; c < labels.length; c++) {
                String txt = table.getValueAt(rowIndex, c).toString();
                g.drawString(txt, colX[c] + 10, yRow);
            }
        }

        // 5. Tổng cộng và ký tên
        // yTotal = 180 + 300 + 40 = 520, totalX = 30 + 300 = 330
        g.drawString("Ký tên:", 330, 520);
        LocalDate t = LocalDate.now();
        String dateStr = "Ngày " + t.getDayOfMonth()
                + " Tháng " + t.getMonthValue()
                + " Năm " + t.getYear();
        // vẽ ở y = 520 + 120 = 640
        g.drawString(dateStr, 330, 640);
        g.dispose();

        // 6. Xuất file

        try {
            ImageIO.write(img, "png", new File("HoaDonNhap\\Hóa đơn nhập hàng.png"));
            JFrame frame = new JFrame("Hóa đơn nhập hàng");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 800);
            frame.setLocationRelativeTo(null);

            JLabel label = new JLabel(new ImageIcon(img));
            frame.add(label);

            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importFromExcel() {
        // Placeholder for Excel import
        JOptionPane.showMessageDialog(null, "Chức năng Nhập Excel đang được phát triển!");
    }

    private void exportToExcel() {
        // Placeholder for Excel export
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
        // Implement Excel export logic using Apache POI
    }

    public JPanel getPanel() {
        return this.panel;
    }
}