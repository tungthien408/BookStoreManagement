package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class NhapSachGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);
    JTextField txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total, txt_name, txt_quantity;

    public NhapSachGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.add(createSearchPanel(), BorderLayout.NORTH); // search panel
        // fake data
        String tableContent_book[][] = {
            {"S001", "Book A", "Fiction", "10", "100"},
            {"S002", "Book B", "Non-Fiction", "15", "150"},
            {"S003", "Book C", "Science", "20", "200"},
            {"S004", "Book D", "History", "25", "250"},
            {"S005", "Book E", "Biography", "30", "300"},
            {"S006", "Book F", "Fantasy", "35", "350"},
            {"S007", "Book G", "Mystery", "40", "400"},
            {"S008", "Book H", "Romance", "45", "450"},
            {"S009", "Book I", "Thriller", "50", "500"},
            {"S010", "Book J", "Adventure", "55", "550"},
            {"S011", "Book K", "Fiction", "60", "600"},
            {"S012", "Book L", "Non-Fiction", "65", "650"},
            {"S013", "Book M", "Science", "70", "700"},
            {"S014", "Book N", "History", "75", "750"},
            {"S015", "Book O", "Biography", "80", "800"},
            {"S016", "Book P", "Fantasy", "85", "850"},
            {"S017", "Book Q", "Mystery", "90", "900"},
            {"S018", "Book R", "Romance", "95", "950"},
            {"S019", "Book S", "Thriller", "100", "1000"},
            {"S020", "Book T", "Adventure", "105", "1050"},
            {"S021", "Book U", "Fiction", "110", "1100"},
            {"S022", "Book V", "Non-Fiction", "115", "1150"},
            {"S023", "Book W", "Science", "120", "1200"},
            {"S024", "Book X", "History", "125", "1250"},
            {"S025", "Book Y", "Biography", "130", "1300"},
            {"S026", "Book Z", "Fantasy", "135", "1350"},
            {"S027", "Book AA", "Mystery", "140", "1400"},
            {"S028", "Book AB", "Romance", "145", "1450"},
            {"S029", "Book AC", "Thriller", "150", "1500"},
            {"S030", "Book AD", "Adventure", "155", "1550"},
            {"S031", "Book AE", "Fiction", "160", "1600"},
            {"S032", "Book AF", "Non-Fiction", "165", "1650"},
            {"S033", "Book AG", "Science", "170", "1700"},
            {"S034", "Book AH", "History", "175", "1750"},
            {"S035", "Book AI", "Biography", "180", "1800"},
            {"S036", "Book AJ", "Fantasy", "185", "1850"},
            {"S037", "Book AK", "Mystery", "190", "1900"},
            {"S038", "Book AL", "Romance", "195", "1950"},
            {"S039", "Book AM", "Thriller", "200", "2000"},
            {"S040", "Book AN", "Adventure", "205", "2050"}      
        };
        String nameField_book[] = {"Mã sách", "Tên sách", "Số lượng", "Đơn giá"};
        
        // table
        panel.add(createTable(650, 10,tableContent_book, nameField_book), BorderLayout.WEST);

        // detail (top right)
        JTextField txt_array_top[] = {txt_importId, txt_employeeId, txt_nxb, txt_date, txt_total};
        String txt_label_top[] = {"Mã phiếu nhập", "Mã NV", "NXB", "Ngày nhập", "Tổng tiền"};
        panel.add(createDetailPanel(400, 30, txt_array_top, txt_label_top, null), BorderLayout.CENTER);
        
        JPanel paymentPanel = tool.createPanel(width - width_sideMenu, (int)(height * 0.55), new BorderLayout());
        JTextField txt_array[] = {txt_name, txt_quantity};
        String[] txt_label = {"Tên sách", "Số lượng"};
        // detail (bottom right)
        paymentPanel.add(createDetailPanel(500, 10, txt_array, txt_label, new ImageIcon("images/Book/the_little_prince.jpg")), BorderLayout.WEST);
        
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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(tool.createButtonPanel(buttonTexts, new Color(0, 36, 107), Color.WHITE, "x"));
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
