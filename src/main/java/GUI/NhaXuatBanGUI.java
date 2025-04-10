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

public class NhaXuatBanGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    JTextField txt_nxbId, txt_name, txt_address, txt_phone;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public NhaXuatBanGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));
        // JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // JTextField searchField = tool.createSearchTextField();
        // searchField.setPreferredSize(new Dimension(100, 30));
        // TODO: Design graphic

        // Fake data
        String tableContent[][] = {
        {"NXB001", "Nhà Xuất Bản Trẻ", "123 Đường ABC, Quận 1, TP.HCM", "0123456789"},
        {"NXB002", "Nhà Xuất Bản Kim Đồng", "456 Đường DEF, Quận 3, TP.HCM", "0987654321"},
        {"NXB003", "Nhà Xuất Bản Giáo Dục", "789 Đường GHI, Quận 5, TP.HCM", "0112233445"},
        {"NXB004", "Nhà Xuất Bản Văn Học", "321 Đường JKL, Quận 7, TP.HCM", "0223344556"},
        {"NXB005", "Nhà Xuất Bản Khoa Học", "654 Đường MNO, Quận 9, TP.HCM", "0334455667"}
        };
        String column[] = {"Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại"};

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

        JTextField txt_array[] = {txt_nxbId, txt_name, txt_address, txt_phone};
        String txt_label[] = {"Mã NXB", "Tên NXB", "Địa chỉ", "Số điện thoại"};
        panelDetail = tool.createDetailPanel(txt_array, txt_label,850,300,0.5, 4);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        panel.add(wrappedPanel, BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        String [] searchOptions = {"Mã NXB", "Tên NXB"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        panel.add(panelSearch, BorderLayout.NORTH);
    }
    
    
    public JPanel getPanel() {
        return this.panel;
    } 
}
