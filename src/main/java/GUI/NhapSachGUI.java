package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import BUS.ChiTietPhieuNhapBUS;
import BUS.NXBBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SachBUS;

public class NhapSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    // JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total, txt_name, txt_quantity;
    JButton[] buttons = new JButton[3];
    JTextField[] txt_array_top = new JTextField[5];
    JTextField[] txt_array_down = new JTextField[2];
    private PhieuNhapBUS phieuNhapBUS;
    private ChiTietPhieuNhapBUS chiTietPhieuNhapBUS;
    private NXBBUS nhaXuatBanBUS;
    private SachBUS sachBUS;
    private NhanVienBUS nhanVienBUS;

         // fake data
    String tableContent_book[][] = {
        {"S001", "Book A", "10", "100"},
        {"S002", "Book B", "15", "150"},
        {"S003", "Book C",  "20", "200"},
        {"S004", "Book D",  "25", "250"},
        {"S005", "Book E", "30", "300"},
        {"S006", "Book F", "35", "350"},
        {"S007", "Book G", "40", "400"},
        {"S008", "Book H",  "45", "450"},
        {"S009", "Book I",  "50", "500"},
        {"S010", "Book J",  "55", "550"},
        {"S011", "Book K", "60", "600"},
        {"S012", "Book L", "65", "650"},
        {"S013", "Book M",  "70", "700"},
        {"S040", "Book AN", "205", "2050"}      
    };
    String nameField_book[] = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};

    public NhapSachGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.add(createSearchPanel(), BorderLayout.NORTH); // search panel
   
        // table
        panel.add(createTable(650, 10,tableContent_book, nameField_book), BorderLayout.WEST);

        // detail (top right)
        // JTextField txt_array_top[] = {txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total};
        txt_array_top[0] = new JTextField(20);
        txt_array_top[1] = new JTextField(20);
        txt_array_top[2] = new JTextField(20);
        txt_array_top[3] = new JTextField(20);
        
        String txt_label_top[] = {"Mã phiếu nhập", "Mã NV", "NXB", "Ngày nhập", "Tổng tiền"};
        panel.add(createDetailPanel(400, 30, txt_array_top, txt_label_top, null), BorderLayout.CENTER);
        
        txt_array_down[0] = new JTextField(20);
        txt_array_down[1] = new JTextField(20);
        JPanel paymentPanel = tool.createPanel(width - width_sideMenu, (int)(height * 0.55), new BorderLayout());
        // JTextField txt_array[] = {txt_name, txt_quantity};
        String[] txt_label_down = {"Tên sách", "Số lượng"};
        // detail (bottom right)
paymentPanel.add(createDetailPanel(500, 10, txt_array_down, txt_label_down, new ImageIcon("images/Book/the_little_prince.jpg")), BorderLayout.WEST);
        
        // buttons
        paymentPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        String tableContent_payment[][] = {
            {"S041", "Book AO", "5", "500"},
            {"S042", "Book AP", "10", "1000"},
            {"S043", "Book AQ", "15", "1500"},
            {"S044", "Book AR", "20", "2000"},
            {"S045", "Book AS", "25", "2500"},
            {"S041", "Book AO", "5", "500"},
            {"S042", "Book AP", "10", "1000"},
            {"S043", "Book AQ", "15", "1500"},
            {"S044", "Book AR", "20", "2000"},
            {"S045", "Book AS", "25", "2500"}
        };
        paymentPanel.add(createTable(500, 20, tableContent_payment, nameField_book), BorderLayout.EAST);
        panel.add(paymentPanel, BorderLayout.SOUTH);

    }

    private JPanel createTable(int width, int padding_top, String tableContent[][], String[] nameField) {
        JTable table = tool.createTable(tableContent, nameField);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(width, 300));

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(padding_top, 10, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createDetailPanel(int width, int padding_top, JTextField txt_array[], String txt_label[], ImageIcon img) {
        JPanel panelDetail = tool.createDetailPanel(txt_array, txt_label, img, width, 300, 2, 5, false);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(padding_top, 0, 0, 0));
        return wrappedPanel;
    }

    private JPanel createButtonPanel() {
        String[] buttonTexts = {"Thêm", "Xóa", "Thanh toán"};
        // // Khai báo và khởi tạo mảng JButton[]
        // buttons[0] = new JButton("Thêm");
        // buttons[1] = new JButton("Xóa");
        // buttons[2] = new JButton("Thanh toán");

        // // Gắn sự kiện cho các nút
        // buttons[0].addActionListener(e -> addPhieuNhap());
        // buttons[1].addActionListener(e -> deletePhieuNhap());
        // buttons[2].addActionListener(e -> thanhtoan());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttons,buttonTexts,new Color(0, 36, 107), Color.WHITE, "x"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 110, 25));
        return buttonPanel;
    }

    private JPanel createSearchPanel() {
        String[] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(Box.createHorizontalStrut(33));
        searchPanel.add(tool.createSearchTextField(300, 30, searchOptions));
        return searchPanel;
    }

    public JPanel getPanel() {
        return this.panel;
    } 
}
