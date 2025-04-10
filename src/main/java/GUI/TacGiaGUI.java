package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TacGiaGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    JTextField txt_authorId, txt_name, txt_address, txt_phone;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public TacGiaGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        // JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // JTextField searchField = tool.createSearchTextField();
        // searchField.setPreferredSize(new Dimension(100, 30));
        // TODO: Design graphic

        // Fake data
        String tableContent[][] = {
            {"TG001", "Nguyễn Nhật Ánh", "Hồ Chí Minh", "0901234567"},
            {"TG002", "Nguyễn Huy Thiệp", "Hà Nội", "0912345678"},
            {"TG003", "Tô Hoài", "Hà Nội", "0923456789"},
            {"TG004", "Nguyễn Du", "Hà Tĩnh", "0934567890"},
            {"TG005", "Xuân Diệu", "Bình Định", "0945678901"},
            {"TG006", "Hàn Mặc Tử", "Quảng Bình", "0956789012"},
            {"TG007", "Nam Cao", "Hà Nam", "0967890123"},
            {"TG008", "Nguyễn Trãi", "Hải Dương", "0978901234"},
            {"TG009", "Nguyễn Đình Chiểu", "Bến Tre", "0989012345"},
            {"TG010", "Phạm Văn Đồng", "Quảng Ngãi", "0990123456"}
        };
        String column[] = {"Mã tác giả", "Tên tác giả", "Địa chỉ", "Số điện thoại"};

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
        panelBtn.add(tool.createButtonHorizontal(btn_txt, new Color(0, 36, 107), Color.WHITE,"y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        panel.add(panelBtn, BorderLayout.CENTER);

        // Chi tiết sản phẩm

        JTextField txt_array[] = {txt_authorId, txt_name, txt_address, txt_phone};
        String txt_label[] = {"Mã tác giả", "Tên tác giả", "Địa chỉ", "Số điện thoại"};
        panelDetail = tool.createDetailPanel(txt_array, txt_label,850,300,0.5, 4);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        panel.add(wrappedPanel, BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        String [] searchOptions = {"Mã tác giả", "Tên tác giả", "Số điện thoại"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        panel.add(panelSearch, BorderLayout.NORTH);
    }
    
    
    public JPanel getPanel() {
        return this.panel;
    } 
}
