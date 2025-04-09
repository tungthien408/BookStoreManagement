package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HoaDonBanGUI {
    Tool tool = new Tool();
    JPanel panel;
    int width = 1200;
    int width_sideMenu = 151;
    int height = (int)(width * 0.625);

    public HoaDonBanGUI() {
        panel = tool.createPanel(width - width_sideMenu, height, new BorderLayout());
        // JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // JTextField searchField = tool.createSearchTextField();
        // searchField.setPreferredSize(new Dimension(100, 30));
        // TODO: Design graphic

        // Fake data
        String tableContent[][] = {
            {"HD001", "NV001", "0123456789", "2023-01-01", "500000"},
            {"HD002", "NV002", "0987654321", "2023-01-02", "450000"},
            {"HD003", "NV003", "0123456789", "2023-01-03", "600000"},
            {"HD004", "NV004", "0987654321", "2023-01-04", "550000"},
            {"HD005", "NV005", "0123456789", "2023-01-05", "700000"},
            {"HD006", "NV006", "0987654321", "2023-01-06", "650000"},
            {"HD007", "NV007", "0123456789", "2023-01-07", "800000"},
            {"HD008", "NV008", "0987654321", "2023-01-08", "750000"},
            {"HD009", "NV009", "0123456789", "2023-01-09", "900000"},
            {"HD010", "NV010", "0987654321", "2023-01-10", "850000"},
            {"HD011", "NV011", "0123456789", "2023-01-11", "500000"},
            {"HD012", "NV012", "0987654321", "2023-01-12", "450000"},
            {"HD013", "NV013", "0123456789", "2023-01-13", "600000"},
            {"HD014", "NV014", "0987654321", "2023-01-14", "550000"},
            {"HD015", "NV015", "0123456789", "2023-01-15", "700000"},
            {"HD016", "NV016", "0987654321", "2023-01-16", "650000"},
            {"HD017", "NV017", "0123456789", "2023-01-17", "800000"},
            {"HD018", "NV018", "0987654321", "2023-01-18", "750000"},
            {"HD019", "NV019", "0123456789", "2023-01-19", "900000"},
            {"HD020", "NV020", "0987654321", "2023-01-20", "850000"},
            {"HD021", "NV021", "0123456789", "2023-01-21", "500000"},
            {"HD022", "NV022", "0987654321", "2023-01-22", "450000"},
            {"HD023", "NV023", "0123456789", "2023-01-23", "600000"},
            {"HD024", "NV024", "0987654321", "2023-01-24", "550000"},
            {"HD025", "NV025", "0123456789", "2023-01-25", "700000"},
            {"HD026", "NV026", "0987654321", "2023-01-26", "650000"},
            {"HD027", "NV027", "0123456789", "2023-01-27", "800000"},
            {"HD028", "NV028", "0987654321", "2023-01-28", "750000"},
            {"HD029", "NV029", "0123456789", "2023-01-29", "900000"},
            {"HD030", "NV030", "0987654321", "2023-01-30", "850000"},
            {"HD031", "NV031", "0123456789", "2023-01-31", "500000"},
            {"HD032", "NV032", "0987654321", "2023-02-01", "450000"},
            {"HD033", "NV033", "0123456789", "2023-02-02", "600000"},
            {"HD034", "NV034", "0987654321", "2023-02-03", "550000"},
            {"HD035", "NV035", "0123456789", "2023-02-04", "700000"},
            {"HD036", "NV036", "0987654321", "2023-02-05", "650000"},
            {"HD037", "NV037", "0123456789", "2023-02-06", "800000"},
            {"HD038", "NV038", "0987654321", "2023-02-07", "750000"},
            {"HD039", "NV039", "0123456789", "2023-02-08", "900000"},
            {"HD040", "NV040", "0987654321", "2023-02-09", "850000"}
        };
        String column[] = {"Mã hóa đơn", "Mã nhân viên", "SĐT Khách", "Ngày bán", "Tổng tiền"};

        // Bảng
        JTable table = tool.createTable(tableContent, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(850, 640));

        // Tạo khoảng cách xung quanh bảng
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50, 40, 10, 10)); // Top, Left, Bottom, Right
        
        // Tạo panel FlowLayout để có thể tùy chỉnh kích cỡ bảng
        JPanel panelTable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable.add(scrollPane);
        panel.add(panelTable, BorderLayout.WEST);

        // Panel chứa button
        String [] btn_txt = {"Chi tiết", "Nhập Excel", "Xuất Excel"};
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(tool.createButtonHorizontal(btn_txt, new Color(21, 96, 130), Color.WHITE));
        panelBtn.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        panel.add(panelBtn, BorderLayout.CENTER);

      
        // Tạo thanh tìm kiếm: Tus lamf ddi
    }
    
    
    public JPanel getPanel() {
        return this.panel;
    }
}
