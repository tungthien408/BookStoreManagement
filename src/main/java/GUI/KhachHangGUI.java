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

public class KhachHangGUI {
    Tool tool = new Tool();
    JPanel panel, panelDetail;
    JTextField txt_customerPhone, txt_name, txt_score;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public KhachHangGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.setBackground(new Color(202, 220, 252));

        panel.add(createKhachHangTable(), BorderLayout.WEST);

        // Panel chứa button
        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Chi tiết sản phẩm

        JTextField txt_array[] = {txt_customerPhone, txt_name, txt_score};
        String txt_label[] = {"Số điện thoại", "Tên", "Điểm tích lũy"};
        panel.add(createDetailPanel(txt_array, txt_label), BorderLayout.SOUTH);
      
        // Tạo thanh tìm kiếm 
        panel.add(createSearchPanel(), BorderLayout.NORTH);
    }
    
    private JPanel createKhachHangTable() {
        // Fake data
        String tableContent[][] = {
        };
        String column[] = {"Số điện thoại", "Tên", "Điểm tích lũy"};

        // Bảng
        JTable table = tool.createTable(tableContent, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 340));

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50, 40, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String [] btn_txt = {"Thêm", "Sửa", "Xóa", "Nhập Excel", "Xuất Excel"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonHorizontal(btn_txt, new Color(0, 36, 107), Color.WHITE,"y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        return panelBtn;
    }

    private JPanel createDetailPanel(JTextField[] txt_array, String txt_label[]) {
        panelDetail = tool.createDetailPanel(txt_array, txt_label, null, 850,300,0.5, 3);

        JPanel wrappedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrappedPanel.add(panelDetail);
        wrappedPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 70, 0));
        return wrappedPanel;
    }

    private JPanel createSearchPanel() {
        String [] searchOptions = {"Mã nhân viên", "Tên nhân viên", "Chức vụ"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(25));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        return panelSearch;
    }
    
    public JPanel getPanel() {
        return this.panel;
    } 
}
