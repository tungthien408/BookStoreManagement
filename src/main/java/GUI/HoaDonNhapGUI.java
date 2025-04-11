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

public class HoaDonNhapGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public HoaDonNhapGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        panel.add(createHoaDonNhapTable(), BorderLayout.WEST);

        panel.add(createPanelButton(), BorderLayout.CENTER);

        // Tạo thanh tìm kiếm 
        panel.add(createSearchPanel(), BorderLayout.NORTH);
    }

    private JPanel createHoaDonNhapTable() {
        // Fake data
        String tableContent[][] = {
            {"PN001", "NV001", "2023-01-01", "500000", "NXB001"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN002", "NV002", "2023-01-02", "600000", "NXB002"},
            {"PN003", "NV003", "2023-01-03", "700000", "NXB003"},
            {"PN004", "NV004", "2023-01-04", "800000", "NXB004"},
            {"PN005", "NV005", "2023-01-05", "900000", "NXB005"},
            {"PN006", "NV006", "2023-01-06", "1000000", "NXB006"},
            {"PN007", "NV007", "2023-01-07", "1100000", "NXB007"},
            {"PN008", "NV008", "2023-01-08", "1200000", "NXB008"},
            {"PN009", "NV009", "2023-01-09", "1300000", "NXB009"},
            {"PN010", "NV010", "2023-01-10", "1400000", "NXB010"},
            {"PN011", "NV011", "2023-01-11", "1500000", "NXB011"},
            {"PN012", "NV012", "2023-01-12", "1600000", "NXB012"},
            {"PN013", "NV013", "2023-01-13", "1700000", "NXB013"},
            {"PN014", "NV014", "2023-01-14", "1800000", "NXB014"},
            {"PN015", "NV015", "2023-01-15", "1900000", "NXB015"},
            {"PN016", "NV016", "2023-01-16", "2000000", "NXB016"},
            {"PN017", "NV017", "2023-01-17", "2100000", "NXB017"},
            {"PN018", "NV018", "2023-01-18", "2200000", "NXB018"},
            {"PN019", "NV019", "2023-01-19", "2300000", "NXB019"},
            {"PN020", "NV020", "2023-01-20", "2400000", "NXB020"}
        };
        String column[] = {"Mã phiếu nhập", "Mã nhân viên", "Ngày nhập", "Tổng tiền", "Mã NXB"};

        // Bảng
        JTable table = tool.createTable(tableContent, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 640));

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50, 40, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        return panelTable;
    }

    private JPanel createPanelButton() {
        String [] btn_txt = {"Chi tiết", "Nhập Excel", "Xuất Excel"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonPanel(btn_txt, new Color(0, 36, 107),  Color.WHITE,"y"));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        return panelBtn;
    }

    private JPanel createSearchPanel() {
        String [] searchOptions = {"Mã nhân viên", "Mã khách hàng"};
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(Box.createHorizontalStrut(33));
        panelSearch.add(tool.createSearchTextField(0, 0,searchOptions));
        return panelSearch;
    }
    
    
    public JPanel getPanel() {
        return this.panel;
    } 
}
