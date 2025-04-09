package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SachGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail, panelImg, panel_detail;
    JTextField txt_name, txt_nxb, txt_author, txt_category, txt_quantity;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public SachGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        // JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // JTextField searchField = tool.createSearchTextField();
        // searchField.setPreferredSize(new Dimension(100, 30));
        // TODO: Design graphic

        // Fake data
        String tableContent[][] = {
            {"S001", "Book A", "Fiction", "10", "100", "A001", "NXB001"},
            {"S002", "Book B", "Non-Fiction", "15", "150", "A002", "NXB002"},
            {"S003", "Book C", "Science", "20", "200", "A003", "NXB003"},
            {"S004", "Book D", "History", "25", "250", "A004", "NXB004"},
            {"S005", "Book E", "Biography", "30", "300", "A005", "NXB005"},
            {"S006", "Book F", "Fantasy", "35", "350", "A006", "NXB006"},
            {"S007", "Book G", "Mystery", "40", "400", "A007", "NXB007"},
            {"S008", "Book H", "Romance", "45", "450", "A008", "NXB008"},
            {"S009", "Book I", "Thriller", "50", "500", "A009", "NXB009"},
            {"S010", "Book J", "Adventure", "55", "550", "A010", "NXB010"},
            {"S011", "Book K", "Fiction", "60", "600", "A011", "NXB011"},
            {"S012", "Book L", "Non-Fiction", "65", "650", "A012", "NXB012"},
            {"S013", "Book M", "Science", "70", "700", "A013", "NXB013"},
            {"S014", "Book N", "History", "75", "750", "A014", "NXB014"},
            {"S015", "Book O", "Biography", "80", "800", "A015", "NXB015"},
            {"S016", "Book P", "Fantasy", "85", "850", "A016", "NXB016"},
            {"S017", "Book Q", "Mystery", "90", "900", "A017", "NXB017"},
            {"S018", "Book R", "Romance", "95", "950", "A018", "NXB018"},
            {"S019", "Book S", "Thriller", "100", "1000", "A019", "NXB019"},
            {"S020", "Book T", "Adventure", "105", "1050", "A020", "NXB020"},
            {"S021", "Book U", "Fiction", "110", "1100", "A021", "NXB021"},
            {"S022", "Book V", "Non-Fiction", "115", "1150", "A022", "NXB022"},
            {"S023", "Book W", "Science", "120", "1200", "A023", "NXB023"},
            {"S024", "Book X", "History", "125", "1250", "A024", "NXB024"},
            {"S025", "Book Y", "Biography", "130", "1300", "A025", "NXB025"},
            {"S026", "Book Z", "Fantasy", "135", "1350", "A026", "NXB026"},
            {"S027", "Book AA", "Mystery", "140", "1400", "A027", "NXB027"},
            {"S028", "Book AB", "Romance", "145", "1450", "A028", "NXB028"},
            {"S029", "Book AC", "Thriller", "150", "1500", "A029", "NXB029"},
            {"S030", "Book AD", "Adventure", "155", "1550", "A030", "NXB030"},
            {"S031", "Book AE", "Fiction", "160", "1600", "A031", "NXB031"},
            {"S032", "Book AF", "Non-Fiction", "165", "1650", "A032", "NXB032"},
            {"S033", "Book AG", "Science", "170", "1700", "A033", "NXB033"},
            {"S034", "Book AH", "History", "175", "1750", "A034", "NXB034"},
            {"S035", "Book AI", "Biography", "180", "1800", "A035", "NXB035"},
            {"S036", "Book AJ", "Fantasy", "185", "1850", "A036", "NXB036"},
            {"S037", "Book AK", "Mystery", "190", "1900", "A037", "NXB037"},
            {"S038", "Book AL", "Romance", "195", "1950", "A038", "NXB038"},
            {"S039", "Book AM", "Thriller", "200", "2000", "A039", "NXB039"},
            {"S040", "Book AN", "Adventure", "205", "2050", "A040", "NXB040"}
        
        };
        String column[] = {"Mã sách", "Tên sách", "Thể loại", "Số lượng", "Đơn giá", "Mã tác giả", "Mã NXB"};

        // Bảng
        JTable table = tool.createTable(tableContent, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 340));

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50, 40, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        panel.add(panelTable, BorderLayout.WEST);

        // Panel chứa button
        String [] btn_txt = {"Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonHorizontal(btn_txt, new Color(21, 96, 130), Color.WHITE));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        panel.add(panelBtn, BorderLayout.CENTER);

        // Chi tiết sản phẩm
        panelDetail = tool.createPanel(850, 300, new GridBagLayout());
        panel_detail = new JPanel(new GridBagLayout());

        // Image
        JLabel label_img = new JLabel();
        ImageIcon img = new ImageIcon("images/Book/the_little_prince.jpeg");
        label_img.setIcon(img);

        panelImg = new JPanel();
        panelImg.add(label_img);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        // add image inside panelImg
        panelDetail.add(panelImg, c);

        String txt_label[] = {"Tên", "Nhà xuất bản", "Tác giả", "Thể loại", "Số lượng"};
        
        txt_name = new JTextField();
        txt_nxb = new JTextField();
        txt_author = new JTextField();
        txt_category = new JTextField();
        txt_quantity = new JTextField();
        
        JTextField txt_array[] = {txt_name, txt_nxb, txt_author, txt_category, txt_quantity};

        for (int i = 0; i < txt_array.length; i++) {
            // txt_array[i] = new JTextField();
            txt_array[i].setPreferredSize(new Dimension(182, 30));
            txt_array[i].setEditable(false);
        }

        for (int i = 0; i < 3; i++) {
            c.gridy += 1;
            JLabel label = new JLabel(txt_label[i]);
            panel_detail.add(label, c);
            c.gridy += 1;
            panel_detail.add(txt_array[i], c);
        }

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 0;
        panelDetail.add(panel_detail, c);
        c.fill = GridBagConstraints.HORIZONTAL;

        panel_detail = new JPanel(new GridBagLayout());

        for (int i = 3; i < txt_array.length; i++) {
            c.gridy += 1;
            JLabel label = new JLabel(txt_label[i]);
            panel_detail.add(label, c);
            c.gridy += 1;
            panel_detail.add(txt_array[i], c);
        }


        c.fill = GridBagConstraints.VERTICAL;
        c.gridy = 0;
        c.gridx = 2;
        panelDetail.add(panel_detail, c);
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        panel.add(wrappedPanel, BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        String [] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        panel.add(panelSearch, BorderLayout.NORTH);
    }
    
    
    public JPanel getPanel() {
        return this.panel;
    } 
}
