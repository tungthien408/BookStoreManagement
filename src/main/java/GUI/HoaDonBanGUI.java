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
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.io.File;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SachBUS;
import DTO.ChiTietHoaDonDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.SachDTO;

public class HoaDonBanGUI {
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
    // private JTable table;
    // private DefaultTableModel tableModel;
    private List<HoaDonDTO> hoaDonList;
    private JTextField[] txt_array_search = new JTextField[1];
    private JTextField txt_search;
    private JComboBox<String> comboBox;
    private JTable tableModel;
    private HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private SachBUS sachBUS = new SachBUS();
    private KhachHangBUS khachHangBUS = new KhachHangBUS();
    private ChiTietHoaDonBUS chiTietHoaDonBUS = new ChiTietHoaDonBUS();

    public HoaDonBanGUI() {
        txt_search = new JTextField();
        txt_array_search = new JTextField[] { txt_search };
        panel = tool.createPanel(WIDTH - SIDE_MENU_WIDTH, HEIGHT, new BorderLayout());
        initializeData();
        panel.add(createSearchPanel(), BorderLayout.NORTH);
        panel.add(createHoaDonBanTable(), BorderLayout.WEST);
        panel.add(createPanelButton(), BorderLayout.CENTER);
        timkiem();
    }

    private void initializeData() {
        try {
            hoaDonList = hoaDonBUS.getAllHoaDon();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }

    private JPanel createHoaDonBanTable() {
        String[] columns = { "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày bán", "Tổng tiền" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        try {
            if (hoaDonList != null) {
                for (HoaDonDTO hoaDon : hoaDonList) {
                    model.addRow(new Object[] {
                            hoaDon.getMaHD(),
                            hoaDon.getMaNV(),
                            hoaDon.getMaKH(),
                            hoaDon.getNgayBan().toString(),
                            hoaDon.getTongTien()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }

        tableModel = tool.createTable(model, columns);
        JScrollPane scrollPane = new JScrollPane(tableModel);
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
        String[] searchOptions = { "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng" };
        comboBox = new JComboBox<>(searchOptions);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextFieldTest(comboBox, txt_array_search));
        return searchPanel;
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
        g.drawString("HÓA ĐƠN BÁN HÀNG", 200, 50);

        // 3. Khung thông tin khách hàng
        g.setFont(new Font("Arial", Font.BOLD, 16));
        // infoX = 30, infoY = 80, infoW = 600-60 = 540, infoH = 80
        g.drawRect(30, 80, 540, 80);

        int sel = tableModel.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(null,
                    "Vui lòng chọn một hóa đơn để xem chi tiết!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String maKH = (String) tableModel.getValueAt(sel, 2);
        KhachHangBUS khBUS = new KhachHangBUS();
        KhachHangDTO kh = khBUS.getKhachHangByMaKH(maKH);
        if (kh != null) {
            // infoTextX = 30 + 10 = 40, infoTextY = 80 + 20 = 100
            g.drawString("Tên Nhân Viên: " + kh.getHoTen(), 40, 100);
            g.drawString("Mã Nhân viên: " + kh.getSdt(), 40, 125);
            g.drawString("Ngày xuất đơn: " + tableModel.getValueAt(sel, 3), 40, 150);
        }

        // 4. Bảng chi tiết sản phẩm
        // tableX = 30, tableY = 180, tableW = 540, tableH = 300
        g.drawRect(30, 180, 540, 300);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        // colX = {30, 140, 250, 380, 480, 570}
        int[] colX = { 30, 140, 250, 380, 480, 570 };
        for (int x : colX) {
            g.drawLine(x, 180, x, 180 + 300);
        }

        // lấy rows đã chọn
        int[] rows = tableModel.getSelectedRows();
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
                "Mã Đơn Hàng", "Mã Nhân Viên",
                "Mã Khách Hàng", "Ngày bán", "Tổng tiền"
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
                String txt = tableModel.getValueAt(rowIndex, c).toString();
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
            ImageIO.write(img, "png", new File("HoaDonBan\\Hoa_Don_Ban.png"));
            JFrame frame = new JFrame("Hóa đơn bán hàng");
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
        // Implement Excel import logic using a library like Apache POI
    }

    private void exportToExcel() {
        // Placeholder for Excel export
        JOptionPane.showMessageDialog(null, "Chức năng Xuất Excel đang được phát triển!");
        // Implement Excel export logic using a library like Apache POI
    }

    private void timkiem() {
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                System.out.println("Tìm kiếm theo: " + selectedOption);
                // Lọc lại bảng với nội dung hiện tại của searchField
                filterTable(txt_array_search[0].getText(), selectedOption);
            }
        });
    }

    // Phương thức lọc bảng dựa trên nội dung tìm kiếm và tiêu chí
    private void filterTable(String query, String searchType) {
        DefaultTableModel model = (DefaultTableModel) tableModel.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        try {
            for (HoaDonDTO hoaDon : hoaDonList) {
                boolean match = false;
                switch (searchType) {
                    case "Mã hóa đơn":
                        match = hoaDon.getMaHD().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Mã nhân viên":
                        match = hoaDon.getMaNV().toLowerCase().contains(query.toLowerCase());
                        break;
                    case "Mã khách hàng":
                        match = hoaDon.getMaKH().toLowerCase().contains(query.toLowerCase());
                        break;
                }
                if (match) {
                    model.addRow(new Object[] {
                            hoaDon.getMaHD(),
                            hoaDon.getMaNV(),
                            hoaDon.getMaKH(),
                            hoaDon.getNgayBan().toString(),
                            hoaDon.getTongTien()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lọc dữ liệu: " + e.getMessage());
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}