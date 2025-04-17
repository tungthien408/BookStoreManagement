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

public class SachGUI {
    Tool tool = new Tool();
    JButton btn[] = new JButton[6];
    JPanel panel, panelDetail;
    JTextField txt_array[] = new JTextField[5];
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public SachGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        String txt_label[] = {"Tên", "Nhà xuất bản", "Tác giả", "Thể loại", "Số lượng"};

        // Table 
        panel.add(createBookTable(txt_array), BorderLayout.WEST);

        // Panel chứa button
        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Chi tiết sản phẩm
        panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        panel.add(createSearchPanel(), BorderLayout.NORTH);
    }

    private JPanel createBookTable(JTextField txt_array[]) {
        // Fake data
        String tableContent[][] = {
            {"S001", "Book A", "Fiction", "10", "100", "A001"},
            {"S002", "Book B", "Non-Fiction", "15", "150", "A002"},
            {"S003", "Book C", "Science", "20", "200", "A003"},
            {"S004", "Book D", "History", "25", "250", "A004"},
            {"S005", "Book E", "Biography", "30", "300", "A005"},
            {"S006", "Book F", "Fantasy", "35", "350", "A006"},
            {"S007", "Book G", "Mystery", "40", "400", "A007"},
            {"S008", "Book H", "Romance", "45", "450", "A008"},
            {"S009", "Book I", "Thriller", "50", "500", "A009"},
            {"S010", "Book J", "Adventure", "55", "550", "A010"},
            {"S011", "Book K", "Fiction", "60", "600", "A011"},
            {"S012", "Book L", "Non-Fiction", "65", "650", "A012"},
            {"S013", "Book M", "Science", "70", "700", "A013"},
            {"S014", "Book N", "History", "75", "750", "A014"},
            {"S015", "Book O", "Biography", "80", "800", "A015"},
            {"S016", "Book P", "Fantasy", "85", "850", "A016"},
            {"S017", "Book Q", "Mystery", "90", "900", "A017"},
            {"S018", "Book R", "Romance", "95", "950", "A018"},
            {"S019", "Book S", "Thriller", "100", "1000", "A019"},
            {"S020", "Book T", "Adventure", "105", "1050", "A020"},
            {"S021", "Book U", "Fiction", "110", "1100", "A021"},
            {"S022", "Book V", "Non-Fiction", "115", "1150", "A022"},
            {"S023", "Book W", "Science", "120", "1200", "A023"},
            {"S024", "Book X", "History", "125", "1250", "A024"},
            {"S025", "Book Y", "Biography", "130", "1300", "A025"},
            {"S026", "Book Z", "Fantasy", "135", "1350", "A026"},
            {"S027", "Book AA", "Mystery", "140", "1400", "A027"},
            {"S028", "Book AB", "Romance", "145", "1450", "A028"},
            {"S029", "Book AC", "Thriller", "150", "1500", "A029"},
            {"S030", "Book AD", "Adventure", "155", "1550", "A030"},
            {"S031", "Book AE", "Fiction", "160", "1600", "A031"},
            {"S032", "Book AF", "Non-Fiction", "165", "1650", "A032"},
            {"S033", "Book AG", "Science", "170", "1700", "A033"},
            {"S034", "Book AH", "History", "175", "1750", "A034"},
            {"S035", "Book AI", "Biography", "180", "1800", "A035"},
            {"S036", "Book AJ", "Fantasy", "185", "1850", "A036"},
            {"S037", "Book AK", "Mystery", "190", "1900", "A037"},
            {"S038", "Book AL", "Romance", "195", "1950", "A038"},
            {"S039", "Book AM", "Thriller", "200", "2000", "A039"},
            {"S040", "Book AN", "Adventure", "205", "2050", "A040"}
        };
        String column[] = {"Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả"};

        // Bảng
        JTable table = tool.createTable(tableContent, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 310));

        // Hiển thị dữ liệu khi chọn một dòng trong bảng
        // __________________________ AI Code __________________________
        
            // Add a ListSelectionListener to the table
            table.getSelectionModel().addListSelectionListener(event -> {
                // Check if a row is selected
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Update the JTextFields with the selected row's data
                    for (int i = 0; i < txt_array.length; i++) {
                        Object value = table.getValueAt(selectedRow, i);
                        if (value != null) {
                            txt_array[i].setText(value.toString());
                        } else {
                            txt_array[i].setText(""); // Clear the field if the value is null
                        }
                    }
                }
            });

        // __________________________ AI Code __________________________
        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String [] btn_txt = {"Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel", "Thoát"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn, btn_txt, new Color(0, 36, 107), Color.WHITE,"y"));
        // panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return panelBtn;
    }
    
    private JPanel createDetailPanel(JTextField txt_array[], String txt_label[]) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, new ImageIcon("images/Book/the_little_prince.jpg"), 850,290, 0.5, 3, true);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return wrappedPanel;
    }
    
    private JPanel createSearchPanel() {
        String [] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        return panelSearch;
    }

    public JPanel getPanel() {
        return this.panel;
    } 
}
