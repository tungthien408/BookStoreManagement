package GUI;

import java.awt.*;
import javax.swing.*;

public class HoaDonNhapGUI {
    Tool tool = new Tool();
    JPanel panel;
    public HoaDonNhapGUI() {
        panel = tool.createPanel(1600, 0, new GridBagLayout());
        // TODO: Design graphic
        // panel.setBackground(new Color(46, 204, 113));
        String tableContent[][] = {
            {"PN001", "NV001", "2023-01-01", "500000", "NXB001"},
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
        JTable table = tool.createTable(tableContent, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        scrollPane.setLocation(50, 50);
        panel.add(scrollPane);
    }
    public JPanel getPanel() {
        return this.panel;
    } 
}
