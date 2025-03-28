package GUI;

import java.awt.*;
import javax.swing.*;

public class MenuGUI {
    String array_function[] = {"Bán sách", "Nhập sách", "Sách", "Nhà xuất bản", "Tác giả", "Hóa đơn nhập", "Hóa đơn bán", "Khuyến mãi", "Khách hàng", "Nhân viên", "Tài khoản", "Phân quyền"};
    public MenuGUI() {
        Tool tool = new Tool();
        int length = array_function.length;

        JFrame frame = tool.createFrame("Book Shop Management", 1600, 1000, null);
        JPanel mainPanel = tool.createPanel(1600, 1000, new BorderLayout());
        JPanel sideMenu = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        // sideMenu.setBackground(new Color(255, 0, 0));

        for (int i = 0; i < length; i++) {
            c.gridy = i;

            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(array_function[i], SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(label, BorderLayout.CENTER);
            panel.setBackground(new Color(21, 96, 130));
            sideMenu.add(panel, c);
        }

        mainPanel.add(sideMenu, BorderLayout.WEST);
        frame.add(mainPanel);


        frame.setVisible(true);
    }
}
